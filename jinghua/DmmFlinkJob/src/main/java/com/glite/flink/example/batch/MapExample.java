package com.glite.flink.example.batch;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple3;

import com.glite.flink.example.domain.TCardPay;
/**
 * Map 转换，将数据集中的每条数据，进行1对1的转换
 * @author chenlj
 *
 */
public class MapExample {
	public static void main(String[] args){
		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
		DataSource<String> dataSource=env.readTextFile("hdfs://ns1/cardpay");
		DataSet<TCardPay> dataSet=dataSource.map(new MapFunction<String, TCardPay>() {
			@Override
			public TCardPay map(String value) throws Exception {
				return new TCardPay(value);
			}
		});
		
		
	}
}
