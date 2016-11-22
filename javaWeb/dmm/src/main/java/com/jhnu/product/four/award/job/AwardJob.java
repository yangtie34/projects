package com.jhnu.product.four.award.job;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.four.award.service.FourAwardService;




@DisallowConcurrentExecution
public class AwardJob implements Job {


	@Autowired
    private FourAwardService fourAwardService;
	
	private static final Logger logger = Logger.getLogger(AwardJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("开始执行奖金log");
		
		
		fourAwardService.SaveAllAwardTimesToLog();
		
		logger.info("执行奖金Log完毕");
	}
	
	
	
}
