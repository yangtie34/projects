package com.jhnu.system.task.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "T_SYS_SCHEDULE_PLAN_LOG")
public class PlanLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 650284067623685675L;

	private String id;
	
	private String planId;
	private Integer isYes;
	private String startTime;
	
	private String endTime;
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
	
	@Column(name = "STARTTIME")
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	@Column(name = "ENDTIME")
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	@Column(name = "ISYES")
	public Integer getIsYes() {
		return isYes;
	}

	public void setIsYes(Integer isYes) {
		this.isYes = isYes;
	}
}
