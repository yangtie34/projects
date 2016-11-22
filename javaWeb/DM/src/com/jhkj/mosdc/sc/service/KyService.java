package com.jhkj.mosdc.sc.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 科研统计服务接口
 * @author Administrator
 *
 */
public interface KyService {
	/**
	 * 科研经费去向统计
	 * @param params
	 * @return
	 */
	public String getKyjfQxChartData(String params);
	/**
	 * 科研经费来源统计
	 * @param params
	 * @return
	 */
	public String getKyjfLyChartData(String params);
	/**
	 * 获取科研经费排名。
	 * @param params
	 * @return
	 */
	public String getKyjfPmChartData(String params);
	/**
	 *  科研经费使用概况
	 * @param params
	 * @return
	 */
	public String getKyjfSyChartData(String params);
	
	/**
	 * 获取划拨经费。
	 * @param params
	 * @return
	 */
	public String getHbjf(String params);
	/**
	 * 经费支出
	 * @param params
	 * @return
	 */
	public String getJfzc(String params);
	/**
	 * 获得在研项目数。
	 * @param params
	 * @return
	 */
	public String getZyxms(String params);
	/**
	 * 获取科研机构饼图数据。
	 * @param params
	 * @return
	 */
	public String getKyjgChartData(String params);
	/**
	 * 获取科研人员柱状图数据
	 * @param params
	 * @return
	 */
	public String getKyryChartData(String params);
	
	/**
	 * 获取科研项目柱状图数据
	 * @param params
	 * @return
	 */
	public String getKyxmChartData(String params);
	/**
	 * 获取科研经费拨入数据。
	 * @param params
	 * @return
	 */
	public String getKyjfChartData(String params);
	/**
	 * 获取科研活动数据。
	 * @param params
	 * @return
	 */
	public String getKyhdChartData(String params);
	/**
	 * 获取科研成果图形数据。
	 * @param params
	 * @return
	 */
	public String getKycgChartData(String params);
	
	/**
	 * 获取科研项目2
	 * 类别（国家、省、市等等）
	 * @param params
	 * @return
	 */
	public String getKyxmlbData(String params);
	
	/***
	 * 获取科研项目2
	 * 级别  （一般、重点、重大）
	 * @param params
	 * @return
	 */
	public String getKyxmJbData(String params);
	
	/***
	 * 获取科研项目2
	 * 项目状态（进行中、完成、终止、其他）
	 * @param params
	 * @return
	 */
	public String getKyxmStateData(String params);
	
	/**
	 * 获取科研项目2
	 * 历年项目数量变化趋势
	 * @param params
	 * @return
	 */
	public String getKyxmLnqsData(String params);
	
	/***
	 * 获取科研项目2
	 * 分学院历年项目数量变化趋势
	 * @param params
	 * @return
	 */
	public String getKyxmXylnqsData(String params);
	
	/***
	 * 获取科研项目2
	 * 历年项目数据详细信息
	 * @param params
	 * @return
	 */
	public String getKyLnxmData(String params);
	/***
	 * 获取科研项目2
	 * 学院 历年项目数据详细信息
	 * @param params
	 * @return
	 */
	public String queryGridContent4xq(String params);
	/***
	 * 历史趋势导出EXCEL表
	 * @param params
	 * @return
	 */
	public HSSFWorkbook getWzsMdExport(String params);
}
