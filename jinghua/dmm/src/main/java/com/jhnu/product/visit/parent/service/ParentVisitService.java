package com.jhnu.product.visit.parent.service;

import java.util.List;
import java.util.Map;

public interface ParentVisitService {
	/**
	 * 获取时间段内总访问次数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map<String,Object> getVisitTotalCounts(String startDate,String endDate);
	
	/**
	 * 获取时间段内PC访问次数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map<String,Object> getVisitPcCounts(String startDate,String endDate);
	
	/**
	 * 获取时间段内手机端访问次数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map<String,Object> getVisitMobelCounts(String startDate,String endDate);
	
	/**
	 * 获取时间段内访问人数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map<String,Object> getUserCounts(String startDate,String endDate);
	
	/**
	 * 获取家长中心首页访问次数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String, Object>> getParentVisitCounts(String startDate,String endDate);
	
	/**
	 * 家长中心首页访问人数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map<String,Object> getParentNums(String startDate,String endDate);

	/**
	 * 家长中心各功能页面的访问次数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String, Object>> getOtherVisitCounts(String startDate,String endDate);
	
	
	
}
