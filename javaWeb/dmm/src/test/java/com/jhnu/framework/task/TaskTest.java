package com.jhnu.framework.task;

import javax.annotation.Resource;

import org.junit.Test;
import org.quartz.SchedulerException;

import com.jhnu.framework.task.service.TaskBaseService;
import com.jhnu.spring.SpringTest;

/**
 * TASK 测试
 */
public class TaskTest extends SpringTest{
	@Resource
	private TaskBaseService taskBaseService;
	
	@Test
	public void testTaskChangeTime(){
		//改变时间
		ScheduleJob job=taskBaseService.getScheduleJobById(1l);
		job.setCronExpression("0/3 * * * * ?");
		try {
			taskBaseService.SaveOrUpdate(job);
		} catch (ClassNotFoundException e) {
			System.out.println("类找不到");
		} catch (SchedulerException e) {
			System.out.println("启动Task异常");
		}
	}
	
	@Test
	public void testTaskStop(){
		//停止
		try {
			taskBaseService.stopJobById(2l);
		} catch (SchedulerException e) {
			System.out.println("停止Task异常");
		}
	}
	
	@Test
	public void testTaskStart(){
		//恢复
		try {
			taskBaseService.startJobById(2l);
		} catch (SchedulerException e) {
			System.out.println("恢复Task异常");
		}
	}
}
