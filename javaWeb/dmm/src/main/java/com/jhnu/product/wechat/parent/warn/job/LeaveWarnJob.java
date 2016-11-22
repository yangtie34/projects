package com.jhnu.product.wechat.parent.warn.job;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.wechat.parent.warn.service.LeaveWarnService;
import com.jhnu.util.common.DateUtils;

@DisallowConcurrentExecution
public class LeaveWarnJob implements Job{
	@Autowired
	private LeaveWarnService leaveWarnService;

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String exe_time=DateUtils.SSS.format(new Date());
		leaveWarnService.saveCancelLeaveWarn(exe_time);
		leaveWarnService.saveLeaveWarn(exe_time);
		leaveWarnService.saveKeepLeaveWarn(exe_time);
		
	}

}
