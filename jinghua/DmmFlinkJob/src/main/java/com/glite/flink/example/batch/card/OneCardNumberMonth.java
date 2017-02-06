package com.glite.flink.example.batch.card;

import java.util.Iterator;

import org.apache.flink.api.common.functions.FlatJoinFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple10;
import org.apache.flink.api.java.tuple.Tuple11;
import org.apache.flink.api.java.tuple.Tuple12;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.api.java.tuple.Tuple6;
import org.apache.flink.api.java.tuple.Tuple7;
import org.apache.flink.api.java.tuple.Tuple8;
import org.apache.flink.api.java.tuple.Tuple9;
import org.apache.flink.util.Collector;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glite.flink.utils.RedisClientUtils;

/*
 * 一卡通持卡人数
 */
public class OneCardNumberMonth {
	
	public static void main(String[] args) throws Exception {
		final ExecutionEnvironment env=ExecutionEnvironment.getExecutionEnvironment();
		DataSet<Tuple4<String,String, String,String>> cardDataSet = env
				.fromCollection(RedisClientUtils.getAllMap("T_CARD").values())
				.flatMap(
						new FlatMapFunction<String, Tuple4<String,String,String, String>>() {
							@Override
							public void flatMap(
									String value,
									Collector<Tuple4<String,String,String, String>> out)
									throws Exception {
								JSONObject json = JSON.parseObject(value);
								out.collect(new Tuple4<String,String,String, String>(
										json.getString("id"), json
												.getString("peopleId"),
												json.getString("cardIdentityId")
												,json.getString("time_")));
							}
						});
		
		
		
		DataSet<Tuple5<String, String, String, String, String>> stuDataSet = env
				.readTextFile("hdfs://192.168.100.132:9000/stu")
				.flatMap(new FlatMapFunction<String, Tuple5<String,String,String,String,String>>() {

					@Override
					public void flatMap(String value,
							Collector<Tuple5<String,  String, String, String, String>> out) throws Exception {
						// TODO Auto-generated method stub
						//Tuple5(no_,sex-code,major_id,dept_id,deu_id)
						String[] f = value.replaceAll("'", "").split(",");
						out.collect(new Tuple5<String,String,String,String,String>(f[1], f[8], f[14], f[13],f[18]));	
					}	
				});
		
		
		DataSet<Tuple2<String, String>> codeEduDataSet = env.fromCollection(
				RedisClientUtils.getAllMap("T_CODE_EDUCATION").values()).flatMap(
				new FlatMapFunction<String, Tuple2<String, String>>() {

					@Override
					public void flatMap(String value,
							Collector<Tuple2<String, String>> out)
							throws Exception {
						JSONObject json = JSON.parseObject(value);
						out.collect(new Tuple2<String, String>(json
								.getString("code_"), json.getString("name_")));
					}
				});
		
		DataSet<Tuple2<String, String>> codeDeptDataSet = env.fromCollection(
				RedisClientUtils.getAllMap("T_CODE_DEPT_TEACH").values()).flatMap(
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
		
		DataSet<Tuple2<String, String>> codeCardIdentity = env.fromCollection(
				RedisClientUtils.getAllMap("T_CODE_CARD_IDENTITY").values()).flatMap(
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
		
		DataSet<Tuple2<String, String>> codeCardIdentity2 = env.fromCollection(
				RedisClientUtils.getAllMap("T_CODE_CARD_IDENTITY").values()).flatMap(
				new FlatMapFunction<String, Tuple2<String, String>>() {

					@Override
					public void flatMap(String value,
							Collector<Tuple2<String, String>> out)
							throws Exception {
						JSONObject json = JSON.parseObject(value);
						out.collect(new Tuple2<String, String>(json
								.getString("id"), json.getString("pid")));
					}
				});
		DataSet<Tuple2<String, String>> codeCardIdentity3 = env.fromCollection(
				RedisClientUtils.getAllMap("T_CODE_CARD_IDENTITY").values()).flatMap(
				new FlatMapFunction<String, Tuple2<String, String>>() {

					@Override
					public void flatMap(String value,
							Collector<Tuple2<String, String>> out)
							throws Exception {
						JSONObject json = JSON.parseObject(value);
						out.collect(new Tuple2<String, String>(json
								.getString("code_"), json.getString("name_")));
					}
				});
	
		
		DataSet<Tuple4<String,String,String, String>> teaDataSet = env.fromCollection(
				RedisClientUtils.getAllMap("T_TEA").values()).flatMap(
				new FlatMapFunction<String, Tuple4<String,String,String, String>>() {

					@Override
					public void flatMap(String value,
							Collector<Tuple4<String,String,String, String>> out)
							throws Exception {
						JSONObject json = JSON.parseObject(value);
						out.collect(new Tuple4<String, String,String,String>(json
								.getString("tea_no"), json.getString("sex_code"),
								json.getString("dept_id"),json.getString("edu_id")
								));
					}
				});
		
		
		//stu和tea合并teaDataSet
		DataSet unionSet=teaDataSet.flatMap(new FlatMapFunction<Tuple4<String,String,String,String>, Tuple5<String, String, String, String, String>>() {
			//Tuple5(tea_no,sex-code,major_id,dept_id,deu_id)    其中教师major_id字段为空
			@Override
			public void flatMap(Tuple4<String, String, String, String> value,
					Collector<Tuple5<String, String, String, String, String>> out) throws Exception {
				// TODO Auto-generated method stub
				out.collect(new Tuple5<String, String, String, String, String>(value.f0, value.f1, "", value.f2, value.f3));
				
				
			}
		}).union(stuDataSet);
		
	
	
		//card和unionSet相关联
		DataSet <Tuple7<String, String, String, String, String, String, String>>cartAndStu=cardDataSet.
		join(unionSet).
		where(1).
		equalTo(0).
		flatMap(new FlatMapFunction<Tuple2<Tuple4<String,String,String,String>,Tuple5<String,String,String,String,String>>, Tuple7<String, String, String, String, String, String, String>>() {

			@Override
			public void flatMap(
					Tuple2<Tuple4<String, String, String, String>, Tuple5<String, String, String, String, String>> value,
					Collector<Tuple7<String, String, String, String, String, String, String>> out) throws Exception {
				// TODO Auto-generated method stub
				//card   (id,peopleId,cardIdentityId,time_)
				//stu    (no_,sex-code,major_id,dept_id,deu_id)
				//Tuple7 (id,time_,sex_code,cardIdentityId,major_id,dept_id,deu_id)
				out.collect(new Tuple7<String, String, String, String, String, String, String>(value.f0.f0,value.f0.f3, value.f1.f1, value.f0.f2, value.f1.f2, value.f1.f3, value.f1.f4));
				
			}
		
		});
		
		
		DataSet <Tuple8<String, String, String, String, String, String, String, String>>cartAndStuAndEdu=cartAndStu.
		join(codeEduDataSet).
		where(6).
		equalTo(0).
		flatMap(new FlatMapFunction<Tuple2<Tuple7<String,String,String,String,String,String,String>,Tuple2<String,String>>, Tuple8<String, String, String, String, String, String, String, String>>() {

			@Override
			public void flatMap(
					Tuple2<Tuple7<String, String, String, String, String, String, String>, Tuple2<String, String>> value,
					Collector<Tuple8<String, String, String, String, String, String, String, String>> out)
					throws Exception {
				// TODO Auto-generated method stub
                //  (id,time_,sex_code,cardIdentityId,major_id,dept_id,deu_id)
				//  (code_,name)
				//  (id,time_,sex_code,cardIdentityId,major_id,dept_id,deu_id,edu_name)
				
				out.collect(new Tuple8<String, String, String, String, String, String, String, String>(value.f0.f0, value.f0.f1, value.f0.f2, value.f0.f3, value.f0.f4, value.f0.f5, value.f0.f6, value.f1.f1));
				
			}
		});
		
		
		
		DataSet <Tuple9<String, String, String, String, String, String, String, String, String>> cartAndStuAndEduAndDept=cartAndStuAndEdu.join(codeDeptDataSet).
				where(5).
				equalTo(0).
				flatMap(new FlatMapFunction<Tuple2<Tuple8<String,String,String,String,String,String,String,String>,Tuple2<String,String>>, Tuple9<String, String, String, String, String, String, String, String, String>>() {

			@Override
			public void flatMap(
					Tuple2<Tuple8<String, String, String, String, String, String, String, String>, Tuple2<String, String>> value,
					Collector<Tuple9<String, String, String, String, String, String, String, String, String>> out)
					throws Exception {
				// TODO Auto-generated method stub
			    //  (id,time_,sex_code,cardIdentityId,major_id,dept_id,deu_id,edu_name)
				//  (id,name_)
				//  (id,time_,sex_code,cardIdentityId,major_id,dept_id,dept_name,deu_id,edu_name)
				out.collect(new Tuple9<String, String, String, String, String, String, String, String, String>(value.f0.f0, value.f0.f1, value.f0.f2, value.f0.f3, value.f0.f4, value.f0.f5, value.f1.f1, value.f0.f6, value.f0.f7));
				
			}
		});
	
		/*
		 * major_id字段连接，
		 */
		
		
		DataSet<Tuple10<String, String, String, String, String, String, String, String, String, String>> cartAndStuAndEduAndDeptAndMajor=cartAndStuAndEduAndDept.
		leftOuterJoin(codeDeptDataSet).
		where(4).
		equalTo(0).
		with(new FlatJoinFunction<Tuple9<String,String,String,String,String,String,String,String,String>, Tuple2<String,String>, Tuple10<String, String, String, String, String, String, String, String, String, String>>() {

			@Override
			public void join(Tuple9<String, String, String, String, String, String, String, String, String> v1,
					Tuple2<String, String> v2,
					Collector<Tuple10<String, String, String, String, String, String, String, String, String, String>> out)
					throws Exception {
				// TODO Auto-generated method stub
				//  (id,time_,sex_code,cardIdentityId,major_id,dept_id,dept_name,deu_id,edu_name)
				//  (id,name)
				//  (id,time_,sex_code,cardIdentityId,major_id, major_name,dept_id,dept_name,deu_id,edu_name)
				out.collect(new Tuple10<String, String, String, String, String, String, String, String, String, String>
				(v1.f0, v1.f1, v1.f2, v1.f3, v1.f4, (v2!=null)?v2.f1:"", v1.f5, v1.f6, v1.f7, v1.f8));
			}
		});

	
		/*
		 * codeCardIdentity字段关联。因为该字段在表种种是以树的形式存在的，所以需要连续关联
		 */
		DataSet<Tuple10<String, String,String, String, String, String, String, String, String, String>> IdentitySet=cartAndStuAndEduAndDeptAndMajor.
		join(codeCardIdentity2).
		where(3).equalTo(0).
		flatMap(new FlatMapFunction<Tuple2<Tuple10<String,String,String,String,String,String,String,String,String,String>,Tuple2<String,String>>, Tuple10<String,String,String,String,String,String,String,String,String,String>>() {

			@Override
			public void flatMap(
					Tuple2<Tuple10<String,String, String, String, String, String, String, String, String, String>, Tuple2<String, String>> value,
					Collector<Tuple10<String,String, String, String, String, String, String, String, String, String>> out)
					throws Exception {
				// TODO Auto-generated method stub
			//  (id,time_,sex_code,cardIdentityId,major_id, major_name,dept_id,dept_name,deu_id,edu_name) 
			//  (id,time_,sex_code,pid,major_id, major_name,dept_id,dept_name,deu_id,edu_name) 
				out.collect(new Tuple10<String, String,String, String, String, String, String, String, String, String>
				(value.f0.f0,value.f0.f1,value.f0.f2,value.f1.f1,value.f0.f4,value.f0.f5,value.f0.f6,value.f0.f7,value.f0.f8,value.f0.f9));
				
			}
		});
		
		
	    //  (id,time_,sex_code,pid,major_id, major_name,dept_id,dept_name,deu_id,edu_name) 
		DataSet<Tuple11<String, String, String, String, String, String, String, String, String, String, String>> IdentitySetDouble=IdentitySet.join(codeCardIdentity3).where(3).equalTo(0).flatMap(new FlatMapFunction<Tuple2<Tuple10<String,String,String,String,String,String,String,String,String,String>,Tuple2<String,String>>, Tuple11<String, String, String, String, String, String, String, String, String, String, String>>() {

			@Override
			public void flatMap(
					Tuple2<Tuple10<String, String, String, String, String, String, String, String, String, String>, Tuple2<String, String>> value,
					Collector<Tuple11<String, String, String, String, String, String, String, String, String, String, String>> out)
					throws Exception {
				// TODO Auto-generated method stub
			//  (id,time_,sex_code,pid,major_id, major_name,dept_id,dept_name,deu_id,edu_name)
			//  (id,time_,sex_code,pid,pid_name,major_id, major_name,dept_id,dept_name,deu_id,edu_name)
				out.collect(new Tuple11<String, String, String, String, String, String, String, String, String, String, String>
				(value.f0.f0, value.f0.f1, value.f0.f2, value.f0.f3, value.f1.f1, value.f0.f4, value.f0.f5, value.f0.f6, value.f0.f7, value.f0.f8, value.f0.f9));
				
			}
		});
		
		
	
		DataSet<Tuple10<String, String, String, String, String, String, String, String, String, String>> yearM=IdentitySetDouble.flatMap(new FlatMapFunction<Tuple11<String,String,String,String,String,String,String,String,String,String,String>, Tuple10<String,String,String,String,String,String,String,String,String,String>>() {

			@Override
			public void flatMap(
					Tuple11<String, String, String, String, String, String, String, String, String, String, String> value,
					Collector<Tuple10<String, String, String, String, String, String, String, String, String, String>> out)
					throws Exception {
				// TODO Auto-generated method stub
			//  (id,time_,sex_code,people_par_code,people_par_name,major_id, major_name,dept_id,dept_name,deu_id,edu_name)
			//  (yy-mm,sex_code,people_par_code,people_par_name,major_id, major_name,dept_id,dept_name,deu_id,edu_name)
				String[]v=value.f1.split("-");
				out.collect(new Tuple10<String, String, String, String, String, String, String, String, String, String>(v[0]+"-"+v[1], value.f2, value.f3, value.f4, value.f5, value.f6, value.f7, value.f8, value.f9, value.f10));
			}
		});
		
		
	
		
		/*
		 * 最终字段
		 * (yy-mm,people_num,sex_code,people_code,people_name,people_par_code,people_par_name,sex_name,major_id,major_name,dept_id,dept_name,edu_id,edu_name)
		 */	
		
		/*
		 * 分组
		 */
		DataSet groupSet=yearM.groupBy(0,1,2,4,6,8).reduceGroup(new GroupReduceFunction<Tuple10<String,String,String,String,String,String,String,String,String,String>, Tuple11<String,String,String,String,String,String,String,String,String,String,String>>() {

			@Override
			public void reduce(
					Iterable<Tuple10<String, String, String, String, String, String, String, String, String, String>> value,
					Collector<Tuple11<String, String, String, String, String, String, String, String, String, String, String>> out)
					throws Exception {
				// TODO Auto-generated method stub
				long sum=0;
				Tuple10 t=null;
			//  (yy-mm,sex_code,people_par_code,people_par_name,major_id, major_name,dept_id,dept_name,deu_id,edu_name)
			//  (yy-mm,people_num,sex_code,people_par_code,people_par_name,sex_name,major_id,major_name,dept_id,dept_name,edu_id,edu_name)
				for (Iterator<Tuple10<String, String, String, String, String, String, String, String, String, String>> it = value.iterator(); it.hasNext();) {
					 t=it.next();
					 sum=sum+1;
				}
				out.collect(new Tuple11<String, String, String, String, String, String, String, String, String, String, String>
				(t.f0.toString(),Long.toString(sum) , t.f1.toString(), t.f2.toString(), t.f3.toString(), t.f4.toString(), t.f5.toString(), t.f6.toString(), t.f7.toString(), t.f8.toString(), t.f9.toString()));				
			}
		});
		
		
		groupSet.print();
		
	
		
	}
	

}
