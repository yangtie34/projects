package com.jhnu.product.four.card.job;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.four.card.service.FourCardService;


@DisallowConcurrentExecution
public class CardAllPayMoneyAndTimesJob implements Job {
	
	@Autowired
    private FourCardService fourCardService;
	
	private static final Logger logger = Logger.getLogger(CardAllPayMoneyAndTimesJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("开始保存所有人的消费总金额和总次数");
		fourCardService.saveAllPayMoneyAndTimes();
		logger.info("保存所有人的消费总金额和总次数完毕");
	}
	
	
}
