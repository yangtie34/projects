package cn.gilight.dmm.teaching.service;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;

public interface ScoreByStuLbService {

	/**
	 * 查询学年学期
	 * @return
	 */
	public List<Map<String, Object>> getXnXqList();
    /**
     * 获取课程类型集合
     * @param schoolYear 学年
     * @param termCode 学期
     * @param advancedList 高级查询参数
     * @return List<Map<String, Object>>
     */
	public List<Map<String, Object>> getCourseTypeList(String schoolYear,
			String termCode, List<AdvancedParam> advancedList);
	 /**
     * 获取课程类别集合
     * @param schoolYear 学年
     * @param termCode 学期
     * @param advancedList 高级查询参数
     * @return List<Map<String, Object>>
     */
	public List<Map<String, Object>> getCourseCategoryList(String schoolYear,
			String termCode, List<AdvancedParam> advancedList);
	 /**
     * 获取课程属性集合
     * @param schoolYear 学年
     * @param termCode 学期
     * @param advancedList 高级查询参数
     * @return List<Map<String, Object>>
     */
	public List<Map<String, Object>> getCourseAttrList(String schoolYear,
			String termCode, List<AdvancedParam> advancedList);
	 /**
     * 获取课程性质集合
     * @param schoolYear 学年
     * @param termCode 学期
     * @param advancedList 高级查询参数
     * @return List<Map<String, Object>>
     */
	public List<Map<String, Object>> getCourseNatureList(String schoolYear,
			String termCode, List<AdvancedParam> advancedList);
	/**
	 * 查询课程集合
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param type 课程类型
	 * @param category 课程类别
	 * @param attr 课程属性
	 * @param nature 课程性质
	 * @param advancedList 高级查询参数
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getCourseList(String schoolYear, String termCode,
			String type, String category, String attr, String nature,
			List<AdvancedParam> advancedList);
	/**
	 * 获取成绩统计指标
	 * @return
	 */
	public List<Map<String, Object>> getTargetList();
	/**
	 * 获取各生源地成绩对比
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param type 课程类型
	 * @param category 课程类别
	 * @param attr 课程属性
	 * @param nature 课程性质
	 * @param course 课程id
	 * @param tag 成绩指标
	 * @param advancedList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getScoreByOriginList(String schoolYear,
			String termCode, String type, String category, String attr,
			String nature, String course, String tag,
			List<AdvancedParam> advancedList);
	/**
	 * 获取生源地成绩分组
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param type 课程类型
	 * @param category 课程类别
	 * @param attr 课程属性
	 * @param nature 课程性质
	 * @param course 课程id
	 * @param originId 生源地id
	 * @param advancedList 高级查询参数
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getScoreFbByOrigin(String schoolYear,
			String termCode, String type, String category, String attr,
			String nature, String course, String originId,
			List<AdvancedParam> advancedList);
	/**
	 * 获取民族成绩分组
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param type 课程类型
	 * @param category 课程类别
	 * @param attr 课程属性
	 * @param nature 课程性质
	 * @param course 课程id
	 * @param originId 生源地id
	 * @param advancedList 高级查询参数
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getScoreFbByNation(String schoolYear,
			String termCode, String type, String category, String attr,
			String nature, String course, String nationId,
			List<AdvancedParam> advancedList);
	/**
	 * 获取政治面貌成绩分组
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param type 课程类型
	 * @param category 课程类别
	 * @param attr 课程属性
	 * @param nature 课程性质
	 * @param course 课程id
	 * @param originId 生源地id
	 * @param advancedList 高级查询参数
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getScoreFbByZzmm(String schoolYear,
			String termCode, String type, String category, String attr,
			String nature, String course, String nationId,
			List<AdvancedParam> advancedList);
	/**
	 * 获取各民族成绩对比
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param type 课程类型
	 * @param category 课程类别
	 * @param attr 课程属性
	 * @param nature 课程性质
	 * @param course 课程id
	 * @param tag 成绩指标
	 * @param advancedList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getScoreByNationList(String schoolYear,
			String termCode, String type, String category, String attr,
			String nature, String course, String tag,
			List<AdvancedParam> advancedList);
	/**
	 * 获取各政治面貌成绩对比
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param type 课程类型
	 * @param category 课程类别
	 * @param attr 课程属性
	 * @param nature 课程性质
	 * @param course 课程id
	 * @param tag 成绩指标
	 * @param advancedList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getScoreByZzmmList(String schoolYear,
			String termCode, String type, String category, String attr,
			String nature, String course, String tag,
			List<AdvancedParam> advancedList);


}
