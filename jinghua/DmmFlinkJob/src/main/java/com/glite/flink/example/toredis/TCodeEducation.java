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

public class TCodeEducation {
	private String id;
	private String code_;
	private String name_;
	private String pid;
	private String path_;
	private Integer level;
	private String level_type;
	private Integer istrue;
	private Integer order;
	
	public static JDBCInputFormat createInputFormat() {
		TypeInformation<?>[] fieldTypes = new TypeInformation<?>[] {
				BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.INT_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.INT_TYPE_INFO,BasicTypeInfo.INT_TYPE_INFO};
		RowTypeInfo rowTypeInfo = new RowTypeInfo(fieldTypes);
		JDBCInputFormat jdbcInputFormat = JDBCInputFormat
				.buildJDBCInputFormat()
				.setDrivername("oracle.jdbc.driver.OracleDriver")
				.setDBUrl("jdbc:oracle:thin:@192.168.30.88:1521/orcl")
				.setUsername("dm")
				.setPassword("dm")
				.setQuery(
						"select ID,CODE_,NAME_,PID,PATH_,LEVEL_,LEVEL_TYPE,ISTRUE,ORDER_ from T_CODE_EDUCATION")
				.setRowTypeInfo(rowTypeInfo).finish();
		return jdbcInputFormat;
	}
	
	public static void main(String[] args) throws Throwable  {
    
		final ExecutionEnvironment env=ExecutionEnvironment.getExecutionEnvironment();
		
		DataSource<Row> datasource=env.createInput(createInputFormat());
		DataSet<Tuple2<String, String>> dataSet=datasource.flatMap(new FlatMapFunction<Row, TCodeEducation>() {
			@Override
			public void flatMap(Row value, Collector<TCodeEducation> out) throws Exception {
				// TODO Auto-generated method stub
				
				out.collect(new TCodeEducation(value.toString()));	
			}
		}).flatMap(new FlatMapFunction<TCodeEducation, Tuple2<String,String>>() {
			@Override
			public void flatMap(TCodeEducation value, Collector<Tuple2<String, String>> out) throws Exception {
				// TODO Auto-generated method stub
				out.collect(new Tuple2(value.getId(),JSON.toJSON(value).toString()));
			}
		});
		
		
		FlinkJedisPoolConfig jedisPoolConfig = new FlinkJedisPoolConfig.Builder()
			     .setHost("192.168.100.133").setPort(6379).setDatabase(1).setPassword("123456").setTimeout(30000).setMaxIdle(100).setMaxTotal(200).build();
		dataSet.output(new RedisOutPutFormat(jedisPoolConfig, new RedisHsetMapper("T_CODE_EDUCATION")));
				env.execute();
			
     

	}
	
	
	
	
	
	
	
	
	
	public TCodeEducation(String values){
		   String[] vs=values.split(",");
		   this.setId(vs[0]);
		   this.setCode_(vs[1]);
		   this.setName_(vs[2]);
		   this.setPid(vs[3]);
		   this.setPath_(vs[4]);
		   this.setLevel(Integer.valueOf(vs[5]));
		   this.setLevel_type(vs[6]);
		   this.setIstrue(Integer.valueOf(vs[7]));
		   this.setOrder((vs[8]!=null && !vs[8].equals("null"))?Integer.valueOf(vs[8]):null);
		   
		   
		   
    }	 
    public TCodeEducation(){
		   super();
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

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPath_() {
		return path_;
	}

	public void setPath_(String path_) {
		this.path_ = path_;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getLevel_type() {
		return level_type;
	}

	public void setLevel_type(String level_type) {
		this.level_type = level_type;
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
}
