package cn.gilight.personal.teacher.dailylife.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;



public interface DailyLifeDao {

	/**
	 * 获取本月消费
	 * @param monthStart
	 * @param tea_id
	 * @return
	 */
	public List<Map<String, Object>> getMonthConsume(String monthStart, String tea_id);

	/**
	 * 获取总消费
	 * @param tea_id
	 * @return
	 */
	public List<Map<String, Object>> getTotalConsume(String tea_id,String monthStart);

	/**
	 * 获取在借图书数
	 * @param tea_id
	 * @return
	 */
	public List<Map<String, Object>> getInBorrow(String tea_id);

	/**
	 * 获取累计借阅图书数
	 * @param tea_id
	 * @return
	 */
	public List<Map<String, Object>> getTotalBorrow(String tea_id);
	
	/**
	 * 获取推荐图书
	 * @return
	 */
	public List<Map<String,Object>> getRecommendedBook(String tea_id);
	
	/**
	 * 获取月消费列表
	 * @param monthStart
	 * @param tea_id
	 * @return
	 */
	public Page getMonthConsumeList(String monthStart,String nextMonth,String tea_id,Page page,String flag);
	
	/**
	 * 获取月餐厅消费
	 * @param monthStart
	 * @param tea_id
	 * @return
	 */
	public List<Map<String,Object>> getMonthMeals(String monthStart,String nextMonth,String tea_id);
	
	/**
	 * 获取月其他消费
	 * @param monthStart
	 * @param tea_id
	 * @return
	 */
	public List<Map<String,Object>> getMonthOther(String monthStart,String nextMonth,String tea_id);
	
	/**
	 * 获取历史消费
	 * @param tea_id
	 * @param year
	 * @return
	 */
	public List<Map<String,Object>> getPayHistory(String tea_id,int year);

	/**
	 * 获取在借图书
	 * @param tea_id
	 * @return
	 */
	public List<Map<String, Object>> getInBorrowList(String tea_id);

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
	public List<Map<String, Object>> getReturnBorrow(String tea_id) ;

	/**
	 * 获取消费年份
	 * @return
	 */
	public List<Map<String, Object>> getYears(String tea_id);
	
	public Map<String,Object> getStopCounts(String tea_id);

	public Map<String, Object> getStopTimeAvg(String tea_id);

	public List<Map<String, Object>> getYearsMonth(String tea_id);

	public List<Map<String, Object>> getCarStop(String tea_id, int year,String month);

}
