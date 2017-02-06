package com.glite.flink.example.batch;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.GroupCombineFunction;
import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.util.Collector;

/**
 * GroupCombine
 * 针对大数据量的数据集，局部计数可以显著提升性能，比如分组计算，我们可以先对一部分分组并计算，然后再对计算后的数据分组计算。
 * 设数据集 a有50亿条数据，先取a的前10亿条数据a[0-10亿]进行分组并计算产生数据集b，数据集b的数据量远小于10亿，
 * 此时系统会将a[0-10亿]从内存中删除，然后对a[10亿+1-20亿]分组计算，并将结果加入b，最后再对数据集b进行分组计算。
 * 实际对a如何小批量计数，由系统根据资源情况决定
 * 
 * @author chenlj
 *
 */
public class GroupCombineExample {
	public static void main(String[] args) {
		
		
		final ExecutionEnvironment env = ExecutionEnvironment
				.getExecutionEnvironment();
		DataSource<String> paySource = env.readTextFile("hdfs://ns1/cardpay");
		DataSet<Tuple3<String, Float, String>> paySet=paySource.flatMap(new FlatMapFunction<String, Tuple3<String,Float,String>>() {

			@Override
			public void flatMap(String value,
					Collector<Tuple3<String, Float, String>> out)
					throws Exception {
				String[] items=value.replaceAll("'", "").split(",");
				out.collect(new  Tuple3<>(items[1],Float.valueOf(items[2]),items[5]));
			}
		});
		
		DataSet<Tuple4<String,String, Float, Long>> CombinePaySet1=paySet.groupBy(0,2).combineGroup(new GroupCombineFunction<Tuple3<String,Float,String>, Tuple4<String,String, Float, Long>>() {
			/*
			 * 局部计数
			 */
			@Override
			public void combine(Iterable<Tuple3<String, Float, String>> values,
					Collector<Tuple4<String, String, Float, Long>> out)
					throws Exception {
				String cardId=null;
				String cardPortId=null;
				Long counts=0L;
				Float money=0F;
				for(Tuple3<String, Float, String> t3:values){
					
				}
				out.collect(new Tuple4<String,String, Float, Long>(cardId, cardPortId, money,counts));
				
			}
		});
		
		DataSet<Tuple4<String,String, Float, Long>> paySet2=CombinePaySet1.groupBy(0,1).reduceGroup(new GroupReduceFunction<Tuple4<String,String, Float, Long>,Tuple4<String,String, Float, Long>>() {
			
			@Override
			public void reduce(Iterable<Tuple4<String,String, Float, Long>> values,
					Collector<Tuple4<String,String, Float, Long>> out)
					throws Exception {
				String cardId=null;
				String cardPortId=null;
				Long counts=0L;
				Float money=0F;
				for(Tuple4<String,String, Float, Long> t4:values){
					
				}
				out.collect(new Tuple4<String,String, Float, Long>(cardId, cardPortId, money,counts));
				
			}
		});
		

	}
	
}
