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

public class TClasses {
	
	private String id;
	private String no_;
	private String name_;
	private String  teach_dept_id;
	private Integer  length_schooling;
	private Integer islive;
	private Long grade;

    public TClasses(){
    	super();
    }
    public TClasses(String values){
    	String[] vs=values.split(",");
    	this.setId(vs[0]);
    	this.setNo_(vs[1]);
    	this.setName_(vs[2]);
    	this.setTeach_dept_id((vs[3]!=null && !vs[3].equals("null"))?vs[3]:null);//
    	this.setLength_schooling(Integer.valueOf(vs[4]));
    	this.setIslive((vs[5]!=null && !vs[5].equals("null"))?Integer.valueOf(vs[5]):null);//
    	this.setGrade(Long.valueOf(vs[6]));
    }

	private String getFieldsNames(){
		return "";
	}
	
	private List getFields(){
		return Arrays.asList(getFieldsNames());
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

	public String getName_() {
		return name_;
	}

	public void setName_(String name_) {
		this.name_ = name_;
	}

	public String getTeach_dept_id() {
		return teach_dept_id;
	}

	public void setTeach_dept_id(String teach_dept_id) {
		this.teach_dept_id = teach_dept_id;
	}

	public Integer getLength_schooling() {
		return length_schooling;
	}

	public void setLength_schooling(Integer length_schooling) {
		this.length_schooling = length_schooling;
	}

	public Integer getIslive() {
		return islive;
	}

	public void setIslive(Integer islive) {
		this.islive = islive;
	}

	public Long getGrade() {
		return grade;
	}

	public void setGrade(Long grade) {
		this.grade = grade;
	}
	public static JDBCInputFormat createInputFormat() {
		TypeInformation<?>[] fieldTypes = new TypeInformation<?>[] {
				BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.INT_TYPE_INFO,BasicTypeInfo.INT_TYPE_INFO,
				BasicTypeInfo.LONG_TYPE_INFO};
		RowTypeInfo rowTypeInfo = new RowTypeInfo(fieldTypes);
		JDBCInputFormat jdbcInputFormat = JDBCInputFormat
				.buildJDBCInputFormat()
				.setDrivername("oracle.jdbc.driver.OracleDriver")
				.setDBUrl("jdbc:oracle:thin:@192.168.30.88:1521/orcl")
				.setUsername("dm")
				.setPassword("dm")
				.setQuery(
						"select ID,NO_,NAME_,TEACH_DEPT_ID,LENGTH_SCHOOLING,ISLIVE,GRADE from T_CLASSES")
				.setRowTypeInfo(rowTypeInfo).finish();
		return jdbcInputFormat;
	}
	
	public static void main(String[] args) throws Throwable  {
    
		final ExecutionEnvironment env=ExecutionEnvironment.getExecutionEnvironment();
		
		DataSource<Row> datasource=env.createInput(createInputFormat());
		DataSet<Tuple2<String, String>> dataSet=datasource.flatMap(new FlatMapFunction<Row, TClasses>() {
			@Override
			public void flatMap(Row value, Collector<TClasses> out) throws Exception {
				// TODO Auto-generated method stub
				
				out.collect(new TClasses(value.toString()));	
			}
		}).flatMap(new FlatMapFunction<TClasses, Tuple2<String,String>>() {
			@Override
			public void flatMap(TClasses value, Collector<Tuple2<String, String>> out) throws Exception {
				// TODO Auto-generated method stub
				out.collect(new Tuple2(value.getNo_(),JSON.toJSON(value).toString()));
			}
		});
		
		
		FlinkJedisPoolConfig jedisPoolConfig = new FlinkJedisPoolConfig.Builder()
			     .setHost("192.168.100.133").setPort(6379).setDatabase(1).setPassword("123456").setTimeout(30000).setMaxIdle(100).setMaxTotal(200).build();
		dataSet.output(new RedisOutPutFormat(jedisPoolConfig, new RedisHsetMapper("T_CLASSES")));
				env.execute();
			
     

	}

	
	
	
}
