package com.jhnu.product.four.card.job;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.four.card.service.FourCardService;


@DisallowConcurrentExecution
public class CardPayCountJob implements Job {
	
	@Autowired
    private FourCardService fourCardService;
	
	private static final Logger logger = Logger.getLogger(CardPayCountJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("开始保存消费统计");
		fourCardService.savePayCountJob();
		logger.info("保存消费统计完毕");
	}
	
	
}
