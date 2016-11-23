package cn.gilight.product.card.service;

import java.util.List;
import java.util.Map;

/**
 * 分学生类型消费习惯分析
 *
 */
public interface HabitStuTypeService {
	
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
	 * 分性别早中晚
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
	public List<Map<String,Object>> getHabitZaoBySex(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 分学历早中晚
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
	public List<Map<String,Object>> getHabitZaoByEdu(String startDate,String endDate,Map<String,String> deptTeach);
	
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
	 * 分性别时段情况
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
	public List<Map<String,Object>> getHabitHourBySex(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 分学历时段情况
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
	public List<Map<String,Object>> getHabitHourByEdu(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 整体刷卡用餐次数
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * all_count 总次数<br>
	 * all_money 总金额<br>
	 * name 名字<br>
	 * code 代码<br>
	 */
	public List<Map<String,Object>> getHabitEat(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 分性别刷卡用餐次数
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
	public List<Map<String,Object>> getHabitEatBySex(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 分学历刷卡用餐次数
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
	public List<Map<String,Object>> getHabitEatByEdu(String startDate,String endDate,Map<String,String> deptTeach);

}
