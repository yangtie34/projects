package com.jhnu.product.four.punish.service;

import com.jhnu.product.four.common.entity.ResultBean;

public interface FourPunishService {
	
	/**
	 *保存所有学生的惩罚信息到log表
	 */
	public void savePunishLog();
	
	
	/**
	 * 根据学生的Id查询学生的处罚信息
	 * @param Id
	 */
	public ResultBean getStuPunishMsgByID(String Id);
	
	

}
