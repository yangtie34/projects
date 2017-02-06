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

public class TTea {
	private String id;
	private String tea_no;
	private String name_;
	private String idno;
	private String fomer_name;
	private String birthday;
	private String dept_id;
	private String sex_code;
	private String natton_code;
	private Integer married;
	
	private String edu_id;
	private String degree_id;
	private String subject_id;
	private String domicile_id;
	private String place_domicile;
	private String anmelden_code;
	private String join_party_date;
	private String  politics_code;
	private String in_date;
	private String authorized_strength_id;
	private String tea_source_id;
	private String bzlb_code;
	private String zyjszw_id;
	private String skill_moves_code;
	private String teaching_date;
	private String gatq_code;
	private String tea_status_code;
	
	private Integer sfssjs;
	private Integer iszb;
	private String work_date;
	
	
	
	public static JDBCInputFormat createInputFormat() {
		TypeInformation<?>[] fieldTypes = new TypeInformation<?>[] {
				BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.INT_TYPE_INFO,
				BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.INT_TYPE_INFO,BasicTypeInfo.INT_TYPE_INFO,
				BasicTypeInfo.STRING_TYPE_INFO};
		RowTypeInfo rowTypeInfo = new RowTypeInfo(fieldTypes);
		JDBCInputFormat jdbcInputFormat = JDBCInputFormat
				.buildJDBCInputFormat()
				.setDrivername("oracle.jdbc.driver.OracleDriver")
				.setDBUrl("jdbc:oracle:thin:@192.168.30.88:1521/orcl")
				.setUsername("dm")
				.setPassword("dm")
				.setQuery(
						"select * from T_TEA")
				.setRowTypeInfo(rowTypeInfo).finish();
		return jdbcInputFormat;
	}
	
	public static void main(String[] args) throws Throwable  {
    
		final ExecutionEnvironment env=ExecutionEnvironment.getExecutionEnvironment();
		
		DataSource<Row> datasource=env.createInput(createInputFormat());
		DataSet<Tuple2<String, String>> dataSet=datasource.flatMap(new FlatMapFunction<Row, TTea>() {
			@Override
			public void flatMap(Row value, Collector<TTea> out) throws Exception {
				// TODO Auto-generated method stub
				
				out.collect(new TTea(value.toString()));	
			}
		}).flatMap(new FlatMapFunction<TTea, Tuple2<String,String>>() {
			@Override
			public void flatMap(TTea value, Collector<Tuple2<String, String>> out) throws Exception {
				// TODO Auto-generated method stub
				out.collect(new Tuple2(value.getTea_no(),JSON.toJSON(value).toString()));
			}
		});
		
		
		FlinkJedisPoolConfig jedisPoolConfig = new FlinkJedisPoolConfig.Builder()
			     .setHost("192.168.100.133").setPort(6379).setDatabase(1).setPassword("123456").setTimeout(30000).setMaxIdle(100).setMaxTotal(200).build();
		dataSet.output(new RedisOutPutFormat(jedisPoolConfig, new RedisHsetMapper("T_TEA")));
				env.execute();
			
     

	}
	public TTea(String values){
		   String[] vs=values.split(",");
		   this.setId(vs[0]);
		   this.setTea_no(vs[1]);
		   this.setName_(vs[2]);
		   this.setIdno(vs[3]);
		   this.setFomer_name(vs[4]);
		   this.setBirthday(vs[5]);
		   this.setDept_id(vs[6]);
		   this.setSex_code(vs[7]);
		   this.setNatton_code(vs[8]);
		   this.setMarried((vs[9]!=null && !vs[9].equals("null"))?Integer.valueOf(vs[9]):null);
		   this.setEdu_id(vs[10]);
		   this.setDegree_id(vs[11]);
		   this.setSubject_id(vs[12]);
		   this.setDomicile_id(vs[13]);
		   this.setPlace_domicile(vs[14]);
		   this.setAnmelden_code(vs[15]);
		   this.setJoin_party_date(vs[16]);
		   this.setPolitics_code(vs[17]);
		   this.setIn_date(vs[18]);
		   this.setAuthorized_strength_id(vs[19]);
		   this.setTea_source_id(vs[20]);
		   this.setBzlb_code(vs[21]);
		   this.setZyjszw_id(vs[22]);
		   this.setSkill_moves_code(vs[23]);
		   this.setTeaching_date(vs[24]);
		   this.setGatq_code(vs[25]);
		   this.setTea_status_code(vs[26]);
		   this.setSfssjs((vs[27]!=null && !vs[27].equals("null"))?Integer.valueOf(vs[27]):null);
		   this.setIszb(Integer.valueOf(vs[28]));
		   this.setWork_date(vs[29]);
		   	   		   
		   
    }
	
	
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTea_no() {
		return tea_no;
	}

