package cn.gilight.dmm.xg.service;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.framework.page.Page;

/**
 * 学籍异动
 * 
 * @author xuebl
 * @date 2016年5月10日 下午6:09:39
 */
public interface ChangeBadService {
	
	/**
	 * 获取不良异动列表
	 * @return Map<String,Object>
	 */
	Map<String, Object> getChangeBadList();
	
	/**
	 * 获取学生异动概况
	 * @param change_bad_codes 不良异动代码
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getStuChangeAbstract(String change_bad_codes, List<AdvancedParam> advancedParamList);
	
	/**
	 * 学籍异动 分布
	 * @param change_codes 异动类型codes
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getStuChange(String change_codes, List<AdvancedParam> advancedParamList);

	/**
	 * 各机构学籍异动变化
	 * @param change_codes 异动类型codes
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getDeptStuChange(String change_codes, List<AdvancedParam> advancedParamList);

	/**
	 * 学籍异动月份分布
	 * @param change_codes 异动类型codes
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getStuChangeMonth(String change_codes, List<AdvancedParam> advancedParamList);

	/**
	 * 学籍异动年度分布
	 * @param change_codes 异动类型codes
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getStuChangeYear(String change_codes, List<AdvancedParam> advancedParamList);

	/**
	 * 学籍异动历史变化
	 * @param change_codes 异动类型codes
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getStuChangeHistory(String change_codes, List<AdvancedParam> advancedParamList);
	
	
	/**
	 * 学籍不良异动下钻
	 * @param deptList 数据权限
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object> 
	 */
	public Map<String ,Object> getStuChangeBadDetail(List<AdvancedParam> advancedParamList,
			Page page, Map<String, Object> keyValue, List<String> fields);
	/**
	 * 学籍不良异动信息 导出
	 * @param deptList 数据权限
	 * @param advancedParamList 高级查询参数
	 * @return List<Map<String,Object>> 
	 */
	public List<Map<String,Object>> getStuDetailList(List<AdvancedParam> advancedParamList,Page page, 
			Map<String, Object> keyValue, List<String> fields);
	
	/**
	 * 获取学籍特殊异动二次比例
	 * @param change_bad_codes 异动code
	 * @param advancedParamList 高级参数
	 * @return map
	 */
	public Map<String, Object> getChangeAgain(String change_bad_codes, List<AdvancedParam> advancedParamList);
}