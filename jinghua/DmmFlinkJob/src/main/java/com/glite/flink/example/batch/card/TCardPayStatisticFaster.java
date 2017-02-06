package com.glite.flink.example.batch.card;

import java.util.Calendar;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.GroupCombineFunction;
import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.io.TextInputFormat;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.api.java.tuple.Tuple7;
import org.apache.flink.api.java.tuple.Tuple9;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.core.fs.Path;
import org.apache.flink.core.fs.FileSystem.WriteMode;
import org.apache.flink.util.Collector;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glite.flink.example.domain.TCardPay;
import com.glite.flink.example.domain.TCardPayStatisticDomain;
import com.glite.flink.utils.RedisClientUtils;

public class TCardPayStatisticFaster {
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
								String[] f = value.replaceAll("'", "").split(
										",");
								out.collect(new Tuple5<String, String, String, String, String>(
										f[1], f[8], f[13], f[14], f[18]));
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
		DataSet<TCardPayStatisticDomain> statDataSet  = paySet
				.groupBy(
						new KeySelector<TCardPay, Tuple5<String, String, String, String, String>>() {
							@Override
							public Tuple5<String, String, String, String, String> getKey(
									TCardPay value) throws Exception {
								// 按cardId,cardPortId,cardDealId,yearmonth,hour 分组
								return new Tuple5<String, String, String, String, String>(
										value.getCardId(), value
												.getCardPortId(), value
												.getCardDealId(), value
												.getTime_().substring(0, 7),
										value.getTime_().substring(11, 13));
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
				.join(stuCardDataSet)
				.where(0)
				.equalTo(5)
				.groupBy(
						new KeySelector<Tuple2<Tuple7<String, String, String, String, String, Float, Long>, Tuple7<String, String, String, String, String, String, String>>, Tuple9<String, String, String, String, String, String, String, String, String>>() {

							@Override
							public Tuple9<String, String, String, String, String, String, String, String, String> getKey(
									Tuple2<Tuple7<String, String, String, String, String, Float, Long>, Tuple7<String, String, String, String, String, String, String>> value)
									throws Exception {

								// "yearMonth", "hour", "cardPortId",
								// "cardPortPid",
								// "cardDealId", "sexCode", "deptId", "majorId",
								// "eduId"
								return new Tuple9<String, String, String, String, String, String, String, String, String>(
										value.f0.f0, value.f0.f1, value.f0.f2,
										value.f0.f3, value.f0.f4, value.f1.f1,
										value.f1.f2, value.f1.f3, value.f1.f4);
							}
						}).reduceGroup(new GroupReduceFunction<Tuple2<Tuple7<String,String,String,String,String,Float,Long>,Tuple7<String,String,String,String,String,String,String>>, TCardPayStatisticDomain>() {

							@Override
							public void reduce(
									Iterable<Tuple2<Tuple7<String, String, String, String, String, Float, Long>, Tuple7<String, String, String, String, String, String, String>>> values,
									Collector<TCardPayStatisticDomain> out)
									throws Exception {
								TCardPayStatisticDomain domain=null;
								/*
								 * 
								 * no_,dept_id,major_id,edu_id,sex_code,card_id,card_identity_id
								 */
								for(Tuple2<Tuple7<String, String, String, String, String, Float, Long>, Tuple7<String, String, String, String, String, String, String>> t2:values){
									if(domain==null){
										domain=new TCardPayStatisticDomain();
										domain.setYearMonth(t2.f0.f3);
										domain.setHour(t2.f0.f4);
										domain.setCardDealId(t2.f0.f2);
										domain.setCardPortId(t2.f0.f1);
										domain.setDeptId(t2.f1.f1);
										domain.setEduId(t2.f1.f3);
										domain.setMajorId(t2.f1.f2);
										domain.setPayCount(t2.f0.f6.intValue());
										domain.setPayMoney(t2.f0.f5);
										domain.setSexCode(t2.f1.f4);
									}else{
										domain.setPayCount(domain.getPayCount()+t2.f0.f6.intValue());
										domain.setPayMoney(domain.getPayMoney()+t2.f0.f5);
									}
								}
								out.collect(domain);
							}
						}).groupBy("yearMonth", "hour", "cardPortId", 
								"cardDealId", "sexCode", "deptId", "majorId", "eduId").reduceGroup(new GroupReduceFunction<TCardPayStatisticDomain, TCardPayStatisticDomain>() {

									@Override
									public void reduce(
											Iterable<TCardPayStatisticDomain> values,
											Collector<TCardPayStatisticDomain> out)
											throws Exception {
										TCardPayStatisticDomain domain = null;
										for(TCardPayStatisticDomain d:values){
											if(domain==null){
												domain=new TCardPayStatisticDomain();
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
											}else{
												domain.setPayCount(domain.getPayCount()
														+ d.getPayCount());
												domain.setPayMoney(domain.getPayMoney()
														+ d.getPayMoney());
											}
										}
										out.collect(domain);
									}
								});
		
		DataSet<TCardPayStatisticDomain> finalDataSet = statDataSet
				.joinWithTiny(cardPortDataSet)
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
				
				.joinWithTiny(codeDeptTeachDataSet)
				.where("deptId")
				.equalTo(0)
				.flatMap(
						new FlatMapFunction<Tuple2<TCardPayStatisticDomain, Tuple2<String, String>>, TCardPayStatisticDomain>() {

							@Override
							public void flatMap(
									Tuple2<TCardPayStatisticDomain, Tuple2<String, String>> value,
									Collector<TCardPayStatisticDomain> out)
									throws Exception {
								TCardPayStatisticDomain d = value.f0;
								TCardPayStatisticDomain domain = new TCardPayStatisticDomain();
								domain.setYearMonth(d.getYearMonth());
								domain.setHour(d.getHour());
								domain.setCardDealId(d.getCardDealId());
								domain.setCardPortId(d.getCardPortId());
								domain.setCardPortPid(d.getCardPortPid());
								domain.setCardPortName(d.getCardPortName());
								domain.setCardPortPname(d.getCardPortPname());
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
				.joinWithTiny(codeMajorDataSet)
				.where("majorId")
				.equalTo(0)
				.flatMap(
						new FlatMapFunction<Tuple2<TCardPayStatisticDomain, Tuple2<String, String>>, TCardPayStatisticDomain>() {

							@Override
							public void flatMap(
									Tuple2<TCardPayStatisticDomain, Tuple2<String, String>> value,
									Collector<TCardPayStatisticDomain> out)
									throws Exception {
								TCardPayStatisticDomain d = value.f0;
								TCardPayStatisticDomain domain = new TCardPayStatisticDomain();
								domain.setYearMonth(d.getYearMonth());
								domain.setHour(d.getHour());
								domain.setCardDealId(d.getCardDealId());
								domain.setCardPortId(d.getCardPortId());
								domain.setCardPortPid(d.getCardPortPid());
								domain.setCardPortName(d.getCardPortName());
								domain.setCardPortPname(d.getCardPortPname());
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
				.joinWithTiny(codeEduDataSet)
				.where("eduId")
				.equalTo(0)
				.flatMap(
						new FlatMapFunction<Tuple2<TCardPayStatisticDomain, Tuple2<String, String>>, TCardPayStatisticDomain>() {

							@Override
							public void flatMap(
									Tuple2<TCardPayStatisticDomain, Tuple2<String, String>> value,
									Collector<TCardPayStatisticDomain> out)
									throws Exception {
								TCardPayStatisticDomain d = value.f0;
								TCardPayStatisticDomain domain = new TCardPayStatisticDomain();
								domain.setYearMonth(d.getYearMonth());
								domain.setHour(d.getHour());
								domain.setCardDealId(d.getCardDealId());
								domain.setCardPortId(d.getCardPortId());
								domain.setCardPortPid(d.getCardPortPid());
								domain.setCardPortName(d.getCardPortName());
								domain.setCardPortPname(d.getCardPortPname());
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
				.joinWithTiny(cardDealDataSet)
				.where("cardDealId")
				.equalTo(0)
				.flatMap(
						new FlatMapFunction<Tuple2<TCardPayStatisticDomain, Tuple2<String, String>>, TCardPayStatisticDomain>() {

							@Override
							public void flatMap(
									Tuple2<TCardPayStatisticDomain, Tuple2<String, String>> value,
									Collector<TCardPayStatisticDomain> out)
									throws Exception {
								TCardPayStatisticDomain d = value.f0;
								TCardPayStatisticDomain domain = new TCardPayStatisticDomain();
								domain.setYearMonth(d.getYearMonth());
								domain.setHour(d.getHour());
								domain.setCardDealId(d.getCardDealId());
								domain.setCardPortId(d.getCardPortId());
								domain.setCardPortPid(d.getCardPortPid());
								domain.setCardPortName(d.getCardPortName());
								domain.setCardPortPname(d.getCardPortPname());
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
				.joinWithTiny(cardDeptDataSet)
				.where("cardPortPid")
				.equalTo(0)
				.flatMap(
						new FlatMapFunction<Tuple2<TCardPayStatisticDomain, Tuple2<String, String>>, TCardPayStatisticDomain>() {

							@Override
							public void flatMap(
									Tuple2<TCardPayStatisticDomain, Tuple2<String, String>> value,
									Collector<TCardPayStatisticDomain> out)
									throws Exception {
								TCardPayStatisticDomain d = value.f0;
								TCardPayStatisticDomain domain = new TCardPayStatisticDomain();
								domain.setYearMonth(d.getYearMonth());
								domain.setHour(d.getHour());
								domain.setCardDealId(d.getCardDealId());
								domain.setCardPortId(d.getCardPortId());
								domain.setCardPortPid(d.getCardPortPid());
								domain.setCardPortName(d.getCardPortName());
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
				.joinWithTiny(sexCodeDataSet)
				.where("sexCode")
				.equalTo(0)
				.flatMap(
						new FlatMapFunction<Tuple2<TCardPayStatisticDomain, Tuple2<String, String>>, TCardPayStatisticDomain>() {

							@Override
							public void flatMap(
									Tuple2<TCardPayStatisticDomain, Tuple2<String, String>> value,
									Collector<TCardPayStatisticDomain> out)
									throws Exception {
								TCardPayStatisticDomain d = value.f0;
								TCardPayStatisticDomain domain = new TCardPayStatisticDomain();
								domain.setYearMonth(d.getYearMonth());
								domain.setHour(d.getHour());
								domain.setCardDealId(d.getCardDealId());
								domain.setCardPortId(d.getCardPortId());
								domain.setCardPortPid(d.getCardPortPid());
								domain.setCardPortName(d.getCardPortName());
								domain.setCardPortPname(d.getCardPortPname());
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
								domain.setSexName(value.f1.f1);
								out.collect(domain);

							}
						});

		finalDataSet.writeAsText("hdfs://ns1/cardpay/stat/",
				WriteMode.OVERWRITE);
		// t5DataSet.writeAsText("hdfs://ns1/cardpay/statistic/"+"year="+year+"/month="+month+"/");
		env.execute("begin run job");
	}
}
