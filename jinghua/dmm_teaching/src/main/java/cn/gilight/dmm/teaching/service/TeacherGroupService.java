package cn.gilight.dmm.teaching.service;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.framework.page.Page;

/**
 * 
 * 
 * @author xuebl
 * @date 2016年7月12日 上午10:55:41
 */
public interface TeacherGroupService {

	/**
	 * 获取摘要数据
	 * @param advancedParamList
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getAbstract(List<AdvancedParam> advancedParamList);

	/**
	 * 获取分布数据
	 * @param advancedParamList
	 * @param lx 教职工类型
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getDistribution(List<AdvancedParam> advancedParamList, String lx);

	/**
	 * 获取职称历史
	 * @param advancedParamList
	 * @param lx
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getHistoryTechnical(List<AdvancedParam> advancedParamList, String lx);
	/**
	 * 获取学位历史
	 * @param advancedParamList
	 * @param lx
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getHistoryDegree(List<AdvancedParam> advancedParamList, String lx);
	/**
	 * 获取学历历史
	 * @param advancedParamList
	 * @param lx
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getHistoryEdu(List<AdvancedParam> advancedParamList, String lx);
	
	/**
	 * 学科师资分布
	 * @param advancedParamList
	 * @param lx 教职工类型
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getSubjectGroup(List<AdvancedParam> advancedParamList, String lx);
	
	/**
	 * 年龄分组
	 * @param advancedParamList
	 * @param lx 教职工类型
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getAgeGroup(List<AdvancedParam> advancedParamList, String lx);

	/**
	 * 教龄分组
	 * @param advancedParamList
	 * @param lx 教职工类型
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getSchoolAgeGroup(List<AdvancedParam> advancedParamList, String lx);

	/**
	 * 获取各机构数据
	 * @param advancedParamList
	 * @param lx 教职工类型
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getDeptList(List<AdvancedParam> advancedParamList, String lx);

	/**
	 * 师资力量历史
	 * @param advancedParamList
	 * @param lx 教职工类型
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getHistoryList(List<AdvancedParam> advancedParamList, String lx);

	/**
	 * 各机构生师比
	 * @param advancedParamList
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getDeptScaleList(List<AdvancedParam> advancedParamList);
	
	/**
	 * 教职工 详情
	 * @param advancedParamList 高级查询参数
	 * @param page 分页参数
	 * @param keyValue 其他参数
	 * @param fields 查询字段
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getTeaDetail(List<AdvancedParam> advancedParamList, Page page, 
			Map<String, Object> keyValue, List<String> fields);

	/**
	 * 教职工详情（全部）
	 * @param advancedParamList 高级查询参数
	 * @param keyValue 其他参数
	 * @param fields 查询字段
	 * @return String
	 */
	public List<Map<String, Object>> getTeaDetailAll(List<AdvancedParam> advancedParamList, Page page,
			Map<String, Object> keyValue, List<String> fields);
	
}