package com.jhnu.framework.task.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.framework.task.ScheduleJob;
import com.jhnu.system.permiss.service.UserService;


@DisallowConcurrentExecution
public class test1Job implements Job {
	@Autowired 
	UserService userService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("任务成功运行test1Job");
		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap()
				.get("scheduleJob");
		System.out.println("任务名称 = [" + scheduleJob.getJobName() + "]");
	}
	
}
