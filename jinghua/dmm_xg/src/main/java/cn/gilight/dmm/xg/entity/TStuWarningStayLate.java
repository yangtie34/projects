package cn.gilight.dmm.xg.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 学生预警-晚勤晚归
 * 
 * @author xuebl
 * @date 2016年10月14日 下午5:23:41
 */
@Entity
@Table(name="T_STU_WARNING_STAY_LATE")
public class TStuWarningStayLate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String stu_id;
	private String class_id;
	private String date_;
	private String school_year;
	private String term_code;
	private String backtime;
	
	public TStuWarningStayLate() {
	}
	public TStuWarningStayLate(String id, String stu_id, String class_id, String date_, String school_year, String term_code, String backtime) {
		this.id = id;
		this.stu_id = stu_id;
		this.class_id = class_id;
		this.date_ = date_;
		this.school_year = school_year;
		this.term_code = term_code;
		this.backtime = backtime;
	}
	public TStuWarningStayLate(String stu_id, String class_id, String date_, String school_year, String term_code, String backtime) {
		this.stu_id = stu_id;
		this.class_id = class_id;
		this.date_ = date_;
		this.school_year = school_year;
		this.term_code = term_code;
		this.backtime = backtime;
	}
	
	
	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 20)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "STU_ID", length = 20)
	public String getStu_id() {
		return stu_id;
	}
	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}
	
	@Column(name = "CLASS_ID", length = 20)
	public String getClass_id() {
		return class_id;
	}
	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}
	
	@Column(name = "DATE_", length = 10)
	public String getDate_() {
		return date_;
	}
	public void setDate_(String date_) {
		this.date_ = date_;
	}

	@Column(name = "SCHOOL_YEAR", length = 10)
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
	
	@Column(name = "BACKTIME", length = 20)
	public String getBacktime() {
		return backtime;
	}
	public void setBacktime(String backtime) {
		this.backtime = backtime;
	}
}
