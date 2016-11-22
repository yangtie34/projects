package com.jhnu.system.task.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "T_SYS_SCHEDULE_PLAN_LOG_DETAIL")
public class PlanLogDetails implements Serializable{


	/**
	 * 
	 */
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
	
	@Column(name = "ID")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "PLANLOGID")
	public String getPlanLogId() {
		return planLogId;
	}

	public void setPlanLogId(String planLogId) {
		this.planLogId = planLogId;
	}
	@Column(name = "LOGTYPEID")
	public String getLogTypeId() {
		return logTypeId;
	}

	public void setLogTypeId(String logTypeId) {
		this.logTypeId = logTypeId;
	}
	@Column(name = "ISYES")
	public Integer getIsYes() {
		return isYes;
	}

	public void setIsYes(Integer isTrue) {
		this.isYes = isTrue;
	}
	@Column(name = "RESULT_DESC")
	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	@Column(name = "LOGTYPE")
	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
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
	@Column(name = "CHECK_")
	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}
}
