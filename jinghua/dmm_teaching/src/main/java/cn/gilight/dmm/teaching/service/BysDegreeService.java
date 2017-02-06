package cn.gilight.dmm.teaching.service;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;

public interface BysDegreeService {
    /**
     * 根据高级查询参数，学年获得已毕业，已授予学位的数据
     * @param advancedList 高级查询参数
     * @param schoolYear 学年
     * @return Map<String, Object>
     */
	public Map<String, Object> getTopData(List<AdvancedParam> advancedList,
			String schoolYear);
    /**
     * 获取表格数据
     * @param advancedList 高级查询参数
     * @param schoolYear 学年
     * @param Lx 类型
     * @param order 排序字段
     * @param asc 正序倒序
     * @return Map<String, Object>
     */
	public Map<String, Object> getTableData(List<AdvancedParam> advancedList,
			String schoolYear, String Lx, String order, Boolean asc,int pagesize,int index);
	/**
	 * 各部门对比
	 * @param advancedList 高级查询参数
	 * @param schoolYear 学年
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getLvByDept(List<AdvancedParam> advancedList,
			String schoolYear);
	/**
	 * 获取毕业生成绩
	 * @param advancedList 高级查询参数
	 * @param schoolYear 学年
	 * @param Lx 指标类型
	 * @param type 成绩类型
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getBysScore(List<AdvancedParam> advancedList,
			String schoolYear, String Lx, String type);
	/**
	 * 获取毕业生gpa分组
	 * @param advancedList 高级查询参数
	 * @param schoolYear 学年
	 * @param type 成绩类型
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getBysGpa(List<AdvancedParam> advancedList,
			String schoolYear, String type);
	/**
	 * 获取表格标题
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getThList();
	/**
	 * 根据高级查询参数获取部门结果
	 * @param list 部门集合
	 * @param schoolYear 学年
	 * @param advancedList 高级查询参数
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getListByDeptList(List<Map<String, Object>> list,
			String schoolYear, List<AdvancedParam> advancedList);
	/**
	 * 根据高级查询参数获取学科结果
	 * @param list 学科集合
	 * @param schoolYear 学年
	 * @param advancedList 高级查询参数
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getListBySubjectList(
			List<Map<String, Object>> list, String schoolYear,
			List<AdvancedParam> advancedList);
	/**
	 * 获取历年毕业和学位授予率
	 * @param advancedParamList 高级查询参数
	 * @param keyValue 其他参数
	 * @return
	 */
	public List<Map<String, Object>> getBySyLvList(
			List<AdvancedParam> advancedParamList,
			Map<String, Object> keyValue);

}
