package cn.gilight.dmm.xg.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_STU_ABNORMAL_MEAL")
public class TStuAbnormalMeal implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String stu_id;
	private String school_year;
	private String term_code;
	private String date_;
	private String meal_name;
	private Double sum_money;
	public TStuAbnormalMeal(){
		
	}
	public TStuAbnormalMeal(String id,String stu_id,String school_year,String term_code,String date_,String meal_name,Double sum_money){
		this.id = id;
		this.stu_id = stu_id;
		this.school_year = school_year;
		this.term_code =term_code;
		this.date_ =date_;
		this.meal_name =meal_name;
		this.sum_money = sum_money;
	}
	public TStuAbnormalMeal(String stu_id,String school_year,String term_code,String date_,String meal_name,Double sum_money){
		this.stu_id = stu_id;
		this.school_year = school_year;
		this.term_code =term_code;
		this.date_ =date_;
		this.meal_name =meal_name;
		this.sum_money = sum_money;
	}
	
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
	
	@Column(name = "SCHOOL_YEAR", length = 9)
	public String getSchool_year() {
		return school_year;
	}
	public void setSchool_year(String school_year) {
		this.school_year = school_year;
	}
	
	@Column(name = "SUM_MONEY",  precision = 10, scale = 2)
	public Double getSum_money() {
		return sum_money;
	}
	public void setSum_money(Double sum_money) {
		this.sum_money = sum_money;
	}
	@Column(name = "TERM_CODE", length = 10)
	public String getTerm_code() {
		return term_code;
	}
	public void setTerm_code(String term_code) {
		this.term_code = term_code;
	}
	@Column(name = "DATE_", length = 10)
	public String getDate_() {
		return date_;
	}
	public void setDate_(String date_) {
		this.date_ = date_;
	}
	
	@Column(name = "MEAL_NAME", length = 20)
	public String getMeal_name() {
		return meal_name;
	}
	public void setMeal_name(String meal_name) {
		this.meal_name = meal_name;
	}
}

