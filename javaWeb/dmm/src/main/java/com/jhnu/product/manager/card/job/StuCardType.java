package com.jhnu.product.manager.card.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.manager.card.service.PayTypeService;
import com.jhnu.product.wechat.parent.check.job.WechatCheckJob;

public class StuCardType implements Job {


		@Autowired
		private PayTypeService payTypeService;
		private static final Logger logger = Logger.getLogger(WechatCheckJob.class);
		
		public void execute(JobExecutionContext arg0) throws JobExecutionException {
			logger.info("开始保存每天各学生消费类型明细");
			//每天执行一次
			try{
				payTypeService.savePayDetail();
				payTypeService.savePay();
			}catch(Exception e) {
				e.printStackTrace();
			}
			logger.info("保存近每天各学生消费类型明细完毕");
		}
	

}
