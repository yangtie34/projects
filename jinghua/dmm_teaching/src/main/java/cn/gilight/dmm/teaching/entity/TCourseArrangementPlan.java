package cn.gilight.dmm.teaching.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="T_COURSE_ARRANGEMENT_PLAN")
public class TCourseArrangementPlan  implements Serializable{
private static final long serialVersionUID = 1L;
	
	private String id;
	private String school_year;
	private String term_code;
	private String class_id;
	private String course_code;
	private Double credit;
	private String course_attr_code;
	private String course_category_code;
	private String course_nature_code;
	
	
	public TCourseArrangementPlan() {
		super();
	}
	
	public TCourseArrangementPlan(String id, String school_year,String term_code,String class_id,String course_code,Double credit,String course_attr_code,String course_category_code, String course_nature_code) {
		super();
		this.id = id;
		this.school_year = school_year;
		this.term_code = term_code;
		this.class_id = class_id;
		this.course_code = course_code;
		this.credit = credit;
		this.course_attr_code = course_attr_code;
		this.course_category_code = course_category_code;
		this.course_nature_code = course_nature_code;
	}
	
	public TCourseArrangementPlan(String school_year,String term_code,String class_id,String course_code,Double credit,String course_attr_code,String course_category_code, String course_nature_code) {
		super();
		this.school_year = school_year;
		this.term_code = term_code;
		this.class_id = class_id;
		this.course_code = course_code;
		this.credit = credit;
		this.course_attr_code = course_attr_code;
		this.course_category_code = course_category_code;
		this.course_nature_code = course_nature_code;
	}
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 30, scale = 0)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	@Column(name = "CLASS_ID", length = 20)
	public String getClass_id() {
		return class_id;
	}

	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}

	@Column(name = "COURSE_CODE", length = 10)
	public String getCourse_code() {
		return course_code;
	}

	public void setCourse_code(String course_code) {
		this.course_code = course_code;
	}
	
	@Column(name = "CREDIT", precision = 4, scale = 1)
	public Double getCredit() {
		return credit;
	}

	public void setCredit(Double credit) {
		this.credit = credit;
	}
	
	@Column(name = "COURSE_ATTR_CODE", length = 20)
	public String getCourse_attr_code() {
		return course_attr_code;
	}

	public void setCourse_attr_code(String course_attr_code) {
		this.course_attr_code = course_attr_code;
	}
	@Column(name = "COURSE_CATEGORY_CODE", length = 20)
	public String getCourse_category_code() {
		return course_category_code;
	}

	public void setCourse_category_code(String course_category_code) {
		this.course_category_code = course_category_code;
	}
	@Column(name = "COURSE_NATURE_CODE", length = 10)
	public String getCourse_nature_code() {
		return course_nature_code;
	}

	public void setCourse_nature_code(String course_nature_code) {
		this.course_nature_code = course_nature_code;
	}

	
	

}

