package cn.gilight.dmm.teaching.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_STU_BEHAVIOR")
public class TStuBehavior implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String stu_id;
	private String school_year;
	private String term_code;
	private Double value_;
	private String type_;
	
	
	public TStuBehavior() {
		super();
	}
	
	public TStuBehavior(String id, String stu_id,String school_year,String term_code,Double value_,String type_) {
		super();
		this.id = id;
		this.stu_id = stu_id;
		this.school_year = school_year;
		this.term_code = term_code;
		this.value_ = value_;
		this.type_ = type_;
	}
	
	public TStuBehavior(String stu_id,String school_year,String term_code,Double value_,String type_) {
		super();
		this.stu_id = stu_id;
		this.school_year = school_year;
		this.term_code = term_code;
		this.value_ = value_;
		this.type_ = type_;
	}
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 20, scale = 0)
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

	@Column(name = "VALUE_", precision = 8, scale = 2)
	public Double getValue_() {
		return value_;
	}
	
	public void setValue_(Double value_) {
		this.value_ = value_;
	}
	
	@Column(name = "TYPE_", length = 20)
	public String getType_() {
		return type_;
	}
	
	public void setType_(String type_) {
		this.type_ = type_;
	}
}
