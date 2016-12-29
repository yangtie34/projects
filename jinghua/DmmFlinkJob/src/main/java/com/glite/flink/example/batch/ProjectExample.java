package com.glite.flink.example.batch;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.util.Collector;

/**
 * project去掉或移动Tuple的字段,只能操作tuple类型的数据
 * @author chenlj
 *
 */
public class ProjectExample {
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
		//project转换
		DataSet<Tuple3<String, Float, String>> paySet1=paySet.project(2,1,0);

	}
}
