package com.jhkj.mosdc.jxpg.service;

import java.util.Map;

/**
 * 2 教师队伍
 * @author xuebl
 * @date 2014-01-16
 */
public interface JxpgJsdwService {

	/**
	 * 2.1 保存”学校生师比及教师情况“
	 * @param params
	 * @return
	 */
	public Map saveBeforeXxssbjjsqk(String params);

	/**
	 * 2.2 保存”教师队伍结构“
	 * @param params
	 * @return
	 */
	public Map saveBeforeJsdwjg(String params);
	
	/**
	 * 2.7 保存”专业带头人情况“
	 * @param params
	 * @return
	 */
	public Map saveBeforeZydtrqk(String params);
	
	/**
	 * 2.8 保存”学校实验系列人员结构“
	 * @param params
	 * @return
	 */
	public Map saveBeforeXxsyxlryjg(String params);
	
	/**
	 * 2.9教师教育教学改革与成果
	 * @Title: saveBeforeJsjyjxggycg 
	 * @param @param params
	 * @param @return
	 * @return Map
	 * @throws
	 */
	public Map saveBeforeJsjyjxggycg(String params);
	/**
	 * 2.6教师培养培训情况
	 * @Title: saveBeforeJspypxqk 
	 * @param @param params
	 * @param @return
	 * @return Map
	 * @throws
	 */
	public Map saveBeforeJspypxqk(String params);
	/**
	 * 2.3各教学单位教师与本科生情况
	 * @Title: saveBeforeGjxdwjsybksqk 
	 * @param @param params
	 * @param @return
	 * @return Map
	 * @throws
	 */
	public Map saveBeforeGjxdwjsybksqk(String params);
	
	/**
	 * 2.4 主讲教师本科授课情况
	 * @param params
	 * @return
	 */
	public Map saveBeforeZjjsbkskqk(String params);
	
	/**
	 * 2.5 教授、副教授讲授本科课程比例
	 * @param params
	 * @return
	 */
	public Map saveBeforeJsfjsjsbkkcbl(String params);
	
	
}