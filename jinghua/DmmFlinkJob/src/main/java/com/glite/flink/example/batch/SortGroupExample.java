package com.glite.flink.example.batch;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.operators.DataSource;

import com.glite.flink.example.domain.TCardPay;

/**
 * 对分组后的每个数据集 依据某个字段属性 进行排序
 * @author chenlj
 *
 */
public class SortGroupExample {
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
		}).sortGroup("payMoney", Order.ASCENDING);
		
	}
}
