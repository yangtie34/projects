package com.glite.flink.example.batch.card;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.TextInputFormat;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.IterativeDataSet;
import org.apache.flink.api.java.tuple.Tuple10;
import org.apache.flink.api.java.tuple.Tuple11;
import org.apache.flink.api.java.tuple.Tuple12;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.api.java.tuple.Tuple6;
import org.apache.flink.api.java.tuple.Tuple7;
import org.apache.flink.api.java.tuple.Tuple8;
import org.apache.flink.api.java.tuple.Tuple9;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.core.fs.Path;
import org.apache.flink.core.fs.FileSystem.WriteMode;
import org.apache.flink.util.Collector;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glite.flink.example.domain.TCardPay;
import com.glite.flink.utils.RedisClientUtils;

/*
 * 学生用餐习惯统计
 */
public class TStuEatCustomStatistics {
	public static void main(String[] args) throws Exception {
		ParameterTool params = ParameterTool.fromArgs(args);
		Calendar cal = Calendar.getInstance();
//		String path = "hdfs://ns1/cardpay/year=";
		 String path = "f:/cardpay/year=";
		path += (params.get("year") != null ? params.get("year") : cal
				.get(Calendar.MONTH));
		if (params.get("month") != null) {
			path += "/month=" + params.get("month");
		}
		if (params.get("day") != null) {
			path += "/day=" + params.get("day");
		}
		
		
		final ExecutionEnvironment env=ExecutionEnvironment.getExecutionEnvironment();
		
		//paycard    在本地文件系统读取
//		String path="F:\\cardpay\\year=2016";

		TextInputFormat format = new TextInputFormat(new Path(path));
		format.setNestedFileEnumeration(true);
		DataSource<String> paySource = env.readFile(format, path);
		
		DataSet<Tuple6<String,String, String, String, String, String>> paySet = paySource.flatMap(new FlatMapFunction<String, Tuple6<String,String, String, String, String, String>>() {
			@Override
			public void flatMap(String value, Collector<Tuple6<String,String, String, String, String, String>> out)
					throws Exception {
				// TODO Auto-generated method stub
				String[]vs=value.replaceAll("'", "").split(",");
				String[]v=vs[4].split(" ");
				//Tuple6(id,card_id,y-m-d,h-m-s,car_port_id,card_deal_id)
				out.collect(new Tuple6<String,String, String, String, String, String>(vs[0],vs[1],v[0],v[1],vs[5],vs[6]));	
			}
		});
		
 
		
		
	
	
		// 学生数据集stu
		// 学生数据集f[1], f[3], f[8], f[13], f[14]),f[18]
		DataSet<Tuple6<String, String, String, String, String, String>> stuDataSet = env
				.readTextFile("hdfs://192.168.100.132:9000/stu")
				.flatMap(new FlatMapFunction<String, Tuple6<String,String,String,String,String,String>>() {

					@Override
					public void flatMap(String value,
							Collector<Tuple6<String, String, String, String, String, String>> out) throws Exception {
						// TODO Auto-generated method stub
						//Tuple6(no_,name,sex-code,dept_id,major——id，deu_id)
						String[] f = value.replaceAll("'", "").split(",");
						out.collect(new Tuple6<String,String,String,String,String,String>(f[1], f[3], f[8], f[13], f[14],f[18]));	
					}	
				});
		
	
		
		
		//card
		DataSet<Tuple2<String, String>> cardDataSet = env
				.fromCollection(RedisClientUtils.getAllMap("T_CARD").values())
				.flatMap(
						new FlatMapFunction<String, Tuple2<String, String>>() {
							@Override
							public void flatMap(
									String value,
									Collector<Tuple2<String, String>> out)
									throws Exception {
								JSONObject json = JSON.parseObject(value);
								out.collect(new Tuple2<String, String>(
										json.getString("id"), json
												.getString("peopleId")));
							}
						});
		
		//code
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
		
		DataSet<Tuple6<Integer, String, String, String, String, String>> sumSet=paySet.groupBy(1,2).reduceGroup(new GroupReduceFunction<Tuple6<String,String,String,String,String,String>, Tuple6<Integer, String, String, String, String, String>>() {

			@Override
			public void reduce(Iterable<Tuple6<String,String, String, String, String, String>> value,
					Collector<Tuple6<Integer, String, String, String, String, String>> out) throws Exception {
				Integer eat=0;
				Tuple6<String,String, String, String, String, String> t=null;
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				Set set=new HashSet();
				for (Iterator<Tuple6<String,String, String, String, String, String>> it = value.iterator(); it.hasNext();) {
					 t = it.next();	
					 long time=sdf.parse(t.f3).getTime();
					 if(sdf.parse("00:00:00").getTime()< time && time < sdf.parse("10:00:00").getTime()){
							set.add("a");
					  }
					 if(sdf.parse("10:00:00").getTime()< time && time < sdf.parse("16:00:00").getTime()){
							set.add("b");
								 
					 }
					 if( time > sdf.parse("16:00:00").getTime()){
							set.add("c");
					 }
					 
				}
				
				//Tuple6(eat,id,card_id,y-m-d,car_port_id,card_deal_id)
				out.collect(new Tuple6<Integer, String, String, String, String, String>(set.size(),t.f0.toString(),t.f1.toString(),t.f2.toString(),t.f4.toString(),t.f5.toString()));		
			}
		});
		
		
		
		
		
		
		
		
		//关联card
		DataSet <Tuple6<Integer, String, String, String, String, String>> set1=sumSet.join(cardDataSet).
		where(2).
		equalTo(0).
		flatMap(new FlatMapFunction<Tuple2<Tuple6<Integer,String,String,String,String,String>,Tuple2<String,String>>, Tuple6<Integer, String, String, String, String, String>>() {

			@Override
			public void flatMap(
					Tuple2<Tuple6<Integer, String, String, String, String, String>, Tuple2<String, String>> value,
					Collector<Tuple6<Integer, String, String, String, String, String>> out) throws Exception {
				// TODO Auto-generated method stub
				  
				//Tuple6(eat,peopleId,card_id,y-m-d,car_port_id,card_deal_id)
				out.collect(new Tuple6<Integer, String, String, String, String, String>(value.f0.f0,value.f1.f1,value.f0.f2,value.f0.f3,value.f0.f4,value.f0.f5));	
			}
		});
		
		//与学生表关联
		DataSet<Tuple8<Integer, String, String, String, String, String, String, String>> set2=set1.
		join(stuDataSet).
		where(1).equalTo(0)
		.flatMap(new FlatMapFunction<Tuple2<Tuple6<Integer,String,String,String,String,String>,Tuple6<String,String,String,String,String,String>>, Tuple8<Integer, String, String, String, String, String, String, String>>() {

			@Override
			public void flatMap(
					Tuple2<Tuple6<Integer, String, String, String, String, String>, Tuple6<String, String, String, String, String, String>> value,
					Collector<Tuple8<Integer, String, String, String, String, String, String, String>> out)
					throws Exception {
				 
				//Tuple8(eat,y-m-d,peopleId,name,sex_code,major_id,dept_id,deu_id)
				out.collect(new Tuple8<Integer, String, String, String, String, String, String, String>(value.f0.f0, value.f0.f3, value.f0.f1, value.f1.f1, value.f1.f2, value.f1.f4, value.f1.f3, value.f1.f5));
				
			}
		});

	    //与codeDept相关联
		DataSet <Tuple9<Integer, String,String, String, String, String, String, String, String>>set3=set2.
		join(codeDeptDataSet).
		where(6).equalTo(0).
		flatMap(new FlatMapFunction<Tuple2<Tuple8<Integer,String,String,String,String,String,String,String>,Tuple2<String,String>>, Tuple9<Integer, String,String, String, String, String, String, String, String>>() {

			@Override
			public void flatMap(
					Tuple2<Tuple8<Integer, String, String, String, String, String, String, String>, Tuple2<String, String>> value,
					Collector<Tuple9<Integer, String,String, String, String, String, String, String, String>> out)
					throws Exception {
				// TODO Auto-generated method stub
				
				//Tuple8(eat,y-m-d,peopleId,name,sex_code,major_id,dept_id,name_,deu_id)
				out.collect(new Tuple9<Integer, String, String, String, String,String, String, String, String>(value.f0.f0,value.f0.f1,value.f0.f2,value.f0.f3,value.f0.f4,value.f0.f5,value.f0.f6,value.f1.f1,value.f0.f7));
			}
		});
		
		

		//与codeEdu相关连
		
		DataSet <Tuple10<Integer, String,String,String, String, String, String, String, String, String>>set4=set3.
				join(codeEduDataSet).
				where(8).equalTo(0).
				flatMap(new FlatMapFunction<Tuple2<Tuple9<Integer,String,String,String,String,String,String,String,String>,Tuple2<String,String>>, Tuple10<Integer,String,String, String, String, String, String, String, String, String>>() {

					@Override
					public void flatMap(
							Tuple2<Tuple9<Integer, String,String, String, String, String, String, String, String>, Tuple2<String, String>> value,
							Collector<Tuple10<Integer, String,String,String, String, String, String, String, String, String>> out)
							throws Exception {
						// TODO Auto-generated method stub
						//Tuple10(eat,y-m-d,peopleId,name,sex_code,major_id,dept_id,name_,deu_id,name_)
						out.collect(new Tuple10<Integer, String, String,String,String, String, String, String, String, String>(value.f0.f0,value.f0.f1,value.f0.f2,value.f0.f3,value.f0.f4,value.f0.f5,value.f0.f6,value.f0.f7,value.f0.f8,value.f1.f1));
					}
				});
		
		
		//与sexCode相关联
		
		DataSet <Tuple11<Integer, String,String,String,String, String, String, String, String, String, String>>set5=set4.join(sexCodeDataSet).where(4).equalTo(0).flatMap(new FlatMapFunction<Tuple2<Tuple10<Integer,String,String,String,String,String,String,String,String,String>,Tuple2<String,String>>, Tuple11<Integer,String,String,String,String,String,String,String,String,String,String>>() {

			@Override
			public void flatMap(
					Tuple2<Tuple10<Integer, String, String, String, String, String, String, String, String, String>, Tuple2<String, String>> value,
					Collector<Tuple11<Integer, String, String, String, String, String, String, String, String, String, String>> out)
					throws Exception {
				// TODO Auto-generated method stub
				//Tuple11(eat,y-m-d,peopleId,name,sex_code,name_,major_id,dept_id,name_,deu_id,name_)
				out.collect(new Tuple11(value.f0.f0, value.f0.f1, value.f0.f2, value.f0.f3, value.f0.f4, value.f1.f1, value.f0.f5, value.f0.f6, value.f0.f7, value.f0.f8, value.f0.f9));
				
			}
		});
		
		DataSet set6=set5.join(codeDeptDataSet).where(6).equalTo(0).flatMap(new FlatMapFunction<Tuple2<Tuple11<Integer,String,String,String,String,String,String,String,String,String,String>,Tuple2<String,String>>, Tuple12<Integer, String, String, String, String, String, String, String, String, String, String, String>>() {

			@Override
			public void flatMap(
					Tuple2<Tuple11<Integer, String, String, String, String, String, String, String, String, String, String>, Tuple2<String, String>> value,
					Collector<Tuple12<Integer, String, String, String, String, String, String, String, String, String, String, String>> out)
					throws Exception {
				// TODO Auto-generated method stub
				//Tuple12(eat,y-m-d,peopleId,name,sex_code,name_,major_id,naem_,dept_id,name_,deu_id,name_)
				out.collect(new Tuple12<Integer, String, String, String, String, String, String, String, String, String, String, String>(value.f0.f0, value.f0.f1, value.f0.f2, value.f0.f3, value.f0.f4, value.f0.f5, value.f0.f6, value.f1.f1, value.f0.f7, value.f0.f8, value.f0.f9, value.f0.f10));
				
			}
		});
		
//		set6.writeAsText("hdfs://192.168.100.132:9000/TStuEatCustom/",
//				WriteMode.OVERWRITE);
//		
//		env.execute("begin run job");
//		

		

		
			
	}

}
