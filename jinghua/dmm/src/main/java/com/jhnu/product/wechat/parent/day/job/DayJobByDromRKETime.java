package com.jhnu.product.wechat.parent.day.job;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.wechat.parent.day.service.WechatDayService;
import com.jhnu.util.common.DateUtils;

@DisallowConcurrentExecution
public class DayJobByDromRKETime implements Job{

	@Autowired
	private WechatDayService wechatDayService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		wechatDayService.exeDayJobByDormRKETime(DateUtils.SSS.format(new Date()));
	}

}
