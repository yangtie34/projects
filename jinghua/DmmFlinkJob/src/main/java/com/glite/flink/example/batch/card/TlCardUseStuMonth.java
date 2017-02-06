package com.glite.flink.example.batch.card;

import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.FlatJoinFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.TextInputFormat;
import org.apache.flink.api.java.tuple.Tuple10;
import org.apache.flink.api.java.tuple.Tuple11;
import org.apache.flink.api.java.tuple.Tuple12;
import org.apache.flink.api.java.tuple.Tuple16;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.api.java.tuple.Tuple7;
import org.apache.flink.api.java.tuple.Tuple8;
import org.apache.flink.api.java.tuple.Tuple9;
import org.apache.flink.core.fs.Path;
import org.apache.flink.core.fs.FileSystem.WriteMode;
import org.apache.flink.util.Collector;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glite.flink.example.domain.TCardUse;
import com.glite.flink.utils.RedisClientUtils;

/**
 * @作者:baihuayang
 *
 * @创建时间:2016年12月27日 上午11:34:57
 *
 * @描述:一卡通使用情况数据计算
 * 
 */
public class TlCardUseStuMonth {

	private static String hdfsPath = "hdfs://centos02:9000";

	private static String inputPath = "/cardpay/year=2016";
	private static String outputPath = "/output/cardUse";

