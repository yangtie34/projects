package com.jhnu.product.manager.student.job;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.manager.student.service.IStuAllInfoService;
import com.jhnu.product.wechat.parent.check.job.WechatCheckJob;
import com.jhnu.util.product.EduUtils;

public class StuAllCardJob implements Job {


		@Autowired
		private IStuAllInfoService stuAllInfoService;
		private static final Logger logger = Logger.getLogger(WechatCheckJob.class);
		
		public void execute(JobExecutionContext arg0) throws JobExecutionException {
			logger.info("开始保存学生成绩统计");
			try{
				String[] school = EduUtils.getSchoolYearTerm(new Date());
				stuAllInfoService.saveStuAllCard(school[0], school[1]);
				stuAllInfoService.saveCard();
			}catch(Exception e) {
				e.printStackTrace();
			}
			logger.info("保存学生成绩统计完毕");
		}
}
