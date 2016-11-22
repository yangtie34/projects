package com.jhnu.framework.task.service;

import java.util.List;

import org.quartz.SchedulerException;

import com.jhnu.framework.task.ScheduleJob;

public interface TaskBaseService {
	/**
	 * 批量保存ScheduleJob
	 * @param jobList
	 * @throws ClassNotFoundException
	 * @throws SchedulerException
	 */
	public void SaveJobs(List<ScheduleJob> jobList) throws ClassNotFoundException, SchedulerException;
	
	/**
	 * 保存或者更新ScheduleJob
	 * @param job
	 * @throws SchedulerException
	 * @throws ClassNotFoundException
	 */
	public void SaveOrUpdate(ScheduleJob job) throws SchedulerException, ClassNotFoundException; 
	
	/**
	 * 停止Job
	 * @param job
	 * @throws SchedulerException
	 */
	public void stopJobById(Long id) throws SchedulerException;
	
	/**
	 * 启动Job
	 * @param job
	 * @throws SchedulerException
	 */
	public void startJobById(Long id) throws SchedulerException;
	
	public ScheduleJob getScheduleJob(ScheduleJob job);
	
	public ScheduleJob getScheduleJobById(Long id);
	
}
