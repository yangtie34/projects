package com.jhkj.mosdc.sc.service;

public interface XsxfqktjService {

	/**
	 * 查询时间段内学生消费概况
	 * @return String
	 */
	public String queryXsxfgk(String params);
	
	/** 
	* @Description: TODO 查询时间段内日均消费情况
	* @return String
	*/
	public String queryRjxfqk(String params);
	
	/** 
	* @Description: TODO 按时间段分析消费情况
	* @return String
	*/
	public String querySjdxfqk(String params);
	
	/** 
	* @Title: queryCtcbxfqk 
	* @Description: TODO 餐厅餐别消费情况
	* @return String
	*/
	public String queryCtcbxfqk(String params);
}
