package com.jhnu.product.four.score.job;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.four.score.service.FourScoreService;


@DisallowConcurrentExecution
public class ScoreGPAScoreJob implements Job {

	@Autowired
	private FourScoreService fourScoreService;
	
	private static final Logger logger = Logger.getLogger(ScoreGPAScoreJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("开始保存学生综合绩点成绩及其所处排名");
		fourScoreService.saveGPAScoreJob();
		logger.info("完毕保存学生综合绩点成绩及其所处排名");
	}
}
