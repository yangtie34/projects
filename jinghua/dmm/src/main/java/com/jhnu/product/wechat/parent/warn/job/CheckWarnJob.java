package com.jhnu.product.wechat.parent.warn.job;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.wechat.parent.warn.service.CheckWarnService;
import com.jhnu.util.common.DateUtils;

@DisallowConcurrentExecution
public class CheckWarnJob implements Job{
	@Autowired
	private CheckWarnService checkWarnService;

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String exe_time=DateUtils.SSS.format(new Date());
		checkWarnService.saveCheckWarn(exe_time);
	}

}
