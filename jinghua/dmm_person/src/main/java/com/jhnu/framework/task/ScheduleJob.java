package com.jhnu.framework.task;

/**
 * 计划任务信息
 *
 * User: liyd Date: 14-1-3 Time: 上午10:24
 */
public class ScheduleJob {

	/** 任务id */
	private Long jobId;

	/** 任务名称 */
	private String jobName;

	/** 任务分组 */
	private String jobGroup = "DEFAULT";

	/** 任务状态 0禁用 1启用  */
	private Integer isTrue = 1;
	
	/**  对应执行类的项目路径 */
	private String classPath;

	/** 任务运行时间表达式 */
	private String cronExpression;
	
	

	/** 任务描述 */
	private String desc;

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getIsTrue() {
		return isTrue;
	}

	public void setIsTrue(Integer isTrue) {
		this.isTrue = isTrue;
	}

	public String getClassPath() {
		return classPath;
	}

	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	
}
