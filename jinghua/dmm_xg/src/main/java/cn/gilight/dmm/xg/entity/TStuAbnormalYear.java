package cn.gilight.dmm.xg.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_STU_ABNORMAL_YEAR")
public class TStuAbnormalYear implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String stu_id;
	private String school_year;
	private Double sum_money;
	public TStuAbnormalYear(){
		
	}
	public TStuAbnormalYear(String id,String stu_id,String school_year,Double sum_money){
		this.id = id;
		this.stu_id = stu_id;
		this.sum_money = sum_money;
		this.school_year = school_year;
	}
	public TStuAbnormalYear(String stu_id,String school_year,Double sum_money){
		this.stu_id = stu_id;
		this.sum_money = sum_money;
		this.school_year = school_year;
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
}

