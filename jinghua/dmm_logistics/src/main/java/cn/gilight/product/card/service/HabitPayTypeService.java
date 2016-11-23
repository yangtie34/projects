package cn.gilight.product.card.service;

import java.util.List;
import java.util.Map;

/**
 * 学生分消费类型消费习惯分析
 *
 */
public interface HabitPayTypeService {
	
	/**
	 * 整体情况
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * all_count 总次数<br>
	 * all_money 总金额<br>
	 */
	public Map<String,Object> getHabitCount(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 分类型情况
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * all_count 总次数<br>
	 * all_money 总金额<br>
	 * type_name 类型名字<br>
	 * type_code 类型代码<br>
	 */
	public List<Map<String, Object>> getHabitCountByType(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 整体早中晚
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * all_count 总次数<br>
	 * all_money 总金额<br>
	 * name 名字<br>
	 * code 代码<br>
	 */
	public List<Map<String,Object>> getHabitZao(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 分类型早中晚
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * all_count 总次数<br>
	 * all_money 总金额<br>
	 * name 名字<br>
	 * code 代码<br>
	 * type_name 类型名字<br>
	 * type_code 类型代码<br>
	 */
	public List<Map<String,Object>> getHabitZaoByType(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 整体时段情况
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * all_count 总次数<br>
	 * all_money 总金额<br>
	 * hour_ 小时<br>
	 */
	public List<Map<String,Object>> getHabitHour(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 分类型时段情况
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * all_count 总次数<br>
	 * all_money 总金额<br>
	 * hour_ 小时<br>
	 * type_name 类型名字<br>
	 * type_code 类型代码<br>
	 */
	public List<Map<String,Object>> getHabitHourByType(String startDate,String endDate,Map<String,String> deptTeach);

}
