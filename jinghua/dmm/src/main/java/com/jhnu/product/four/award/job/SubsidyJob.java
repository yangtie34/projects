package com.jhnu.product.four.award.job;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.four.award.service.FourAwardService;


@DisallowConcurrentExecution
public class SubsidyJob implements Job{
       
	@Autowired
	private FourAwardService fourAwardService;
	
	private static final Logger logger=Logger.getLogger(SubsidyJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		logger.info("begin execute SubsidyJob");
		
		fourAwardService.SaveAllSubsidy();
		
		logger.info("SubsidyJob execute over");
	        
	}
}
