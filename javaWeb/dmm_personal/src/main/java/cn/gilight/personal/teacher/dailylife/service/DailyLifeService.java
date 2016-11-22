package cn.gilight.personal.teacher.dailylife.service;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;


public interface DailyLifeService {

	/**
	 * 获取月消费情况
	 * @param tea_id
	 * @return
	 */
	public Map<String, Object> getMonthConsume(String tea_id);
	/**
	 * 获取总消费情况
	 * @param tea_id
	 * @return
	 */
	public Map<String, Object> getTotalConsume(String tea_id);
	
	/**
	 * 获取图书借阅情况
	 * @param tea_id
	 * @return
	 */
	public Map<String, Object> getBorrow(String tea_id);
	
	/**
	 * 获取在借图书
	 * @param tea_id
	 * @return
	 */
	public List<Map<String,Object>> getInBorrow(String tea_id);

	/**
	 * 获取推荐图书
	 * @return
	 */
	public List<Map<String, Object>> getRecommendedBook(String tea_id);

	/**
	 * 获取历史消费
	 * @param tea_id
	 * @return
	 */
	public List<Map<String,Object>> getPayHistory(String tea_id,String currYear);

	/**
	 * 获取月消费记录
	 * @param tea_id
	 * @param monthStart
	 * @param page
	 * @return
	 */
	public Page getMonthConsumeList(String tea_id,String monthStart,Page page,String flag);
	/**
	 * 获取月消费类型及总计
	 * @param tea_id
	 * @param monthStart
	 * @return
	 */
	public Map<String, Object> getMonthPayType(String tea_id, String monthStart);
	/**
	 * 获取超期图书
	 * @param tea_id
	 * @return
	 */
	public List<Map<String, Object>> getOutOfDateBorrow(String tea_id);
	
	/**
	 * 获取已还图书
	 * @param tea_id
	 * @return
	 */
	public List<Map<String, Object>> getReturnBorrow(String tea_id);
	
	public Map<String,Object> getStopCounts(String tea_id);
	public Map<String, Object> getStopTimeAvg(String tea_id);
	public List<Map<String, Object>> getCarStop(String tea_id);

	
}
