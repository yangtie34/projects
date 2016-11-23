package com.jhnu.edu.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "T_EDU_CODE")
public class TEDUCODE implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6056535730115438974L;

	private String code;
	
	private String name;
	
	private String codeType;
	
	private String codeTypeName;
	
	private String order;
	
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
	@Column(name = "CODETYPE_NAME")
	public String getCodeTypeName() {
		return codeTypeName;
	}
	public void setCodeTypeName(String codeTypeName) {
		this.codeTypeName = codeTypeName;
	}
	@Column(name = "ORDER_")
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}

}
