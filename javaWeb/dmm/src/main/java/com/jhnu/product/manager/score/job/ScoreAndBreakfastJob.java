package com.jhnu.product.manager.score.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.manager.score.service.ScoreAndBreakfastService;
import com.jhnu.product.wechat.parent.check.job.WechatCheckJob;

public class ScoreAndBreakfastJob implements Job {


		@Autowired
		private ScoreAndBreakfastService scoreAndBreakfastService;
		private static final Logger logger = Logger.getLogger(WechatCheckJob.class);
		
		public void execute(JobExecutionContext arg0) throws JobExecutionException {
			logger.info("开始保存成绩与吃早餐次数统计");
			try{
				scoreAndBreakfastService.saveScoreAndBreakfast();
			}catch(Exception e) {
				e.printStackTrace();
			}
			logger.info("保存学生成绩与吃早餐次数完毕");
		}
	

}
