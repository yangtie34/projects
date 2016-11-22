package com.jhnu.product.four.net.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.four.net.service.NetService;

public class NetJcOnlineJob implements Job  {

	@Autowired
    private NetService netService;
	
	private static final Logger logger = Logger.getLogger(NetJcOnlineJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("开始保存经常上网时间的信息");
		netService.saveAllNetJcsxsj();
		logger.info("保存经常上网时间的信息完毕");
	}

}
