package cn.gilight.dmm.xg.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_STU_ABNORMAL_MAIL_MONTH")
public class TStuAbnormalMailMonth implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String name_;
	private String send_date;
	private String dept_id;
	private int status_;
	private String type_;
	private String school_year;
	private String term_code;
	private int month;
	
	
    public TStuAbnormalMailMonth(){
		
	}
	public TStuAbnormalMailMonth(String id,String name_,String send_date,String dept_id,int status_,String type_,String school_year,String term_code,int month){
		this.id = id;
		this.name_ = name_;
		this.send_date = send_date;
		this.dept_id = dept_id;
		this.status_ =status_;
		this.type_ = type_;
		this.school_year = school_year;
		this.term_code = term_code;
		this.month= month;
	}
	public TStuAbnormalMailMonth(String name_,String send_date,String dept_id,int status_,String type_,String school_year,String term_code,int month){
		this.name_ = name_;
		this.send_date = send_date;
		this.dept_id = dept_id;
		this.status_ =status_;
		this.type_ = type_;
		this.school_year = school_year;
		this.term_code = term_code;
		this.month= month;
	}
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 20)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "MONTH", length = 2)
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
	@Column(name="NAME_",length = 200)
	public String getName_() {
		return name_;
	}
	public void setName_(String name_) {
		this.name_ = name_;
	}
	
	@Column(name="SEND_DATE",length = 10)
	public String getSend_date() {
		return send_date;
	}
	public void setSend_date(String send_date) {
		this.send_date = send_date;
	}
	@Column(name="DEPT_ID",length = 20)
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	@Column(name="STATUS_",length = 1)
	public int getStatus_() {
		return status_;
	}
	public void setStatus_(int status_) {
		this.status_ = status_;
	}
	@Column(name="TYPE_",length = 20)
	public String getType_() {
		return type_;
	}
	public void setType_(String type_) {
		this.type_ = type_;
	}
}
