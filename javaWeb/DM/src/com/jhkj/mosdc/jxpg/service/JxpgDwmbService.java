package com.jhkj.mosdc.jxpg.service;

import java.util.Map;

/**
 * 1-定位目标
 * @author xuebl
 * @date 2015-01-14
 */
public interface JxpgDwmbService {

	/**
	 * 1.2 保存”校领导年龄和学位结构“
	 * @param params
	 * @return
	 */
	public Map saveBeforeXldnlhxwjg(String params);
	
	/**
	 * 1.3 保存”校级教学管理人员结构“
	 * @param params
	 * @return
	 */
	public Map saveBeforeXjjxglryjg(String params);
	
	/**
	 * 1.4 保存”教育教学改革与成果“
	 * @param params
	 * @return
	 */
	public Map saveBeforeJyjxggycg(String params);
	
	/**
	 * 1.4 保存”专业布局概览“
	 * @param params
	 * @return
	 */
	public Map saveBeforeZybjgl(String params);
	
	
}