package cn.gilight.product.card.dao;

import java.util.Map;

public interface JobCardDao {
	
	
	/**
	 * 一卡通使用情况
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String,Integer> updateCardUsed(String yearMonth);
	
	/**
	 * 充值结果表
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String,Integer> updateRecharge(String yearMonth);
	
	/**
	 * 充值年度趋势结果表
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String,Integer> updateCardTrend();
	
	/**
	 * 一卡通持卡人数
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String,Integer> updateCardPeople(String yearMonth);
	
	/**
	 * 学生月度消费表
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String,Integer> updateStuPay(String yearMonth);
	
	/**
	 * 学生用餐情况月度统计
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String,Integer> updateStuEat(String yearMonth);
	
	/**
	 * 学生用餐情况月度统计
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String,Integer> updateStuPayDetil(String yearMonth);
	
}
