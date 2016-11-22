package com.jhnu.product.four.award.dao;

import java.util.List;
import java.util.Map;

public interface FourAwardDao {
	
	/**
	 * 统计所有在校学生获得奖学金的次数  11级以及11级以后的所有在校学生
	 * 排序按照 先年级  后获得奖学金次数  的顺序
	 */
	public List<Map<String,Object>> getAwardTimes();
	
	
	/**
	 * 从log表中获取指定学生的获奖次数信息
	 * @param Id
	 * @return
	 */
	public List<Map<String,Object>> getAwardTimes(String Id);
	
	/**
	 * 保存所有的奖金次数以及超越百分比到log表中
	 * @param list
	 */
	public void SaveAwardLog(List<Map<String,Object>> list);
	
	
	/**
	 * 获取所有的补助次数以及总金额
	 * @return
	 */
	public List<Map<String,Object>> getSubsidyTimesAndMoney();
	
	/**
	 * 从log表中获取指定学生的补助信息
	 * @param Id
	 * @return
	 */
	public List<Map<String,Object>> getSubsidyTimesAndMoney(String Id);
	
	
	/**
	 * 保存所有助学金的的次数以及总金额到log表中
	 * @param list
	 */
	public void SaveSubsidyLog(List<Map<String,Object>> list);
	
	
	/**
	 * 获取第一次奖励信息  包括时间和金额
	 * @return
	 */
	public List<Map<String,Object>> getFirstAwardMsg();
	
	/**
	 * 从log表中获取指定学生的第一次奖励信息
	 * @param Id
	 * @return
	 */
	public List<Map<String,Object>> getFirstAwardMsg(String Id);
	
	
	/**
	 * 保存所有学生第一次获得奖励的信息
	 */
	public void SaveFirstAwardMsgLog(List<Map<String,Object>> list);

}
