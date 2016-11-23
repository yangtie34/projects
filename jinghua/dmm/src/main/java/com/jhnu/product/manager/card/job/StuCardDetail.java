package com.jhnu.product.manager.card.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.manager.card.service.LowPayService;
import com.jhnu.product.wechat.parent.check.job.WechatCheckJob;

public class StuCardDetail implements Job {


		@Autowired
		private LowPayService lowPayService;
		private static final Logger logger = Logger.getLogger(WechatCheckJob.class);
		
		public void execute(JobExecutionContext arg0) throws JobExecutionException {
			logger.info("开始保存近三个月各学生每天的就餐明细");
			try{
				lowPayService.saveStuEatDetailLog();
			}catch(Exception e) {
				e.printStackTrace();
			}
			logger.info("保存近三个月各学生每天的就餐明细完毕");
		}
	

}
