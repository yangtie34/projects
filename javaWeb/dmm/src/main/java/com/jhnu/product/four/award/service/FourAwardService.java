package com.jhnu.product.four.award.service;

import com.jhnu.product.four.common.entity.ResultBean;

public interface FourAwardService {

	/**
	 * 保存所有的在校学生的获奖次数 将结果集存放在log的表中
	 */
	public void SaveAllAwardTimesToLog();

	/**
	 * 从Log表中获取指定学生的获奖次数信息
	 * 
	 * @param Id
	 * @return
	 */
	public ResultBean getAwardTimes(String Id);

	/**
	 * 从Log表中获取指定学生的助学金的信息
	 * 
	 * @param Id
	 * @return
	 */
	public ResultBean getSubsidyTimesAndMoney(String Id);

	/**
	 * 从Log表中获取指定学生的第一次获奖的信息
	 * 
	 * @param Id
	 * @return
	 */
	public ResultBean getFirstAwardMsg(String Id);


	/**
	 * 保存所有学生的补助信息
	 */
	public void SaveAllSubsidy();

	/**
	 * 保存所有学生第一次获奖的信息
	 */
	public void SaveAllFirstAward();

}
