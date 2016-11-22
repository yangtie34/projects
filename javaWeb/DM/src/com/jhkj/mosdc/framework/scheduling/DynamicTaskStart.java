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
public abstract class DynamicTaskStart implements InitializingBean{

	public abstract void initCustomTask()  throws Exception;
}
