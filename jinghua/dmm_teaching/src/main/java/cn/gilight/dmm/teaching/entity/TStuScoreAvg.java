package cn.gilight.dmm.teaching.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_STU_SCORE_AVG")
public class TStuScoreAvg implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String stu_id;
	private String school_year;
	private String term_code;
	private Double weight_avg;
	private Double score_avg;
	
	public TStuScoreAvg() {
		super();
	}
	public TStuScoreAvg(String id, String stu_id, String school_year, String term_code, Double weight_avg, Double score_avg) {
		super();
		this.id = id;
		this.stu_id = stu_id;
		this.school_year = school_year;
		this.term_code = term_code;
		this.weight_avg = weight_avg;
		this.score_avg = score_avg;
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
	
	@Column(name = "SCHOOL_YEAR", length = 20)
	public String getSchool_year() {
		return school_year;
	}
	public void setSchool_year(String school_year) {
		this.school_year = school_year;
	}
	
	@Column(name = "TERM_CODE", length = 20)
	public String getTerm_code() {
		return term_code;
	}
	public void setTerm_code(String term_code) {
		this.term_code = term_code;
	}
	
	@Column(name = "WEIGHT_AVG", precision = 8, scale = 2)
	public Double getWeight_avg() {
		return weight_avg;
	}
	public void setWeight_avg(Double weight_avg) {
		this.weight_avg = weight_avg;
	}
	
	@Column(name = "SCORE_AVG", precision = 8, scale = 2)
	public Double getScore_avg() {
		return score_avg;
	}
	public void setScore_avg(Double score_avg) {
		this.score_avg = score_avg;
	}
}
