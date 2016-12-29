package com.glite.flink.example.batch.aggregate;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.DistinctOperator;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.util.Collector;

import com.glite.flink.example.domain.TCardPay;

/**
 * 去重
 * @author chenlj
 *
 */
public class DistinctExample {
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
		dataSet.distinct("cardId","cardPortId","cardDealId");
		
	}
}
