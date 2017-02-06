package cn.gilight.dmm.xg.service;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.framework.page.Page;

/**
 * 学生工作者接口
 * 
 * @author xuebl
 * @date 2016年4月19日 下午3:28:44
 */
public interface WorkerService {

	
	/**
	 * 获取学生工作者基础数据
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getWorker(List<AdvancedParam> advancedParamList);
	
	/**
	 * 获取专职辅导员与学生比
	 * @param list
	 * @param advancedParamList 高级查询参数
	 * @return Integer
	 */
//	public int[] getInstructorsStuRatio(List<Map<String, Object>> list, String pid);
	
	/**
	 * 获取学生工作者人员分布
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getWorkerDistribute(List<AdvancedParam> advancedParamList);
	
	/**
	 * 获取专职辅导员人员分布
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getInstructorsDistribute(List<AdvancedParam> advancedParamList);
	
	/**
	 * 获取各组织机构专职辅导员学生比
	 * @param advancedParamList 高级查询参数
	 * @param schoolYear 学年
	 * @return List<Map<String ,Object>>
	 */
	public List<Map<String ,Object>> getOrganizationInstructorsStuRatio(List<AdvancedParam> advancedParamList, Integer schoolYear);

	/**
	 * 分页数据-学生工作者下钻明细
	 * @param advancedParamList 高级查询参数
	 * @param page 分页参数
	 * @param keyValue 其他参数
	 * @param fields 字段名
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getTeaDetail(List<AdvancedParam> advancedParamList, Page page,
			Map<String, Object> keyValue, List<String> fields);

	/**
	 * 导出用-学生工作者下钻明细
	 * @param advancedParamList 高级查询参数
	 * @param keyValue 其他参数
	 * @param fields 字段名
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getTeaDetailList(List<AdvancedParam> advancedParamList, Page page,
			Map<String, Object> keyValue, List<String> fields);
}
