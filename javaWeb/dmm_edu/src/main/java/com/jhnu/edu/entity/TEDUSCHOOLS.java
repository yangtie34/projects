package com.jhnu.edu.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "T_EDU_SCHOOLS")
public class TEDUSCHOOLS implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -310151340147417587L;

	private String id;
	
	private String chName;
	
	private String enName;
	
	private String icoId;
	
	private String desc;
	
	@Column(name = "ID")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "CH_NAME")
	public String getChName() {
		return chName;
	}

	public void setChName(String chName) {
		this.chName = chName;
	}
	@Column(name = "EN_NAME")
	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}
	@Column(name = "ICOID")
	public String getIcoId() {
		return icoId;
	}

	public void setIcoId(String icoId) {
		this.icoId = icoId;
	}
	@Column(name = "DESC_")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
