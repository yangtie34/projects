package com.jhnu.product.manager.card.service;

import java.util.List;
import java.util.Map;

import com.jhnu.system.common.page.Page;

public interface LowPayService {

	/**
	 * 获取某时间段内平均每天的男女各餐消费金额
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String, Object>> getAvgEatMoneyBySex(String startDate, String endDate);

	/**
	 * 保存近三个月各学生每天的就餐明细及结果到临时表
	 */
	public void saveStuEatDetailLog();

	/**
	 * 获取近三个月某部门的低消费学生名单
	 * 
	 * @param startDate
	 * @param endDate
	 * @param dept_id
	 * @return
	 */
	public Page getLowPayStu(String dept_id, boolean isLeaf, int currentPage, int pageSize);

	/**
	 * 获取低消费学生的消费详情
	 * 
	 * @param stuId
	 * @param sexCode
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Page getLowPayDetailStu(String stuId, String sexCode, String startDate, String endDate, int currentPage, int pageSize);

}
