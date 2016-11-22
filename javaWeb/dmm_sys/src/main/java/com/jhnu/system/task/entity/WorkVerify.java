package com.jhnu.system.task.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;
@Table(name = "T_SYS_SCHEDULE_WORK_VERIFY")
public class WorkVerify implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8772262895787471512L;

	private String id;
	
	private String workId;
	
	private String verifyId;
	
	private int order_;
	
	private Integer rule=1;
	
	@Column(name = "ID")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "WORKID")
	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}
	@Column(name = "VERIFYID")
	public String getVerifyId() {
		return verifyId;
	}

	public void setVerifyId(String verifyId) {
		this.verifyId = verifyId;
	}
	@Column(name = "ORDER_")
	public int getOrder_() {
		return order_;
	}

	public void setOrder_(int order_) {
		this.order_ = order_;
	}
	@Column(name = "RULE")
	public Integer getRule() {
		return rule;
	}

	public void setRule(Integer rule) {
		this.rule = rule;
	}
}
