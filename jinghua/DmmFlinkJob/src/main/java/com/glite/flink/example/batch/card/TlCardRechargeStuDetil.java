package com.glite.flink.example.batch.card;

import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.flink.api.common.functions.FlatJoinFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.TextInputFormat;
import org.apache.flink.api.java.tuple.Tuple10;
import org.apache.flink.api.java.tuple.Tuple17;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple6;
import org.apache.flink.api.java.tuple.Tuple7;
import org.apache.flink.api.java.tuple.Tuple8;
import org.apache.flink.api.java.tuple.Tuple9;
import org.apache.flink.core.fs.FileSystem.WriteMode;
import org.apache.flink.core.fs.Path;
import org.apache.flink.util.Collector;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glite.flink.example.domain.CardRecharge;
import com.glite.flink.utils.RedisClientUtils;

/**
 * @作者:baihuayang
 *
 * @创建时间:2016年12月27日 上午11:34:57
 *
 * @描述:充值结果数据计算
 * 
 */
public class TlCardRechargeStuDetil {

	private static String hdfsPath = "hdfs://centos02:9000";

	private static String inputPath = "/card_recharge/year=2013";
	private static String outputPath = "/output/cardRecharge";

	public static void main(String[] args) {
		try {
			cardRecharge(inputPath, outputPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private static void cardRecharge(String inputPath, String outputPath) throws Exception {
		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
		// 学生数据集 输出列：NO_,NAME_,SEX_CODE,DEPT_ID,MAJOR_ID,EDU_ID
		DataSet<Tuple6<String, String, String, String, String, String>> stuDataSet = env.readTextFile(hdfsPath + "/stu").flatMap(new FlatMapFunction<String, Tuple6<String, String, String, String, String, String>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void flatMap(String value, Collector<Tuple6<String, String, String, String, String, String>> out) throws Exception {
				String[] f = value.replaceAll("'", "").split(",");
				out.collect(new Tuple6<String, String, String, String, String, String>(f[1], f[3], f[8], f[13], f[14], f[18]));
			}
		});

		// 输出列：TEA_NO, NAME_,SEX_CODE,DEPT_ID,MAJOR_ID,EDU_ID 其中，MAJOR_ID和CLASS_ID为空。
		DataSet<Tuple6<String, String, String, String, String, String>> teaDataSet = env.fromCollection(RedisClientUtils.getAllMap("T_TEA").values()).flatMap(new FlatMapFunction<String, Tuple6<String, String, String, String, String, String>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void flatMap(String value, Collector<Tuple6<String, String, String, String, String, String>> out) throws Exception {
				JSONObject json = JSON.parseObject(value);
				out.collect(new Tuple6<String, String, String, String, String, String>(json.getString("tea_no"), json.getString("name_"), json.getString("sex_code"), json.getString("dept_id"), "", json.getString("edu_id")));
			}
		});
		// 合并学生和老师信息集合
		DataSet<Tuple6<String, String, String, String, String, String>> stuTeaDataSet = stuDataSet.union(teaDataSet);

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

		// 输出列： NO_,NAME_,SEX_CODE,SEX_NAME,DEPT_ID,MAJOR_ID,EDU_ID
		DataSet<Tuple7<String, String, String, String, String, String, String>> stuSexDataSet = stuTeaDataSet.leftOuterJoin(sexCodeDataSet).where(2).equalTo(0).with(new FlatJoinFunction<Tuple6<String, String, String, String, String, String>, Tuple2<String, String>, Tuple7<String, String, String, String, String, String, String>>() {
			private static final long serialVersionUID = 1L;

			public void join(Tuple6<String, String, String, String, String, String> first, Tuple2<String, String> second, Collector<Tuple7<String, String, String, String, String, String, String>> out) throws Exception {
				out.collect(new Tuple7<String, String, String, String, String, String, String>(first.f0, first.f1, first.f2, (second != null) ? second.f1 : null, first.f3, first.f4, first.f5));
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
		// 输出列：NO_,NAME_,SEX_CODE,SEX_NAME,DEPT_ID,DEPT_NAME,MAJOR_ID,EDU_ID
		DataSet<Tuple8<String, String, String, String, String, String, String, String>> stuSexDeptDataSet = stuSexDataSet.leftOuterJoin(codeDeptDataSet).where(4).equalTo(0).with(new FlatJoinFunction<Tuple7<String, String, String, String, String, String, String>, Tuple2<String, String>, Tuple8<String, String, String, String, String, String, String, String>>() {
			private static final long serialVersionUID = 1L;

			public void join(Tuple7<String, String, String, String, String, String, String> first, Tuple2<String, String> second, Collector<Tuple8<String, String, String, String, String, String, String, String>> out) throws Exception {
				out.collect(new Tuple8<String, String, String, String, String, String, String, String>(first.f0, first.f1, first.f2, first.f3, first.f4, ((second != null) ? second.f1 : null), first.f5, first.f6));
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
		// 输出列： NO_,NAME_,SEX_CODE,SEX_NAME,DEPT_ID,DEPT_NAME,MAJOR_ID MAJOR_NAME,EDU_ID
		DataSet<Tuple9<String, String, String, String, String, String, String, String, String>> stuSexDeptTeachDataSet = stuSexDeptDataSet.leftOuterJoin(codeDeptTeachDataSet).where(6).equalTo(0).with(new FlatJoinFunction<Tuple8<String, String, String, String, String, String, String, String>, Tuple2<String, String>, Tuple9<String, String, String, String, String, String, String, String, String>>() {
			private static final long serialVersionUID = 1L;

			public void join(Tuple8<String, String, String, String, String, String, String, String> first, Tuple2<String, String> second, Collector<Tuple9<String, String, String, String, String, String, String, String, String>> out) throws Exception {
				out.collect(new Tuple9<String, String, String, String, String, String, String, String, String>(first.f0, first.f1, first.f2, first.f3, first.f4, first.f5, first.f6, (second == null) ? null : second.f1, first.f7));
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
		// 输出列： NO_,NAME_,SEX_CODE,SEX_NAME,DEPT_ID,DEPT_NAME,MAJOR_ID MAJOR_NAME,EDU_ID,EDU_NAME
		DataSet<Tuple10<String, String, String, String, String, String, String, String, String, String>> stuSexDeptTeachEduDataSet = stuSexDeptTeachDataSet.leftOuterJoin(codeEduDataSet).where(8).equalTo(0).with(new FlatJoinFunction<Tuple9<String, String, String, String, String, String, String, String, String>, Tuple2<String, String>, Tuple10<String, String, String, String, String, String, String, String, String, String>>() {
			private static final long serialVersionUID = 1L;

			public void join(Tuple9<String, String, String, String, String, String, String, String, String> first, Tuple2<String, String> second, Collector<Tuple10<String, String, String, String, String, String, String, String, String, String>> out) throws Exception {
				out.collect(new Tuple10<String, String, String, String, String, String, String, String, String, String>(first.f0, first.f1, first.f2, first.f3, first.f4, first.f5, first.f6, first.f7, first.f8, (second == null) ? null : second.f1));
			}
		});

		// 获取消费类型
		DataSet<Tuple2<String, String>> cardDealDataSet = env.fromCollection(RedisClientUtils.getAllMap("T_CODE_CARD_DEAL").values()).flatMap(new FlatMapFunction<String, Tuple2<String, String>>() {
			private static final long serialVersionUID = -606682234015131246L;

			@Override
			public void flatMap(String value, Collector<Tuple2<String, String>> out) throws Exception {
				JSONObject json = JSON.parseObject(value);
				out.collect(new Tuple2<String, String>(json.getString("id"), json.getString("name_")));
			}
		});

		String path = hdfsPath + inputPath;
		TextInputFormat format = new TextInputFormat(new Path(path));
		format.setNestedFileEnumeration(true);
		// 输出列： CARD_ID, YEAR_MONTH, HOUR_, UP_TIME, UP_MONEY, OLD_MONEY
		// 解析t_card_recharge
		DataSet<Tuple7<String, Date, String, String, String, String, String>> cardRechargeDataSet = env.readFile(format, path).flatMap(new FlatMapFunction<String, Tuple7<String, Date, String, String, String, String, String>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void flatMap(String value, Collector<Tuple7<String, Date, String, String, String, String, String>> out) throws Exception {
				String[] f = value.replaceAll("'", "").split(",");
				String card_id = f[1];
				String money = f[2];
				String time_ = f[3];
				String cardDealId = f[4];
				String oldMoney = f[6];
				Date YEAR_MONTH = new SimpleDateFormat("yyyy-MM").parse(time_);
				String HOUR_ = new SimpleDateFormat("HH").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time_));
				String UP_TIME = time_;
				String UP_MONEY = new DecimalFormat("#.00").format(Double.valueOf(money));
				out.collect(new Tuple7<String, Date, String, String, String, String, String>(card_id, YEAR_MONTH, HOUR_, UP_TIME, UP_MONEY, oldMoney, cardDealId));
			}
		});
		// 输出列： card_id, YEAR_MONTH, HOUR_, UP_TIME, UP_MONEY, oldMoney, cardDealId, cardDealName
		DataSet<Tuple8<String, Date, String, String, String, String, String, String>> cardRechargeDealDataSet = cardRechargeDataSet.leftOuterJoin(cardDealDataSet).where(6).equalTo(0).with(new FlatJoinFunction<Tuple7<String, Date, String, String, String, String, String>, Tuple2<String, String>, Tuple8<String, Date, String, String, String, String, String, String>>() {
			private static final long serialVersionUID = 2553498839030346733L;

			@Override
			public void join(Tuple7<String, Date, String, String, String, String, String> first, Tuple2<String, String> second, Collector<Tuple8<String, Date, String, String, String, String, String, String>> out) throws Exception {
				out.collect(new Tuple8<String, Date, String, String, String, String, String, String>(first.f0, first.f1, first.f2, first.f3, first.f4, first.f5, first.f6, (second == null) ? null : second.f1));
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

		// 输出列： card_id, YEAR_MONTH, HOUR_, UP_TIME, UP_MONEY, oldMoney, cardDealId, cardDealName,peopleId
		DataSet<Tuple9<String, Date, String, String, String, String, String, String, String>> cardRechargeDealCardDataSet = cardRechargeDealDataSet.leftOuterJoin(cardDataSet).where(0).equalTo(0).with(new FlatJoinFunction<Tuple8<String, Date, String, String, String, String, String, String>, Tuple2<String, String>, Tuple9<String, Date, String, String, String, String, String, String, String>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void join(Tuple8<String, Date, String, String, String, String, String, String> first, Tuple2<String, String> second, Collector<Tuple9<String, Date, String, String, String, String, String, String, String>> out) throws Exception {
				out.collect(new Tuple9<String, Date, String, String, String, String, String, String, String>(first.f0, first.f1, first.f2, first.f3, first.f4, first.f5, first.f6, first.f7, (second == null) ? "" : second.f1));
			}
		});
		// 输出列：YEAR_MONTH, HOUR_, UP_TIME, UP_MONEY, oldMoney, cardDealId, cardDealName, NO_,NAME_,SEX_CODE,SEX_NAME,DEPT_ID,DEPT_NAME,MAJOR_ID MAJOR_NAME,EDU_ID,EDU_NAME
		DataSet<CardRecharge> resultDataSet = cardRechargeDealCardDataSet.leftOuterJoin(stuSexDeptTeachEduDataSet).where(8).equalTo(0).with(new FlatJoinFunction<Tuple9<String, Date, String, String, String, String, String, String, String>, Tuple10<String, String, String, String, String, String, String, String, String, String>, CardRecharge>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void join(Tuple9<String, Date, String, String, String, String, String, String, String> first, Tuple10<String, String, String, String, String, String, String, String, String, String> second, Collector<CardRecharge> out) throws Exception {
				out.collect(new CardRecharge(first.f1, first.f2, first.f3, first.f4, first.f5, first.f6, first.f7, (second == null) ? null : second.f0, (second == null) ? null : second.f1, (second == null) ? null : second.f2, (second == null) ? null : second.f3, (second == null) ? null : second.f4, (second == null) ? null : second.f5, (second == null) ? null : second.f6, (second == null) ? null : second.f7, (second == null) ? null : second.f8, (second == null) ? null : second.f9));
			}
		});
		resultDataSet.writeAsText(hdfsPath + outputPath, WriteMode.OVERWRITE);
		env.execute("begin run job");

		// 方便检查数据
		// DataSet<Tuple17<String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String>> result = resultDataSet.map(new MapFunction<Tuple17<Date, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String>, Tuple17<String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String>>() {
		// private static final long serialVersionUID = 1L;
		//
		// @Override
		// public Tuple17<String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String> map(Tuple17<Date, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String> value) throws Exception {
		// return new Tuple17<String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String>(new SimpleDateFormat("yyyy-MM").format(value.f0), value.f1, value.f2, value.f3, value.f4, value.f5, value.f6, value.f7, value.f8, value.f9, value.f10, value.f11, value.f12, value.f13, value.f14, value.f15, value.f16);
		// }
		// });
		// result.print();

		// System.setOut(new PrintStream("d:/aaa.txt"));
		// for (Iterator<Tuple17<String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String>> it = result.collect().iterator(); it.hasNext();) {
		// Tuple17<String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String> type = it.next();
		// System.out.println(type);
		// }
	}
}
