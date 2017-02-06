package cn.gilight.dmm.teaching.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.framework.page.Page;

/**
 * 挂科预测
 * @author lijun
 *
 */
public interface ScorePredictService {
	/**
	 * 得到学年 
	 * @return
	 */
	Map<String, Object> getYearAndTerm();
	/**
	 * 得到课程
	 * @return
	 */
	 Map<String, Object> queryCourseByType(String id, String schoolYear, String termCode);
	/**
	 * 得到辅导员所带班级数，总人数，以及所带专业
	 * @param deptList
	 * @return
	 */
	List<Map<String, Object>> queryGkInfo(List<AdvancedParam> advancedList, String schoolYear, String termCode, String edu,String majorType);
	List<Map<String, Object>> queryGkInfo(List<String> deptList,List<AdvancedParam> advancedList, String schoolYear, String termCode, String edu,String majorType);
	/**
	 * 成绩预测概况
	 * @param deptList
	 * @return
	 */
	List<Map<String, Object>> queryScoreInfo(List<AdvancedParam> advancedList,String schoolYear, String termCode, String edu,String courseType,String courseid,String majorType);
	List<Map<String, Object>> queryScoreInfo(List<String> deptList,List<AdvancedParam> advancedList,String schoolYear, String termCode, String edu,String courseType,String courseid,String majorType);
	/**
	 * 成绩分布(按课程)
	 * @param deptList
	 * @return
	 */
	List<Map<String, Object>> queryScorefb(List<AdvancedParam> advancedList,String schoolYear, String termCode, String edu,String majorType);
	List<Map<String, Object>> queryScorefb(List<String> deptList,List<AdvancedParam> advancedList,String schoolYear, String termCode, String edu,String majorType);
	/**
	 * 成绩分布(按课程性质)
	 * @param deptList
	 * @return
	 */
	List<Map<String, Object>> queryScorefbByNature(List<AdvancedParam> advancedList,String schoolYear, String termCode, String edu,String majorType);
	List<Map<String, Object>> queryScorefbByNature(List<String> deptList,List<AdvancedParam> advancedList,String schoolYear, String termCode, String edu,String majorType);
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
