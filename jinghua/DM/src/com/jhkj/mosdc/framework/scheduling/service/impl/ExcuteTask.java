package com.jhkj.mosdc.framework.scheduling.service.impl;

import com.jhkj.mosdc.framework.scheduling.Task;

/**
 * 执行的任务
 * @author Administrator
 *
 */
public class ExcuteTask extends Task {
	private String types = "jzg,role";//可能会生成的类型
	
	public void addAnnouncementEvent(){
		String lastEndTime = getLastEndTime();
		String sql = "";
//		System.out.println("execute task+----"+lastEndTime);
	}
}
