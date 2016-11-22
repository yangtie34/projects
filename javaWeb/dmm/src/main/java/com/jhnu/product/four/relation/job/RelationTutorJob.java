package com.jhnu.product.four.relation.job;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.four.relation.service.FourRelationService;


@DisallowConcurrentExecution
public class RelationTutorJob implements Job {

	@Autowired
	private FourRelationService fourRelationService;
	
	private static final Logger logger = Logger.getLogger(RelationTutorJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("开始保存辅导员");
		fourRelationService.saveTutorJob();
		logger.info("保存辅导员完毕");
	}
	
}
