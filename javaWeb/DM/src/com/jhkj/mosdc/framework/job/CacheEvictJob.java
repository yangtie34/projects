package com.jhkj.mosdc.framework.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jhkj.mosdc.framework.util.EhcacheUtil;

public class CacheEvictJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		EhcacheUtil.evict();
	}

}
