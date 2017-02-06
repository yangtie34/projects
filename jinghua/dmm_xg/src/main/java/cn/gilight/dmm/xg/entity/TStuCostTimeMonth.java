package cn.gilight.dmm.xg.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_STU_COST_TIME_MONTH")
public class TStuCostTimeMonth implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String start_date;
	private String end_date;
	private int month;
	private String school_year;
	private String term_code;
	private int days;
	
	
	public TStuCostTimeMonth(){
		
	}
	public TStuCostTimeMonth(String id,String start_date,String end_date,int month,String school_year,String term_code,int days){
		this.id = id;
		this.start_date = start_date;
	    this.end_date = end_date;
	    this.month = month;
	    this.school_year = school_year;
	    this.start_date = term_code;
	    this.days = days;
	}
	public TStuCostTimeMonth(String start_date,String end_date,int month,String school_year,String term_code,int days){
	    this.start_date = start_date;
	    this.end_date = end_date;
	    this.month = month;
	    this.school_year = school_year;
	    this.start_date = term_code;
	    this.days = days;
	}
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 20)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "START_DATE", length =20)
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	
	@Column(name = "END_DATE", length = 20)
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	@Column(name = "MONTH", length = 10)
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
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
	@Column(name = "DAYS", length = 10)
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	
}
