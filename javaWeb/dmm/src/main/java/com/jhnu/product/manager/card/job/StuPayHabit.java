package com.jhnu.product.manager.card.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.manager.card.service.StuPayHabitService;
import com.jhnu.product.wechat.parent.check.job.WechatCheckJob;

public class StuPayHabit implements Job {


		@Autowired
		private StuPayHabitService stuPayHabitService;
		private static final Logger logger = Logger.getLogger(WechatCheckJob.class);
		
		public void execute(JobExecutionContext arg0) throws JobExecutionException {
			logger.info("开始保存每天各学生每天的就餐明细");
			//每天执行一次
			try{
				stuPayHabitService.saveStuPays();
				stuPayHabitService.saveStuEatNumbers();
				stuPayHabitService.saveStuPayMoney();
			}catch(Exception e) {
				e.printStackTrace();
			}
			logger.info("保存近每天各学生每天的就餐明细完毕");
		}
	

}
