package cn.gilight.dmm.teaching.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 
 * @author xuebl
 * @date 2016年6月22日 下午8:27:51
 */
@Entity
@Table(name="T_STU_SCORE_GPA")
public class TStuScoreGpa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String stu_id;
	private String school_year;
	private String term_code;
	private Double gpa;
	private String gpa_code;
	
	
	public TStuScoreGpa() {
		super();
	}
	public TStuScoreGpa(String id, String stu_id, String school_year, String term_code, Double gpa, String gpa_code) {
		super();
		this.id = id;
		this.stu_id = stu_id;
		this.school_year = school_year;
		this.term_code = term_code;
		this.gpa = gpa;
		this.gpa_code = gpa_code;
	}
	
	
	// Property accessors
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
	public void setTerm_code(String term_code) {
		this.term_code = term_code;
	}
	public void setGpa(Double gpa) {
		this.gpa = gpa;
	}

	@Column(name = "GPA", precision = 8, scale = 2)
	public Double getGpa() {
		return gpa;
	}
	public String getTerm_code() {
		return term_code;
	}

	@Column(name = "GPA_CODE", length = 20)
	public String getGpa_code() {
		return gpa_code;
	}
	public void setGpa_code(String gpa_code) {
		this.gpa_code = gpa_code;
	}
}
