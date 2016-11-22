package com.jhkj.mosdc.sc.service;
/**
 * 疑似低消费预警service
 * @author Administrator
 *
 */
public interface YsPksService {
	/**
	 * 计算并保存低消费交易明细TB_YKT_TEMP_DXF
	 */
	public void saveXfxx();
	/**
	 * 各院系疑似低消费学生名单
	 * @param params
	 * @return
	 */
	public String queryGridContent(String params);
	/**
	 * 获取指定学生的近三月消费明细（按三餐）
	 * @param params
	 * @return
	 */
	public String queryGridContent4xq(String params);
	/**
	 * 获取三餐均值及日均值
	 * @param params
	 * @return
	 */
	public String queryChartData(String params);
}
