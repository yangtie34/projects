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

public class TStu {
	private String id;
	private String no_;
	private String examinee_no;
	private String name_;
	private String fomer_name;
	private String english_name;
	private String birthday;
	private String idno;
	private String sex_code;
	private String natton_code;
	private String politics_code;
	private String anmelden_code;
	private Integer married;
	private String dept_id;
	private String major_id;
	private String class_id;
	private Integer length_schooling;
	
	
	
	
	
	private String before_edu_id;
	private String edu_id;
	private String degree_id;
	private String training_id;
	private String training_level_code;
	private String recrutt_code;
	private String learning_style_code;
	private String stu_from_code;
	private String stu_category_id;
	private String enroll_date;
	private String enroll_grade;
	private String stu_state_code;
	private String stu_roll_code;
	private String stu_origin_id;
	private String  place_domicile;
	private String schooltag;
	private String gatq_code;
	private String learning_from_code;
	public static JDBCInputFormat createInputFormat() {
		TypeInformation<?>[] fieldTypes = new TypeInformation<?>[] {
				BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.INT_TYPE_INFO,
				BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.INT_TYPE_INFO,
				BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,
				BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.STRING_TYPE_INFO,
		
		};
		RowTypeInfo rowTypeInfo = new RowTypeInfo(fieldTypes);
		JDBCInputFormat jdbcInputFormat = JDBCInputFormat
				.buildJDBCInputFormat()
				.setDrivername("oracle.jdbc.driver.OracleDriver")
				.setDBUrl("jdbc:oracle:thin:@192.168.30.88:1521/orcl")
				.setUsername("dm")
				.setPassword("dm")
				.setQuery(
						"select ID,NO_,EXAMINEE_NO,NAME_,FOMER_NAME,ENGLISH_NAME,BIRTHDAY,IDNO,SEX_CODE,NATION_CODE,POLITICS_CODE,ANMELDEN_CODE,MARRIED,DEPT_ID,MAJOR_ID,CLASS_ID,LENGTH_SCHOOLING,BEFORE_EDU_ID,EDU_ID,DEGREE_ID,TRAINING_ID"
						+ ",TRAINING_LEVEL_CODE,RECRUIT_CODE,LEARNING_STYLE_CODE,STU_FROM_CODE,STU_CATEGORY_ID,ENROLL_DATE,ENROLL_GRADE,STU_STATE_CODE,"
						+ "STU_ROLL_CODE,STU_ORIGIN_ID,PLACE_DOMICILE,SCHOOLTAG,GATQ_CODE,LEARNING_FORM_CODE  from T_STU")
				.setRowTypeInfo(rowTypeInfo).finish();
		return jdbcInputFormat;
	}
	
	public static void main(String[] args) throws Throwable  {
    
		final ExecutionEnvironment env=ExecutionEnvironment.getExecutionEnvironment();
		
		DataSource<Row> datasource=env.createInput(createInputFormat());
		DataSet<Tuple2<String, String>> dataSet=datasource.flatMap(new FlatMapFunction<Row, TStu>() {
			@Override
			public void flatMap(Row value, Collector<TStu> out) throws Exception {
				// TODO Auto-generated method stub
				
				out.collect(new TStu(value.toString()));	
			}
		}).flatMap(new FlatMapFunction<TStu, Tuple2<String,String>>() {
			@Override
			public void flatMap(TStu value, Collector<Tuple2<String, String>> out) throws Exception {
				// TODO Auto-generated method stub
				out.collect(new Tuple2(value.getNo_(),JSON.toJSON(value).toString()));
			}
		});
		
		
		FlinkJedisPoolConfig jedisPoolConfig = new FlinkJedisPoolConfig.Builder()
			     .setHost("192.168.100.133").setPort(6379).setDatabase(1).setPassword("123456").setTimeout(30000).setMaxIdle(100).setMaxTotal(200).build();
		dataSet.output(new RedisOutPutFormat(jedisPoolConfig, new RedisHsetMapper("T_STU")));
				env.execute();
			
     

	}

	
	
	
	
	public TStu(String values){
		   String[] vs=values.split(",");
		   this.setId(vs[0]);
		   this.setNo_(vs[1]);
		   this.setExaminee_no(vs[2]);
		   this.setName_(vs[3]);
		   this.setFomer_name(vs[4]);
		   this.setEnglish_name(vs[5]);
		   this.setBirthday(vs[6]);
		   this.setIdno(vs[7]);
		   this.setSex_code(vs[8]);
		   this.setNatton_code(vs[9]);
		   this.setPolitics_code(vs[10]);
		   this.setAnmelden_code(vs[11]);
		   this.setMarried((vs[12]!=null && !vs[12].equals("null"))?Integer.valueOf(vs[12]):null);
		   this.setDept_id(vs[13]);
		   this.setMajor_id(vs[14]);
		   this.setClass_id(vs[15]);
		   this.setLength_schooling(Integer.valueOf(vs[16]));
		   this.setBefore_edu_id(vs[17]);
		   this.setEdu_id(vs[18]);
		   this.setDegree_id(vs[19]);
		   this.setTraining_id(vs[20]);
		   this.setTraining_level_code(vs[21]);
		   this.setRecrutt_code(vs[22]);
		   this.setLearning_style_code(vs[23]);
		   this.setStu_from_code(vs[24]);
		   this.setStu_category_id(vs[25]);
		   this.setEnroll_date(vs[26]);
		   this.setEnroll_grade(vs[27]);
		   this.setStu_state_code(vs[28]);
		   this.setStu_roll_code(vs[29]);
		   this.setStu_origin_id(vs[30]);
		   this.setPlace_domicile(vs[31]);
		   this.setSchooltag(vs[32]);
		   this.setGatq_code(vs[33]);
		   this.setLearning_from_code(vs[34]);
		   
		   
		   
		   
}	 
	
	
	


