package com.glite.flink.example.batch;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.util.Collector;

import com.glite.flink.example.domain.TCardPay;

/**
 * FlapMap 转换，对数据集中的数据，进行1对多的转换
 * @author chenlj
 *
 */
public class FlapMapExample {
	public static void main(String[] args) {
		try{
			
		}catch(Throwable e){
			
		}
		final ExecutionEnvironment env = ExecutionEnvironment
				.getExecutionEnvironment();
		DataSource<String> paySource = env.readTextFile("hdfs://ns1/cardpay");
		DataSet<TCardPay> paySet=paySource.flatMap(new FlatMapFunction<String, TCardPay>() {
			@Override
			public void flatMap(String value, Collector<TCardPay> out)
					throws Exception {
				out.collect(new TCardPay(value));
				/**
				 * 这里可以collect多个元素
				 */
				
			}
		});

	}
}
