package com.jhnu.product.four.score.job;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.four.score.service.FourScoreService;


@DisallowConcurrentExecution
public class ScoreBestScoreJob implements Job {

	@Autowired
	private FourScoreService fourScoreService;
	
	private static final Logger logger = Logger.getLogger(ScoreBestScoreJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("开始保存最高成绩");
		fourScoreService.saveBestScoreJob();
		logger.info("保存最高成绩完毕");
	}
}
