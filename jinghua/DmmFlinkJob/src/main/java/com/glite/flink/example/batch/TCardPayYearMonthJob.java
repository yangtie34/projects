package com.glite.flink.example.batch;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.flink.api.common.functions.GroupCombineFunction;
import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.io.OutputFormat;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.io.TextInputFormat;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.fs.Path;
import org.apache.flink.util.Collector;

import com.glite.flink.example.domain.TCardPay;

/**
 * 读取一个月的学生消费数据
 * 
 * @author chenlj
 * 
 */
public class TCardPayYearMonthJob {

	public static void main(String[] args) throws Throwable {
		ParameterTool params = ParameterTool.fromArgs(args);
		Calendar cal=Calendar.getInstance();
		int m=cal.get(Calendar.MONTH);
		String year=params.get("year")!=null?params.get("year"):cal.get(Calendar.YEAR)+"";
		String month=params.get("month")!=null?params.get("month"):(m>9?m+"":"0"+m);
//		String path="D:/cardpay/"+"year="+year+"/month="+month+"/";
		String path="hdfs://ns1/cardpay/"+"year="+year+"/month="+month+"/";
		final ExecutionEnvironment env = ExecutionEnvironment
				.getExecutionEnvironment();
		
		//如果在本地eclipse中运行，这里可以改为本地的目录，如D:/cardpar/
		TextInputFormat format=new TextInputFormat(new Path(path));
		format.setNestedFileEnumeration(true);
		DataSource<String> paySource = env.readFile(format, path);
//				.readTextFile("D:/cardpay/"+path);// 读取2016-10目录下的所有文件
		/*
		 * 执行转换，将每行数据，转为TCardPay对象
		 */
		DataSet<TCardPay> paySet = paySource
				.map(new MapFunction<String, TCardPay>() {
					@Override
					public TCardPay map(String value) throws Exception {
						return new TCardPay(value);
					}
				});
		/*
		 * 这里根据cardId，cardPortId，yearMonth分组，也可以加上hour分组
		 */
		DataSet<Tuple5<String, String, String, Float, Long>> t5DataSet=paySet.groupBy(new KeySelector<TCardPay, Tuple3<String,String,String>>() {
			@Override
			public Tuple3<String,String,String> getKey(TCardPay value)
					throws Exception {
				//返回年月日
				return new Tuple3<String, String, String>(value.getCardId(), value.getCardPortId(), value.getTime_().substring(0,10));
			}
		}).combineGroup(new GroupCombineFunction<TCardPay, Tuple5<String,String,String,Float,Long>>() {
			/*
			 * 这里假设数据量非常大，用局部分组计数，具体应用是，根据数据量情况定。
			 * 数据量小的话，可以直接使用reduce或reducdgroup
			 */
			@Override
			public void combine(Iterable<TCardPay> values,
					Collector<Tuple5<String, String, String, Float, Long>> out)
					throws Exception {
				String cardId="";
				String cardPortId="";
				String yearMonth="";
				float payMoney=0;
				long counts=0;
				for(TCardPay pay:values){
					cardId=pay.getCardId();
					cardPortId=pay.getCardPortId();
					yearMonth=pay.getTime_().substring(0,10);
					payMoney+=pay.getPayMoney();
					counts++;
				}
				out.collect(new Tuple5<String, String, String, Float, Long>(cardId, cardPortId, yearMonth, payMoney, counts));
				
			}
		})
		.groupBy(0,1,2).reduce(new ReduceFunction<Tuple5<String,String,String,Float,Long>>() {
			
			@Override
			public Tuple5<String, String, String, Float, Long> reduce(
					Tuple5<String, String, String, Float, Long> value1,
					Tuple5<String, String, String, Float, Long> value2)
					throws Exception {
				// TODO Auto-generated method stub
				return new Tuple5<String, String, String, Float, Long>(value1.f0, value1.f1, value1.f2, value1.f3+value2.f3, value1.f4+value2.f4);
			}
		})
		;
		/*
		 * 到此，t5DataSet中的数据是 cardId，cardPortId，yearMonth，payMoney，counts
		 */
		
		t5DataSet.writeAsText("hdfs://ns1/cardpay/statistic/"+"year="+year+"/month="+month+"/");
		env.execute("开始执行job");
	}

}
