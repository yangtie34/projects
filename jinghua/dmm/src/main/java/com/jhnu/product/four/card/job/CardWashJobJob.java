package com.jhnu.product.four.card.job;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.four.card.service.FourCardService;


@DisallowConcurrentExecution
public class CardWashJobJob implements Job {
	
	@Autowired
    private FourCardService fourCardService;
	
	private static final Logger logger = Logger.getLogger(CardWashJobJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("开始保存洗浴信息");
		fourCardService.saveWashJob();
		logger.info("保存洗浴信息完毕");
	}
	
	
}
