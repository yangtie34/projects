package com.jhnu.system.task.service;


import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.jhnu.system.task.entity.Plan;


public interface TaskBaseService extends ApplicationListener<ContextRefreshedEvent>{
	/**
	 * 创建或者更新任务
	 * @param job
	 * @throws SchedulerException 
	 * @
	 * @throws ClassNotFoundException
	 */
	public void updateOrInsert(Plan plan) throws SchedulerException; 
	
	/**
	 * 创建任务
	 * @param job
	 * @throws SchedulerException 
	 * @
	 * @throws ClassNotFoundException
	 */
	public void addJob(Plan plan) throws SchedulerException; 
	
	/**
	 * 更新任务
	 * @param job
	 * @throws SchedulerException 
	 * @
	 * @throws ClassNotFoundException
	 */
	public void updateOrInsertJob(Plan plan) throws SchedulerException; 
	
	/**
	 * 暂停任务
	 * @param job
	 * @throws SchedulerException 
	 * @
	 */
	public void stopJob(Plan plan) throws SchedulerException ;
	
	/**
	 * 恢复任务
	 * @param job
	 * @throws SchedulerException 
	 * @
	 */
	public void startJob(Plan plan) throws SchedulerException ;
	/**
	 * 删除任务
	 * @param job
	 * @throws SchedulerException 
	 * @
	 */
	public void delJob(Plan plan) throws SchedulerException ;

	void runJob(Plan plan) throws SchedulerException;

	List<Plan> getPlans();
		
}
