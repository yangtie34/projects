package com.jhnu.system.task.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;
@Table(name = "T_SYS_SCHEDULE_PLAN_WORK")
public class PlanWork implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8613850076413383620L;

	private String id;
	
	private String planId;
	@Column(name = "ID")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "PLANID")
	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}
	@Column(name = "WORKID")
	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}
	@Column(name = "ORDER_")
	public int getOrder_() {
		return order_;
	}

	public void setOrder_(int order_) {
		this.order_ = order_;
	}

	private String workId;
	
	private int order_;
}
