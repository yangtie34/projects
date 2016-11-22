package com.jhnu.system.task.service.impl;

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
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.jhnu.system.task.entity.Plan;
import com.jhnu.system.task.jobs.PublicJob;
import com.jhnu.system.task.service.PlanService;
import com.jhnu.system.task.service.TaskBaseService;


@Service("taskBaseService")
public class TaskBaseServiceImpl implements TaskBaseService{
	
	@Autowired
	private SchedulerFactoryBean  schedulerFactoryBean;
	
	Scheduler scheduler =null;
	
	@Autowired
	private PlanService PlanService;
	@Override
	public void updateOrInsert(Plan plan) throws SchedulerException,RuntimeException {
		// 这里获取任务信息数据
				TriggerKey triggerKey = TriggerKey.triggerKey(plan.getName_(),plan.getGroup_());	
		// 获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		if (null == trigger) {
			addJob(plan);
		}else{
			updateOrInsertJob(plan);
		}
	}
	@Override
 public List<Plan> getPlans(){
	 scheduler  = schedulerFactoryBean.getScheduler();
	 List<Plan> plans=PlanService.getPlans();
	 return plans;
 }
	@Override
	public void addJob(Plan plan) throws SchedulerException,RuntimeException{
		PublicJob publicJob=new PublicJob();
		Class cla=publicJob.getClass();
		JobDetail jobDetail = JobBuilder.newJob(cla)
				.withIdentity(plan.getName_(),plan.getGroup_())	
				.build();
		jobDetail.getJobDataMap().put("plan", plan);

		// 表达式调度构建器
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
				.cronSchedule(plan.getCron_expression());

		// 按新的cronExpression表达式构建一个新的trigger
		CronTrigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(plan.getName_(),plan.getGroup_())	
				.withSchedule(scheduleBuilder).build();

		scheduler.scheduleJob(jobDetail, trigger);
		//job.getIsTrue()为0表示停止
		if(plan.getIsTrue()==0){
			scheduler.pauseJob(new JobKey(plan.getName_(),plan.getGroup_()));	
		}
	}
	@Override
	public void stopJob(Plan plan) throws SchedulerException{
		scheduler.pauseJob(new JobKey(plan.getName_(),plan.getGroup_()));	
	}
	
	@Override
	public void startJob(Plan plan) throws SchedulerException{
		scheduler.resumeJob(new JobKey(plan.getName_(),plan.getGroup_()));	
	}

	@Override
	public void updateOrInsertJob(Plan plan) throws SchedulerException,RuntimeException {
		delJob(plan);
		addJob(plan);
	}

	@Override
	public void delJob(Plan plan) throws SchedulerException {
		scheduler.deleteJob(new JobKey(plan.getName_(),plan.getGroup_()));
		
	}
	@Override
	public void runJob(Plan plan) throws SchedulerException{
		scheduler.triggerJob(new JobKey(plan.getName_(),plan.getGroup_()));	
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")){
	}
	}

}
