package com.jhnu.product.four.card.job;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.four.card.service.FourCardService;


@DisallowConcurrentExecution
public class CardMealsCountJob implements Job {
	
	@Autowired
    private FourCardService fourCardService;
	
	private static final Logger logger = Logger.getLogger(CardMealsCountJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("开始保存学生的三餐消费次数及其所处位置");
		fourCardService.saveMealsCount();
		logger.info("保存学生的三餐消费次数及其所处位置完毕");
	}
	
	
}
