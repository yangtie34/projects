package cn.gilight.personal.student.four.service;

import java.util.List;
import java.util.Map;

public interface FourCardService {
	
	/**
	 * 查询学生消费次数及总金额及日均消费
	 * @param username
	 * @return
	 */
	public Map<String,Object> getCardMap(String username);
	
	/**
	 * 查询学生年消费及同届平均年消费
	 * @param username
	 * @return
	 */
	public List<Map<String,Object>> getCardChart(String username);

	/**
	 * 查询学生餐厅及超市消费
	 * @param username
	 * @return
	 */
	public Map<String, Object> getCardPie(String username);
	
	/**
	 * 查询学生最爱就餐的窗口
	 * @param username
	 * @return
	 */
	public List<Map<String,Object>> getCardWins(String username);
	
	/**
	 * 查询超市消费
	 * @param username
	 * @return
	 */
	public Map<String,Object> getShopCard(String username);	
	
	/**
	 * 查询洗浴支出
	 * @param username
	 * @return
	 */
	public Map<String,Object> getWashCard(String username);	

	/**
	 * 查询吃早餐次数满2餐天数满3餐天数
	 * @param username
	 * @return
	 */
	public Map<String,Object> getCardMeal(String username);
	
	/**
	 * 查询吃早餐午餐晚餐平均消费
	 * @param username
	 * @return
	 */
	public Map<String,Object> getCardMealPayAvg(String username);
}
