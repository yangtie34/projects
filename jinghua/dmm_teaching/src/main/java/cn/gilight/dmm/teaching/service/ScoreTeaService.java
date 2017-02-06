package cn.gilight.dmm.teaching.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;

public interface ScoreTeaService {
    /**
     * 查询应该展示的学年学期
     * @return
     */
	public List<Map<String, Object>> getTimeList();
    /**
     * 查询各教学班成绩分布
     * @param schoolYear 学年
     * @param termCode 学期
     * @param courseId 课程id
     * @param nature 课程性质
     * @param edu 就读学历
     * @param advancedList 高级查询参数
     * @return  Map<String, Object>
     */
	public Map<String, Object> getClassScoreGk(String schoolYear, String termCode,
			String courseId, String nature, String edu,
			List<AdvancedParam> advancedList);
	/**
	 * 获取同年级同课程同课程属性 学生成绩
	 * @param schoolYear 学年
	 * @param courseId 课程id
	 * @param nature 课程性质
	 * @param edu 就读学历
	 * @param advancedList 高级查询参数
	 * @return  Map<String, Object>
	 */
	public Map<String, Object> getSameData(String schoolYear, String courseId,
			String nature, String edu, List<AdvancedParam> advancedList);
	/**
	 * 获取某学年学期我所带某个课程的各课程性质预测成绩分布
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param courseId 课程id
	 * @param edu 就读学历
	 * @param advancedList 高级查询参数
	 * @return Map<String, Object> 
	 */
	public Map<String, Object> getChartData(String schoolYear, String termCode,
			String courseId, String edu, List<AdvancedParam> advancedList);
	/**
	 * 获取各教学班实际考试人数
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param courseId 课程id
	 * @param nature 课程性质
	 * @param edu 就读学历
	 * @param advancedList 高级查询参数
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getReport(String schoolYear, String termCode,
			String courseId, String nature, String edu,
			List<AdvancedParam> advancedList);
	/**
	 * 获取打印数据
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param courseId 课程id
	 * @param classid 班级id
	 * @param nature 课程性质
	 * @param edu 就读学历
	 * @param advancedList 高级查询参数
	 * @return Map<String, Object>
	 */
	public 	Map<String, Object> getDyData(String schoolYear, String termCode,
			String courseId, String classid, String nature, String edu,
			List<AdvancedParam> advancedList);
	/**
	 * 保存验证成功信息
	 * @param schoolYear 
	 * @param termCode
	 * @param course
	 * @param nature
	 * @param teachClass
	 */
	public void saveYzZt(String schoolYear, String termCode, String course,
			String nature, String teachClass);
	/**
	 * 获取验证状态
	 * @param schoolYear
	 * @param termCode
	 * @param course
	 * @param nature
	 * @param teachClass
	 * @return
	 */
	public Boolean getYzZt(String schoolYear, String termCode, String course,
			String nature, String teachClass);
    /**
     * 
     * @return
     */
	public List<Map<String, Object>> getEduList();
	 /**
     * 
     * @return
     */
	public List<Map<String, Object>> getThList();
	 /**
     * 
     * @return
     */
	public List<Map<String, Object>> getFthList();
	 /**
     * 
     * @return
     */
	public List<Map<String, Object>> getCourseList(List<AdvancedParam> advancedList,
			String schoolYear, String termCode, String edu);
	 /**
     * 
     * @return
     */
	public Map<String, Object> getTeachClassAndStuCount(String schoolYear,
			String termCode, String courseId, String edu,
			List<AdvancedParam> advancedList);
	 /**
     * 
     * @return
     */
	public List<Map<String, Object>> getCourseNatureList(String schoolYear,
			String termCode, String courseId, String edu,
			List<AdvancedParam> advancedList);
	public void saveDt(String schoolYear, String termCode, String course,
			String nature, String teachClass, String kslx, String kcxx,
			String one, String two, String three, String four, String five,
			String fxr, String zr, String sj,String qt);
	public Map<String, Object> getDtXt(String schoolYear, String termCode,
			String course, String nature, String teachClass);
	public void saveXt(String schoolYear, String termCode, String course,
			String nature, String teachClass, List<HashMap> tx,
			List<HashMap> tf, List<HashMap> df,
			List<String> th, List<String> thn);

}
