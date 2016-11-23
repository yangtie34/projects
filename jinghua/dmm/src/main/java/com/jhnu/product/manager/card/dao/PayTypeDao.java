package com.jhnu.product.manager.card.dao;

import java.util.List;
import java.util.Map;

public interface PayTypeDao {
	
	/**
	 * 获取某时间段内各消费类型分析
	 * @param type_code
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String,Object>> getPay(String startDate, String endDate);
	
	/**
	 * 保存各消费类型明细到临时表
	 * @param list
	 */
	public void  savePay(List<Map<String,Object>> list);
	
	/**
	 * 获取某时间段内各消费类型分析
	 * @param type_code
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String,Object>> getPayLog(String dept_id,boolean isLeaf,String type_code,String startDate, String endDate);
	
	/**
	 * 获取各消费类型明细
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String,Object>> getPayDetail(String startDate, String endDate);
	
	/**
	 * 保存各消费类型明细到临时表
	 * @param list
	 */
	public void  savePayDetail(List<Map<String,Object>> list);
	
	/**
	 * 从临时表中获取各消费类型明细
	 * @param dept_id
	 * @param isLeaf
	 * @param type_code
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String,Object>> getPayDetailLog(String dept_id,boolean isLeaf, String type_code,String startDate,String endDate);
	
	
	
}
