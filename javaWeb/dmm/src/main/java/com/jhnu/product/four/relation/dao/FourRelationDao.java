package com.jhnu.product.four.relation.dao;

import java.util.List;
import java.util.Map;

public interface FourRelationDao {
	/**
	 * 获取室友关系
	 * @return
	 */
	public List<Map<String,Object>> getRoommate();
	
	/**
	 * 将室友关系保存至LOG
	 * @param list
	 */
	public void saveRoommateLog(List<Map<String,Object>> list);
	
	/**
	 * 通过LOG获取室友
	 * @param id 学生ID
	 * @return
	 */
	public List<Map<String,Object>> getRoommateLog(String id);
	
	/**
	 * 获取辅导员
	 * @return
	 */
	public List<Map<String,Object>> getTutor();
	
	/**
	 * 保存辅导员关系至LOG
	 * @param list
	 */
	public void saveTutorLog(List<Map<String,Object>> list);
	
	/**
	 * 获取自身辅导员
	 * @param id 通过学生ID
	 * @return
	 */
	public List<Map<String,Object>> getTutorLog(String id);
	
	
	
	
}
