package com.jhnu.system.permiss.entity;

import java.io.Serializable;

public class DataServe implements Serializable{

	private static final long serialVersionUID = -4394678358582293056L;
	
	private Long id;
	
	private String name_;
	
	private String servicename;
	
	private Long perm_id;
	
	private String perm_type;

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

	public String getServicename() {
		return servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	public Long getPerm_id() {
		return perm_id;
	}

	public void setPerm_id(Long perm_id) {
		this.perm_id = perm_id;
	}

	public String getPerm_type() {
		return perm_type;
	}

	public void setPerm_type(String perm_type) {
		this.perm_type = perm_type;
	}
	
}
