package com.jhnu.person.job;

import com.jhnu.framework.entity.JobResultBean;

public interface PersonJob {
	/*
	 *一卡通消费 job 统计每天
	 */
	public JobResultBean yktxffxJob();

	/*
	 * 上网时间统计 job 统计每天
	 */
	public JobResultBean swsjJob();
}
