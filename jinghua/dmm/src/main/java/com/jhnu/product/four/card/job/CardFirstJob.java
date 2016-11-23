package com.jhnu.product.four.card.job;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.four.card.service.FourCardService;


@DisallowConcurrentExecution
public class CardFirstJob implements Job {
	
	@Autowired
    private FourCardService fourCardService;
	
	private static final Logger logger = Logger.getLogger(CardFirstJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("开始保存第一次交易的信息");
		fourCardService.saveFirstJob();
		logger.info("保存第一次交易的信息完毕");
	}
	
	
}