	public TStu(){
		  super();
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

	public String getExaminee_no() {
		return examinee_no;
	}

	public void setExaminee_no(String examinee_no) {
		this.examinee_no = examinee_no;
	}

	public String getName_() {
		return name_;
	}

	public void setName_(String name_) {
		this.name_ = name_;
	}

	public String getFomer_name() {
		return fomer_name;
	}

	public void setFomer_name(String fomer_name) {
		this.fomer_name = fomer_name;
	}

	public String getEnglish_name() {
		return english_name;
	}

	public void setEnglish_name(String english_name) {
		this.english_name = english_name;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getIdno() {
		return idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
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

	public String getPolitics_code() {
		return politics_code;
	}

	public void setPolitics_code(String politics_code) {
		this.politics_code = politics_code;
	}

	public String getAnmelden_code() {
		return anmelden_code;
	}

	public void setAnmelden_code(String anmelden_code) {
		this.anmelden_code = anmelden_code;
	}

	public Integer getMarried() {
		return married;
	}

	public void setMarried(Integer married) {
		this.married = married;
	}

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	public String getMajor_id() {
		return major_id;
	}

	public void setMajor_id(String major_id) {
		this.major_id = major_id;
	}

	public String getClass_id() {
		return class_id;
	}

	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}

	public Integer getLength_schooling() {
		return length_schooling;
	}

	public void setLength_schooling(Integer length_schooling) {
		this.length_schooling = length_schooling;
	}

	public String getBefore_edu_id() {
		return before_edu_id;
	}

	public void setBefore_edu_id(String before_edu_id) {
		this.before_edu_id = before_edu_id;
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

	public String getTraining_id() {
		return training_id;
	}

	public void setTraining_id(String training_id) {
		this.training_id = training_id;
	}

	public String getTraining_level_code() {
		return training_level_code;
	}

	public void setTraining_level_code(String training_level_code) {
		this.training_level_code = training_level_code;
	}

	public String getRecrutt_code() {
		return recrutt_code;
	}

	public void setRecrutt_code(String recrutt_code) {
		this.recrutt_code = recrutt_code;
	}

	public String getLearning_style_code() {
		return learning_style_code;
	}

	public void setLearning_style_code(String learning_style_code) {
		this.learning_style_code = learning_style_code;
	}

	public String getStu_from_code() {
		return stu_from_code;
	}

	public void setStu_from_code(String stu_from_code) {
		this.stu_from_code = stu_from_code;
	}

	public String getStu_category_id() {
		return stu_category_id;
	}

	public void setStu_category_id(String stu_category_id) {
		this.stu_category_id = stu_category_id;
	}

	public String getEnroll_date() {
		return enroll_date;
	}

	public void setEnroll_date(String enroll_date) {
		this.enroll_date = enroll_date;
	}

	public String getEnroll_grade() {
		return enroll_grade;
	}

	public void setEnroll_grade(String enroll_grade) {
		this.enroll_grade = enroll_grade;
	}

	public String getStu_state_code() {
		return stu_state_code;
	}

	public void setStu_state_code(String stu_state_code) {
		this.stu_state_code = stu_state_code;
	}

	public String getStu_roll_code() {
		return stu_roll_code;
	}

	public void setStu_roll_code(String stu_roll_code) {
		this.stu_roll_code = stu_roll_code;
	}

	public String getStu_origin_id() {
		return stu_origin_id;
	}

	public void setStu_origin_id(String stu_origin_id) {
		this.stu_origin_id = stu_origin_id;
	}

	public String getPlace_domicile() {
		return place_domicile;
	}

	public void setPlace_domicile(String place_domicile) {
		this.place_domicile = place_domicile;
	}

	public String getSchooltag() {
		return schooltag;
	}

	public void setSchooltag(String schooltag) {
		this.schooltag = schooltag;
	}

	public String getGatq_code() {
		return gatq_code;
	}

	public void setGatq_code(String gatq_code) {
		this.gatq_code = gatq_code;
	}

	public String getLearning_from_code() {
		return learning_from_code;
	}

	public void setLearning_from_code(String learning_from_code) {
		this.learning_from_code = learning_from_code;
	}

	private String getFieldsNames(){
		return "";
	}
	
	private List getFields(){
		return Arrays.asList(getFieldsNames());
	}
}
