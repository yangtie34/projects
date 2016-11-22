package com.jhnu.product.wechat.parent.check.job;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.wechat.parent.check.service.WechatCheckService;

@DisallowConcurrentExecution
public class WechatCheckJob implements Job{

	@Autowired
	private WechatCheckService checkService;
	private static final Logger logger = Logger.getLogger(WechatCheckJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("开始保存考勤分析");
		try{
			checkService.saveCheckLog();
			checkService.saveStuCutClassLog();
		}catch(Exception e) {
			e.printStackTrace();
		}
		logger.info("保存考勤分析完毕");
	}
}
