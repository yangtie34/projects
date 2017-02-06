package cn.gilight.dmm.xg.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 学生预警-疑似逃课
 * 
 * @author xuebl
 * @date 2016年6月12日 下午5:23:41
 */
@Entity
@Table(name="T_STU_WARNING_SKIP_CLASSES")
public class TStuWarningSkipClasses implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String stu_id;
	private String class_id;
	private String date_;
	/** 课程安排结果ID */
	private String course_arrangement_id;
	private String school_year;
	private String term_code;
	
	public TStuWarningSkipClasses() {
	}
	public TStuWarningSkipClasses(String id, String stu_id, String class_id, String date_, 
			String course_arrangement_id, String school_year, String term_code) {
		this.id = id;
		this.stu_id = stu_id;
		this.class_id = class_id;
		this.date_ = date_;
		this.course_arrangement_id = course_arrangement_id;
		this.school_year = school_year;
		this.term_code = term_code;
	}
	public TStuWarningSkipClasses(String stu_id, String class_id, String date_, String course_arrangement_id, 
			String school_year, String term_code) {
		this.stu_id = stu_id;
		this.class_id = class_id;
		this.date_ = date_;
		this.course_arrangement_id = course_arrangement_id;
		this.school_year = school_year;
		this.term_code = term_code;
	}
	/*public TStuWarningSkipClasses(String stu_id, String class_id, String date_, String course_arrangement_id) {
		this.stu_id = stu_id;
		this.class_id = class_id;
		this.date_ = date_;
		this.course_arrangement_id = course_arrangement_id;
	}*/

	
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

	@Column(name = "COURSE_ARRANGEMENT_ID", length = 20)
	public String getCourse_arrangement_id() {
		return course_arrangement_id;
	}
	public void setCourse_arrangement_id(String course_arrangement_id) {
		this.course_arrangement_id = course_arrangement_id;
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
}
