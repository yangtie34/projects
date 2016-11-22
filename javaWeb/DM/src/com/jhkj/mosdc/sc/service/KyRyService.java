package com.jhkj.mosdc.sc.service;
/**
 * 科研人员统计接口
 * @author Administrator
 *
 */
public interface KyRyService {
	/**
	 * 获取统计类别
	 * @param params
	 * @return
	 */
	public String getTjlx(String params);
	/**
	 * 获取科研人员职称分布
	 * @param params
	 * @return
	 */
	public String getKyryZc(String params);
	/**
	 * 根据统计类型获得总人数
	 * @param params
	 * @return
	 */
	public String getCountByTjlb(String params);
}
