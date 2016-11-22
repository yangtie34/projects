package com.jhnu.product.common.stu.job;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.common.stu.service.StuService;

@DisallowConcurrentExecution
public class LateDormStuJobByDay implements Job{

	@Autowired
	private StuService stuService;
	
	private static final Logger logger = Logger.getLogger(LateDormStuJobByDay.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("=====================开始保存昨日晚寝晚归学生====================");
		stuService.saveLateDormStudentByYestDay(new Date());
		logger.info("=====================保存昨日晚寝晚归学生完毕====================");
	}

}
