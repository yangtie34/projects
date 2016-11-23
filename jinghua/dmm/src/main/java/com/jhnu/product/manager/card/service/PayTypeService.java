package com.jhnu.product.manager.card.service;

import java.util.List;
import java.util.Map;

public interface PayTypeService {
	
	/**
	 * 保存各消费类型分析到临时表
	 * @param type_code
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public void savePay();

	
	/**
	 * 从临时表中获取某时间段内各消费类型分析
	 * @param type_code
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String,Object>> getPayLog(String dept_id,boolean isLeaf,String type_code,String startDate, String endDate);
	
	/**
	 * 从临时表中获取某时间段内各消费类型明细
	 * @param type_code
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String,Object>> getPayDetailLog(String dept_id,boolean isLeaf,String type_code,String startDate, String endDate);
	
	/**
	 * 保存各消费类型明细到临时表
	 * @param type_code
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public void savePayDetail();
}
