package cn.gilight.dmm.teaching.service;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.framework.page.Page;

public interface ScorePredictTeaService {
    /**
     * 获取学历集合
     * @return List<Map<String, Object>>
     */
	public List<Map<String, Object>> getEduList();
    /**
     * 获取时间选择集合
     * @return List<Map<String, Object>> 
     */
	public List<Map<String, Object>> getTimeList();
	/**
	 * 获取某学年学期我所带的课程集合
	 * @param advancedList 高级查询参数
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getCourseList(List<AdvancedParam> advancedList,
			String schoolYear, String termCode,String edu);
	/**
	 * 获取某学年学期我给多少教学班上这门课
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param courseId 课程id
	 * @return  Map<String, Object>
	 */
	public Map<String, Object> getTeachClassAndStuCount(String schoolYear,
			String termCode, String courseId,String edu,List<AdvancedParam> advancedList);
	/**
	 * 获取某学年某学期我所带的课程的课程性质集合
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param courseId 课程id
	 * @return List<Map<String,Object>
	 */
	public List<Map<String, Object>> getCourseNatureList(String schoolYear,
			String termCode, String courseId,String edu,List<AdvancedParam> advancedList);
	/**
	 * 获取某学年学期我所带每个班的成绩预测情况
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param courseId 课程id
	 * @param nature 课程性质
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getClassScoreGk(String schoolYear, String termCode,
			String courseId, String nature,String edu,List<AdvancedParam> advancedList);
	/**
	 * 获取某学年学期我所带某个课程的各课程性质预测成绩分布
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param courseId 课程id
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getChartData(String schoolYear, String termCode,
			String courseId,String edu,List<AdvancedParam> advancedList);
	/**
	 * 获取同年级同课程同课程属性 学生成绩
	 * @param schoolYear 学年
	 * @param courseId 课程id
	 * @param nature 课程性质
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getSameData(String schoolYear, String courseId,
			String nature,String edu,List<AdvancedParam> advancedList);
	/**
	 * 获取近五年精准度
	 * @param schoolYear 学年
	 * @param courseId 课程id
	 * @param nature 课程性质
	 * @return Map<String, Object> 
	 */
	public Map<String, Object> getPrecisionList(String schoolYear, 
			String courseId, String nature,String edu,List<AdvancedParam> advancedList);
	/**
	 * 获取当前应该展示的精度
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param courseId 课程id
	 * @param nature 课程性质
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getNowPrecision(String schoolYear, String termCode,
			String courseId, String nature,String edu,List<AdvancedParam> advancedList);
	/**
	 * 获取表格标题
	 * @return 
	 */
	public List<Map<String, Object>> getThList();
	/**
	 * 获取表格副标题
	 * @return
	 */
	public List<Map<String, Object>> getFthList();
	/**
	 * 查询预测数据来源截止日期
	 * @return
	 */
	public Map<String, Object> getDate();
	/**
	 * 查询学生明细（所有）
	 * @param advancedParamList
	 * @param keyValue
	 * @param fields
	 * @return
	 */
	public List<Map<String, Object>> getStuDetailList(
			List<AdvancedParam> advancedParamList,
			Map<String, Object> keyValue, List<String> fields);
	/**
	 * 查询学生明细（分页）
	 * @param advancedParamList
	 * @param page
	 * @param keyValue
	 * @param fields
	 * @return
	 */
	public Map<String, Object> getStuDetail(List<AdvancedParam> advancedParamList,
			Page page, Map<String, Object> keyValue, List<String> fields);
	/**
	 * 查询预测来源截止日期
	 * @param table 成绩预测表
	 * @return
	 */
	public Map<String, Object> getDate(String table);
	/**
	 * 查询时间集合
	 * @param table 成绩预测表
	 * @return
	 */
	public List<Map<String, Object>> getTimeList(String table);
	/**
	 * 获取某学年学期我所带每个班的成绩预测情况
	 * @param schoolYear 学年 
	 * @param termCode 学期
	 * @param courseId 课程id
	 * @param nature 课程属性
	 * @param edu 就读学历
	 * @param advancedList 高级查询参数
	 * @param table 成绩预测表
	 * @return
	 */
	public Map<String, Object> getClassScoreGk(String schoolYear, String termCode,
			String courseId, String nature, String edu,
			List<AdvancedParam> advancedList, String table);
	/**
	 * 获取某学年学期我所带某个课程的各课程性质预测成绩分布
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param courseId 课程id
	 * @param edu 就读学历
	 * @param advancedList 高级查询参数
	 * @param table 成绩预测表
	 * @return
	 */
	public Map<String, Object> getChartData(String schoolYear, String termCode,
			String courseId, String edu, List<AdvancedParam> advancedList,
			String table);
	/**
	 * 获取同年级同课程同课程属性 学生成绩
	 * @param schoolYear 学年
	 * @param courseId 课程id
	 * @param nature 课程性质
	 * @param edu 就读学历
	 * @param advancedList 高级查询参数
	 * @param table 成绩预测表
	 * @return
	 */
	public Map<String, Object> getSameData(String schoolYear, String courseId,
			String nature, String edu, List<AdvancedParam> advancedList,
			String table);
	/**
	 * 获取近五年精准度
	 * @param schoolYear 学年
	 * @param courseId 课程id
	 * @param nature 课程性质
	 * @param edu 就读学历
	 * @param advancedList 高级查询参数
	 * @param table 成绩预测表
	 * @return
	 */
	public Map<String, Object> getPrecisionList(String schoolYear, String courseId,
			String nature, String edu, List<AdvancedParam> advancedList,
			String table);
	/**
	 *  获取当前应该展示的精度
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param courseId 课程id
	 * @param nature 课程性质
	 * @param edu 就读学历
	 * @param advancedList 高级查询参数
	 * @param table 成绩预测表
	 * @return
	 */
	public Map<String, Object> getNowPrecision(String schoolYear, String termCode,
			String courseId, String nature, String edu,
			List<AdvancedParam> advancedList, String table);

}
