package cn.gilight.dmm.xg.service;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.framework.page.Page;

public interface StuEnrollService {
	/**
	 * 查询当前部门下在籍学生数
	 * @param stuAdvancedList 高级查询条件
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getStuCountByDept(List<AdvancedParam> advancedParamList);
	/**
	 * 查询各性别在籍学生数
	 * @param stuAdvancedList 高级查询条件
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getStuCountBySex(List<AdvancedParam> advancedParamList);
	/**
	 * 查询各年龄段在籍学生数
	 * @param stuAdvancedList 高级查询条件
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getStuCountByAge(List<AdvancedParam> advancedParamList);
	/**
	 * 查询各部门在籍生人数对比
	 * @param stuAdvancedList 高级查询条件
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getContrastByDept(List<AdvancedParam> advancedParamList);
	/**
	 * 查询各政治面貌在籍学生数
	 * @param stuAdvancedList 高级查询条件
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getContrastByPolitics(List<AdvancedParam> advancedParamList);
	/**
	 * 查询各地区在籍学生数
	 * @param stuAdvancedList 高级查询条件
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getStuFrom(List<AdvancedParam> advancedParamList);
	/**
	 * 查询本省外省历年学生人数变化趋势
	 * @param stuAdvancedList 高级查询条件
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getStuFromLine(List<AdvancedParam> advancedParamList);
	/**
	 * 查询历年学生总人数变化趋势
	 * @param stuAdvancedList 高级查询条件
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getStuCountLine(List<AdvancedParam> advancedParamList);
	/**
	 * 查询历年汉族和少数民族学生人数变化趋势
	 * @param stuAdvancedList 高级查询条件
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getStuNationLine(List<AdvancedParam> advancedParamList);
	/**
	 * 查询历年男女学生人数变化趋势
	 * @param stuAdvancedList 高级查询条件
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getStuSexLine(List<AdvancedParam> advancedParamList);
	/**
	 * 查询历年学生入学时的平均年龄
     * @param stuAdvancedList 高级查询条件
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getStuAgeLine(List<AdvancedParam> advancedParamList);
	/**
	 * 查询历年各政治面貌的学生人数变化趋势
     * @param stuAdvancedList 高级查询条件
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getPoliticsLine(List<AdvancedParam> advancedParamList);
	/**
	 * 查询历年各部门学生人数变化趋势
     * @param stuAdvancedList 高级查询条件
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getDeptLine(List<AdvancedParam> advancedParamList);
	/**
	 * 查询历年各学习方式学生人数变化趋势
     * @param stuAdvancedList 高级查询条件
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getStyleLine(List<AdvancedParam> advancedParamList);
	/**
	 * 转换数据格式
     * @param result 结果集
     * @param a x轴
     * @param b y轴
     * @param c 图例code
     * @param d x轴code
	 * @return Map<String, Object>
	 */
	public Map<String, Object> shiftList(List<Map<String, Object>> result, String a,
			String b,String c,String d);
	/**
	 * 查询历年各学习形式学生人数变化趋势
     * @param stuAdvancedList 高级查询条件
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getFormLine(List<AdvancedParam> advancedParamList);
	/**
	 * 分页数据-学生明细信息
	 * @param advancedParamList 高级查询参数
	 * @param page 分页参数
	 * @param keyValue 其他参数
	 * @param fields 字段名
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getStuDetail(List<AdvancedParam> advancedParamList,
			Page page, Map<String, Object> keyValue, List<String> fields);
	/**
	 * 导出数据-学生明细信息
	 * @param advancedParamList 高级查询参数
	 * @param keyValue 其他参数
	 * @param fields 字段名
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getStuDetailList(
			List<AdvancedParam> advancedParamList,
			Map<String, Object> keyValue, List<String> fields);
	/**
	 * 根据高级查询参数获取研究生人数
	 * @param advancedParamList 高级查询参数
	 * @return  Map<String, Object> 
	 */
	public Map<String, Object> getGraduateStuCount(
			List<AdvancedParam> advancedParamList);
	/**
	 * 获取博士生总人数 以及 各年级的博士生人数
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object> 
	 */
	public Map<String, Object> getBsCount(List<AdvancedParam> advancedParamList);

}
