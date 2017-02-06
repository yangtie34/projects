package cn.gilight.dmm.xg.service;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.framework.page.Page;

/**
 * 学籍异动
 * 
 * @author xuebl
 * @date 2016年5月3日 下午5:17:14
 */
public interface ChangeService {


	/**
	 * 获取学生异动概况
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getStuChangeAbstract(List<AdvancedParam> advancedParamList);

	/**
	 * 获取学生异动概况（异动人数、不良异动人数、占比）
	 * @param deptList 数据权限
	 * @param change_bad_codes 不良异动代码
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getStuChangeAbstract(List<String> deptList, String change_bad_codes, List<AdvancedParam> advancedParamList);
	
	/**
	 * 学籍异动 分布
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getStuChange(List<AdvancedParam> advancedParamList);
	
	/**
	 * 学籍异动 分布
	 * @param deptList 数据权限
	 * @param change_codes 异动类型codes
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getStuChange(List<String> deptList, String change_codes, List<AdvancedParam> advancedParamList);

	/**
	 * 学籍异动 分布
	 * @param deptList 数据权限
	 * @param change_codes 异动类型codes
	 * @param advancedParamList 高级查询参数
	 * @param schoolYear 学年
	 * @param start_date 开始时间
	 * @param end_date 结束时间
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getStuChange(List<String> deptList, String change_codes, List<AdvancedParam> advancedParamList, 
			int schoolYear, String start_date, String end_date);
	
	/**
	 * 各机构学籍异动变化
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getDeptStuChange(List<AdvancedParam> advancedParamList);
	/**
	 * 各机构学籍异动变化
	 * @param deptList 数据权限
	 * @param change_codes 异动类型codes
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getDeptStuChange(List<String> deptList, String change_codes, List<AdvancedParam> advancedParamList);
	
	/**
	 * 学籍异动月份分布
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getStuChangeMonth(List<AdvancedParam> advancedParamList);
	/**
	 * 学籍异动月份分布
	 * @param deptList 数据权限
	 * @param change_codes 异动类型codes
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getStuChangeMonth(List<String> deptList, String change_codes, List<AdvancedParam> advancedParamList);
	
	/**
	 * 学籍异动年度分布
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getStuChangeYear(List<AdvancedParam> advancedParamList);
	/**
	 * 学籍异动年度分布
	 * @param deptList 数据权限
	 * @param change_codes 异动类型codes
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getStuChangeYear(List<String> deptList, String change_codes, List<AdvancedParam> advancedParamList);

	/**
	 * 学籍异动历史数据
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getStuChangeHistory(List<AdvancedParam> advancedParamList);
	
	/**
	 * 学籍异动历史数据
	 * @param deptList 数据权限
	 * @param change_codes 异动类型codes
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getStuChangeHistory(List<String> deptList, String change_codes, List<AdvancedParam> advancedParamList);

	/**
	 * 学籍异动下钻
	 * @param deptList 数据权限
	 * @param change_codes 异动类型codes
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object> 
	 */
	public Map<String ,Object> getStuChangeDetail(List<AdvancedParam> advancedParamList,
			Page page, Map<String, Object> keyValue, List<String> fields);
	
	/**
	 * 导出用-学生下钻明细
	 * @param advancedParamList 高级查询参数
	 * @param keyValue 其他参数
	 * @param fields 字段名
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String,Object>> getStuDetailList(List<AdvancedParam> advancedParamList, Page page,
			Map<String, Object> keyValue, List<String> fields);
	/**
	 * 判断是否为全部权限
	 * @return
	 */
	public boolean isAll(List<AdvancedParam> advancedParamList);
	/**
	 * 转专业学生
	 * @param advancedParamList
	 * @return
	 */
	
	public Map<String, Object> getStuChangeByDeptOrMajor(List<AdvancedParam> advancedParamList,String tag);
	/**
	 * 转专业学生
	 * @param deptList 权限
	 * @param change_codes 转专业code
	 * @param advancedParamList 高级查询参数
	 * @return
	 */
	public  Map<String ,Object> getStuChangeByDeptOrMajor(List<String> deptList, String tag, List<AdvancedParam> advancedParamList);
}
