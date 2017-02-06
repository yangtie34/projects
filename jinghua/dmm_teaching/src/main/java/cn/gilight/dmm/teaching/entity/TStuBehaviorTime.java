package cn.gilight.dmm.teaching.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="T_STU_BEHAVIOR_TIME")
public class TStuBehaviorTime implements Serializable {
private static final long serialVersionUID = 1L;
	
	private String id;
	private String value_;
	private String school_year;
	private String term_code;
	private String type_;
	private String stu_type;
	private String season;
	
	public TStuBehaviorTime() {
		super();
	}
	
	public TStuBehaviorTime(String id, String value_,String school_year,String term_code,String type_,String stu_type,String season) {
		super();
		this.id = id;
		this.value_ = value_;
		this.school_year = school_year;
		this.term_code = term_code;
		this.type_ = type_;	
		this.stu_type=stu_type;
		this.season = season;
	}
	
	public TStuBehaviorTime(String value_,String school_year,String term_code,String type_,String stu_type,String season) {
		super();
		this.school_year = school_year;
		this.term_code = term_code;
		this.value_ = value_;
		this.type_ = type_;
		this.stu_type=stu_type;
		this.season = season;
	}
	
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 20, scale = 0)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "VALUE_", length = 20)
	public String getValue_() {
		return value_;
	}
	public void setValue_(String value_) {
		this.value_ = value_;
	}
	@Column(name = "SCHOOL_YEAR", length = 9)
	public String getSchool_year() {
		return school_year;
	}
	public void setSchool_year(String school_year) {
		this.school_year = school_year;
	}
	@Column(name = "TERM_CODE", length = 10)
	public String getTerm_code() {
		return term_code;
	}
	public void setTerm_code(String term_code) {
		this.term_code = term_code;
	}
	@Column(name = "TYPE_", length = 20)
	public String getType_() {
		return type_;
	}
	public void setType_(String type_) {
		this.type_ = type_;
	}
	@Column(name = "STU_TYPE", length = 20)
	public String getStu_type() {
		return stu_type;
	}
	public void setStu_type(String stu_type) {
		this.stu_type = stu_type;
	}
	@Column(name = "SEASON", length = 20)
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
}
