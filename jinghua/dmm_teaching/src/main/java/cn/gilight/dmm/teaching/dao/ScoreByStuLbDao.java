package cn.gilight.dmm.teaching.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;

public interface ScoreByStuLbDao {

	/**
	 * 获取课程类型集合
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param deptList 标准权限
	 * @param advancedList 高级查询参数
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getCourseTypeList(String schoolYear,
			String termCode, List<String> deptList,
			List<AdvancedParam> advancedList);
	/**
	 * 获取课程类别、课程性质、课程属性集合
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param codeType 类型 eg:'COURSE_CATEGORY' 课程类别,'COURSE_NATURE_CODE' 课程性质,'COURSE_ATTR_CODE'课程属性
	 * @param deptList 标准权限
	 * @param advancedList 高级查询参数
	 * @return List<Map<String, Object>> 
	 */
	public List<Map<String, Object>> getCourseCategoryList(String schoolYear,
			String termCode, String codeType, List<String> deptList,
			List<AdvancedParam> advancedList);
	/**
	 * 获取课程集合
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param type 课程类型
	 * @param category 课程类别
	 * @param attr 课程属性
	 * @param nature 课程性质
	 * @param deptList 标准权限
	 * @param advancedList 高级查询参数
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getCourseList(String schoolYear, String termCode,
			String type, String category, String attr, String nature,
			List<String> deptList, List<AdvancedParam> advancedList);
	/**
	 * 根据生源地id查询它的下层地区
	 * @param id 生源地id
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getNextById(String id);
	/**
	 * 获取各成绩指标的值
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param type 课程类型
	 * @param category 课程类别
	 * @param attr 课程属性
	 * @param nature 课程性质
	 * @param course 课程id
	 * @param tag 成绩指标
	 * @param deptList 标准权限
	 * @param advancedList 高级查询参数
	 * @return Double
	 */
	public Double getValue(String schoolYear, String termCode, String type,
			String category, String attr, String nature, String course,
			String tag, List<String> deptList, List<AdvancedParam> advancedList);
	/**
	 * 获取成绩集合
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param type 课程类型
	 * @param category 课程类别
	 * @param attr 课程属性
	 * @param nature 课程性质
	 * @param course 课程id
	 * @param deptList 标准权限
	 * @param advancedList 高级查询参数
	 * @return List<Double>
	 */
	public List<Double> getScoreList(String schoolYear, String termCode, String type,
			String category, String attr, String nature, String course,
			List<String> deptList, List<AdvancedParam> advancedList);

}
