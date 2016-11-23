package com.jhkj.mosdc.framework.scheduling;

import java.util.List;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.scheduling.quartz.JobDetailBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 动态添加任务类
 * @author Administrator
 *
 */
public class DynamicTaskStartImpl extends DynamicTaskStart implements InitializingBean{

	/**
	 * 任务调度工厂
	 */
	private Scheduler scheduler;
	private TaskXmlParser taskParser;

	public void setTaskParser(TaskXmlParser taskParser) {
		this.taskParser = taskParser;
	}
	/**
	 * Scheduler注入
	 * @param schedul
	 */
	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public void initCustomTask() throws Exception{
		List<JobDetail> jobList = taskParser.getMethodInvokingList();
		List<CronTriggerBean> triggerList = taskParser.getTriggerList();
		for(int i=0;i<jobList.size();i++){
			scheduler.scheduleJob(jobList.get(i), triggerList.get(i));
		}
		scheduler.start();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.initCustomTask();
	}
}
