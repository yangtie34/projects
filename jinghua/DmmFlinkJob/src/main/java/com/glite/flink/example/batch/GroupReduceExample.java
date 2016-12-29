package com.glite.flink.example.batch;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.util.Collector;
/**
 * GroupReduce
 * 对数据集或分组后形成的多个数据集a,b,.
 * 针对a中的数据，逐条进行操作，最后形成一条数据或多条数据 数据类型可以和输入的类型不一样。
 * 典型的例子是分组后的求和且计数操作操作，
 * 
 * Combines a group of elements into a single element by repeatedly combining two elements into one. 
 * Reduce may be applied on a full data set, or on a grouped data set.
 * 注意和 reduce比较
 * @author chenlj
 *
 */
public class GroupReduceExample {
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
		
		DataSet<Tuple4<String,String, Float, Long>> paySet1=paySet.groupBy(0,1).reduceGroup(new GroupReduceFunction<Tuple3<String,Float,String>,Tuple4<String,String, Float, Long>>() {

			@Override
			public void reduce(Iterable<Tuple3<String, Float, String>> values,
					Collector<Tuple4<String,String, Float, Long>> out)
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
		

	}
}
