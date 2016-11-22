package com.jhnu.product.wechat.parent.check.dao;

import java.util.List;
import java.util.Map;



public interface WechatCheckDao {
	
	/**
	 * 查询考勤信息
	 * @return
	 */
	public List<Map<String,Object>> getCheck();
	
	/**
	 * 查询学生总课数
	 * @return
	 */
	public List<Map<String,Object>> getClasses();
	
	/**
	 * 根据学生ID从考勤分析临时表中获取该生的考勤分析
	 * @param stuId
	 * @return
	 */
	public List<Map<String,Object>> getCheckLog(String stuId);
	
	/**
	 * 将查出来的考勤信息保存到考勤分析临时表中
	 * @param list
	 */
	public void saveCheckLog(List<Map<String,Object>> list);
	
	/**
	 * 查询学生迟到最多的课
	 * @return
	 */
	public List<Map<String,Object>> getLateClassCourse();
	
	/**
	 * 查询学生旷课最多的课
	 * @return
	 */
	public List<Map<String,Object>> getCutClassCourse();
	
	/**
	 * 将学生迟到，旷课最多的课存到临时表
	 * @param list
	 */
	public void saveStuCutClassLog(List<Map<String,Object>> list);
	
	/**
	 * 根据学生ID从旷课，迟到最多的临时表中查出该生的旷课迟到最多的课
	 * @param stuId
	 * @return
	 */
	public List<Map<String,Object>> getStuCutClassLog(String stuId);
	
	
}
