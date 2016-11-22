package cn.gilight.product.card.dao;

import java.util.List;
import java.util.Map;

/**
 * 学生充值分析
 *
 */
public interface RechargeDao {
	
	/**
	 * 充值统计
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * all_count 总次数<br>
	 * all_money 总金额<br>
	 * all_stu 总人数<br>
	 * stu_monty 人均充值金额<br>
	 */
	public Map<String,Object> getRecharge(String startDate,String endDate,Map<String,String> deptTeach);
	
	
	/**
	 * 分学院充值统计
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * all_count 总次数<br>
	 * all_money 总金额<br>
	 * all_stu 总人数<br>
	 * stu_monty 人均充值金额<br>
	 */
	public List<Map<String,Object>> getRechargeByDept(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 充值金额区间
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * value 值<br>
	 * name 名字<br>
	 * code 代码<br>
	 */
	public List<Map<String,Object>> getRechargeRegion(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 剩余金额充值区间
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * value 值<br>
	 * name 名字<br>
	 * code 代码<br>
	 */
	public List<Map<String,Object>> getLastMoneyRegion(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 充值类型统计
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * all_count 充值次数<br>
	 * all_money 充值金额<br>
	 * name 名字<br>
	 * code 代码<br>
	 */
	public List<Map<String,Object>> getRechargeByType(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 分时段充值类型统计
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * all_count 充值次数<br>
	 * all_money 充值金额<br>
	 * hour_ 小时<br>
	 * name 名字<br>
	 * code 代码<br>
	 */
	public List<Map<String,Object>> getRechargeByHour(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 年度充值趋势
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * all_count 充值次数<br>
	 * all_money 充值金额<br>
	 * year 年<br>
	 */
	public List<Map<String,Object>> getRechargeTrend(Map<String,String> deptTeach);
	
	/**
	 * 年度充值分类型趋势
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * all_count 充值次数<br>
	 * all_money 充值金额<br>
	 * year 年<br>
	 * name 名字<br>
	 * code 代码<br>
	 */
	public List<Map<String,Object>> getRechargeTrendByType(Map<String,String> deptTeach);

}
