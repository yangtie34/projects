package com.glite.flink.example.batch.aggregate;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.util.Collector;

import com.glite.flink.example.domain.TCardPay;

public class TopNExample {
	public static void main(String[] args){
		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
		DataSource<String> dataSource=env.readTextFile("hdfs://ns1/cardpay");
		DataSet<TCardPay> dataSet=dataSource.flatMap(new FlatMapFunction<String, TCardPay>() {
			@Override
			public void flatMap(String value, Collector<TCardPay> out)
					throws Exception {
				out.collect(new TCardPay(value));
				
			}
		});
		DataSet<TCardPay> top10=dataSet.sortPartition("payMoney", Order.DESCENDING).first(10);
		
	}
}
