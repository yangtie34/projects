package com.jhnu.product.wechat.parent.day.job;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.wechat.parent.day.service.WechatDayService;
import com.jhnu.util.common.DateUtils;

@DisallowConcurrentExecution
public class DayJobByCardTime implements Job{

	@Autowired
	private WechatDayService wechatDayService;
	
	private static final Logger logger = Logger.getLogger(DayJobByCardTime.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("-----------------开始执行孩子一天-----------");
		wechatDayService.exeDayJobByCardTime(DateUtils.SSS.format(new Date()));
		logger.info("=================结束执行孩子一天===========");
	}

}
