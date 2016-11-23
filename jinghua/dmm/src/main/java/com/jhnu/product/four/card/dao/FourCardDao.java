package com.jhnu.product.four.card.dao;

import java.util.List;
import java.util.Map;

import com.jhnu.system.common.page.Page;

public interface FourCardDao {

	/**
	 * 获取所有人的第一次消费信息
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getFirstPay();

	/**
	 * 从log表中获取指定ID学生的首次消费信息
	 * 
	 * @param Id
	 * @return
	 */
	public List<Map<String, Object>> getFirstPayMsg(String Id);

	/**
	 * 获取所有人大学四年来一共的花费 消费次数
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getSumMoneyAndPayTimes();

	/**
	 * 从Log表中获取某个学生花费的总金额和从次数
	 * 
	 * @param Id
	 * @return
	 */
	public List<Map<String, Object>> getSumMoneyAndPayTimes(String Id);

	/**
	 * 获取所有学生的洗浴次数
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getWashTimes();
	
	
	/**
	 * 按性别年级获取平均洗澡次数
	 * @param SexCode
	 * @param byn
	 * @return
	 */
	public List<Map<String,Object>> getAvgWashTimes(String SexCode,String byn);

	/**
	 * 从log表中获取指定学生的洗浴信息
	 * 
	 * @param Id
	 * @return
	 */
	public List<Map<String, Object>> getWashTimes(String Id);

	/**
	 * 获得各届、各性别学生的洗浴的总数
	 */
	public List<Map<String, Object>> getPeopleNumByBynAndSex();

	/**
	 * 保存第一次消费信息
	 */
	public void saveFirstJob(List<Map<String, Object>> list);

	/**
	 * 依据学生三餐消费计算其超越值
	 */
	public void saveMealsCount(List<Map<String, Object>> list);

	/**
	 * 获取学生的三餐消费次数
	 * 
	 * @return
	 */
	public Map<String, List<Map<String, Object>>> getMealsCount();

	/**
	 * 创建学生三餐消费统计临时表。
	 */
	public void createTempMealsCount();

	/**
	 * 删除学生三餐消费统计临时表
	 */
	public void dropTempMealsCount();

	/**
	 * 获取消费分类占比
	 * 
	 * @return
	 */
	public Page getPayCount(int num, int sum, Integer tol, Integer status);

	/**
	 * 保存消费分类占比至LOG
	 * 
	 * @param list
	 */
	public void savePayCountLog(List<Map<String, Object>> list,boolean isFist);

	/**
	 * 从LOG中获取消费占比
	 * 
	 * @param id
	 *            学生ID
	 * @return
	 */
	public List<Map<String, Object>> getPayCountLog(String id);

	/**
	 * 获取经常吃饭的地方
	 * 
	 * @return
	 */
	public Page getLikeEat(int num, int sum, Integer tol, Integer status);

	/**
	 * 将经常吃饭的地方保存至LOG
	 * 
	 * @param list
	 */
	public void saveLikeEatLog(List<Map<String, Object>> list,boolean isFist);

	/**
	 * 从LOG中获取经常吃饭的地方
	 * 
	 * @param id
	 *            学生ID
	 * @param order
	 *            前order名
	 * @return
	 */
	public List<Map<String, Object>> getLikeEatLog(String id, int order);

	/**
	 * 获取经常购物的地方
	 * 
	 * @return
	 */
	public Page getLikeShop(int currentPage, int numPerPage, Integer totalRows,
			Integer status);

	/**
	 * 将经常购物的地方保存至LOG
	 * 
	 * @param list
	 */
	public void saveLikeShopLog(List<Map<String, Object>> list,boolean isFist);

	/**
	 * 从LOG中获取经常购物的地方
	 * 
	 * @param id
	 *            学生ID
	 * @param order
	 *            前order名
	 * @return
	 */
	public List<Map<String, Object>> getLikeShopLog(String id, int order);

	/**
	 * 获取经常洗浴的地方
	 * 
	 * @return
	 */
	public Page getLikeWash(int currentPage, int numPerPage, Integer totalRows,
			Integer status);

	/**
	 * 将经常洗浴的地方保存至LOG
	 * 
	 * @param list
	 */
	public void saveLikeWashLog(List<Map<String, Object>> list,boolean isFist);

	/**
	 * 从LOG中获取经常洗浴的地方
	 * 
	 * @param id
	 *            学生ID
	 * @param order
	 *            前order名
	 * @return
	 */
	public List<Map<String, Object>> getLikeWashLog(String id, int order);

	/**
	 * 获取所有学生充值分类占比 （按毕业年分类）
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getAllRecCount();

	/**
	 * 保存所有学生充值分类占比
	 * 
	 * @param list
	 */
	public void saveAllRecCountLog(List<Map<String, Object>> list);

	/**
	 * 从LOG中获取所有学生充值分类占比 （按毕业年分类）
	 * 
	 * @param endYear
	 *            毕业年
	 * @return
	 */
	public List<Map<String, Object>> getAllRecCountLog(String endYear);

	/**
	 * 获取充值分类占比
	 * 
	 * @return
	 */
	public Page getRecCount(int currentPage, int numPerPage, Integer totalRows,
			Integer status);

	/**
	 * 保存充值分类占比至LOG
	 * 
	 * @param list
	 */
	public void saveRecCountLog(List<Map<String, Object>> list,boolean isFist);

	/**
	 * 从LOG中获取充值分类占比
	 * 
	 * @param id
	 *            学生ID
	 * @param order
	 *            前order名
	 * @return
	 */
	public List<Map<String, Object>> getRecCountLog(String id, int order);

	/**
	 * 保存所有人的洗浴信息到log表
	 * 
	 * @param list
	 */
	public void saveWashLog(List<Map<String, Object>> list);

	/**
	 * 保存所有人的总金额和总交易次数
	 * 
	 * @param list
	 */
	public void savePayTimesAndMoneyLog(List<Map<String, Object>> list);

	/**
	 * 创建每生三餐的平均消费值临时表
	 */
	public void createTempMealsAvg();

	/**
	 * 删除每生三餐的平均消费值临时表
	 */
	public void dropTempMealsAvg();

	/**
	 * 获取每生三餐的平均消费值
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getMealsAvg();

	/**
	 * 保存每生三餐的平均消费值
	 * 
	 * @param list
	 */
	public void saveMealsAvg(List<Map<String, Object>> list);

	/**
	 * 依据学生id获取学生三餐次数及超越值
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getMealsCountLog(String id);

	/**
	 * 依据学生id获取学生三餐的平均值
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getMealsAvgLog(String id);

	/**
	 * 依据毕业届与性别获取该届该性别下的就餐平均值
	 * 
	 * @param endYear
	 * @param sex
	 * @return
	 */
	public List<Map<String, Object>> getMealsAvgLogByYearSex(String endYear,
			String sex);
	
	
	public Page getCardDetailLog(int currentPage,int numPerPage,String tj);
	
	public List<Map<String, Object>> getCardDetailGroupByDeal(String id);
	
	public List<Map<String, Object>> getCardDetailGroupByTime(String id);
	
}
