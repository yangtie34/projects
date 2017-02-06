package com.glite.flink.example.toredis;

import java.util.Arrays;
import java.util.List;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.jdbc.JDBCInputFormat;
import org.apache.flink.api.java.io.jdbc.JDBCInputFormat.JDBCInputFormatBuilder;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.table.Row;
import org.apache.flink.api.table.typeutils.RowTypeInfo;
import org.apache.flink.streaming.connectors.redis.RedisSink;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommand;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommandDescription;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisMapper;
import org.apache.flink.util.Collector;


import com.alibaba.fastjson.JSON;
import com.glite.flink.example.toredis.output.RedisHsetMapper;
import com.glite.flink.example.toredis.output.RedisOutPutFormat;

public class TCard {
	private String id;
	private String no_;
	private String peopleId;
	private String cardNo;
	private String cardIdentityId;
	private String time_;
	private Integer istrue;
	private Double cardBalance;
	
	public TCard() {
		super();
	}

	public TCard(String values){
		String[] vs=values.split(",");
		//ID,NO_,PEOPLE_ID,CARD_NO,CARD_IDENTITY_ID,TIME_,ISTRUE,CARD_BALANCE
		this.setId(vs[0]);
		this.setNo_(vs[1]);
		this.setPeopleId(vs[2]);
		this.setCardNo(vs[3]);
		this.setCardIdentityId(vs[4]);
		this.setTime_(vs[5]);
		this.setIstrue(Integer.valueOf(vs[6]));
		this.setCardBalance(Double.valueOf(vs[7]));
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNo_() {
		return no_;
	}

	public void setNo_(String no_) {
		this.no_ = no_;
	}

	public String getPeopleId() {
		return peopleId;
	}

	public void setPeopleId(String peopleId) {
		this.peopleId = peopleId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardIdentityId() {
		return cardIdentityId;
	}

	public void setCardIdentityId(String cardIdentityId) {
		this.cardIdentityId = cardIdentityId;
	}

	public String getTime_() {
		return time_;
	}

	public void setTime_(String time_) {
		this.time_ = time_;
	}

	public Integer getIstrue() {
		return istrue;
	}

	public void setIstrue(Integer istrue) {
		this.istrue = istrue;
	}

	public Double getCardBalance() {
		return cardBalance;
	}

	public void setCardBalance(Double cardBalance) {
		this.cardBalance = cardBalance;
	}

	private static String fieldsNames() {
		return "ID,NO_,PEOPLE_ID,CARD_NO,CARD_IDENTITY_ID,TIME_,ISTRUE,CARD_BALANCE";
	}

	private static List fields() {
		return Arrays.asList(fieldsNames().split(","));
	}

	

	public static void main(String[] args) throws Throwable {
		final ExecutionEnvironment env = ExecutionEnvironment
				.getExecutionEnvironment();
		/*
		 * 从oracle中读取数据
		 */
		DataSource<Row> dataSource = env.createInput(createInputFormat());

		DataSet<Tuple2<String, String>> dataSet=dataSource.flatMap(new FlatMapFunction<Row, TCard>() {
			/*
			 * 转换成TCard的数据集
			 */
			@Override
			public void flatMap(Row value, Collector<TCard> out)
					throws Exception {
				out.collect(new TCard(value.toString()));
			}
		}).flatMap(new FlatMapFunction<TCard, Tuple2<String, String>>() {
			/*
			 * 转换成以card_no为key，以TCard的json字符串为value的Tuple2数据集
			 */
			@Override
			public void flatMap(TCard value,
					Collector<Tuple2<String, String>> out) throws Exception {
				out.collect(new Tuple2<String, String>(value.getId(),JSON.toJSON(value).toString()));
			}
		});
		/*
		 * 将数据集保存到redis中
		 */
		FlinkJedisPoolConfig jedisPoolConfig = new FlinkJedisPoolConfig.Builder()
		     .setHost("CentOS03").setPort(6379).setDatabase(1).setPassword("123456").setTimeout(30000).setMaxIdle(100).setMaxTotal(200).build();
		
		dataSet.output(new RedisOutPutFormat(jedisPoolConfig, new RedisHsetMapper("T_CARD")));
		
		env.execute();
		 //		dataSet.output(outputFormat)
	}
	
	public static JDBCInputFormat createInputFormat() {
		TypeInformation<?>[] fieldTypes = new TypeInformation<?>[] {
				BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.INT_TYPE_INFO, BasicTypeInfo.DOUBLE_TYPE_INFO };
		RowTypeInfo rowTypeInfo = new RowTypeInfo(fieldTypes);
		JDBCInputFormat jdbcInputFormat = JDBCInputFormat
				.buildJDBCInputFormat()
				.setDrivername("oracle.jdbc.driver.OracleDriver")
				.setDBUrl("jdbc:oracle:thin:@192.168.30.88:1521/orcl")
				.setUsername("dm")
				.setPassword("dm")
				.setQuery(
						"select ID,NO_,PEOPLE_ID,CARD_NO,CARD_IDENTITY_ID,TIME_,ISTRUE,CARD_BALANCE from T_CARD")
				.setRowTypeInfo(rowTypeInfo).finish();
		return jdbcInputFormat;
	}
	
}
