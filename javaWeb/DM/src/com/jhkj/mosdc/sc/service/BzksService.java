package com.jhkj.mosdc.sc.service;

import java.util.List;
import java.util.Map;

public interface BzksService {
	/**
	 * 获取学生统计功能用学生类别。
	 * @param params
	 * @return
	 */
	public List<Map> getXsTjlb();
	/**
	 * 根据统计类别获得对应的学生数。
	 * @param json
	 * @return
	 */
	public String getCountsByXslb(String params);
	
	/**
	 * 根据学生类别获取学生的性别分布
	 * @param json
	 * @return
	 */
	public String getXbfbByXslb(String params);
	/**
	 * 根据学生类别获取学生的年龄段分布
	 * @param json
	 * @return
	 */
	public String getNldfbByXslb(String params);
	/**
	 * 根据学生类别获取学生的民族组成
	 * @param json
	 * @return
	 */
	public String getMzzcByXslb(String params);
	/**
	 * 根据学生类别获取学生的政治面貌
	 * @param json
	 * @return
	 */
	public String getZzmmByXslb(String params);
	/**
	 * 根据学生类别获取学生的生源地
	 * @param json
	 * @return
	 */
	public String getLydByXslb(String params);
	/**
	 * 根据学生类别获取学生的学科分布
	 * @param json
	 * @return
	 */
	public String getRyxkByXslb(String params);
	/**
	 * 根据学生类别获取学生的学位组成
	 * @param json
	 * @return
	 */
	public String getXwzcByXslb(String params);
	/**
	 * 根据学生类别获取学生的学历组成
	 * @param json
	 * @return
	 */
	public String getXlzcByXslb(String params);
	/**
	 * 获取各学院人数对比统计。
	 * @param params
	 * @return
	 */
	public String getXyRsdb(String params);
	/**
	 * 获取人员学科组成数据
	 * @param params
	 * @return
	 */
	public String getRyxkzc(String params);
}
