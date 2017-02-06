package cn.gilight.dmm.xg.service;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.framework.page.Page;

/**
 * 奖学金
 * 
 * @author xuebl
 * @date 2016年5月19日 下午6:03:07
 */
public interface ScholarshipService {

	/**
	 * 查询标准代码
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
	 * 摘要
	 * @param deptList 数据权限
	 * @param advancedParamList 高级查询参数
	 * @param schoolYear 学年
	 * @param edu 学历
	 * @param Constant.JCZD_Table 奖惩助贷
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getAbstract(List<String> deptList, List<AdvancedParam> advancedParamList, 
			String schoolYear, String edu, Constant.JCZD_Table table);
	
	/**
	 * 查询奖学金类型数据
	 * @param schoolYear 学年
	 * @param edu 学历
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> queryTypeList(String schoolYear, String edu, List<AdvancedParam> advancedParamList);
	
	/**
	 * 查询奖学金类型数据
	 * @param deptList 数据权限
	 * @param advancedParamList 高级查询参数
	 * @param schoolYear 学年
	 * @param edu 学历
	 * @param Constant.JCZD_Table 奖惩助贷
	 * @return Map<String, Object>
	 */
	public Map<String, Object> queryTypeList(List<String> deptList, List<AdvancedParam> advancedParamList, 
			String schoolYear, String edu, Constant.JCZD_Table table);

	/**
	 * 各组织机构奖学金数据
	 * @param schoolYear 学年
	 * @param edu 学历
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryDeptDataList(String schoolYear, String edu, List<AdvancedParam> advancedParamList);
	
	/**
	 * 各组织机构奖学金数据
	 * @param deptList 数据权限
	 * @param advancedParamList 高级查询参数
	 * @param schoolYear 学年
	 * @param edu 学历
	 * @param Constant.JCZD_Table 奖惩助贷
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryDeptDataList(List<String> deptList, List<AdvancedParam> advancedParamList, String schoolYear, String edu, Constant.JCZD_Table table);

	/**
	 * 奖学金学生行为
	 * @param schoolYear 学年
	 * @param edu 学历
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getBehavior(String schoolYear, String edu, List<AdvancedParam> advancedParamList);
	
	/**
	 * 奖学金学生行为
	 * @param deptList 数据权限
	 * @param advancedParamList 高级查询参数
	 * @param schoolYear 学年
	 * @param edu 学历
	 * @param Constant.JCZD_Table 奖惩助贷
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getBehavior(List<String> deptList, List<AdvancedParam> advancedParamList, 
			String schoolYear, String edu, Constant.JCZD_Table table);

	/**
	 * 各年级奖学金覆盖率
	 * @param schoolYear 学年
	 * @param edu 学历
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getCoverageGrade(String schoolYear, String edu, List<AdvancedParam> advancedParamList);
	
	/**
	 * 各年级奖学金覆盖率
	 * @param deptList 数据权限
	 * @param advancedParamList 高级查询参数
	 * @param schoolYear 学年
	 * @param edu 学历
	 * @param Constant.JCZD_Table 奖惩助贷
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getCoverageGrade(List<String> deptList, List<AdvancedParam> advancedParamList, 
			String schoolYear, String edu, Constant.JCZD_Table table);

	/**
	 * 各机构奖学金覆盖率
	 * @param schoolYear 学年
	 * @param edu 学历
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getCoverageDept(String schoolYear, String edu, List<AdvancedParam> advancedParamList);
	
	/**
	 * 各机构奖学金覆盖率
	 * @param deptList 数据权限
	 * @param advancedParamList 高级查询参数
	 * @param schoolYear 学年
	 * @param edu 学历
	 * @param Constant.JCZD_Table 奖惩助贷
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getCoverageDept(List<String> deptList, List<AdvancedParam> advancedParamList, 
			String schoolYear, String edu, Constant.JCZD_Table table);

	/**
	 * 各年级 各性别 奖学金覆盖率
	 * @param schoolYear 学年
	 * @param edu 学历
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getCoverageGradeSex(String schoolYear, String edu, List<AdvancedParam> advancedParamList);

	/**
	 * 奖学金排行榜
	 * @param schoolYear 学年
	 * @param edu 学历
	 * @param advancedParamList 高级查询参数
	 * @param desc_column 倒序字段
	 * @param index 下标
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getTop(String schoolYear, String edu, List<AdvancedParam> advancedParamList, String desc_column, Integer index);
	
	/**
	 * 奖学金历史
	 * @param schoolYear 学年
	 * @param edu 学历
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getHistory(String schoolYear, String edu, List<AdvancedParam> advancedParamList);
	
	/**
	 * 奖学金历史
	 * @param deptList 数据权限
	 * @param schoolYear 学年
	 * @param edu 学历
	 * @param advancedParamList 高级查询参数
	 * @param Constant.JCZD_Table 奖惩助贷
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getHistory(List<String> deptList, List<AdvancedParam> advancedParamList, 
			String schoolYear, String edu, Constant.JCZD_Table table);
	
	/**
	 * 获得奖学金学生 明细下钻
	 * @param deptList 数据权限
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object> 
	 */
	public Map<String ,Object> getStuScholarshipDetail(List<AdvancedParam> advancedParamList,Page page, Map<String, Object> keyValue, List<String> fields);
	/**
	 * 获得奖学金学生 明细下钻
	 * @param deptList 数据权限
	 * @param advancedParamList 高级查询参数
	 * @return Map<String,Object> 
	 */
	public Map<String ,Object> getStuScholarshipDetail(List<AdvancedParam> advancedParamList,
			Page page, Map<String, Object> keyValue, List<String> fields,Constant.JCZD_Table table);
	
	/**
	 * 学生明细下载
	 * @param advancedParamList 高级查询参数
	 * @param keyValue 详细参数
	 * @param fields 查询字段
	 * @return
	 */
	public List<Map<String, Object>> getStuDetailList(List<AdvancedParam> advancedParamList,Page page,
			Map<String, Object> keyValue, List<String> fields);
	/**
	 * 学生明细下载
	 * @param advancedParamList 高级查询参数
	 * @param keyValue 详细参数
	 * @param fields 查询字段
	 * @return
	 */
	public List<Map<String,Object>> getStuDetailList(List<AdvancedParam> advancedParamList,Page page,
			 Map<String, Object> keyValue, List<String> fields,Constant.JCZD_Table table);
		
	
	
}