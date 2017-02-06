package cn.gilight.dmm.xg.service;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.framework.page.Page;

/**
 * 助学金
 * 
 * @author xuebl
 * @date 2016年5月27日 下午3:06:00
 */
public interface SubsidyService {

	/**
	 * 获取标准代码
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getBzdm();
	
	/**
	 * 摘要
	 * @param schoolYear 学年
	 * @param edu 学历
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getAbstract(String schoolYear, String edu, List<AdvancedParam> advancedParamList);
	
	/**
	 * 查询奖学金类型数据
	 * @param schoolYear 学年
	 * @param edu 学历
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> queryTypeList(String schoolYear, String edu, List<AdvancedParam> advancedParamList);

	/**
	 * 各组织机构奖学金数据
	 * @param schoolYear 学年
	 * @param edu 学历
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryDeptDataList(String schoolYear, String edu, List<AdvancedParam> advancedParamList);

	/**
	 * 奖学金学生行为
	 * @param schoolYear 学年
	 * @param edu 学历
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getBehavior(String schoolYear, String edu, List<AdvancedParam> advancedParamList);

	/**
	 * 各年级奖学金覆盖率
	 * @param schoolYear 学年
	 * @param edu 学历
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getCoverageGrade(String schoolYear, String edu, List<AdvancedParam> advancedParamList);

	/**
	 * 各机构奖学金覆盖率
	 * @param schoolYear 学年
	 * @param edu 学历
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getCoverageDept(String schoolYear, String edu, List<AdvancedParam> advancedParamList);

	/**
	 * 奖学金历史
	 * @param schoolYear 学年
	 * @param edu 学历
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getHistory(String schoolYear, String edu, List<AdvancedParam> advancedParamList);
	
	/**
	 * 获得助学金学生明细
	 * @param advancedParamList 高级查询参数
	 * @param page 分页参数
	 * @param keyValues 详细查询参数
	 * @param fields 查询字段
	 * @return
	 */
	public Map<String,Object> getSubsidyStuDetail(List<AdvancedParam> advancedParamList,Page page,Map<String,Object> keyValue,List<String> fields);
	/**
	 * 学生明细下载
	 * @param advancedParamList 高级查询参数
	 * @param keyValue 详细参数
	 * @param fields 查询字段
	 * @return
	 */
	public List<Map<String,Object>> getStuDetailList(List<AdvancedParam> advancedParamList,Page page,
			 Map<String, Object> keyValue, List<String> fields);
}