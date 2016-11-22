package com.jhkj.mosdc.sc.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public interface ScientificAchievementService {
	
	/**
	 * 获取职称分布数
	 * @param params
	 * @return
	 */
	public String getZcFb(String params);
	
	/**
	 * 获取职称明细分布数
	 * @param params
	 * @return
	 */
	public String getZcMxFb(String params);
	
	/**
	 * 获取文化程度分布
	 * @param params
	 * @return
	 */
	public String getWhcdFb(String params);
	
	/**
	 * 获取学位分布
	 * @param params
	 * @return
	 */
	public String getXwFb(String params);
	
	/**
	 * 获取单位分布
	 * @param params
	 * @return
	 */
	public String getDwFb(String params);
	/**
	 * 获取下钻table数据
	 * @param params
	 * @return
	 */
	public String getTable(String params);
	
	/**
	 * 获取下钻table2数据
	 * @param params
	 * @return
	 */
	public String getTable2(String params);
	/**
	 * 导出table1
	 * @param params
	 * @return
	 */
	public HSSFWorkbook getData1Export(String params);
	/**
	 * 导出table2
	 * @param params
	 * @return
	 */
	public HSSFWorkbook getData2Export(String params);
	
	
}