	public static void main(String[] args) {
		try {
			CardUse(inputPath, outputPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private static void CardUse(String inputPath, String outputPath) throws Exception {

		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
		// 学生数据集
		// 输出列：NO_,NAME_,SEX_CODE,DEPT_ID,MAJOR_ID,EDU_ID,CLASS_ID
		DataSet<Tuple7<String, String, String, String, String, String, String>> stuDataSet = env.readTextFile(hdfsPath + "/stu").flatMap(new FlatMapFunction<String, Tuple7<String, String, String, String, String, String, String>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void flatMap(String value, Collector<Tuple7<String, String, String, String, String, String, String>> out) throws Exception {
				String[] f = value.replaceAll("'", "").split(",");
				out.collect(new Tuple7<String, String, String, String, String, String, String>(f[1], f[3], f[8], f[13], f[14], f[18], f[15]));
			}
		});

		// 输出列：TEA_NO, NAME_,SEX_CODE,DEPT_ID,MAJOR_ID,EDU_ID,CLASS_ID 其中，MAJOR_ID和CLASS_ID为空。
		DataSet<Tuple7<String, String, String, String, String, String, String>> teaDataSet = env.fromCollection(RedisClientUtils.getAllMap("T_TEA").values()).flatMap(new FlatMapFunction<String, Tuple7<String, String, String, String, String, String, String>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void flatMap(String value, Collector<Tuple7<String, String, String, String, String, String, String>> out) throws Exception {
				JSONObject json = JSON.parseObject(value);
				out.collect(new Tuple7<String, String, String, String, String, String, String>(json.getString("tea_no"), json.getString("name_"), json.getString("sex_code"), json.getString("dept_id"), "", json.getString("edu_id"), ""));
			}
		});
		// 合并学生和老师信息集合
		DataSet<Tuple7<String, String, String, String, String, String, String>> stuTeaDataSet = stuDataSet.union(teaDataSet);
		// 获取学生性别信息
		DataSet<Tuple2<String, String>> sexCodeDataSet = env.fromCollection(RedisClientUtils.getAllMap("T_CODE").values()).flatMap(new FlatMapFunction<String, Tuple2<String, String>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void flatMap(String value, Collector<Tuple2<String, String>> out) throws Exception {
				JSONObject json = JSON.parseObject(value);
				if ("SEX_CODE".equals(json.getString("code_type"))) {
					out.collect(new Tuple2<String, String>(json.getString("code_"), json.getString("name_")));
				}
			}
		});
		/**
		 * 合并了性别名称 输出列：NO_,NAME_,SEX_CODE,SEX_NAME,DEPT_ID,MAJOR_ID,EDU_ID,CLASS_ID
		 */
		DataSet<Tuple8<String, String, String, String, String, String, String, String>> stuSexDataSet = stuTeaDataSet.leftOuterJoin(sexCodeDataSet).where(2).equalTo(0).with(new FlatJoinFunction<Tuple7<String, String, String, String, String, String, String>, Tuple2<String, String>, Tuple8<String, String, String, String, String, String, String, String>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void join(Tuple7<String, String, String, String, String, String, String> first, Tuple2<String, String> second, Collector<Tuple8<String, String, String, String, String, String, String, String>> out) throws Exception {
				out.collect(new Tuple8<String, String, String, String, String, String, String, String>(first.f0, first.f1, first.f2, (second != null) ? second.f1 : null, first.f3, first.f4, first.f5, first.f6));
			}
		});
		// 获取组织机构
		DataSet<Tuple2<String, String>> codeDeptDataSet = env.fromCollection(RedisClientUtils.getAllMap("T_CODE_DEPT").values()).flatMap(new FlatMapFunction<String, Tuple2<String, String>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void flatMap(String value, Collector<Tuple2<String, String>> out) throws Exception {
				JSONObject json = JSON.parseObject(value);
				out.collect(new Tuple2<String, String>(json.getString("code_"), json.getString("name_")));
			}
		});
		// 输出列：NO_,NAME_,SEX_CODE,SEX_NAME,DEPT_ID,DEPT_NAME,MAJOR_ID,EDU_ID,CLASS_ID
		DataSet<Tuple9<String, String, String, String, String, String, String, String, String>> stuSexDeptDataSet = stuSexDataSet.leftOuterJoin(codeDeptDataSet).where(4).equalTo(0).with(new FlatJoinFunction<Tuple8<String, String, String, String, String, String, String, String>, Tuple2<String, String>, Tuple9<String, String, String, String, String, String, String, String, String>>() {
			private static final long serialVersionUID = 1L;

			public void join(Tuple8<String, String, String, String, String, String, String, String> first, Tuple2<String, String> second, Collector<Tuple9<String, String, String, String, String, String, String, String, String>> out) throws Exception {
				out.collect(new Tuple9<String, String, String, String, String, String, String, String, String>(first.f0, first.f1, first.f2, first.f3, first.f4, ((second != null) ? second.f1 : null), first.f5, first.f6, first.f7));
			}
		});

		// 获取专业信息
		DataSet<Tuple2<String, String>> codeDeptTeachDataSet = env.fromCollection(RedisClientUtils.getAllMap("T_CODE_DEPT_TEACH").values()).flatMap(new FlatMapFunction<String, Tuple2<String, String>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void flatMap(String value, Collector<Tuple2<String, String>> out) throws Exception {
				JSONObject json = JSON.parseObject(value);
				out.collect(new Tuple2<String, String>(json.getString("code_"), json.getString("name_")));
			}
		});
		// 输出列：NO_,NAME_,SEX_CODE,SEX_NAME,DEPT_ID,DEPT_NAME,MAJOR_ID MAJOR_NAME,EDU_ID,CLASS_ID
		DataSet<Tuple10<String, String, String, String, String, String, String, String, String, String>> stuSexDeptTeachDataSet = stuSexDeptDataSet.leftOuterJoin(codeDeptTeachDataSet).where(6).equalTo(0).with(new FlatJoinFunction<Tuple9<String, String, String, String, String, String, String, String, String>, Tuple2<String, String>, Tuple10<String, String, String, String, String, String, String, String, String, String>>() {
			private static final long serialVersionUID = 1L;

			public void join(Tuple9<String, String, String, String, String, String, String, String, String> first, Tuple2<String, String> second, Collector<Tuple10<String, String, String, String, String, String, String, String, String, String>> out) throws Exception {
				out.collect(new Tuple10<String, String, String, String, String, String, String, String, String, String>(first.f0, first.f1, first.f2, first.f3, first.f4, first.f5, first.f6, (second == null) ? null : second.f1, first.f7, first.f8));
			}
		});

		// 获取学历信息
		DataSet<Tuple2<String, String>> codeEduDataSet = env.fromCollection(RedisClientUtils.getAllMap("T_CODE_EDUCATION").values()).flatMap(new FlatMapFunction<String, Tuple2<String, String>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void flatMap(String value, Collector<Tuple2<String, String>> out) throws Exception {
				JSONObject json = JSON.parseObject(value);
				out.collect(new Tuple2<String, String>(json.getString("code_"), json.getString("name_")));
			}
		});
		// 输出列：NO_,NAME_,SEX_CODE,SEX_NAME,DEPT_ID,DEPT_NAME,MAJOR_ID MAJOR_NAME,EDU_ID,EDU_NAME,CLASS_ID
		DataSet<Tuple11<String, String, String, String, String, String, String, String, String, String, String>> stuSexDeptTeachEduDataSet = stuSexDeptTeachDataSet.leftOuterJoin(codeEduDataSet).where(8).equalTo(0).with(new FlatJoinFunction<Tuple10<String, String, String, String, String, String, String, String, String, String>, Tuple2<String, String>, Tuple11<String, String, String, String, String, String, String, String, String, String, String>>() {
			private static final long serialVersionUID = 1L;

			public void join(Tuple10<String, String, String, String, String, String, String, String, String, String> first, Tuple2<String, String> second, Collector<Tuple11<String, String, String, String, String, String, String, String, String, String, String>> out) throws Exception {
				out.collect(new Tuple11<String, String, String, String, String, String, String, String, String, String, String>(first.f0, first.f1, first.f2, first.f3, first.f4, first.f5, first.f6, first.f7, first.f8, (second == null) ? null : second.f1, first.f9));
			}
		});
		// 获取班级
		DataSet<Tuple2<String, String>> classesDataSet = env.fromCollection(RedisClientUtils.getAllMap("T_CLASSES").values()).flatMap(new FlatMapFunction<String, Tuple2<String, String>>() {
			private static final long serialVersionUID = -606682234015131246L;

			@Override
			public void flatMap(String value, Collector<Tuple2<String, String>> out) throws Exception {
				JSONObject json = JSON.parseObject(value);
				out.collect(new Tuple2<String, String>(json.getString("no_"), json.getString("name_")));
			}

		});

		// 输出列：NO_,NAME_,SEX_CODE,SEX_NAME,DEPT_ID,DEPT_NAME,MAJOR_ID MAJOR_NAME,EDU_ID,EDU_NAME,CLASS_ID,CLASS_NAME
		DataSet<Tuple12<String, String, String, String, String, String, String, String, String, String, String, String>> stuSexDeptTeachEduClassesDataSet = stuSexDeptTeachEduDataSet.leftOuterJoin(classesDataSet).where(10).equalTo(0).with(new FlatJoinFunction<Tuple11<String, String, String, String, String, String, String, String, String, String, String>, Tuple2<String, String>, Tuple12<String, String, String, String, String, String, String, String, String, String, String, String>>() {
			private static final long serialVersionUID = 1L;

			public void join(Tuple11<String, String, String, String, String, String, String, String, String, String, String> first, Tuple2<String, String> second, Collector<Tuple12<String, String, String, String, String, String, String, String, String, String, String, String>> out) throws Exception {
				out.collect(new Tuple12<String, String, String, String, String, String, String, String, String, String, String, String>(first.f0, first.f1, first.f2, first.f3, first.f4, first.f5, first.f6, first.f7, first.f8, first.f9, first.f10, (second == null) ? null : second.f1));
			}
		});

		String path = hdfsPath + inputPath;
		TextInputFormat format = new TextInputFormat(new Path(path));
		format.setNestedFileEnumeration(true);
		// 输出列： CARD_ID,PAY_MONEY,TIME_
		DataSet<Tuple3<String, Double, Date>> cardPayDataSet = env.readFile(format, path).flatMap(new FlatMapFunction<String, Tuple3<String, Double, Date>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void flatMap(String value, Collector<Tuple3<String, Double, Date>> out) throws Exception {
				String[] f = value.replaceAll("'", "").split(",");
				String card_id = f[1];
				String payMoney = f[2];
				if (StringUtils.isEmpty(payMoney))
					throw new IllegalArgumentException("the params must be not null");
				String time_ = f[4];
				if (StringUtils.isEmpty(time_))
					throw new IllegalArgumentException("the params must be not null");
				Date date = null;
				try {
					date = new SimpleDateFormat("yyyy-MM-dd").parse(time_);
				} catch (Exception e) {
					throw new RuntimeException("日期格式不对！ " + "源数据为：" + value + "  待转换的日期格式是：" + time_);
				}
				out.collect(new Tuple3<String, Double, Date>(card_id, Double.valueOf(payMoney), date));
			}
		});
		// 分组计算结果，分组依据是card_id和日期（年月日）
		// 输出列：CARD_ID,TIME_,MONEY,COUNT
		DataSet<Tuple4<String, Date, Double, Long>> cardPaySumDataSet = cardPayDataSet.groupBy(0, 2).reduceGroup(new GroupReduceFunction<Tuple3<String, Double, Date>, Tuple4<String, Date, Double, Long>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void reduce(Iterable<Tuple3<String, Double, Date>> values, Collector<Tuple4<String, Date, Double, Long>> out) throws Exception {
				String card_id = null;
				Date date = null;
				Long count = 0L;
				Double money = 0.0D;
				for (Iterator<Tuple3<String, Double, Date>> it = values.iterator(); it.hasNext();) {
					Tuple3<String, Double, Date> element = it.next();
					if (StringUtils.isEmpty(card_id))
						card_id = element.f0;
					if (date == null)
						date = element.f2;
					money += element.f1;
					count++;
				}
				out.collect(new Tuple4<String, Date, Double, Long>(card_id, date, money, count));
			}
		});

		DataSet<Tuple2<String, String>> cardDataSet = env.fromCollection(RedisClientUtils.getAllMap("T_CARD").values()).flatMap(new FlatMapFunction<String, Tuple2<String, String>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void flatMap(String value, Collector<Tuple2<String, String>> out) throws Exception {
				JSONObject json = JSON.parseObject(value);
				out.collect(new Tuple2<String, String>(json.getString("no_"), json.getString("peopleId")));
			}
		});
		// 输出列： CARD_ID,TIME_,MONEY,COUNT,peopleId
		DataSet<Tuple5<String, Date, Double, Long, String>> cardPaySumDataCardSet = cardPaySumDataSet.leftOuterJoin(cardDataSet).where(0).equalTo(0).with(new FlatJoinFunction<Tuple4<String, Date, Double, Long>, Tuple2<String, String>, Tuple5<String, Date, Double, Long, String>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void join(Tuple4<String, Date, Double, Long> first, Tuple2<String, String> second, Collector<Tuple5<String, Date, Double, Long, String>> out) throws Exception {
				out.collect(new Tuple5<String, Date, Double, Long, String>(first.f0, first.f1, first.f2, first.f3, (second.f1 == null) ? null : second.f1));
			}
		});

		// 输出实体：TCardUse
		DataSet<TCardUse> resultDataSet = cardPaySumDataCardSet.leftOuterJoin(stuSexDeptTeachEduClassesDataSet).where(4).equalTo(0).with(new FlatJoinFunction<Tuple5<String, Date, Double, Long, String>, Tuple12<String, String, String, String, String, String, String, String, String, String, String, String>, TCardUse>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void join(Tuple5<String, Date, Double, Long, String> first, Tuple12<String, String, String, String, String, String, String, String, String, String, String, String> second, Collector<TCardUse> out) throws Exception {
				String PAY_DAYS = new SimpleDateFormat("yyyy-MM-dd").format(first.f1);
				Date YEAR_MONTH = new SimpleDateFormat("yyyy-MM").parse(new SimpleDateFormat("yyyy-MM").format(first.f1));
				String PAY_MONEY = new DecimalFormat("#.00").format(first.f2);
				if (second != null)
					out.collect(new TCardUse(YEAR_MONTH, first.f3.toString(), PAY_MONEY, PAY_DAYS, second.f0, second.f1, second.f2, second.f3, second.f4, second.f5, second.f6, second.f7, second.f8, second.f9, second.f10, second.f11));
				if (second == null)
					out.collect(new TCardUse(YEAR_MONTH, first.f3.toString(), PAY_MONEY, PAY_DAYS, null, null, null, null, null, null, null, null, null, null, null, null));
			}
		});

		resultDataSet.writeAsText(hdfsPath + outputPath, WriteMode.OVERWRITE);
		env.execute("begin run job");
		// 方便检查数据
		// DataSet<Tuple16<String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String>> result = resultDataSet.map(new MapFunction<Tuple16<Date, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String>, Tuple16<String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String>>() {
		// private static final long serialVersionUID = 1L;
		//
		// @Override
		// public Tuple16<String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String> map(Tuple16<Date, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String> value) throws Exception {
		// return new Tuple16<String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String>(new SimpleDateFormat("yyyy-MM").format(value.f0), value.f1, value.f2, value.f3, value.f4, value.f5, value.f6, value.f7, value.f8, value.f9, value.f10, value.f11, value.f12, value.f13, value.f14, value.f15);
		// }
		// });

		// result.print();
		// System.setOut(new PrintStream("d:/bbb.txt"));
		// for (Iterator<Tuple16<String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String>> it = result.collect().iterator(); it.hasNext();) {
		// Tuple16<String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String> type = it.next();
		// System.out.println(type);
		// }

	}
}
