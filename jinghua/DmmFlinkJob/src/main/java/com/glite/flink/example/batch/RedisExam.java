package com.glite.flink.example.batch;

import java.util.Map;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapPartitionFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.api.java.tuple.Tuple7;
import org.apache.flink.core.fs.FileSystem.WriteMode;
import org.apache.flink.util.Collector;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glite.flink.utils.RedisClientUtils;

public class RedisExam {
	public static void main(String[] args) throws Throwable {
		final ExecutionEnvironment env = ExecutionEnvironment
				.getExecutionEnvironment();
		
		// 学生数据集
				DataSet<Tuple5<String, String, String, String, String>> stuDataSet = env
						.readTextFile("d:/cardpay/stu")
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
//										System.out.println(new Tuple5<String, String, String, String, String>(
//												f[1], f[8], f[13], f[14], f[18]));
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
		
//		Map map=RedisClientUtils.getAllMap("T_CARD");
//		DataSource<String> source=env.fromCollection(map.values());
		stuCardDataSet.writeAsText("D:/cardpay/stuCardDataSet/",WriteMode.OVERWRITE);
		cardPortDataSet.writeAsText("D:/cardpay/cardPortDataSet/",WriteMode.OVERWRITE);
		env.execute();
//		DataSource<String> paySource = env.readTextFile("hdfs://ns1/cardpay");
//		DataSet<Tuple3<String, Float, String>> paySet=paySource.flatMap(new FlatMapFunction<String, Tuple3<String,Float,String>>() {
//
//			@Override
//			public void flatMap(String value,
//					Collector<Tuple3<String, Float, String>> out)
//					throws Exception {
//				String[] items=value.replaceAll("'", "").split(",");
//				out.collect(new  Tuple3<>(items[1],Float.valueOf(items[2]),items[5]));
//			}
//		});
//		
//		DataSet<Tuple3<String, Float, String>> paySet1=paySet.groupBy(0,2).reduce(new ReduceFunction<Tuple3<String,Float,String>>() {
//			
//			@Override
//			public Tuple3<String, Float, String> reduce(
//					Tuple3<String, Float, String> value1,
//					Tuple3<String, Float, String> value2) throws Exception {
//				
//				return new Tuple3<String, Float, String>(value1.f0,value1.f1+value2.f1,value1.f2) ;
//			}
//		});
//		paySet1.mapPartition(new MapPartitionFunction<Tuple3<String,Float,String>, Tuple3<String,Float,String>>( ) {
//
//			@Override
//			public void mapPartition(
//					Iterable<Tuple3<String, Float, String>> values,
//					Collector<Tuple3<String, Float, String>> out)
//					throws Exception {
//				// TODO Auto-generated method stub
//				
//			}
//		});

	}
}
