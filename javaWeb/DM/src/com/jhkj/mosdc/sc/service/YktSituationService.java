package com.jhkj.mosdc.sc.service;

public interface YktSituationService {
	/**
	 * 保存近1个月的消费明细数据，
	 * 每天更新一次
	 */
	public void saveMonthXfmx();
	/**
	 * 获取日生均消费笔数。
	 * @param params
	 * @return
	 */
	public String getRsjXfbs(String params);
	/**
	 * 获取日生均消费金额。
	 * @param params
	 * @return
	 */
	public String getRsjXfje(String params);
	/**
	 * 获取高低消费人数及占比数据。
	 * @param params
	 * @return
	 */
	public String getGDxfrs(String params);
	/**
	 * 获取生均消费笔数
	 * @param params
	 * @return
	 */
	public String getListSjxfBsAndJe(String params);
	/**
	 * 获取消费区间分布数据。
	 * @param params
	 * @return
	 */
	public String getXfqj(String params);
	/**
	 * 学生就餐习惯分析。
	 * @param params
	 * @return
	 */
	public String getXsjcxg(String params);
	
}
