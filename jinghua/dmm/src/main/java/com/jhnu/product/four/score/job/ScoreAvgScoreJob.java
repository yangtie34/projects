package com.jhnu.product.four.score.job;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.four.score.service.FourScoreService;


@DisallowConcurrentExecution
public class ScoreAvgScoreJob implements Job {

	@Autowired
	private FourScoreService fourScoreService;
	
	private static final Logger logger = Logger.getLogger(ScoreAvgScoreJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("开始保存平均成绩");
		fourScoreService.saveAvgScoreJob();
		logger.info("保存平均成绩完毕");
	}
}
