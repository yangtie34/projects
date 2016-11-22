package com.jhnu.product.four.award.job;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.four.award.service.FourAwardService;

@DisallowConcurrentExecution
public class FirstAwardJob implements Job{
    @Autowired
    private FourAwardService fourAwardService;
   
    private static final Logger logger=Logger.getLogger(FirstAwardJob.class);
    
    @Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("保存第一次获得奖励");
		
		fourAwardService.SaveAllFirstAward();
		
		logger.info("保存第一次获得奖励结束");
	}
	
    
}
