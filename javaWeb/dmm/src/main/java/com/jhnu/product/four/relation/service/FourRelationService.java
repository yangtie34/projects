package com.jhnu.product.four.relation.service;

import com.jhnu.product.four.common.entity.ResultBean;

public interface FourRelationService {
	
	/**
	 * 获取室友
	 * @param id 学生ID
	 * @return
	 */
	public ResultBean getRoommateLog(String id);
	
	/**
	 * 获取辅导员
	 * @param id 学生ID
	 * @return
	 */
	public ResultBean getTutorLog(String id);
	
	/**
	 * 保存室友JOB
	 */
	public void saveRoommateJob();
	
	/**
	 * 保存辅导员JOB
	 */
	public void saveTutorJob();

}
