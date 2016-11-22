package com.jhnu.product.manager.student.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.manager.student.service.StuScoreService;
import com.jhnu.product.wechat.parent.check.job.WechatCheckJob;

public class DeptScoreJob implements Job {


		@Autowired
		private StuScoreService stuScoreService;
		private static final Logger logger = Logger.getLogger(WechatCheckJob.class);
		
		public void execute(JobExecutionContext arg0) throws JobExecutionException {
			logger.info("开始保存学生成绩统计");
			try{
				stuScoreService.saveStuScoreLog();
			}catch(Exception e) {
				e.printStackTrace();
			}
			logger.info("保存学生成绩统计完毕");
		}
}
