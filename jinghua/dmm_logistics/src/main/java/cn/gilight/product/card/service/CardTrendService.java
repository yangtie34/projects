package cn.gilight.product.card.service;

import java.util.List;
import java.util.Map;

public interface CardTrendService {
	
	/**
	 * 充值趋势统计
	 * @param deptTeach
	 * @return
	 * all_count 总次数<br>
	 * all_money 总金额<br>
	 * use_people 充值人数<br>
	 * people_monty 人均充值金额<br>
	 * year_month 月份<br>
	 */
	public List<Map<String,Object>> getRechargeTrend(Map<String,String> deptTeach);
	
	
	/**
	 * 一卡通使用率趋势统计
	 * @param deptTeach
	 * @param type_code  'all'
	 * @param flag  'use_all','use_sex','use_edu'
	 * @return
	 * use_people 使用人数<br>
	 * all_people 总人数<br>
	 * use_rate  使用率<br>
	 * year_month 月份<br>
	 */
	public List<Map<String,Object>> getCardUsedTrend(Map<String,String> deptTeach,String type_code,String flag);
	
	
	/**
	 * 分消费类型消费趋势统计
	 * @param deptTeach
	 * @param type_code 'all'
	 * @return
	 * all_count 总次数<br>
	 * all_money 总金额<br>
	 * count_rate 刷卡率<br>
	 * money_rate 消费率<br>
	 * year_month 月份<br>
	 */
	public List<Map<String,Object>> getPayTypeTrend(Map<String,String> deptTeach,String type_code);
	
	/**
	 * 分学生类型消费趋势统计
	 * @param deptTeach
	 * @param type_code 'all'
	 * @param flag  'pay_all','pay_sex','pay_edu'
	 * @return
	 * all_count 总次数<br>
	 * all_money 总金额<br>
	 * one_money 笔均消费金额<br>
	 * day_money 笔均消费金额<br>
	 */
	public List<Map<String,Object>> getStuPayTrend(Map<String,String> deptTeach,String type_code,String flag);
	
	
	/**
	 * 餐厅窗口消费趋势统计
	 * @param type_code 点击的餐厅，窗口ID
	 * @param flag  'eat_room','eat_port'
	 * @param queryType 查询条件：'all','zao','zhong','wan'
	 * @return
	 * all_count 总次数<br>
	 * all_money 总金额<br>
	 * one_money 笔均消费金额<br>
	 * day_count 日均消费笔数<br>
	 * day_money 日均消费金额<br>
	 */
	public List<Map<String,Object>> getEatTrend(String type_code,String flag,String queryType);
	
	/**
	 * 人均年消费趋势统计
	 * @param deptTeach 
	 * @return
	 * all_money 总金额<br>
	 * use_people 消费人数<br>
	 * year_money 人均年消费<br>
	 */
	public List<Map<String,Object>> getPayYearTrend(Map<String,String> deptTeach);
	
}
