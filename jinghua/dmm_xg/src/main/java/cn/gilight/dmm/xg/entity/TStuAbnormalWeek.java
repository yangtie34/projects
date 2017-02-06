package cn.gilight.dmm.xg.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_STU_ABNORMAL_WEEK")
public class TStuAbnormalWeek implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String stu_id;
	private String start_date;
	private String end_date;
	private int weekth;
	private Double sum_money;
	private String school_year;
	private String term_code;
	
	public TStuAbnormalWeek(){
		
	}
	public TStuAbnormalWeek(String id,String stu_id,String start_date,String end_date,int weekth,Double sum_money,String school_year,String term_code){
		this.id = id;
		this.stu_id = stu_id;
		this.start_date = start_date;
		this.end_date = end_date;
		this.weekth= weekth;
		this.sum_money = sum_money;
		this.school_year = school_year;
		this.term_code = term_code;
	}
	public TStuAbnormalWeek(String stu_id,String start_date,String end_date,int weekth,Double sum_money,String school_year,String term_code){
		this.stu_id = stu_id;
		this.start_date = start_date;
		this.end_date = end_date;
		this.weekth= weekth;
		this.sum_money = sum_money;
		this.school_year = school_year;
		this.term_code = term_code;
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
	
	@Column(name = "START_DATE", length = 10)
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	
	@Column(name = "END_DATE", length = 10)
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	
	@Column(name = "WEEKTH", length = 2)
	public int getWeekth() {
		return weekth;
	}
	public void setWeekth(int weekth) {
		this.weekth = weekth;
	}
	
	@Column(name = "SUM_MONEY",  precision = 10, scale = 2)
	public Double getSum_money() {
		return sum_money;
	}
	public void setSum_money(Double sum_money) {
		this.sum_money = sum_money;
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
}
