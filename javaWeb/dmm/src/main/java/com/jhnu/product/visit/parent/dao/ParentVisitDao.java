package com.jhnu.product.visit.parent.dao;

import java.util.List;
import java.util.Map;

public interface ParentVisitDao {
	
	/**
	 * 获取时间段内总访问次数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String,Object>> getVisitTotalCounts(String startDate,String endDate);
	
	/**
	 * 获取时间段内PC访问次数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String,Object>> getVisitPcCounts(String startDate,String endDate);
	
	/**
	 * 获取时间段内手机端访问次数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String,Object>> getVisitMobelCounts(String startDate,String endDate);
	
	/**
	 * 获取时间段内访问人数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String,Object>> getUserCounts(String startDate,String endDate);

	/**
	 * 获取时间段内家长访问首页次数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String, Object>> getParentVisitCounts(String startDate,String endDate);
	
	/**
	 * 获取时间段内访问首页家长人数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String,Object>> getParentNums(String startDate,String endDate);

	/**
	 * 获取时间段内各功能页面访问次数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String, Object>> getOtherVisitCounts(String startDate,
			String endDate);
	
}
