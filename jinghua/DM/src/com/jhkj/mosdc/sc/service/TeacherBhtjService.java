package com.jhkj.mosdc.sc.service;
/**
 * 教职工变化统计服务
 * @author Administrator
 *
 */
public interface TeacherBhtjService {
	/**
	 * 获取该类支持的统计类型。
	 * @param params
	 * @return
	 */
	public String getTjlx(String params);
	/**
	 * 获取图形数据
	 * @param params
	 * @return
	 */
	public String getChartData(String params);
	/**
	 * 获得指定年份内每月份的入职人数
	 * @param parsms
	 * @return
	 */
	public String getRzrsByNf(String parsms);
	/**
	 * 获得指定年份段内的入职人数
	 * @param parsms
	 * @return
	 */
	public String getRzrsByNfd(String parsms);
	/**
	 * 获取指定年份内各月份的总教职工数
	 * @param parsms
	 * @return
	 */
	public String getCountByNf(String parsms);
	/**
	 * 获取指定年份段内的入职人数
	 * @param parsms
	 * @return
	 */
	public String getCountByNfd(String parsms);
	
	public String getLylxrsByNf(String params);
	public String getLylxrsByNfd(String params);
	public String getBzlbrsByNf(String params);
	public String getBzlbrsByNfd(String params);
	public String getZclbrsByNf(String params);
	public String getZclbrsByNfd(String params);
	/**
	 * 获取grid显示数据。
	 * @param params
	 * @return
	 */
	public String queryGridContent(String params);
}
