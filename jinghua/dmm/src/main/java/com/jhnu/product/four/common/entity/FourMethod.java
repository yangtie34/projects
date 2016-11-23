package com.jhnu.product.four.common.entity;

import java.io.Serializable;

public class FourMethod implements Serializable{

	private static final long serialVersionUID = -1246320216049329076L;
	
	private Long id;
	
	private String bean;
	
	private String method;
	
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBean() {
		return bean;
	}

	public void setBean(String bean) {
		this.bean = bean;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
