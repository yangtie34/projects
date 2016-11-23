package com.jhnu.product.mosdc.dao;

import java.util.List;
import java.util.Map;


public interface UserSynDao {
	
	/**
	 * 获取没有再用户表的迎新学生信息
	 * @Title: getWelStusNotInUser 
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String,Object>> getWelStusNotInUser();
	/**
	 * 获取没有在用户表的学生
	 * @Title: getStusNotInUser 
	 * @param @return
	 * @return List<TbWelcomeXs>
	 * @throws
	 */
	public List<Map<String,Object>> getStusNotInUser();
	/**
	 * 获取没有在用户表的教职工
	 * @Title: getJzgsNotInUser 
	 * @param @return
	 * @return List<TbJxzzjg>
	 * @throws
	 */
	public List<Map<String,Object>> getJzgsNotInUser();
}
