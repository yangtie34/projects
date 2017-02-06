package cn.gilight.dmm.teaching.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_STU_GRADUATE_RESULT_DEPT")
public class TStuGraduateResultDept implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String dept_id;
	private String school_year;
	private int sum_count;
	private int rel_count;
	private Double graduation_scale;
	private Double last_graduation_scale;
	private int degree_count;
	private Double degree_scale;
	private Double last_degree_scale;
	
	public TStuGraduateResultDept() {
		super();
	}
	
	public TStuGraduateResultDept(String id, String dept_id, String school_year, int sum_count, int rel_count, Double graduation_scale,
			Double last_graduation_scale,int degree_count,Double degree_scale,Double last_degree_scale) {
		super();
		this.id = id;
		this.dept_id = dept_id;
		this.school_year = school_year;
		this.sum_count = sum_count;
		this.rel_count = rel_count;
		this.graduation_scale = graduation_scale;
		this.last_graduation_scale = last_graduation_scale;
		this.degree_count = degree_count;
		this.degree_scale = degree_scale;
		this.last_degree_scale = last_degree_scale;
	}
	
	public TStuGraduateResultDept( String dept_id, String school_year, int sum_count, int rel_count, Double graduation_scale,
			Double last_graduation_scale,int degree_count,Double degree_scale,Double last_degree_scale) {
		super();
		this.dept_id = dept_id;
		this.school_year = school_year;
		this.sum_count = sum_count;
		this.rel_count = rel_count;
		this.graduation_scale = graduation_scale;
		this.last_graduation_scale = last_graduation_scale;
		this.degree_count = degree_count;
		this.degree_scale = degree_scale;
		this.last_degree_scale = last_degree_scale;
	}
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 20, scale = 0)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "DEPT_ID", length = 20)
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	@Column(name = "SCHOOL_YEAR", length = 20)
	public String getSchool_year() {
		return school_year;
	}
	public void setSchool_year(String school_year) {
		this.school_year = school_year;
	}
	@Column(name = "SUM_COUNT", precision = 10)
	public int getSum_count() {
		return sum_count;
	}
	public void setSum_count(int sum_count) {
		this.sum_count = sum_count;
	}
	@Column(name = "REL_COUNT", precision = 10)
	public int getRel_count() {
		return rel_count;
	}
	public void setRel_count(int rel_count) {
		this.rel_count = rel_count;
	}
	@Column(name = "GRADUATION_SCALE", precision = 4, scale = 1)
	public Double getGraduation_scale() {
		return graduation_scale;
	}
	public void setGraduation_scale(Double graduation_scale) {
		this.graduation_scale = graduation_scale;
	}
	@Column(name = "LAST_GRADUATION_SCALE", precision = 4, scale = 1)
	public Double getLast_graduation_scale() {
		return last_graduation_scale;
	}
	public void setLast_graduation_scale(Double last_graduation_scale) {
		this.last_graduation_scale = last_graduation_scale;
	}
	@Column(name = "DEGREE_COUNT", precision = 10)
	public int getDegree_count() {
		return degree_count;
	}
	public void setDegree_count(int degree_count) {
		this.degree_count = degree_count;
	}
	@Column(name = "DEGREE_SCALE", precision = 4, scale = 1)
	public Double getDegree_scale() {
		return degree_scale;
	}
	public void setDegree_scale(Double degree_scale) {
		this.degree_scale = degree_scale;
	}
	@Column(name = "LAST_DEGREE_SCALE", precision = 4, scale = 1)
	public Double getLast_degree_scale() {
		return last_degree_scale;
	}
	public void setLast_degree_scale(Double last_degree_scale) {
		this.last_degree_scale = last_degree_scale;
	}
}
