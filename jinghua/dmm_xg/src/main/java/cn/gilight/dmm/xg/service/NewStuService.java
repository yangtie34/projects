package cn.gilight.dmm.xg.service;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.framework.page.Page;

public interface NewStuService {
    /**
     * 获取各部门各就读学历的报道和未报到的新生人数
     * @param pid
     * @return
     */
	public Map<String, Object> getIsRegisterCount(List<AdvancedParam> stuAdvancedList);
	/**
	 * 获取各部门历年的的报到人数和报到率
	 * @param pid
	 * @return
	 */
	public Map<String, Object> getCountAndLv(List<AdvancedParam> stuAdvancedList);
	/**
	 * 获取各个部门近五年的平均报到率
	 * @param pid
	 * @return
	 */
	public Map<String, Object> getDeptAvgLv(List<AdvancedParam> stuAdvancedList);
	/**
	 * 获取贫困生新生人数变化
	 * @param pid
	 * @return
	 */
	public Map<String, Object> getPoorCount(List<AdvancedParam> stuAdvancedList,String year);
	/**
	 * 获取助学贷款的新生人数变化
	 * @param pid
	 * @return
	 */
	public Map<String, Object> getLoanCount(List<AdvancedParam> stuAdvancedList,String year);
	/**
	 * 获取学费减免的新生人数
	 * @param pid
	 * @return
	 */
	public Map<String, Object> getJmCount(List<AdvancedParam> stuAdvancedList,String year);
	/**
	 * 获取学费减免的新生中各户口类型的人数
	 * @param pid
	 * @param year
	 * @return
	 */
//	public Map<String, Object> getJmPie(List<AdvancedParam> stuAdvancedList, String year);
	/**
	 * 获取助学贷款的新生中各户口类型的人数
	 * @param pid
	 * @param year
	 * @return
	 */
//	public Map<String, Object> getLoanPie(List<AdvancedParam> stuAdvancedList, String year);
	/**
	 * 获取贫困生的新生中各户口类型的人数
	 * @param pid
	 * @param year
	 * @return
	 */
//	public Map<String, Object> getPoorPie(List<AdvancedParam> stuAdvancedList, String year);
	/**
	 * 获取学生明细
	 * @param advancedParamList 高级查询参数
	 * @param page 分页参数
	 * @param keyValue 其他参数
	 * @param fields 字段名
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getStuDetail(List<AdvancedParam> advancedParamList,
			Page page, Map<String, Object> keyValue, List<String> fields);
	/**
	 * 获取学生明细（导出用）
	 * @param advancedParamList 高级查询参数
	 * @param keyValue 其他参数
	 * @param fields 字段名
	 * @return Map<String,Object>
	 */
	public List<Map<String, Object>> getStuDetailList(
			List<AdvancedParam> advancedParamList,
			Map<String, Object> keyValue, List<String> fields);
}
