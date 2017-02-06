package com.glite.flink.example.batch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.GroupCombineFunction;
import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.io.InputFormat;
import org.apache.flink.api.common.io.OutputFormat;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.io.TextInputFormat;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.api.java.tuple.Tuple7;
import org.apache.flink.api.java.tuple.Tuple9;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.fs.Path;
import org.apache.flink.core.fs.FileSystem.WriteMode;
import org.apache.flink.util.Collector;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glite.flink.example.domain.TCardPay;
import com.glite.flink.example.domain.TCardPayStatisticDomain;
import com.glite.flink.utils.RedisClientUtils;

public class TCardPayStatistic {
	public static void main(String[] args) throws Throwable {
		ParameterTool params = ParameterTool.fromArgs(args);
		Calendar cal = Calendar.getInstance();
		String path = "hdfs://ns1/cardpay/year=";
		// String path = "d:/cardpay/year=";
		path += (params.get("year") != null ? params.get("year") : cal
				.get(Calendar.MONTH));
		if (params.get("month") != null) {
			path += "/month=" + params.get("month");
		}
		if (params.get("day") != null) {
			path += "/day=" + params.get("day");
		}

		final ExecutionEnvironment env = ExecutionEnvironment
				.getExecutionEnvironment();

		// carddeal
		DataSet<Tuple2<String, String>> cardDealDataSet = env.fromCollection(
				RedisClientUtils.getAllMap("T_CODE_CARD_DEAL").values())
				.flatMap(new FlatMapFunction<String, Tuple2<String, String>>() {

					@Override
					public void flatMap(String value,
							Collector<Tuple2<String, String>> out)
							throws Exception {
						JSONObject json = JSON.parseObject(value);
						out.collect(new Tuple2<String, String>(json
								.getString("id"), json.getString("name_")));
					}
				});

		DataSet<Tuple3<String, String, String>> cardPortDataSet = env
				.fromCollection(
						RedisClientUtils.getAllMap("T_CARD_PORT").values())
				.flatMap(
						new FlatMapFunction<String, Tuple3<String, String, String>>() {

							@Override
							public void flatMap(
									String value,
									Collector<Tuple3<String, String, String>> out)
									throws Exception {
								JSONObject json = JSON.parseObject(value);
								out.collect(new Tuple3<String, String, String>(
										json.getString("id"), json
												.getString("name_"), json
												.getString("card_dept_id")));
							}
						});

		DataSet<Tuple2<String, String>> cardDeptDataSet = env.fromCollection(
				RedisClientUtils.getAllMap("T_CARD_DEPT").values()).flatMap(
				new FlatMapFunction<String, Tuple2<String, String>>() {

					@Override
					public void flatMap(String value,
							Collector<Tuple2<String, String>> out)
							throws Exception {
						JSONObject json = JSON.parseObject(value);
						out.collect(new Tuple2<String, String>(json
								.getString("id"), json.getString("name_")));
					}
				});

		DataSet<Tuple2<String, String>> codeDeptTeachDataSet = env
				.fromCollection(
						RedisClientUtils.getAllMap("T_CODE_DEPT_TEACH")
								.values()).flatMap(
						new FlatMapFunction<String, Tuple2<String, String>>() {

							@Override
							public void flatMap(String value,
									Collector<Tuple2<String, String>> out)
									throws Exception {
								JSONObject json = JSON.parseObject(value);
								out.collect(new Tuple2<String, String>(json
										.getString("id"), json
										.getString("name_")));
							}
						});
		DataSet<Tuple2<String, String>> codeMajorDataSet = env.fromCollection(
				RedisClientUtils.getAllMap("T_CODE_DEPT_TEACH").values())
				.flatMap(new FlatMapFunction<String, Tuple2<String, String>>() {

					@Override
					public void flatMap(String value,
							Collector<Tuple2<String, String>> out)
							throws Exception {
						JSONObject json = JSON.parseObject(value);
						out.collect(new Tuple2<String, String>(json
								.getString("id"), json.getString("name_")));
					}
				});

		DataSet<Tuple2<String, String>> codeEduDataSet = env.fromCollection(
				RedisClientUtils.getAllMap("T_CODE_EDUCATION").values())
				.flatMap(new FlatMapFunction<String, Tuple2<String, String>>() {

					@Override
					public void flatMap(String value,
							Collector<Tuple2<String, String>> out)
							throws Exception {
						JSONObject json = JSON.parseObject(value);
						out.collect(new Tuple2<String, String>(json
								.getString("id"), json.getString("name_")));
					}
				});
		DataSet<Tuple3<String, String, String>> cardDataSet = env
				.fromCollection(RedisClientUtils.getAllMap("T_CARD").values())
				.flatMap(
						new FlatMapFunction<String, Tuple3<String, String, String>>() {
							@Override
							public void flatMap(
									String value,
									Collector<Tuple3<String, String, String>> out)
									throws Exception {
								JSONObject json = JSON.parseObject(value);
								out.collect(new Tuple3<String, String, String>(
										json.getString("no_"), json
												.getString("peopleId"), json
												.getString("cardIdentityId")));
							}
						});

		DataSet<Tuple2<String, String>> sexCodeDataSet = env.fromCollection(
				RedisClientUtils.getAllMap("T_CODE").values()).flatMap(
				new FlatMapFunction<String, Tuple2<String, String>>() {

					@Override
					public void flatMap(String value,
							Collector<Tuple2<String, String>> out)
							throws Exception {
						JSONObject json = JSON.parseObject(value);
						if ("SEX_CODE".equals(json.getString("code_type"))) {
							out.collect(new Tuple2<String, String>(json
									.getString("code_"), json
									.getString("name_")));
						}

					}
				});
		// 学生数据集
		DataSet<Tuple5<String, String, String, String, String>> stuDataSet = env
				.readTextFile("hdfs://ns1/stu")
				.flatMap(
						new FlatMapFunction<String, Tuple5<String, String, String, String, String>>() {

							@Override
							public void flatMap(
									String value,
									Collector<Tuple5<String, String, String, String, String>> out)
									throws Exception {
								String[] f = value.replaceAll("'", "").split(",");
								out.collect(new Tuple5<String, String, String, String, String>(
										f[1], f[8], f[13], f[14], f[18]));
							}
						});

		// 如果在本地eclipse中运行，这里可以改为本地的目录，如D:/cardpar/
		TextInputFormat format = new TextInputFormat(new Path(path));
		format.setNestedFileEnumeration(true);
		DataSource<String> paySource = env.readFile(format, path);
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
		DataSet<Tuple7<String, String, String, String, String, Float, Long>> t7DataSet = paySet
				.groupBy(
						new KeySelector<TCardPay, Tuple5<String, String, String, String,String>>() {
							@Override
							public Tuple5<String, String, String, String,String> getKey(
									TCardPay value) throws Exception {
								// 按cardId,cardPortId,cardDealId,hour 分组
								return new Tuple5<String, String, String, String,String>(
										value.getCardId(), value
												.getCardPortId(), value
												.getCardDealId(),value.getTime_().substring(0,7), value
												.getTime_().substring(11, 13));
							}
						})
				.combineGroup(
						new GroupCombineFunction<TCardPay, Tuple7<String, String, String, String, String, Float, Long>>() {
							/*
							 * 这里假设数据量非常大，用局部分组计数，具体应用是，根据数据量情况定。
							 * 数据量小的话，可以直接使用reduce或reducdgroup
							 */
							@Override
							public void combine(
									Iterable<TCardPay> values,
									Collector<Tuple7<String, String, String, String, String, Float, Long>> out)
									throws Exception {
								String cardId = "";
								String cardPortId = "";
								String cardDealId = "";
								String yearMonth = "";
								String hour = "";
								float payMoney = 0;
								long counts = 0;
								for (TCardPay pay : values) {
									cardId = pay.getCardId();
									cardPortId = pay.getCardPortId();
									cardDealId = pay.getCardDealId();
									yearMonth = pay.getTime_().substring(0, 7);
									hour = pay.getTime_().substring(11, 13);
									payMoney += pay.getPayMoney();
									counts++;
								}
								out.collect(new Tuple7<String, String, String, String, String, Float, Long>(
										cardId, cardPortId, cardDealId,
										yearMonth, hour, payMoney, counts));

							}
						})
				.groupBy(0, 1, 2, 3, 4)
				.reduce(new ReduceFunction<Tuple7<String, String, String, String, String, Float, Long>>() {

					@Override
					public Tuple7<String, String, String, String, String, Float, Long> reduce(
							Tuple7<String, String, String, String, String, Float, Long> value1,
							Tuple7<String, String, String, String, String, Float, Long> value2)
							throws Exception {
						// TODO Auto-generated method stub
						return new Tuple7<String, String, String, String, String, Float, Long>(
								value1.f0, value1.f1, value1.f2, value1.f3,
								value1.f4, value1.f5 + value2.f5, value1.f6
										+ value2.f6);
					}
				});

		/*
		 * no_,dept_id,major_id,edu_id,sex_code,card_id,card_identity_id
		 */

		DataSet<Tuple7<String, String, String, String, String, String, String>> stuCardDataSet = cardDataSet
				.join(stuDataSet)
				.where(1)
				.equalTo(0)
				.flatMap(
						new FlatMapFunction<Tuple2<Tuple3<String, String, String>, Tuple5<String, String, String, String, String>>, Tuple7<String, String, String, String, String, String, String>>() {

							@Override
							public void flatMap(
									Tuple2<Tuple3<String, String, String>, Tuple5<String, String, String, String, String>> value,
									Collector<Tuple7<String, String, String, String, String, String, String>> out)
									throws Exception {

								out.collect(new Tuple7<String, String, String, String, String, String, String>(
										value.f1.f0, value.f1.f2, value.f1.f3,
										value.f1.f4, value.f1.f1, value.f0.f0,
										value.f0.f2));

							}
						});

		// DataSet<Tuple7<String, String, String, String, String, String,
		// String>> stuCardDataSet = cardDataSet
		// .flatMap(new FlatMapFunction<Tuple3<String, String, String>,
		// Tuple7<String, String, String, String, String, String, String>>() {
		//
		// @Override
		// public void flatMap(
		// Tuple3<String, String, String> value,
		// Collector<Tuple7<String, String, String, String, String, String,
		// String>> out)
		// throws Exception {
		// if(value.f1!=null){
		// String stuInfo = RedisClientUtils.getMap("T_STU",
		// value.f1);
		// if (stuInfo != null) {
		// JSONObject stu = JSON.parseObject(stuInfo);
		// String no_ = value.f1;
		// String deptId = stu.getString("dept_id");
		// String majorId = stu.getString("major_id");
		// String eduId = stu.getString("edu_id");
		// String sexCode = stu.getString("sex_code");
		// out.collect(new Tuple7<String, String, String, String, String,
		// String, String>(
		// no_, deptId, majorId, eduId, sexCode,
		// value.f0, value.f2));
		// }
		// }
		//
		// }
		// });

		/*
		 * 到此，t7DataSet中的数据是
		 * cardId，cardPortId，cardDealId,yearMonth，hour,payMoney，counts
		 */
		DataSet<TCardPayStatisticDomain> statDataSet = t7DataSet
				.join(stuCardDataSet)
				.where(0)
				.equalTo(5)
				.flatMap(
						new FlatMapFunction<Tuple2<Tuple7<String, String, String, String, String, Float, Long>, Tuple7<String, String, String, String, String, String, String>>, TCardPayStatisticDomain>() {

							@Override
							public void flatMap(
									Tuple2<Tuple7<String, String, String, String, String, Float, Long>, Tuple7<String, String, String, String, String, String, String>> value,
									Collector<TCardPayStatisticDomain> out)
									throws Exception {
								/*
								 * f0:
								 * cardId，cardPortId，cardDealId,yearMonth，hour
								 * ,payMoney，counts f1:
								 * no_,dept_id,major_id,edu_id
								 * ,sex_code,card_id,card_identity_id
								 */

								String yearMonth = value.f0.f3;
								String hour = value.f0.f4;
								Float payMoney = value.f0.f5;
								Integer payCount = value.f0.f6.intValue();
								String cardPortId = value.f0.f1;
								String cardDealId = value.f0.f2;
								String sexCode = value.f1.f4;
								String deptId = value.f1.f1;
								String majorId = value.f1.f2;
								String eduId = value.f1.f3;
								// String cardPortName;
								// String cardPortPid;
								// String cardPortPname;
								// String cardDealName;
								// String sexName;
								// String deptName;
								// String majorName;
								// String eduName;

								TCardPayStatisticDomain domain = new TCardPayStatisticDomain();
								domain.setYearMonth(yearMonth);
								domain.setHour(hour);
								domain.setCardDealId(cardDealId);
								domain.setCardPortId(cardPortId);
								domain.setDeptId(deptId);
								domain.setEduId(eduId);
								domain.setMajorId(majorId);
								domain.setPayCount(payCount);
								domain.setPayMoney(payMoney);
								domain.setSexCode(sexCode);
								out.collect(domain);
							}
						});
		DataSet<TCardPayStatisticDomain> finalDataSet = statDataSet
				.join(cardPortDataSet)
				.where("cardPortId")
				.equalTo(0)
				.flatMap(
						new FlatMapFunction<Tuple2<TCardPayStatisticDomain, Tuple3<String, String, String>>, TCardPayStatisticDomain>() {

							@Override
							public void flatMap(
									Tuple2<TCardPayStatisticDomain, Tuple3<String, String, String>> value,
									Collector<TCardPayStatisticDomain> out)
									throws Exception {
								TCardPayStatisticDomain d = value.f0;
								TCardPayStatisticDomain domain = new TCardPayStatisticDomain();
								domain.setYearMonth(d.getYearMonth());
								domain.setHour(d.getHour());
								domain.setCardDealId(d.getCardDealId());
								domain.setCardPortId(d.getCardPortId());
								domain.setDeptId(d.getDeptId());
								domain.setEduId(d.getEduId());
								domain.setMajorId(d.getMajorId());
								domain.setPayCount(d.getPayCount());
								domain.setPayMoney(d.getPayMoney());
								domain.setSexCode(d.getSexCode());
								domain.setCardPortName(value.f1.f1);
								domain.setCardPortPid(value.f1.f2);
								out.collect(domain);

							}
						})
				.groupBy("yearMonth", "hour", "cardPortId", "cardPortPid",
						"cardDealId", "sexCode", "deptId", "majorId", "eduId")
				.combineGroup(
						new GroupCombineFunction<TCardPayStatisticDomain, TCardPayStatisticDomain>() {

							@Override
							public void combine(
									Iterable<TCardPayStatisticDomain> values,
									Collector<TCardPayStatisticDomain> out)
									throws Exception {
								TCardPayStatisticDomain domain = null;
								for (TCardPayStatisticDomain d : values) {
									if (domain == null) {
										domain = new TCardPayStatisticDomain();
										domain.setYearMonth(d.getYearMonth());
										domain.setHour(d.getHour());
										domain.setCardDealId(d.getCardDealId());
										domain.setCardPortId(d.getCardPortId());
										domain.setCardPortPid(d.getCardPortPid());
										domain.setDeptId(d.getDeptId());
										domain.setEduId(d.getEduId());
										domain.setMajorId(d.getMajorId());
										domain.setPayCount(d.getPayCount());
										domain.setPayMoney(d.getPayMoney());
										domain.setSexCode(d.getSexCode());
									} else {
										domain.setPayCount(domain.getPayCount()
												+ d.getPayCount());
										domain.setPayMoney(domain.getPayMoney()
												+ d.getPayMoney());
									}
								}
								out.collect(domain);
							}
						})
				.groupBy("yearMonth", "hour", "cardPortId", "cardPortPid",
						"cardDealId", "sexCode", "deptId", "majorId", "eduId")
				.reduceGroup(
						new GroupReduceFunction<TCardPayStatisticDomain, TCardPayStatisticDomain>() {

							@Override
							public void reduce(
									Iterable<TCardPayStatisticDomain> values,
									Collector<TCardPayStatisticDomain> out)
									throws Exception {
								TCardPayStatisticDomain domain = null;
								for (TCardPayStatisticDomain d : values) {
									if (domain == null) {
										domain = new TCardPayStatisticDomain();
										domain.setYearMonth(d.getYearMonth());
										domain.setHour(d.getHour());
										domain.setCardDealId(d.getCardDealId());
										domain.setCardPortId(d.getCardPortId());
										domain.setCardPortPid(d.getCardPortPid());
										domain.setDeptId(d.getDeptId());
										domain.setEduId(d.getEduId());
										domain.setMajorId(d.getMajorId());
										domain.setPayCount(d.getPayCount());
										domain.setPayMoney(d.getPayMoney());
										domain.setSexCode(d.getSexCode());
									} else {
										domain.setPayCount(domain.getPayCount()
												+ d.getPayCount());
										domain.setPayMoney(domain.getPayMoney()
												+ d.getPayMoney());
									}
								}
								out.collect(domain);

							}
						})
				.join(codeDeptTeachDataSet)
				.where("deptId")
				.equalTo(0)
				.flatMap(
						new FlatMapFunction<Tuple2<TCardPayStatisticDomain, Tuple2<String, String>>, TCardPayStatisticDomain>() {

							@Override
							public void flatMap(
									Tuple2<TCardPayStatisticDomain, Tuple2<String, String>> value,
									Collector<TCardPayStatisticDomain> out)
									throws Exception {							
								TCardPayStatisticDomain d=value.f0;
								TCardPayStatisticDomain domain = new TCardPayStatisticDomain();
								domain.setYearMonth(d.getYearMonth());
								domain.setHour(d.getHour());
								domain.setCardDealId(d.getCardDealId());
								domain.setCardPortId(d.getCardPortId());
								domain.setCardPortPid(d.getCardPortPid());
								domain.setDeptId(d.getDeptId());
								domain.setEduId(d.getEduId());
								domain.setMajorId(d.getMajorId());
								domain.setPayCount(d.getPayCount());
								domain.setPayMoney(d.getPayMoney());
								domain.setSexCode(d.getSexCode());
								domain.setDeptName(value.f1.f1);
								out.collect(domain);

							}
						})
				.join(codeMajorDataSet)
				.where("majorId")
				.equalTo(0)
				.flatMap(
						new FlatMapFunction<Tuple2<TCardPayStatisticDomain, Tuple2<String, String>>, TCardPayStatisticDomain>() {

							@Override
							public void flatMap(
									Tuple2<TCardPayStatisticDomain, Tuple2<String, String>> value,
									Collector<TCardPayStatisticDomain> out)
									throws Exception {
								TCardPayStatisticDomain d=value.f0;
								TCardPayStatisticDomain domain = new TCardPayStatisticDomain();
								domain.setYearMonth(d.getYearMonth());
								domain.setHour(d.getHour());
								domain.setCardDealId(d.getCardDealId());
								domain.setCardPortId(d.getCardPortId());
								domain.setCardPortPid(d.getCardPortPid());
								domain.setDeptId(d.getDeptId());
								domain.setEduId(d.getEduId());
								domain.setMajorId(d.getMajorId());
								domain.setPayCount(d.getPayCount());
								domain.setPayMoney(d.getPayMoney());
								domain.setSexCode(d.getSexCode());
								domain.setDeptName(d.getDeptName());
								domain.setMajorName(value.f1.f1);
								out.collect(domain);

							}
						})
				.join(codeEduDataSet)
				.where("eduId")
				.equalTo(0)
				.flatMap(
						new FlatMapFunction<Tuple2<TCardPayStatisticDomain, Tuple2<String, String>>, TCardPayStatisticDomain>() {

							@Override
							public void flatMap(
									Tuple2<TCardPayStatisticDomain, Tuple2<String, String>> value,
									Collector<TCardPayStatisticDomain> out)
									throws Exception {
								TCardPayStatisticDomain d=value.f0;
								TCardPayStatisticDomain domain = new TCardPayStatisticDomain();
								domain.setYearMonth(d.getYearMonth());
								domain.setHour(d.getHour());
								domain.setCardDealId(d.getCardDealId());
								domain.setCardPortId(d.getCardPortId());
								domain.setCardPortPid(d.getCardPortPid());
								domain.setDeptId(d.getDeptId());
								domain.setEduId(d.getEduId());
								domain.setMajorId(d.getMajorId());
								domain.setPayCount(d.getPayCount());
								domain.setPayMoney(d.getPayMoney());
								domain.setSexCode(d.getSexCode());
								domain.setDeptName(d.getDeptName());
								domain.setMajorName(d.getMajorName());
								domain.setEduName(value.f1.f1);
								out.collect(domain);

							}
						})
				.join(cardDealDataSet)
				.where("cardDealId")
				.equalTo(0)
				.flatMap(
						new FlatMapFunction<Tuple2<TCardPayStatisticDomain, Tuple2<String, String>>, TCardPayStatisticDomain>() {

							@Override
							public void flatMap(
									Tuple2<TCardPayStatisticDomain, Tuple2<String, String>> value,
									Collector<TCardPayStatisticDomain> out)
									throws Exception {
								TCardPayStatisticDomain d=value.f0;
								TCardPayStatisticDomain domain = new TCardPayStatisticDomain();
								domain.setYearMonth(d.getYearMonth());
								domain.setHour(d.getHour());
								domain.setCardDealId(d.getCardDealId());
								domain.setCardPortId(d.getCardPortId());
								domain.setCardPortPid(d.getCardPortPid());
								domain.setDeptId(d.getDeptId());
								domain.setEduId(d.getEduId());
								domain.setMajorId(d.getMajorId());
								domain.setPayCount(d.getPayCount());
								domain.setPayMoney(d.getPayMoney());
								domain.setSexCode(d.getSexCode());
								domain.setDeptName(d.getDeptName());
								domain.setMajorName(d.getMajorName());
								domain.setEduName(d.getEduName());
								domain.setCardDealName(value.f1.f1);
								out.collect(domain);

							}
						})
				.join(cardDeptDataSet)
				.where("cardPortPid")
				.equalTo(0)
				.flatMap(
						new FlatMapFunction<Tuple2<TCardPayStatisticDomain, Tuple2<String, String>>, TCardPayStatisticDomain>() {

							@Override
							public void flatMap(
									Tuple2<TCardPayStatisticDomain, Tuple2<String, String>> value,
									Collector<TCardPayStatisticDomain> out)
									throws Exception {
								TCardPayStatisticDomain d=value.f0;
								TCardPayStatisticDomain domain = new TCardPayStatisticDomain();
								domain.setYearMonth(d.getYearMonth());
								domain.setHour(d.getHour());
								domain.setCardDealId(d.getCardDealId());
								domain.setCardPortId(d.getCardPortId());
								domain.setCardPortPid(d.getCardPortPid());
								domain.setDeptId(d.getDeptId());
								domain.setEduId(d.getEduId());
								domain.setMajorId(d.getMajorId());
								domain.setPayCount(d.getPayCount());
								domain.setPayMoney(d.getPayMoney());
								domain.setSexCode(d.getSexCode());
								domain.setDeptName(d.getDeptName());
								domain.setMajorName(d.getMajorName());
								domain.setEduName(d.getEduName());
								domain.setCardDealName(d.getCardDealName());
								domain.setCardPortPname(value.f1.f1);
								out.collect(domain);

							}
						})
				.join(sexCodeDataSet)
				.where("sexCode")
				.equalTo(0)
				.flatMap(
						new FlatMapFunction<Tuple2<TCardPayStatisticDomain, Tuple2<String, String>>, TCardPayStatisticDomain>() {

							@Override
							public void flatMap(
									Tuple2<TCardPayStatisticDomain, Tuple2<String, String>> value,
									Collector<TCardPayStatisticDomain> out)
									throws Exception {
								TCardPayStatisticDomain d=value.f0;
								TCardPayStatisticDomain domain = new TCardPayStatisticDomain();
								domain.setYearMonth(d.getYearMonth());
								domain.setHour(d.getHour());
								domain.setCardDealId(d.getCardDealId());
								domain.setCardPortId(d.getCardPortId());
								domain.setCardPortPid(d.getCardPortPid());
								domain.setDeptId(d.getDeptId());
								domain.setEduId(d.getEduId());
								domain.setMajorId(d.getMajorId());
								domain.setPayCount(d.getPayCount());
								domain.setPayMoney(d.getPayMoney());
								domain.setSexCode(d.getSexCode());
								domain.setDeptName(d.getDeptName());
								domain.setMajorName(d.getMajorName());
								domain.setEduName(d.getEduName());
								domain.setCardDealName(d.getCardDealName());
								domain.setCardPortPname(d.getCardPortName());
								domain.setSexName(value.f1.f1);
								out.collect(domain);

							}
						});

		// DataSet<TCardPayStatisticDomain> statSet = t7DataSet
		// .flatMap(new FlatMapFunction<Tuple7<String, String, String, String,
		// String, Float, Long>, TCardPayStatisticDomain>() {
		//
		// @Override
		// public void flatMap(
		// Tuple7<String, String, String, String, String, Float, Long> value,
		// Collector<TCardPayStatisticDomain> out)
		// throws Exception {
		// /*
		// * value.f0 cardId value.f1 cardPortId value.f2
		// * cardDealId value.f3 year-month value.f4 hour value.f5
		// * payMoney value.f6 payCount
		// */
		// String cardInfo = RedisClientUtils.getMap("T_CARD",
		// value.f0);
		// if (cardInfo != null) {
		// String cardPortInfo = RedisClientUtils.getMap(
		// "T_CARD_PORT", value.f1);
		// JSONObject card = JSON.parseObject(cardInfo);
		// String peopleId = card.getString("peopleId");
		// String cardIdentityId = card
		// .getString("cardIdentityId");
		// String cardIdentityInfo = RedisClientUtils.getMap(
		// "T_CODE_CARD_IDENTITY", cardIdentityId);
		// JSONObject cardIdentity = JSON
		// .parseObject(cardIdentityInfo);
		// String pid_identity = cardIdentity.getString("pid");
		// if (pid_identity.equals("P1")) {
		// // 学生，从学生表读数据
		// String stuInfo = RedisClientUtils.getMap(
		// "T_STU", peopleId);
		// JSONObject stu = JSON.parseObject(stuInfo);
		// String deptId = stu.getString("dept_id");
		// String majorId = stu.getString("major_id");
		// String eduId = stu.getString("edu_id");
		// String sexCode = stu.getString("sex_code");
		// TCardPayStatisticDomain domain = new TCardPayStatisticDomain();
		// domain.setYearMonth(value.f3);
		// domain.setHour(value.f4);
		// domain.setCardDealId(value.f2);
		// domain.setCardPortId(value.f1);
		// domain.setDeptId(deptId);
		// domain.setEduId(eduId);
		// domain.setMajorId(majorId);
		// domain.setPayCount(value.f6.intValue());
		// domain.setPayMoney(value.f5);
		// domain.setSexCode(sexCode);
		// out.collect(domain);
		// } else {
		// // 从职工表读数据
		// }
		// } else {
		// System.out.println(value.f0);
		// }
		// //
		// }
		// });
		// DataSet<TCardPayStatisticDomain> finalSet = statSet
		// .groupBy("yearMonth", "hour", "cardPortId", "cardDealId",
		// "sexCode", "deptId", "majorId", "eduId")
		// .reduce(new ReduceFunction<TCardPayStatisticDomain>() {
		//
		// @Override
		// public TCardPayStatisticDomain reduce(
		// TCardPayStatisticDomain value1,
		// TCardPayStatisticDomain value2) throws Exception {
		// // TODO Auto-generated method stub
		// TCardPayStatisticDomain domain = new TCardPayStatisticDomain();
		// domain.setYearMonth(value1.getYearMonth());
		// domain.setHour(value1.getHour());
		// domain.setCardDealId(value1.getCardDealId());
		// domain.setCardPortId(value1.getCardPortId());
		// domain.setDeptId(value1.getDeptId());
		// domain.setEduId(value1.getEduId());
		// domain.setMajorId(value1.getMajorId());
		// domain.setPayCount(value1.getPayCount()
		// + value2.getPayCount());
		// domain.setPayMoney(value1.getPayMoney()
		// + value2.getPayMoney());
		// domain.setSexCode(value1.getSexCode());
		// return domain;
		// }
		// })
		// .map(new MapFunction<TCardPayStatisticDomain,
		// TCardPayStatisticDomain>() {
		//
		// @Override
		// public TCardPayStatisticDomain map(
		// TCardPayStatisticDomain value) throws Exception {
		// String cardDealInfo = RedisClientUtils.getMap(
		// "T_CODE_CARD_DEAL", value.getCardDealId());
		// String cardPortInfo = RedisClientUtils.getMap(
		// "T_CARD_PORT", value.getCardPortId());
		// String cardPortPInfo = RedisClientUtils.getMap(
		// "T_CARD_DEPT", value.getCardPortPid());
		// String deptInfo = RedisClientUtils.getMap(
		// "T_CODE_DEPT_TEACH", value.getDeptId());
		// String eduInfo = RedisClientUtils.getMap(
		// "T_CODE_EDUCATION", value.getEduId());
		// String majorInfo = RedisClientUtils.getMap(
		// "T_CODE_DEPT_TEACH", value.getMajorId());
		// String sexInfo = RedisClientUtils.getMap("T_CODE",
		// "SEX_CODE_" + value.getSexCode());
		// JSONObject cardDeal = JSON.parseObject(cardDealInfo);
		// JSONObject cardPort = JSON.parseObject(cardPortInfo);
		// JSONObject cardPortP = JSON.parseObject(cardPortPInfo);
		// JSONObject dept = JSON.parseObject(deptInfo);
		// JSONObject edu = JSON.parseObject(eduInfo);
		// JSONObject major = JSON.parseObject(majorInfo);
		// JSONObject sex = JSON.parseObject(sexInfo);
		// value.setCardDealName(cardDeal.getString("name_"));
		// value.setCardPortName(cardPort.getString("name_"));
		// value.setCardPortPname(cardPortP.getString("name_"));
		// value.setDeptName(dept.getString("name_"));
		// value.setEduName(edu.getString("name_"));
		// value.setMajorName(major.getString("name_"));
		// value.setSexName(sex.getString("name_"));
		// return value;
		// }
		// });
		// finalSet.writeAsText("hdfs://ns1/cardpay/stat/" + "year=" + year
		// + "/month=" + month + "/", WriteMode.OVERWRITE);
		// finalDataSet.writeAsText("d:/cardpay/stat/",
		finalDataSet.writeAsText("hdfs://ns1/cardpay/stat/",
				WriteMode.OVERWRITE);
		// t5DataSet.writeAsText("hdfs://ns1/cardpay/statistic/"+"year="+year+"/month="+month+"/");
		env.execute("begin run job");
	}
}
