package cn.gilight.dmm.xg.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_STU_ABNORMAL_TERM")
public class TStuAbnormalTerm implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String stu_id;
	private String school_year;
	private String term_code;
	private Double sum_money;
	private int breakfast_;
	private int lunch_;
	private int dinner_;
	public TStuAbnormalTerm(){
		
	}
	public TStuAbnormalTerm(String id,String stu_id,String school_year,String term_code,Double sum_money,
			int breakfast_,int lunch_,int dinner_){
		this.id = id;
		this.stu_id = stu_id;
		this.sum_money = sum_money;
		this.school_year = school_year;
		this.term_code = term_code;
		this.breakfast_ = breakfast_;
		this.lunch_ = lunch_;
		this.dinner_ = dinner_;
	}
	public TStuAbnormalTerm(String stu_id,String school_year,String term_code,Double sum_money,
			int breakfast_,int lunch_,int dinner_){
		this.stu_id = stu_id;
		this.sum_money = sum_money;
		this.school_year = school_year;
		this.term_code = term_code;
		this.breakfast_ = breakfast_;
		this.lunch_ = lunch_;
		this.dinner_ = dinner_;
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
	@Column(name = "TERM_CODE", length = 10)
	public String getTerm_code() {
		return term_code;
	}
	public void setTerm_code(String term_code) {
		this.term_code = term_code;
	}
	
	@Column(name = "SUM_MONEY",  precision = 10, scale = 2)
	public Double getSum_money() {
		return sum_money;
	}
	public void setSum_money(Double sum_money) {
		this.sum_money = sum_money;
	}
	
	@Column(name = "BREAKFAST_", length = 10)
	public int getBreakfast_() {
		return breakfast_;
	}
	public void setBreakfast_(int breakfast_) {
		this.breakfast_ = breakfast_;
	}
	
	@Column(name = "LUNCH_", length = 10)
	public int getLunch_() {
		return lunch_;
	}
	public void setLunch_(int lunch_) {
		this.lunch_ = lunch_;
	}
	
	@Column(name = "DINNER_", length = 10)
	public int getDinner_() {
		return dinner_;
	}
	public void setDinner_(int dinner_) {
		this.dinner_ = dinner_;
	}
}
