package com.jhnu.product.four.relation.job;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.four.relation.service.FourRelationService;


@DisallowConcurrentExecution
public class RelationRoommateJob implements Job {

	@Autowired
	private FourRelationService fourRelationService;
	
	private static final Logger logger = Logger.getLogger(RelationRoommateJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("开始保存室友");
		fourRelationService.saveRoommateJob();
		logger.info("保存室友完毕");
	}
	
}
