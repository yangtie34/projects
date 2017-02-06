package com.glite.flink.example.batch;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapPartitionFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.util.Collector;
/**
 * reduce
 * 对数据集a或分组后形成的多个数据集a,b,...
 * 针对a中的数据，逐条进行操作，最后形成一条数据。输入输出的数据类型，必须一样
 * 典型的例子是分组后的求和操作，
 * 
 * 注意和 GroupReduce比较
 * 
 * Combines a group of elements into a single element by repeatedly combining two elements into one. 
 * Reduce may be applied on a full data set, or on a grouped data set.
 * @author chenlj
 *
 */
public class ReduceExample {
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
		
		DataSet<Tuple3<String, Float, String>> paySet1=paySet.groupBy(0,2).reduce(new ReduceFunction<Tuple3<String,Float,String>>() {
			
			@Override
			public Tuple3<String, Float, String> reduce(
					Tuple3<String, Float, String> value1,
					Tuple3<String, Float, String> value2) throws Exception {
				
				return new Tuple3<String, Float, String>(value1.f0,value1.f1+value2.f1,value1.f2) ;
			}
		});
		paySet1.mapPartition(new MapPartitionFunction<Tuple3<String,Float,String>, Tuple3<String,Float,String>>( ) {

			@Override
			public void mapPartition(
					Iterable<Tuple3<String, Float, String>> values,
					Collector<Tuple3<String, Float, String>> out)
					throws Exception {
				// TODO Auto-generated method stub
				
			}
		});

	}
}
