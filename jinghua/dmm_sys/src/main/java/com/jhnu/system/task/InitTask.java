package com.jhnu.system.task;

import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import com.jhnu.system.task.dao.PlanDao;
import com.jhnu.system.task.entity.Plan;
import com.jhnu.system.task.entity.PlanLog;
import com.jhnu.system.task.service.PlanLogService;
import com.jhnu.system.task.service.TaskBaseService;


@Service
public class InitTask implements InitializingBean, ServletContextAware{

	@Autowired
	private PlanDao planDao;
	@Autowired
	private TaskBaseService taskBaseService;
	@Autowired
	private PlanLogService planLogService;
	
	private static final Logger logger = Logger.getLogger(InitTask.class);
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		logger.info("===============初始化任务===================");
		 List<Plan> plans=taskBaseService.getPlans();
		for(int i=0;i<plans.size();i++){
			Plan plan=plans.get(i);
			try {
				taskBaseService.addJob(plan);
			} catch (SchedulerException  e) {
				e.printStackTrace();
			}catch (RuntimeException  e) {
				plan.setIsTrue(0);
					planDao.updateOrInsert(plan);
			System.err.println("计划"+plan.getName_()+"表达式错误");
				//e.printStackTrace();
			}
		}
		PlanLog planLog=new PlanLog();
		planLog.setIsYes(0);
		planLogService.updateRuningToError(planLog);
	}
	

	@Override
	public void afterPropertiesSet() throws Exception {
	}
	
}
