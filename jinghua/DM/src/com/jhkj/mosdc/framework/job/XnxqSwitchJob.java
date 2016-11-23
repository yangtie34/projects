package com.jhkj.mosdc.framework.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jhkj.mosdc.framework.bean.ApplicationComponentStaticRetriever;
import com.jhkj.mosdc.framework.service.BaseService;

public class XnxqSwitchJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		BaseService baseService = (BaseService) ApplicationComponentStaticRetriever
				.getComponentByItsName("baseService");
		try {
			baseService.updateAutoSwitchXnxq();
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

}
