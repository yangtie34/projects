package com.glite.flink.example.batch;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple3;

import com.glite.flink.example.domain.TCardPay;
/**
 * keyselecter
 * 对数据集进行分组，但分组是依据某个字段的部分数据，或者多个字段的部分数据
 * 典型的例子是 依据yyyy-MM-dd HH:mm:ss格式时间的年月，或者年月日进行分组，
 * @author chenlj
 *
 */
public class KeySelecterExample {
	public static void main(String[] args){
		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
		DataSource<String> dataSource=env.readTextFile("hdfs://ns1/cardpay");
		DataSet<TCardPay> dataSet=dataSource.map(new MapFunction<String, TCardPay>() {
			@Override
			public TCardPay map(String value) throws Exception {
				return new TCardPay(value);
			}
		});
		dataSet.groupBy(new KeySelector<TCardPay, String>() {
			@Override
			public String getKey(TCardPay value)
					throws Exception {
				//返回年月日
				return value.getTime_().substring(0,10);
			}
		});
		
	}
}
