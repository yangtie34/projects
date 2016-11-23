package com.jhnu.framework.task;

import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import com.jhnu.framework.task.dao.TaskBaseDao;
import com.jhnu.framework.task.service.TaskBaseService;

@Service
public class InitTask implements InitializingBean, ServletContextAware{

	@Autowired
	TaskBaseDao taskBaseDao;
	@Autowired
	TaskBaseService taskBaseService;
	
	private static final Logger logger = Logger.getLogger(InitTask.class);
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		logger.info("===============初始化任务===================");
		List<ScheduleJob> jobs= taskBaseDao.getScheduleJobs();
		try {
			taskBaseService.SaveJobs(jobs);
		} catch (ClassNotFoundException e) {
			logger.error("类找不到："+e.toString());
		} catch (SchedulerException e) {
			logger.error("Scheduler出错："+e.toString());
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
	}
	
}
