package com.jhnu.system.common.code.entity;

import java.io.Serializable;

public class Code implements Serializable{

	private static final long serialVersionUID = -3608406780630664757L;

	private String code_;
	
	private String name_;
	
	private String code_category;
	
	private String code_type;
	
	private String codetype_name;
	
	private Integer istrue;
	
	private Integer order_;

	public String getCode_() {
		return code_;
	}

	public void setCode_(String code_) {
		this.code_ = code_;
	}

	public String getName_() {
		return name_;
	}

	public void setName_(String name_) {
		this.name_ = name_;
	}

	public String getCode_category() {
		return code_category;
	}

	public void setCode_category(String code_category) {
		this.code_category = code_category;
	}

	public String getCode_type() {
		return code_type;
	}

	public void setCode_type(String code_type) {
		this.code_type = code_type;
	}

	public String getCodetype_name() {
		return codetype_name;
	}

	public void setCodetype_name(String codetype_name) {
		this.codetype_name = codetype_name;
	}

	public Integer getIstrue() {
		return istrue;
	}

	public void setIstrue(Integer istrue) {
		this.istrue = istrue;
	}

	public Integer getOrder_() {
		return order_;
	}

	public void setOrder_(Integer order_) {
		this.order_ = order_;
	}
	
	
	
	
}
