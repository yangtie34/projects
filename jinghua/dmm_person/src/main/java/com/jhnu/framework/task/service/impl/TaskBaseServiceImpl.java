package com.jhnu.framework.task.service.impl;

import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.jhnu.framework.task.ScheduleJob;
import com.jhnu.framework.task.dao.TaskBaseDao;
import com.jhnu.framework.task.service.TaskBaseService;

@Service
public class TaskBaseServiceImpl implements TaskBaseService{
	
	@Autowired
	SchedulerFactoryBean  schedulerFactoryBean;
	@Autowired
	TaskBaseDao taskBaseDao;
	
	Scheduler scheduler =null;
	
	public void SaveJobs(List<ScheduleJob> jobList) throws ClassNotFoundException, SchedulerException {
		// 这里获取任务信息数据
		for (ScheduleJob job : jobList) {
			SaveOrUpdate(job);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void SaveOrUpdate(ScheduleJob job) throws SchedulerException, ClassNotFoundException {
		scheduler  = schedulerFactoryBean.getScheduler();
		// 这里获取任务信息数据
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(),
				job.getJobGroup());
		// 获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		// 不存在，创建一个
		if (null == trigger) {
			Class cla=Class.forName(job.getClassPath());
			JobDetail jobDetail = JobBuilder.newJob(cla)
					.withIdentity(job.getJobName(), job.getJobGroup())
					.build();
			jobDetail.getJobDataMap().put("scheduleJob", job);

			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
					.cronSchedule(job.getCronExpression());

			// 按新的cronExpression表达式构建一个新的trigger
			trigger = TriggerBuilder.newTrigger()
					.withIdentity(job.getJobName(), job.getJobGroup())
					.withSchedule(scheduleBuilder).build();

			scheduler.scheduleJob(jobDetail, trigger);
			//job.getIsTrue()为0表示停止
			if(job.getIsTrue()==0){
				scheduler.pauseJob(new JobKey(job.getJobName(),job.getJobGroup()));	
			}
		} else {
			taskBaseDao.update(job);
			// Trigger已存在，那么更新相应的定时设置
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
					.cronSchedule(job.getCronExpression());
			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
					.withSchedule(scheduleBuilder).build();
			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		}
	}
	
	public void stopJobById(Long id) throws SchedulerException{
		ScheduleJob job=getScheduleJobById(id);
		job.setIsTrue(0);
		taskBaseDao.update(job);
		scheduler.pauseJob(new JobKey(job.getJobName(),job.getJobGroup()));		
	}
	
	public void startJobById(Long id) throws SchedulerException{
		ScheduleJob job=getScheduleJobById(id);
		job.setIsTrue(1);
		taskBaseDao.update(job);
		scheduler.resumeJob(new JobKey(job.getJobName(),job.getJobGroup()));	
	}

	@Override
	public ScheduleJob getScheduleJob(ScheduleJob job) {
		return taskBaseDao.getScheduleJob(job);
	}

	@Override
	public ScheduleJob getScheduleJobById(Long id) {
		return taskBaseDao.getScheduleJobById(id);
	}
}
