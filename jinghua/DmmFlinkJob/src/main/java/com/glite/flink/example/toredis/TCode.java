package com.glite.flink.example.toredis;

import java.util.Arrays;
import java.util.List;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.jdbc.JDBCInputFormat;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.table.Row;
import org.apache.flink.api.table.typeutils.RowTypeInfo;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig;
import org.apache.flink.util.Collector;

import com.alibaba.fastjson.JSON;
import com.glite.flink.example.toredis.output.RedisHsetMapper;
import com.glite.flink.example.toredis.output.RedisOutPutFormat;

import scala.collection.Iterator;

public class TCode {
	private String code_;
	private String name_;
	private String  code_category;
	private String code_type;
	private String codetype_name;
	private Integer istrue;
	private Integer order;
 

	public TCode(){
		super();
	}
	
	public TCode(Row r){
		Iterator<Object> i=r.productIterator();
		String[] vs=new String[8];
		int m=0;
		while(i.hasNext()){
			Object v=i.next();
			vs[m]=String.valueOf(v);
			m++;
		}		
		this.setCode_(vs[0]);
		this.setName_(vs[1]);
		this.setCode_category(vs[2]);
		this.setCode_type(vs[3]);
		this.setCodetype_name(vs[4]);
		this.setIstrue(vs[5]!=null && !vs[5].equals("null")?Integer.valueOf(vs[5]):null);
		this.setOrder(vs[6]!=null && !vs[6].equals("null")?Integer.valueOf(vs[6]):null);
		
	}
	
	public TCode(String values){
		String[] vs=values.split(",");
		this.setCode_(vs[0]);
		this.setName_(vs[1]);
		this.setCode_category(vs[2]);
		this.setCode_type(vs[3]);
		this.setCodetype_name(vs[4]);
		this.setIstrue(Integer.valueOf(vs[5]));
		this.setOrder(vs[6]!=null && !vs[6].equals("null")?Integer.valueOf(vs[6]):null);
		
	}
	private String getFieldsNames(){
		return "";
	}
	
	private List getFields(){
		return Arrays.asList(getFieldsNames());
	}

	public String getCode_() {
		return code_;
	}

	public void setCode_(String code_) {
		this.code_ = code_;
	}

	public String getName_() {
		return name_;
	}

	public void setName_(String name_) {
		this.name_ = name_;
	}

	public String getCode_category() {
		return code_category;
	}

	public void setCode_category(String code_category) {
		this.code_category = code_category;
	}

	public String getCode_type() {
		return code_type;
	}

	public void setCode_type(String code_type) {
		this.code_type = code_type;
	}

	public String getCodetype_name() {
		return codetype_name;
	}

	public void setCodetype_name(String codetype_name) {
		this.codetype_name = codetype_name;
	}

	public Integer getIstrue() {
		return istrue;
	}

	public void setIstrue(Integer istrue) {
		this.istrue = istrue;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
	
	public static JDBCInputFormat createInputFormat() {
		TypeInformation<?>[] fieldTypes = new TypeInformation<?>[] {
				BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.INT_TYPE_INFO,
				BasicTypeInfo.INT_TYPE_INFO};
		RowTypeInfo rowTypeInfo = new RowTypeInfo(fieldTypes);
		JDBCInputFormat jdbcInputFormat = JDBCInputFormat
				.buildJDBCInputFormat()
				.setDrivername("oracle.jdbc.driver.OracleDriver")
				.setDBUrl("jdbc:oracle:thin:@192.168.30.88:1521/orcl")
				.setUsername("dm")
				.setPassword("dm")
				.setQuery(
						"select * from T_CODE ")
				.setRowTypeInfo(rowTypeInfo).finish();
		return jdbcInputFormat;
	}
	public static void main(String[] args) throws Exception {
		
		final ExecutionEnvironment env=ExecutionEnvironment.getExecutionEnvironment();
		
		DataSource<Row> datasource=env.createInput(createInputFormat());
		DataSet<Tuple2<String, String>> dataSet=datasource.flatMap(new FlatMapFunction<Row, TCode>() {
			@Override
			public void flatMap(Row value, Collector<TCode> out) throws Exception {
				// TODO Auto-generated method stub
				
				out.collect(new TCode(value));	
			}
		}).flatMap(new FlatMapFunction<TCode, Tuple2<String,String>>() {
			@Override
			public void flatMap(TCode value, Collector<Tuple2<String, String>> out) throws Exception {
				// TODO Auto-generated method stub
				out.collect(new Tuple2(value.getCode_type()+"_"+value.getCode_(),JSON.toJSON(value).toString()));
				
			}
		});
		
		
		FlinkJedisPoolConfig jedisPoolConfig = new FlinkJedisPoolConfig.Builder()
			     .setHost("192.168.100.133").setPort(6379).setDatabase(1).setPassword("123456").setTimeout(30000).setMaxIdle(100).setMaxTotal(200).build();
		dataSet.output(new RedisOutPutFormat(jedisPoolConfig, new RedisHsetMapper("T_CODE")));
				env.execute();
			
		
		
	}
}
