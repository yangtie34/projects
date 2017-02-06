package cn.gilight.dmm.xg.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;

/**
 * 学籍异动
 * 
 * @author xuebl
 * @date 2016年5月4日 上午11:24:37
 */
public interface ChangeDao {

	/**
	 * 查询学籍异动摘要
	 * @param schoolYear 学年；eg：2015
	 * @param deptList 标准权限
	 * @param stuAdvancedList 学生高级查询参数
	 * @param start_date 开始日期
	 * @param end_date 截止日期
	 * @param change_codes 异动codes
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryChangeAbstract(int schoolYear, List<String> deptList, List<AdvancedParam> advanceParamList,
			String start_date, String end_date, String change_codes);
	
	/**
	 * 获取某学年、日期段学籍异动  类型分组
	 * @param schoolYear 学年；eg：2015
	 * @param deptList 标准权限
	 * @param stuAdvancedList 学生高级查询参数
	 * @param start_date 开始日期
	 * @param end_date 截止日期
	 * @param change_codes 异动codes
	 * @return List<Map<String,Object>>
	 */
	List<Map<String, Object>> queryChangeType(int schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, 
			String start_date, String end_date, String change_codes);

	/**
	 * 获取某学年、日期段 学籍异动  年级分组
	 * @param schoolYear 学年；eg：2015
	 * @param deptList 标准权限
	 * @param stuAdvancedList 学生高级查询参数
	 * @param start_date 开始日期
	 * @param end_date 截止日期
	 * @param change_codes 异动codes
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryChangeGrade(int schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, 
			String start_date, String end_date, String change_codes);

	/**
	 * 获取某学年、日期段 学籍异动  学科分组
	 * @param schoolYear 学年；eg：2015
	 * @param deptList 标准权限
	 * @param stuAdvancedList 学生高级查询参数
	 * @param start_date 开始日期
	 * @param end_date 截止日期
	 * @param change_codes 异动codes
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryChangeSubject(int schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, 
			String start_date, String end_date, String change_codes);

	/**
	 * 获取某学年、日期段 学籍异动  性别分组
	 * @param schoolYear 学年；eg：2015
	 * @param deptList 标准权限
	 * @param stuAdvancedList 学生高级查询参数
	 * @param start_date 开始日期
	 * @param end_date 截止日期
	 * @param change_codes 异动codes
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryChangeSex(int schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, 
			String start_date, String end_date, String change_codes);
	
	/**
	 * 根据组织机构获取学籍异动  人数
	 * @param schoolYear 学年；eg：2015
	 * @param deptList 标准权限
	 * @param stuAdvancedList 学生高级查询参数
	 * @param start_date 开始日期
	 * @param end_date 截止日期
	 * @param deptIds 异动源组织机构Ids
	 * @param change_codes 异动codes
	 * @return int
	 */
	public int queryStuChangeCountByDeptId(int schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, 
			String start_date, String end_date, String deptIds, String change_codes);
	
	/**
	 * 根据权限获取组织机构学籍异动人数 sql
	 * @param schoolYear 学年；eg：2015
	 * @param deptList 标准权限
	 * @param stuAdvancedList 学生高级查询参数
	 * @param start_date 开始日期
	 * @param end_date 截止日期
	 * @param deptIds 异动源组织机构Ids
	 * @param change_codes 异动codes
	 * @return String
	 */
	public String queryStuChangeCountByDeptIdSql(int schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, 
			String start_date, String end_date, String change_codes);

	/**
	 * 获取学籍异动人数  月份分组
	 * @param schoolYear 学年；eg：2015
	 * @param deptList 标准权限
	 * @param stuAdvancedList 学生高级查询参数
	 * @param start_date 开始日期
	 * @param end_date 截止日期
	 * @param change_codes 异动codes
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryChangeMonth(int schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, 
			String start_date, String end_date, String change_codes);

	/**
	 * 查询符合条件的异动标准代码
	 * @param start_date 开始日期
	 * @param end_date 截止日期
	 * @param change_codes  异动codes
	 * @return List<Map<String,Object>>
	 * <br> [ {name:'', code:''}, {} ]
	 */
	public List<Map<String, Object>> queryChangeBzdmList(String start_date, String end_date, String change_codes);
	
	/**
	 * 获取学籍异动sql
	 * @param advancedParamList
	 * @param keyValue
	 * @param fields
	 * @param change_codes
	 * @param deptList
	 * @return
	 */
	public String getStuChangeDetailSql(List<AdvancedParam> advancedParamList, Map<String, Object> keyValue,List<String> fields,String change_codes,List<String> deptList);
	
	/**
	 * 获取转出 转入sql
	 * @param schoolYear 学年
	 * @param deptList 权限
	 * @param stuAdvancedList 高级查询参数
	 * @param start_date 开始时间
	 * @param end_date 截止时间
	 * @param level 机构层次
	 * @return
	 */
	public String getStuChangeByDeptOrMajorSql(int schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, 
			String start_date, String end_date,String id, String level,String type);
	
	/**
	 * 获取学籍特殊异动，二次异动人数
	 * @param schoolYear 学年
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级参数
	 * @param start_date 开始时间
	 * @param end_date 结束时间
	 * @param change_codes 异动code
	 * @return
	 */
	public int queryPunishAgainCount(int schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList,
			String start_date, String end_date, String change_codes);
}