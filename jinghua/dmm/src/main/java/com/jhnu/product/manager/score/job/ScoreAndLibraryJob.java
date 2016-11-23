package com.jhnu.product.manager.score.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.manager.score.service.ScoreAndLibraryService;
import com.jhnu.product.wechat.parent.check.job.WechatCheckJob;

public class ScoreAndLibraryJob implements Job {


		@Autowired
		private ScoreAndLibraryService scoreAndLibraryService;
		private static final Logger logger = Logger.getLogger(WechatCheckJob.class);
		
		public void execute(JobExecutionContext arg0) throws JobExecutionException {
			logger.info("开始保存成绩与进入图书馆次数统计");
			try{
				scoreAndLibraryService.saveScoreAndLibraryLog();
			}catch(Exception e) {
				e.printStackTrace();
			}
			logger.info("保存学生成绩与进入图书馆次数完毕");
		}
	

}
