package cn.gilight.dmm.teaching.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.teaching.entity.TStuScorepredTermGroup;

public interface ScorePredictGroupDao {

	/**
	 * 查询某学年的班级有多少个入学年级
	 * @param schoolYear 学年
	 * @param deptList 标准权限
	 * @param advancedList 高级查询参数
	 * @return List<String>
	 */
	public List<String> queryGroupGradeList(int schoolYear, List<String> deptList);

	/**
	 * 获取成绩预测各院系课程，实际有成绩人数，上课总人数
	 * @param start_schoolYear 开始学年
	 * @param start_termCode 开始学期
	 * @param end_schoolYear 结束学年
	 * @param end_termCode 结束学期
	 * @param grade 入学年级
	 * @param yc_start_schoolYear 预测开始学年
	 * @param yc_start_termCode 预测开始学期
	 * @param yc_end_schoolYear 预测结束学年
	 * @param yc_end_termCode 预测结束学期
	 * @param isXx 是否选修课
	 * @return List<Map<String, Object>>  [kc:'',xn:'',xq:'',stu:'',allstu:'',bj:'']
	 */
	public List<Map<String, Object>> queryAllCourseList(String start_schoolYear,
			String start_termCode, String end_schoolYear, String end_termCode,
			String grade, String yc_start_schoolYear,String yc_start_termCode,String yc_end_schoolYear,
			  String yc_end_termCode,Boolean isXx);
    /**
     * 查询所有学生list,有成绩学生list,有成绩班级list
     * @param courseMap 课程map
     * @param grade 入学年级
     * @return  Map<String, Object> 
     */
	public Map<String, Object> getBjXsList(Map<String, Object> courseMap,String grade,Boolean hasScore);
	/**
	 * 根据开始学年，开始学期，结束学年，结束学期获取之前所有的学年学期
	 * @param start_schoolYear 
	 * @param start_termCode
	 * @param end_schoolYear
	 * @param end_termCode
	 * @return List<Map<String, Object>> list:[{schoolYear:"2015-2016",termCode:"01",ycXq:"last"}]
	 */
	public List<Map<String, Object>> getYearTermAry(String start_schoolYear,
			String start_termCode, String end_schoolYear, String end_termCode);

	/**
	 * 根据开始学年，开始学期，结束学年，结束学期获取分组
	 * @param start_schoolYear
	 * @param start_termCode
	 * @param end_schoolYear
	 * @param end_termCode
	 * @param truth 真伪分组
	 * @param iselective 是否选修
	 * @return  List<TStuScorepredTermGroup>
	 */ 
	public List<TStuScorepredTermGroup> getGroup(String start_schoolYear,
			String start_termCode, String end_schoolYear, String end_termCode,int truth,int iselective);
    /**
     * 获取选修课学生集合
     * @param courseMap 课程对象
     * @param grade 入学年级
     * @return Map<String, Object>
     */
	public Map<String, Object> getXxCourseStuList(Map<String, Object> courseMap,
			String grade,Boolean hasScore);

	/**
	 * 获取伪分组所有课程
	 * @param start_schoolYear 开始学年
	 * @param start_termCode 开始学期
	 * @param end_schoolYear 结束学年
	 * @param end_termCode 结束学期
	 * @param type 课程类型 eg:"xx"：选修，"bx":必修，"all"：全部
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getWfzCourseList(String start_schoolYear,
			String start_termCode, String end_schoolYear, String end_termCode,String type);

	/**
	 * 获取伪分组课程集合
	 * @param start_schoolYear 开始学年
	 * @param start_termCode 开始学期
	 * @param end_schoolYear 结束学年 
	 * @param end_termCode 结束学期
	 * @param grade 入学年级
	 * @param hasScore 是否关联成绩
	 * @param isXx 是否选修
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getWfzCourseList(String start_schoolYear,
			String start_termCode, String end_schoolYear, String end_termCode,
			String grade, Boolean hasScore, Boolean isXx);


}
