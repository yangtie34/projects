package com.jhnu.system.task.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "T_SYS_SCHEDULE_PLAN")
public class Plan implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8248923665032362393L;

	private String id;
	
	private String name_;
	
	private String group_;
	
	private String cron_expression;
	
	private String desc_;
	
	private Integer isTrue=1;
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
	@Column(name = "CRON_EXPRESSION")
	public String getCron_expression() {
		return cron_expression;
	}

	public void setCron_expression(String cron_expression) {
		this.cron_expression = cron_expression;
	}
	@Column(name = "ISTRUE")
	public Integer getIsTrue() {
		return isTrue;
	}

	public void setIsTrue(Integer isTrue) {
		this.isTrue = isTrue;
	}
	
}
