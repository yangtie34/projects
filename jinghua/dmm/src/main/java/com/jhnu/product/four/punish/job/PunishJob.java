package com.jhnu.product.four.punish.job;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.four.award.job.SubsidyJob;
import com.jhnu.product.four.punish.service.FourPunishService;


@DisallowConcurrentExecution
public class PunishJob implements Job{
 
	@Autowired
	private FourPunishService fourPunishService;
	
	private static final Logger logger=Logger.getLogger(SubsidyJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		logger.info("begin execute SubsidyJob");
		
		fourPunishService.savePunishLog();
		
		logger.info("SubsidyJob execute over");
	        
	}
}
