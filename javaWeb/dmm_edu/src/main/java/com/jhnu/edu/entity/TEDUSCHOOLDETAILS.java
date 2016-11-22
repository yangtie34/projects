package com.jhnu.edu.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "T_EDU_SCHOOL_DETAILS")
public class TEDUSCHOOLDETAILS implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7128319824854042793L;

	private String id;
	
	private String schoolId;
	
	private String titleId;
	
	private String value;
	
	private String year;
	
	@Column(name = "ID")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "SCHOOLId")
	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	@Column(name = "TITLEID")
	public String getTitleId() {
		return titleId;
	}

	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}
	@Column(name = "VALUE_")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	@Column(name = "YEAR_")
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	

}
