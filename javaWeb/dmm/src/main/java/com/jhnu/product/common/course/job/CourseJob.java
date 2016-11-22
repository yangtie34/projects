package com.jhnu.product.common.course.job;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.common.course.service.CourseService;

@DisallowConcurrentExecution
public class CourseJob implements Job{

	@Autowired
	private CourseService courseService;
	
	private static final Logger logger = Logger.getLogger(CourseJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("-----------------初始化课程周次开始----------------");
		courseService.initCourseWeekJob();
		logger.info("=================初始化课程周次结束================");
	}

}
