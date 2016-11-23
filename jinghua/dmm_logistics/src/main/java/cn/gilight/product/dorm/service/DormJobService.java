package cn.gilight.product.dorm.service;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;

public interface DormJobService {
	
	/**
	 * 初始化学生住宿情况
	 * @return
	 */
	public JobResultBean dormStuJob();
	
	/**
	 * 初始化住宿学生的门禁使用情况
	 * @return
	 */
	public JobResultBean initDormRkeUsedStuJob();
	
	/**
	 * 每月更新住宿学生的门禁使用情况
	 * @return
	 */
	public JobResultBean dormRkeUsedStuJob();

	/**
	 * 初始化住宿学生的门禁使用情况
	 * @return
	 */
	public JobResultBean initDormTrend();
	
	/**
	 * 每月更新住宿学生的门禁使用情况
	 * @return
	 */
	public JobResultBean dormTrend();
	
	
}
