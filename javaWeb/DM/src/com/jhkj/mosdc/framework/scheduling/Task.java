package com.jhkj.mosdc.framework.scheduling;

import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;

public class Task{

	public String lastEndTime;
	/**
	 * 获取上次任务停止的时间
	 * @return
	 */
	public String getLastEndTime() {
		return lastEndTime;
	}
	
	public void setLastEndTime(String lastEndTime) {
		this.lastEndTime = lastEndTime;
	}
}
