package com.jhnu.framework.task.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jhnu.framework.task.ScheduleJob;


@DisallowConcurrentExecution
public class test2Job implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("任务成功运行test2Job");
		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap()
				.get("scheduleJob");
		System.out.println("任务名称 = [" + scheduleJob.getJobName() + "]");
	}
	
}
