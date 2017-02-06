package cn.gilight.dmm.teaching.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;

/**
 * 成绩预测
 * @author lijun
 *
 */
public interface ScorePredictDao {

	/**
	 * 得到课程
	 * @param id
	 * @param schoolYear
	 * @param termCode
	 * @return
	 */
	
	List<Map<String, Object>> queryCourseByType(String id, String schoolYear, String termCode);
	/**
	 * 得到当前学年期末考试人数及挂科信息
	 * @param deptList
	 * @return
	 */
	List<Map<String, Object>> queryGkInfo(String stuSql,List<String> deptList,List<AdvancedParam> advancedList, String schoolYear, String termCode, String edu,String userId,String majorType);
	
	/**
	 * 成绩预测概况
	 * @param deptList
	 * @return
	 */
	List<Map<String, Object>> queryScoreInfo(List<String> deptList,List<AdvancedParam> advancedList,String schoolYear, String termCode, String edu,String courseType,String courseid,String majorType);
	/**
	 * 成绩分布(按课程)
	 * @param deptList
	 * @return
	 */
	List<Map<String, Object>> queryScorefb(List<String> deptList,List<AdvancedParam> advancedList,String schoolYear, String termCode, String edu,String majorType);
	/**
	 * 成绩分布(按课程性质)
	 * @param deptList
	 * @return
	 */
	List<Map<String, Object>> queryScorefbByNature(List<String> deptList,List<AdvancedParam> advancedList,String schoolYear, String termCode, String edu,String majorType);
	/**
	 * 得到学生详细信息表
	 * @param deptList
	 * @param advancedList
	 * @return
	 */
	String getDetailstuSql(List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 得到查看学生详细信息表
	 * @param deptList
	 * @param advancedList
	 * @return
	 */
    String getlookStuSql(List<String> deptList,List<AdvancedParam> advancedList,String schoolYear, String termCode, String edu,String majotType);
    /**
     * 得到对比学生详细信息表
     * @param deptList
     * @param advancedList
     * @return
     */
    String getContrastStuSql(List<String> deptList,List<AdvancedParam> advancedList,String schoolYear, String termCode, String edu,String majorType,String coursemc,String coursetypemc);
    /**
     * 得到准确率详细信息表
     * @param deptList
     * @param advancedList
     * @return
     */
    String getAccuracyStuSql(List<String> deptList,List<AdvancedParam> advancedList,String schoolYear, String majorType,String coursemc,String coursetypemc);
	/**
	 * 得到学生详细信息表
	 * @param deptList
	 * @param advancedList
	 * @return
	 */
	String getDetailstuSql(List<String> deptList,List<AdvancedParam> advancedList,String schoolYear, String termCode, String edu,String majorType,String coursemc,String coursetypemc,String choose,String classes);
}
