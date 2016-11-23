package com.jhnu.product.four.card.service;

import java.util.List;
import java.util.Map;

import com.jhnu.product.four.common.entity.ResultBean;
import com.jhnu.system.common.condition.Condition;
import com.jhnu.system.common.page.Page;

public interface FourCardService {
	
	/**
	 * 获取指定学生的首次消费信息
	 * @param Id
	 * @return
	 */
	public ResultBean getFirstPayMsg(String Id);
	
	
    /**
     * 获取指定学生的消费总金额和消费总次数
     * @param Id
     * @return
     */
	public ResultBean getSumMoneyAndPayTimes(String Id);
	
	
	
	
	/**
	 * 从log表中获取指定人的洗浴信息
	 * @param Id
	 * @return
	 */
	public ResultBean getWashTimes(String Id);
	
	/**
	 * 获取消费统计
	 * @param Id 学生ID
	 * @return
	 */
	public ResultBean getPayCount(String Id);
	
	/**
	 * 获取充值统计
	 * @param Id 学生ID
	 * @return
	 */
	public ResultBean getRecCount(String Id);
	/**
	 * 依据学生id获取学生三餐平均消费及同届同性别下的平均值
	 * @param id
	 * @return
	 */
	public ResultBean getCompareMealsAvg(String id);
	
	/**
	 * 保存第一次交易的信息
	 */
	public void saveFirstJob();
	
	
	/**
	 * 保存所有人的消费总金额和总次数
	 */
	public void saveAllPayMoneyAndTimes();
	
	/**
	 * 保存学生的三餐消费次数及其所处位置
	 */
	public void saveMealsCount();
	/**
	 * 保存学生的三餐平均消费
	 */
	public void saveMealsAvg();
	
	
	/**
	 * 保存洗浴信息
	 */
    public void saveWashJob();
    
	
	/**
	 * 保存消费统计JOB
	 */
	public void savePayCountJob();
	
	/**
	 * 保存喜欢吃饭的地方JOB
	 */
	public void saveLikeEatJob();
	
	/**
	 * 保存喜欢购物的地方JOB
	 */
	public void saveLikeShopJob();
	
	/**
	 * 保存喜欢洗浴的地方JOB
	 */
	public void saveLikeWashJob();
	
	/**
	 * 保存充值方式LOG
	 */
	public void saveRecCountJob();
	
	/**
	 * 保存全校充值方式LOG
	 */
	public void saveAllRecCountJob();
	/**
	 * 根据学生id获取学生的消费习惯
	 * @param id
	 * @return
	 */
	public ResultBean getCardHabit(String id);
	
	public Page getCardDetailLog(String id,Page page,List<Condition> conditions);
	
	public List<Map<String, Object>> getCardDetailGroupByDeal(String id);
	
	public List<Map<String, Object>> getCardDetailGroupByTime(String id);
}
