package com.jhnu.syspermiss.task.entity;

import java.io.Serializable;

public class PlanLogDetails implements Serializable{


	private static final long serialVersionUID = 3583990887772980069L;

	private String id;
	
	private String planLogId;
	
	private String logTypeId;
	
	private Integer isYes;
	
	private String resultDesc;
	
	private String logType;
	
	private String startTime;
	
	private String endTime;
	
	private String check;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getPlanLogId() {
		return planLogId;
	}

	public void setPlanLogId(String planLogId) {
		this.planLogId = planLogId;
	}
	public String getLogTypeId() {
		return logTypeId;
	}

	public void setLogTypeId(String logTypeId) {
		this.logTypeId = logTypeId;
	}
	public Integer getIsYes() {
		return isYes;
	}

	public void setIsYes(Integer isTrue) {
		this.isYes = isTrue;
	}
	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}
}
