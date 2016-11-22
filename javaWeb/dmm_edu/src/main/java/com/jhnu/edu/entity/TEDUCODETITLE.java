package com.jhnu.edu.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "T_EDU_CODE_TITLE")
public class TEDUCODETITLE implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -310451293822126173L;

	private String code;
	
	private String name;
	
	private String isCode;
	
	private String codeType;
	
	@Column(name = "CODE_")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(name = "NAME_")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "CODE_TYPE")
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	@Column(name = "ISCODE")
	public String getIsCode() {
		return isCode;
	}
	public void setIsCode(String isCode) {
		this.isCode = isCode;
	}

}
