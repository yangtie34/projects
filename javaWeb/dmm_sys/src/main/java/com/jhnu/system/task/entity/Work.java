package com.jhnu.system.task.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;
@Table(name = "T_SYS_SCHEDULE_WORK")
public class Work implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1730430192966723014L;

	private String id;
	
	private String name_;
	
	private String group_;
	
	private String service;
	
	
	private String desc_;
	
	private Integer isTrue;
	@Column(name = "ID")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "NAME_")
	public String getName_() {
		return name_;
	}

	public void setName_(String name_) {
		this.name_ = name_;
	}
	@Column(name = "GROUP_")
	public String getGroup_() {
		return group_;
	}

	public void setGroup_(String group_) {
		this.group_ = group_;
	}
	@Column(name = "DESC_")
	public String getDesc_() {
		return desc_;
	}

	public void setDesc_(String desc_) {
		this.desc_ = desc_;
	}

	@Column(name = "ISTRUE")
	public Integer getIsTrue() {
		return isTrue;
	}

	public void setIsTrue(Integer isTrue) {
		this.isTrue = isTrue;
	}
	@Column(name = "SERVICE")
	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}
	
}
