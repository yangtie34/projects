package com.glite.flink.example.batch;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;

import com.glite.flink.example.domain.TCardPay;

/**
 * 过滤 操作，只保留符合条件的元素
 * 
 * @author chenlj
 * 
 */
public class FilterExample {
	public static void main(String[] args) throws Throwable {
		final ExecutionEnvironment env = ExecutionEnvironment
				.getExecutionEnvironment();
		DataSource<String> dataSource = env.readTextFile("hdfs://ns1/cardpay");

		DataSet<TCardPay> dataSet = dataSource.map(
				new MapFunction<String, TCardPay>() {
					@Override
					public TCardPay map(String value) throws Exception {
						return new TCardPay(value);
					}
				}).filter(new FilterFunction<TCardPay>() {
			/*
			 * 不符合条件的元素，将会被丢弃
			 */
			@Override
			public boolean filter(TCardPay value) throws Exception {
				// TODO Auto-generated method stub
				return value.getPayMoney() > 1 ? true : false;
			}
		});
		dataSet.writeAsText("");
		env.execute();
	}
}
