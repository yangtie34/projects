package cn.gilight.product.card.service;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;

public interface JobCardService {
	
	
	/**
	 * 初始化一卡通使用情况
	 * @param year
	 * @param month
	 * @return
	 */
	public JobResultBean initCardUsed();
	
	/**
	 * 初始化充值结果表
	 * @param year
	 * @param month
	 * @return
	 */
	public JobResultBean initRecharge();
	
	/**
	 * 初始化一卡通持卡人月度数量
	 * @param year
	 * @param month
	 * @return
	 */
	public JobResultBean initCardPeople();
	
	/**
	 * 初始化学生月度消费表
	 * @param year
	 * @param month
	 * @return
	 */
	public JobResultBean initStuPay();
	
	/**
	 * 初始化学生用餐情况月度统计
	 * @param year
	 * @param month
	 * @return
	 */
	public JobResultBean initStuEat();
	
	/**
	 * 初始化学生消费明细
	 * @param year
	 * @param month
	 * @return
	 */
	public JobResultBean initStuPayDetil();
	/**
	 * 初始化消费热度
	 * @param year
	 * @param month
	 * @return
	 */
	public JobResultBean initCardHot();
	
	/**
	 * 更新消费热度
	 * @param year
	 * @param month
	 * @return
	 */
	public JobResultBean updateCardHot();
	/**
	 * 更新一卡通趋势
	 * @param year
	 * @param month
	 * @return
	 */
	public JobResultBean updateCardTrend();
	
	/**
	 * 更新一卡通使用情况
	 * @param year
	 * @param month
	 * @return
	 */
	public JobResultBean updateCardUsed();
	
	/**
	 * 更新充值结果表
	 * @param year
	 * @param month
	 * @return
	 */
	public JobResultBean updateRecharge();
	
	/**
	 * 更新一卡通持卡人月度数量
	 * @param year
	 * @param month
	 * @return
	 */
	public JobResultBean updateCardPeople();
	
	/**
	 * 更新学生月度消费表
	 * @param year
	 * @param month
	 * @return
	 */
	public JobResultBean updateStuPay();
	
	/**
	 * 更新学生用餐情况月度统计
	 * @param year
	 * @param month
	 * @return
	 */
	public JobResultBean updateStuEat();
	
	/**
	 * 更新学生消费明细
	 * @param year
	 * @param month
	 * @return
	 */
	public JobResultBean updateStuPayDetil();
	
}
