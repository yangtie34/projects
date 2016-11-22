package com.jhkj.mosdc.sc.service;

public interface SslService {
	/**
	 * 获取页头部数字显示区数据
	 * @param params
	 * @return
	 */
	public String getTitleData(String params);
	/**
	 * 获取宿舍楼入住，展示为柱状图
	 * @param params
	 * @return
	 */
	public String getSslRzColumn(String params);
	/**
	 * 获得宿舍楼入住表数据
	 * @param params
	 * @return
	 */
	public String getTable1Data(String params);
	/**
	 * 获得院系入住表数据
	 * @param params
	 * @return
	 */
	public String getTable2Data(String params);
}
