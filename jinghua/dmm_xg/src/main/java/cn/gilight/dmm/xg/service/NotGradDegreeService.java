package cn.gilight.dmm.xg.service;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.framework.page.Page;

public interface NotGradDegreeService {
	
	/**
	 * 得到无法毕业或者无学位证类型
	 * @return
	 */
	List<Map<String, Object>> getNoDegreeType();
	/**
	 * 标准代码-学工口-学生无法毕业/无学位证无学位证、无法毕业）
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryNoDegreeList();
	/**
	 * 得到无法毕业和无学位证的人数
	 * @param schoolYear
	 * @param id
	 * @param pid
	 * @return
	 */
	Map<String, Object> queryXwInfo(List<AdvancedParam> advancedList);//List<String> deptList
	Map<String, Object> queryXwInfo(List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 得到学生分布和比例
	 * @param schoolYear
	 * @param id
	 * @param pid
	 * @return
	 */
	Map<String, Object> queryXwfbAndRatio(String fb,List<AdvancedParam> advancedList);
	/**
	 * 无学位学生学科分布
	 * @param deptList
	 * @return
	 */
	List<Map<String, Object>> queryXkfb(List<AdvancedParam> advancedList);
	List<Map<String, Object>> queryXkfb(List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 无学位学生年级分布
	 * @param deptList
	 * @return
	 */
	List<Map<String, Object>> queryNjfb(List<AdvancedParam> advancedList);
	List<Map<String, Object>> queryNjfb(List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 无学位学生性别分布
	 * @param deptList
	 * @return
	 */
	List<Map<String, Object>> queryXbfb(List<AdvancedParam> advancedList);
	List<Map<String, Object>> queryXbfb(List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 无学位学生院系分布
	 * @param deptList
	 * @return
	 */
	List<Map<String, Object>> queryYyfb(List<AdvancedParam> advancedList);
	List<Map<String, Object>> queryYyfb(List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 最近几年无学位学生原因分布
	 * @param deptList
	 * @return
	 */
	Map<String, Object> queryStatefbByYear(List<AdvancedParam> advancedList);
	Map<String, Object> queryStatefbByYear(List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 最近几年无学位学生年级分布
	 * @param deptList
	 * @return
	 */
	Map<String, Object> queryNjfbByYear(List<AdvancedParam> advancedList);
	Map<String, Object> queryNjfbByYear(List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 最近几年无学位学生学科分布
	 * @param deptList
	 * @return
	 */
	Map<String, Object> queryXkfbByYear(List<AdvancedParam> advancedList);
	Map<String, Object> queryXkfbByYear(List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 最近几年无学位学生性别分布
	 * @param deptList
	 * @return
	 */
	Map<String, Object> queryXbfbByYear(List<AdvancedParam> advancedList);
	Map<String, Object> queryXbfbByYear(List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 学生 详情
	 * @param advancedParamList 高级查询参数
	 * @param page 分页参数
	 * @param keyValue 其他参数
	 * @param fields 查询字段
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getTeaDetail(List<AdvancedParam> advancedParamList, Page page, 
			Map<String, Object> keyValue, List<String> fields);

	/**
	 * 学生详情（全部）
	 * @param advancedParamList 高级查询参数
	 * @param keyValue 其他参数
	 * @param fields 查询字段
	 * @return String
	 */
	public List<Map<String, Object>> getStuDetailAll(List<AdvancedParam> advancedParamList,Page page, Map<String, Object> keyValue, List<String> fields);
}