	public void setTea_no(String tea_no) {
		this.tea_no = tea_no;
	}

	public String getName_() {
		return name_;
	}

	public void setName_(String name_) {
		this.name_ = name_;
	}

	public String getIdno() {
		return idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getFomer_name() {
		return fomer_name;
	}

	public void setFomer_name(String fomer_name) {
		this.fomer_name = fomer_name;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	public String getSex_code() {
		return sex_code;
	}

	public void setSex_code(String sex_code) {
		this.sex_code = sex_code;
	}

	public String getNatton_code() {
		return natton_code;
	}

	public void setNatton_code(String natton_code) {
		this.natton_code = natton_code;
	}

	public Integer getMarried() {
		return married;
	}

	public void setMarried(Integer married) {
		this.married = married;
	}

	public String getEdu_id() {
		return edu_id;
	}

	public void setEdu_id(String edu_id) {
		this.edu_id = edu_id;
	}

	public String getDegree_id() {
		return degree_id;
	}

	public void setDegree_id(String degree_id) {
		this.degree_id = degree_id;
	}

	public String getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(String subject_id) {
		this.subject_id = subject_id;
	}

	public String getDomicile_id() {
		return domicile_id;
	}

	public void setDomicile_id(String domicile_id) {
		this.domicile_id = domicile_id;
	}

	public String getPlace_domicile() {
		return place_domicile;
	}

	public void setPlace_domicile(String place_domicile) {
		this.place_domicile = place_domicile;
	}

	public String getAnmelden_code() {
		return anmelden_code;
	}

	public void setAnmelden_code(String anmelden_code) {
		this.anmelden_code = anmelden_code;
	}

	public String getJoin_party_date() {
		return join_party_date;
	}

	public void setJoin_party_date(String join_party_date) {
		this.join_party_date = join_party_date;
	}

	public String getPolitics_code() {
		return politics_code;
	}

	public void setPolitics_code(String politics_code) {
		this.politics_code = politics_code;
	}

	public String getIn_date() {
		return in_date;
	}

	public void setIn_date(String in_date) {
		this.in_date = in_date;
	}

	public String getAuthorized_strength_id() {
		return authorized_strength_id;
	}

	public void setAuthorized_strength_id(String authorized_strength_id) {
		this.authorized_strength_id = authorized_strength_id;
	}

	public String getTea_source_id() {
		return tea_source_id;
	}

	public void setTea_source_id(String tea_source_id) {
		this.tea_source_id = tea_source_id;
	}

	public String getBzlb_code() {
		return bzlb_code;
	}

	public void setBzlb_code(String bzlb_code) {
		this.bzlb_code = bzlb_code;
	}

	public String getZyjszw_id() {
		return zyjszw_id;
	}

	public void setZyjszw_id(String zyjszw_id) {
		this.zyjszw_id = zyjszw_id;
	}

	public String getSkill_moves_code() {
		return skill_moves_code;
	}

	public void setSkill_moves_code(String skill_moves_code) {
		this.skill_moves_code = skill_moves_code;
	}

	public String getTeaching_date() {
		return teaching_date;
	}

	public void setTeaching_date(String teaching_date) {
		this.teaching_date = teaching_date;
	}

	public String getGatq_code() {
		return gatq_code;
	}

	public void setGatq_code(String gatq_code) {
		this.gatq_code = gatq_code;
	}

	public String getTea_status_code() {
		return tea_status_code;
	}

	public void setTea_status_code(String tea_status_code) {
		this.tea_status_code = tea_status_code;
	}

	public Integer getSfssjs() {
		return sfssjs;
	}

	public void setSfssjs(Integer sfssjs) {
		this.sfssjs = sfssjs;
	}

	public Integer getIszb() {
		return iszb;
	}

	public void setIszb(Integer iszb) {
		this.iszb = iszb;
	}

	public String getWork_date() {
		return work_date;
	}

	public void setWork_date(String work_date) {
		this.work_date = work_date;
	}	 
 
	public TTea(){
		   super();
    }
	private String getFieldsNames(){
		return "";
	}
	
	private List getFields(){
		return Arrays.asList(getFieldsNames());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}














