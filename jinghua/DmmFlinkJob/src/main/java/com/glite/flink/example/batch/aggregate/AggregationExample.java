package com.glite.flink.example.batch.aggregate;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.aggregation.Aggregations;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.util.Collector;

public class AggregationExample {
	public static void main(String[] args) {
		final ExecutionEnvironment env = ExecutionEnvironment
				.getExecutionEnvironment();
		DataSource<String> paySource = env.readTextFile("hdfs://ns1/cardpay");
		DataSet<Tuple3<String, Float, String>> paySet = paySource
				.flatMap(new FlatMapFunction<String, Tuple3<String, Float, String>>() {

					@Override
					public void flatMap(String value,
							Collector<Tuple3<String, Float, String>> out)
							throws Exception {
						String[] items = value.replaceAll("'", "").split(",");
						out.collect(new Tuple3<>(items[1], Float
								.valueOf(items[2]), items[5]));
					}
				});

		paySet.groupBy(0, 2).aggregate(Aggregations.SUM, 1).andMax(1).andMin(1);

		DataSet<Tuple3<Integer, String, Double>> input = null;
		DataSet<Tuple3<Integer, String, Double>> output = input.aggregate(
				Aggregations.SUM, 0).and(Aggregations.MIN, 2);

	}
}
