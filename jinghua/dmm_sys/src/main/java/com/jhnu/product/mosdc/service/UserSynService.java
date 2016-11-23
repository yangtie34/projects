package com.jhnu.product.mosdc.service;

import com.jhnu.framework.entity.JobResultBean;


public interface UserSynService {
	
	
	/**
	 * 获取没有在用户表的迎新学生信息
	 * @Title: getWelStusNotInUser 
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public JobResultBean userSyn4WelStu();
	/**
	 * 获取没有在用户表的学生信息
	 * @Title: getStusNotInUser 
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public JobResultBean userSyn4Stu();
	/**
	 * 获取没有在用户表的教师信息
	 * @Title: getJzgsNotInUser 
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public JobResultBean userSyn4Jzg();
}
