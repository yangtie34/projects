package com.jhnu.syspermiss.permiss.entity;

import java.io.Serializable;

public class Operate implements Serializable{

	private static final long serialVersionUID = 1357467126249514336L;
	
	private Long id;
	
	private String name_;
	
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName_() {
		return name_;
	}

	public void setName_(String name_) {
		this.name_ = name_;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
