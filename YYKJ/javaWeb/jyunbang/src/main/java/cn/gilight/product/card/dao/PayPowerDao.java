package cn.gilight.product.card.dao;

import java.util.List;
import java.util.Map;

/**
 * 学生消费能力分析
 *
 */
public interface PayPowerDao {
	
	/**
	 * 整体消费能力
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * all_count 总次数<br>
	 * all_money 总金额<br>
	 * one_money 单笔金额<br>
	 * day_money 日均金额<br>
	 */
	public List<Map<String,Object>> getPower(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 分性别消费能力
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * all_count 总次数<br>
	 * all_money 总金额<br>
	 * one_money 单笔金额<br>
	 * day_money 日均金额<br>
	 * type_name 类型名字<br>
	 * type_code 类型代码<br>
	 */
	public List<Map<String,Object>> getPowerBySex(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 分学历消费能力
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * all_count 总次数<br>
	 * all_money 总金额<br>
	 * one_money 单笔金额<br>
	 * day_money 日均金额<br>
	 * type_name 类型名字<br>
	 * type_code 类型代码<br>
	 */
	public List<Map<String,Object>> getPowerByEdu(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 分学院消费能力
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * all_count 总次数<br>
	 * all_money 总金额<br>
	 * one_money 单笔金额<br>
	 * day_money 日均金额<br>
	 * type_name 类型名字<br>
	 * type_code 类型代码<br>
	 */
	public List<Map<String,Object>> getPowerByDept(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 整体消费组成
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * all_count 总次数<br>
	 * all_money 总金额<br>
	 * name 名字<br>
	 * code 代码<br>
	 */
	public List<Map<String,Object>> getPayType(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 分性别消费组成
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
	public List<Map<String,Object>> getPayTypeBySex(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 分学历消费组成
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
	public List<Map<String,Object>> getPayTypeByEdu(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 整体消费区间
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * all_stu 总人数<br>
	 * code 值<br>
	 * name 名称<br>
	 */
	public List<Map<String,Object>> getPayRegion(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 分性别消费区间
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * all_stu 总人数<br>
	 * code 值<br>
	 * name 名称<br>
	 * type_name 类型名字<br>
	 * type_code 类型代码<br>
	 */
	public List<Map<String,Object>> getPayRegionBySex(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 分学历消费区间
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * all_stu 总人数<br>
	 * code 值<br>
	 * name 名称<br>
	 * type_name 类型名字<br>
	 * type_code 类型代码<br>
	 */
	public List<Map<String,Object>> getPayRegionByEdu(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 分地区消费能力
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * all_count 总次数<br>
	 * all_money 总金额<br>
	 * one_money 单笔金额<br>
	 * day_money 日均金额<br>
	 * type_name 类型名字<br>
	 * type_code 类型代码<br>
	 */
	public List<Map<String,Object>> getPowerByArea(String startDate,String endDate,Map<String,String> deptTeach);
	/**
	 * 分地区消费组成
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
	public List<Map<String,Object>> getPayTypeByArea(String startDate,String endDate,Map<String,String> deptTeach);
	/**
	 * 分地区消费区间
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * all_stu 总人数<br>
	 * code 值<br>
	 * name 名称<br>
	 * type_name 类型名字<br>
	 * type_code 类型代码<br>
	 */
	public List<Map<String,Object>> getPayRegionByArea(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 分地区消费能力
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * all_count 总次数<br>
	 * all_money 总金额<br>
	 * one_money 单笔金额<br>
	 * day_money 日均金额<br>
	 * type_name 类型名字<br>
	 * type_code 类型代码<br>
	 */
	public List<Map<String,Object>> getPowerByMZ(String startDate,String endDate,Map<String,String> deptTeach);
	/**
	 * 分地区消费组成
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
	public List<Map<String,Object>> getPayTypeByMZ(String startDate,String endDate,Map<String,String> deptTeach);
	/**
	 * 分地区消费区间
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * all_stu 总人数<br>
	 * code 值<br>
	 * name 名称<br>
	 * type_name 类型名字<br>
	 * type_code 类型代码<br>
	 */
	public List<Map<String,Object>> getPayRegionByMZ(String startDate,String endDate,Map<String,String> deptTeach);

}
