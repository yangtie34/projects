package com.glite.flink.example.tohdfs;

import java.io.IOException;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.io.OutputFormat;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.jdbc.JDBCInputFormat;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.table.Row;
import org.apache.flink.api.table.typeutils.RowTypeInfo;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.fs.FileSystem.WriteMode;
import org.apache.flink.util.Collector;


public class TCardPay {
	private Long id;
	private String cardId;
	private Float payMoney;
	private Float surplusMoney;
	private String time_;
	private String cardPortId;
	private String cardDealId;
	private String walletCode;	
	
	public TCardPay() {
		super();
	}

	public TCardPay(String items){
		String[] fields=items.replace("'", "").split(",");
		this.setId(Long.valueOf(fields[0]));
		this.setCardId(fields[1]);
		this.setPayMoney(Float.valueOf(fields[2]));
		this.setSurplusMoney(Float.valueOf(fields[3]));
		this.setTime_(fields[4]);
		this.setCardPortId(fields[5]);
		this.setCardDealId(fields[6]);
		this.setWalletCode(fields[7]);		
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public Float getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(Float payMoney) {
		this.payMoney = payMoney;
	}
	public Float getSurplusMoney() {
		return surplusMoney;
	}
	public void setSurplusMoney(Float surplusMoney) {
		this.surplusMoney = surplusMoney;
	}
	public String getTime_() {
		return time_;
	}
	public void setTime_(String time_) {
		this.time_ = time_;
	}
	public String getCardPortId() {
		return cardPortId;
	}
	public void setCardPortId(String cardPortId) {
		this.cardPortId = cardPortId;
	}
	public String getCardDealId() {
		return cardDealId;
	}
	public void setCardDealId(String cardDealId) {
		this.cardDealId = cardDealId;
	}
	public String getWalletCode() {
		return walletCode;
	}
	public void setWalletCode(String walletCode) {
		this.walletCode = walletCode;
	}
	
	public static ExecutionEnvironment createEnv(){
		return ExecutionEnvironment.createRemoteEnvironment("CentOS02", 6123, "");
	}
	
	public static void main(String[] args) throws Throwable {
		final ExecutionEnvironment env = ExecutionEnvironment
				.getExecutionEnvironment();
		/*
		 * 从oracle中读取数据
		 */
		DataSource<Row> dataSource = env.createInput(createDayInputFormat());
		DataSet<Tuple2<String,String>> sourceSets=dataSource.flatMap(new FlatMapFunction<Row, Tuple2<String,String>>() {

			@Override
			public void flatMap(Row value, Collector<Tuple2<String, String>> out)
					throws Exception {
				ExecutionEnvironment subEnv=ExecutionEnvironment
				.getExecutionEnvironment();
				String str=value.toString();
				String year=str.substring(0,4);
				String month=str.substring(5,7);
				String day=str.substring(8,10);
				String path="d:/cardpay/year="+year+"/month="+month+"/day="+day+"/";
//				String path="hdfs://ns1/cardpay/year="+year+"/month="+month+"/day="+day+"/";
				DataSource<Row> innerRow=subEnv.createInput(createInputFormat(value.toString()));
				DataSet<String> subDataSet=innerRow.map(new MapFunction<Row, String>() {

					@Override
					public String map(
							Row value) throws Exception {
						return value.toString();
					}
				});
				subDataSet.writeAsText(path,WriteMode.OVERWRITE);
				subEnv.execute();
				out.collect(new Tuple2<String, String>(value.toString(),"success"));
				
			}
		});
		
		
//		DataSet<Tuple2<String,String>> sourceSets=
//				dataSource.map(new MapFunction<Row, Tuple2<String,String>>() {
//			@Override
//			public Tuple2<String,String> map(Row value) throws Exception {
//				String str=value.toString();
//				String year=str.substring(0,4);
//				String month=str.substring(6,2);
//				String day=str.substring(9,2);
//				String path="/cardpay/year="+year+"/month="+month+"/day="+day+"/";
//				DataSource<Row> innerRow=env.createInput(createInputFormat(value.toString()));
//				innerRow.writeAsText(path);
//				return new Tuple2<String,String>(str,"success");
////				return new Tuple2<String,List<Row>>(value.toString(), env.createInput(createInputFormat(value.toString())));
//			}
//		});
		
//		DataSet sets=sourceSets.map(new MapFunction<Tuple2<String,DataSource<Row>>, Tuple2<String,String>>() {
//
//			@Override
//			public Tuple2<String, String> map(
//					Tuple2<String, DataSource<Row>> value) throws Exception {
//				String year=value.f0.substring(0,4);
//				String month=value.f0.substring(6,2);
//				String day=value.f0.substring(9,2);
//				String path="/cardpay/year="+year+"/month="+month+"/day="+day+"/";
//				value.f1.writeAsText(path);
//				return new Tuple2<>(value.f0,"success");
//			}
//		});

		sourceSets.writeAsText("D:/cardpay/result/",WriteMode.OVERWRITE);
		env.execute();
	}
	
	public static JDBCInputFormat createDayInputFormat() {
		//ID,CARD_ID,PAY_MONEY,SURPLUS_MONEY,TIME_,CARD_PORT_ID,CARD_DEAL_ID,WALLET_CODE
		
		TypeInformation<?>[] fieldTypes = new TypeInformation<?>[] {
				BasicTypeInfo.STRING_TYPE_INFO};
		RowTypeInfo rowTypeInfo = new RowTypeInfo(fieldTypes);
		JDBCInputFormat jdbcInputFormat = JDBCInputFormat
				.buildJDBCInputFormat()
				.setDrivername("oracle.jdbc.driver.OracleDriver")
				.setDBUrl("jdbc:oracle:thin:@192.168.30.88:1521/orcl")
				.setUsername("dm")
				.setPassword("dm")
				.setQuery(
						"select distinct substr(time_,1,10) from T_CARD_PAY")
				.setRowTypeInfo(rowTypeInfo).finish();
		return jdbcInputFormat;
	}
	
	
	public static JDBCInputFormat createInputFormat(String dayPre) {
		//ID,CARD_ID,PAY_MONEY,SURPLUS_MONEY,TIME_,CARD_PORT_ID,CARD_DEAL_ID,WALLET_CODE
		
		TypeInformation<?>[] fieldTypes = new TypeInformation<?>[] {
				BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.DOUBLE_TYPE_INFO, BasicTypeInfo.DOUBLE_TYPE_INFO,
				BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO };
		RowTypeInfo rowTypeInfo = new RowTypeInfo(fieldTypes);
		JDBCInputFormat jdbcInputFormat = JDBCInputFormat
				.buildJDBCInputFormat()
				.setDrivername("oracle.jdbc.driver.OracleDriver")
				.setDBUrl("jdbc:oracle:thin:@192.168.30.88:1521/orcl")
				.setUsername("dm")
				.setPassword("dm")
				.setQuery(
						"select ID,CARD_ID,PAY_MONEY,SURPLUS_MONEY,TIME_,CARD_PORT_ID,CARD_DEAL_ID,WALLET_CODE from T_CARD_PAY where time_ like '"+dayPre+"%'")
				.setRowTypeInfo(rowTypeInfo).finish();
		return jdbcInputFormat;
	}
	
}
