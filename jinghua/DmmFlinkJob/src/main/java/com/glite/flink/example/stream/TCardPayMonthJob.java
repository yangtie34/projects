package com.glite.flink.example.stream;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.annotation.Nullable;

import org.apache.flink.annotation.Public;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.api.java.tuple.Tuple8;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer09;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;
import org.apache.flink.util.Collector;

import com.alibaba.fastjson.JSONObject;
import com.glite.flink.example.domain.TCardPay;
import com.glite.flink.example.domain.TCardPayDayHour;
import com.glite.flink.utils.PropertieUtils;

public class TCardPayMonthJob {
	static String topic = "T_CARD_PAY";
	static String groupId = "TCardPayMonthJob";
	static String monthPayPre = "CardPayMonth_";
	static String monthPayDayPre = "CardPayMonthDay_";
	static String monthPayHoutPre = "CardPayMonthHour_";
	static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@SuppressWarnings("serial")
	public static void main(String[] args) {
		try {
			// 获取执行环境，这里根据实际的环境自动设置，在本地eclipse中运行，是本地模式，集群上是集群模式环境
			StreamExecutionEnvironment env = StreamExecutionEnvironment
					.getExecutionEnvironment();
			// 设置事件类型是eventtime类型
			env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
			// 禁用输出打印执行进度的日志
			env.getConfig().disableSysoutLogging();
			// 设置用于恢复的重新启动策略
			env.getConfig().setRestartStrategy(
					RestartStrategies.fixedDelayRestart(4, 10000));
			// 设置检查点间隔时间，每隔多长时间执行一次快照
			env.enableCheckpointing(5000); //
			// 加载kafka配置属性
			Properties props = PropertieUtils
					.loadProperties("/kafka.properties");
			// 设置消费topic的分组
			if (groupId != null) {
				props.put("group.id", groupId);
			}
			// 设置消费kafka的数据为数据流的源
			DataStream<String> messageStream = env
					.addSource(new FlinkKafkaConsumer09<>(topic,
							new SimpleStringSchema(), props));

			// 执行转换，将每条数据，转换成TCardPay对象，生成TCardPay的流
			DataStream<TCardPay> payStream = messageStream
					.flatMap(new FlatMapFunction<String, TCardPay>() {

						@Override
						public void flatMap(String value,
								Collector<TCardPay> out) throws Exception {
							out.collect(new TCardPay(value));
						}
					});
			// 设置数据流的时间戳和水平线
			DataStream<TCardPay> WateredPayStream = payStream
					/*
					 * 以TCardPay的time_为时间戳，并产生水印标记。
					 * 产生水印标记的目的，是为后面的window时间窗口分组使用；
					 * 由于数据流是无界的，永远不会结束，所有我们需要对数据每隔一段时间，处理一次。时间窗口就是将数据依照一段一段的时间将数据分组
					 * 由于每条数据，因为各种原因，不一定是按照顺序进入流计算，如先产生的数据后进入数据流，水印线，可以设置一个延时时间，以保证乱序的数据
					 * 被分配的合适的窗口中
					 */
					.assignTimestampsAndWatermarks(new HourPeriodTimestampAndWartermarker());
			
			WindowedStream windowedStream = WateredPayStream
					/*
					 * 类型sql中的groupby
					 */
					.keyBy("cardId","cardPortId", "cardDealId")
					/*
					 * 设置一个小时的时间窗口。这里产生的时间窗口，是自然时间窗口，系统自动生成，
					 * 比如当前设置的，只会生成类似
					 * (2016-10-01 11:00:00 2016-10-01 12:00:00 )
					 * (2016-10-01 12:00:00 2016-10-01 13:00:00 )
					 * 
					 * 如果设置是5秒钟，则
					 * （2016-10-01 11:00:00     2016-10-01 11:00:05）
					 * （2016-10-01 11:00:05     2016-10-01 11:00:10）
					 */
					.timeWindow(Time.seconds(3600));
			/*
			 * 对时间窗口数据流应用一些处理
			 */
			windowedStream.apply(new HandlePayWindow())
				/*
				 * 数据下沉，也就是，保存数据或者输出数据
				 * 可以保存到hdfs上，也可以保存到hbase，oracle等
				 */
				.addSink(new SinkFunction<TCardPayDayHour>() {
					@Override
					public void invoke(TCardPayDayHour value) throws Exception {
						System.out.println(value);
					}
			});

			env.execute("Read from Kafka example");
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	public static class HourPeriodTimestampAndWartermarker implements
			AssignerWithPeriodicWatermarks<TCardPay> {
		Long maxTimeStamp = 0L;
		Long maxOutOfOrderness = 300000L;
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		@Override
		public long extractTimestamp(TCardPay element,
				long previousElementTimestamp) {
			Long time = 0L;
			try {
				time = f.parse(element.getTime_()).getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			maxTimeStamp = Math.max(maxTimeStamp, time);
			return time;
		}

		@Override
		@Nullable
		public Watermark getCurrentWatermark() {
			return new Watermark(maxTimeStamp - maxOutOfOrderness);
		}

	}

	public static class HourTimestampAndWartermarker implements
			AssignerWithPunctuatedWatermarks<TCardPay> {
		Long maxTimeStamp = 0L;
		Long maxOutOfOrderness = 3600000L;
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		/**
		 * 获取元素当前的时间
		 */
		@Override
		public long extractTimestamp(TCardPay element,
				long previousElementTimestamp) {
			Long time = 0L;
			try {
				time = f.parse(element.getTime_()).getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			maxTimeStamp = Math.max(maxTimeStamp, time);
			return time;
		}

		@Override
		@Nullable
		public Watermark checkAndGetNextWatermark(TCardPay lastElement,
				long extractedTimestamp) {
			Long time = 0L;
			try {
				time = f.parse(lastElement.getTime_()).getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return new Watermark(maxTimeStamp - maxOutOfOrderness);
		}

	}

	public static class HandlePayWindow
			implements
			WindowFunction<TCardPay, TCardPayDayHour, Tuple3<String, String, String>, TimeWindow> {
		@Override
		public void apply(
				Tuple3<String, String, String> key,
				TimeWindow window,
				Iterable<TCardPay> input,
				Collector<TCardPayDayHour> out)
				throws Exception {
			String startDate = format.format(new Date(window.getStart()));
			String endDate = format.format(new Date(window.getEnd()));
			String year=startDate.substring(0,4);
			String month=startDate.substring(6,2);
			String day=startDate.substring(9,2);
			String start=startDate.substring(12,8);
			String end=endDate.substring(12,8);
			Float money = 0F;
			Long counts = 0L;
			for (TCardPay pay : input) {
				counts++;
				money += pay.getPayMoney();
			}
			out.collect(new TCardPayDayHour(key.f0, key.f1, key.f2, year, month, day, start, end, counts, money));
		}

	}

	public static class HandleWindowFunction
			implements
			WindowFunction<Tuple8<Long, String, Float, Float, String, String, String, String>, Tuple5<String, Float, Integer, String, String>, String, TimeWindow> {

		@Override
		public void apply(
				String key,
				TimeWindow window,
				Iterable<Tuple8<Long, String, Float, Float, String, String, String, String>> input,
				Collector<Tuple5<String, Float, Integer, String, String>> out)
				throws Exception {
			String start = format.format(new Date(window.getStart()));
			String end = format.format(new Date(window.getEnd()));
			Float money = 0F;
			Integer counts = 0;
			for (Tuple8<Long, String, Float, Float, String, String, String, String> t8 : input) {
				counts++;
				money += t8.f2;
			}
			out.collect(new Tuple5<String, Float, Integer, String, String>(key,
					money, counts, start, end));

		}

	}
}
