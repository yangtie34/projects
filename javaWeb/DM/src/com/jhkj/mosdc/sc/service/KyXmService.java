package com.jhkj.mosdc.sc.service;
/**
 * 科研项目服务接口
 * @author Administrator
 *
 */
public interface KyXmService {
	
	/**
	 * 获取图形组件显示数据。
	 * @param params
	 * @return
	 */
	public String getChartData(String params);
	/**
	 * 获取科研项目grid数据。
	 * @param params
	 * @return
	 */
	public String queryGridContent(String params);
	/**
	 * 获得统计类型数据。
	 * @param params
	 * @return
	 */
	public String getTjlx(String params);
	
	/**
	 * 项目来源统计
	 * @param params
	 * @return
	 */
	public String getCountByXmly(String params);
	public String getCountByXmlyNfd(String params);
	/**
	 * 项目状态统计
	 * @param params
	 * @return
	 */
	public String getCountByXmzt(String params);
	/**
	 * 项目状态统计(年份段)
	 * @param params
	 * @return
	 */
	public String getCountByXmztNfd(String params);
	/**
	 * 项目分类统计
	 * @param params
	 * @return
	 */
	public String getCountByXmfl(String params);
	
	public String getCountByXmflNfd(String params);
}
