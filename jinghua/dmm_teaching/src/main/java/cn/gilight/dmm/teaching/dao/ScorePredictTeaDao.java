package cn.gilight.dmm.teaching.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;



public interface ScorePredictTeaDao {

    /**
     * 根据职工号，学年学期，查询课程集合
     * @param schoolYear 学年
     * @param termCode 学期
     * @param teaNo 职工号
     * @return List<Map<String, Object>>
     */
	public List<Map<String, Object>> queryCourseList(String schoolYear,String termCode, String teaNo);
    
	/**
	 * 查询教师所带的教学班和学生数
	 * @param schoolYear 学年 
	 * @param termCode 学期
	 * @param teaNo 职工号
	 * @param courseId 课程id
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryTeachClassAndStuCount(String schoolYear,
			String termCode, String teaNo,String courseId,Integer year,List<String> deptList,List<AdvancedParam> advancedList);
    /**
     * 查询某学年某学期我所带课程的所有性质集合
     * @param schoolYear 学年
     * @param termCode 学期
     * @param teaNo 职工号
     * @param courseId 课程id
     * @return List<Map<String, Object>>
     */
	public List<Map<String, Object>> queryCourseNatureList(String schoolYear,
			String termCode, String teaNo, String courseId);
    /**
     * 查询某学年某学期我带的行政班集合
     * @param schoolYear 学年
     * @param termCode 学期
     * @param teaNo 职工号
     * @param courseId 课程id
     * @param nature 课程性质
     * @return List<Map<String, Object>>
     */
	public List<Map<String, Object>> queryClassList(String schoolYear,
			String termCode, String teaNo, String courseId, String nature,Integer year,String table,List<String> deptList,List<AdvancedParam> advancedList);
    /**
     * 查询某些学生的成绩预测结果
     * @param stuSql 学生sql
     * @param schoolYear 学年
     * @param termCode 学期
     * @param courseId 课程id
     * @param table 预测表      
     * @return List<Double>
     */
	public List<Double> queryClassScoreGk(String stuSql, String schoolYear,
			String termCode, String courseId,String table);
    /**
     * 获取某学年学期我带的课程所有课程性质的成绩分布
     * @param schoolYear 学年
     * @param termCode 学期
     * @param teaNo 职工号
     * @param courseId 课程id
     * @param start 分段最小值
     * @param end 分段最大值
     * @return List<Map<String, Object>>
     */
	public Map<String, Object> queryNatureList(String schoolYear,
			String termCode, String teaNo, String courseId, String start,
			String end,Integer year,List<String> deptList,List<AdvancedParam> advancedList,String table);
    /**
     * 查询我带的某一类学生的成绩分布
     * @param schoolYear 学年
     * @param teaNo 职工号
     * @param courseId 课程id
     * @param nature 课程性质
     * @param type 类型
     * @return List<Double>
     */
	public List<Double> queryClassScore(String schoolYear, String teaNo,
			String courseId, String nature, String type,Integer year,List<String> deptList,List<AdvancedParam> advancedList,String table);
    /**
     * 获取某年某学期预测的精准度
     * @param schoolYear 学年
     * @param termCode 学期
     * @param courseId 课程id
     * @param teaNo 职工号
     * @param nature 课程性质
     * @param isMe 是否是我本人
     * @return Double
     */
	public Double queryPrecision(String schoolYear, String termCode, String courseId,
			String teaNo, String nature, Boolean isMe,Integer year,List<String> deptList,List<AdvancedParam> advancedList,String table);
    /**
     * 查询预测数据来源截止日期
     * @return
     */
	public String queryDate(String table);
    /**
     * 根据职工号查询应该展示的学年学期和课程
     * @param teaNo
     * @return
     */
	public List<Map<String, Object>> queryTimeList(String teaNo,String table);
    /**
     * 查询教师带的学生
     * @param teaNo
     * @param deptList
     * @return
     */
	public String queryStuSql(String teaNo,List<String> deptList);
    /**
     * 获取学生明细sql
     * @param schoolYear
     * @param termCode
     * @param courseId
     * @param stuSql
     * @param th
     * @return
     */
	public String getYcSql(String schoolYear, String termCode, String courseId,
			String stuSql, String th,String table);

	/**
	 * 获取班级以及成绩集合
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param teaNo 职工号
	 * @param courseId 课程id
	 * @param nature 课程性质
	 * @param year 学年
	 * @param deptList 标准权限
	 * @param advancedList 高级查询参数
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryClassList(String schoolYear,
			String termCode, String teaNo, String courseId, String nature,
			Integer year, List<String> deptList,
			List<AdvancedParam> advancedList);

	  /**
     * 根据职工号查询应该展示的学年学期和课程
     * @param teaNo
     * @return
     */
	public List<Map<String, Object>> queryTimeList(String teaNo);
	  /**
     * 查询某些学生的成绩结果
     * @param stuSql 学生sql
     * @param schoolYear 学年
     * @param termCode 学期
     * @param courseId 课程id
     * @return List<Double>
     */
	public List<Double> queryClassScore(String schoolYear, String teaNo,
			String courseId, String nature, String type, Integer year,
			List<String> deptList, List<AdvancedParam> advancedList);

	   /**
     * 获取某学年学期我带的课程所有课程性质的成绩分布
     * @param schoolYear 学年
     * @param termCode 学期
     * @param teaNo 职工号
     * @param courseId 课程id
     * @param start 分段最小值
     * @param end 分段最大值
     * @return List<Map<String, Object>>
     */
	public Map<String, Object> queryNatureList(String schoolYear, String termCode,
			String teaNo, String courseId, String start, String end,
			Integer year, List<String> deptList,
			List<AdvancedParam> advancedList);
    /**
     * 获取各教学班人数
     * @param schoolYear
     * @param termCode
     * @param teaNo
     * @param courseId
     * @param nature
     * @param year
     * @param deptList
     * @param advancedList
     * @return
     */
	public List<Map<String, Object>> getStuCount(String schoolYear, String termCode,
			String teaNo, String courseId, String nature, Integer year,
		    List<String> deptList,
			List<AdvancedParam> advancedList);

	/**
	 * 获取各教学班实际考试人数
	 * @param schoolYear
	 * @param termCode
	 * @param teaNo
	 * @param courseId
	 * @param nature
	 * @param year
	 * @param deptList
	 * @param advancedList
	 * @return
	 */
	public List<Map<String, Object>> getStuCountTrue(String schoolYear,
			String termCode, String teaNo, String courseId, String nature,
			Integer year, List<String> deptList,
			List<AdvancedParam> advancedList,Boolean isgd);

	/**
	 * 获取课程属性
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param teaNo 职工号
	 * @param course 课程
	 * @param classid 教学班级id
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryAttrList(String schoolYear, String termCode,
			String teaNo, String course, String classid);
    /**
     * 获取开课院系以及专业班级名称
     * @param course 课程
     * @param attr 课程属性
     * @param classid 教学班id
     * @return Map<String,Object>
     */
	public Map<String, Object> queryYxAndBjList(String course, String attr,
			String classid);

	/**
	 * 查询课程属性编码
	 * @return
	 */
	public List<Map<String, Object>> getCodeAttr();

	/**
	 * 查询违纪等学生
	 * @param schoolYear
	 * @param termCode
	 * @param teaNo
	 * @param courseId
	 * @param nature
	 * @param year
	 * @param deptList
	 * @param advancedList
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getStuCountOther(String schoolYear,
			String termCode, String teaNo, String courseId, String nature,
			Integer year, List<String> deptList,
			List<AdvancedParam> advancedList);

	/**
	 * 获取验证状态
	 * @param schoolYear
	 * @param termCode
	 * @param course
	 * @param nature
	 * @param classid
	 * @return
	 */
	public Boolean getYzZt(String schoolYear, String termCode, String course,
			String nature, String classid);

	/**
	 * 查询实际学生数和全部学生数
	 * @param schoolYear
	 * @param teaNo
	 * @param courseId
	 * @param nature
	 * @param type
	 * @param year
	 * @param deptList
	 * @param advancedList
	 * @return
	 */
	public Map<String, Object> queryClassCount(String schoolYear, String teaNo,
			String courseId, String nature, String type, Integer year,
			List<String> deptList, List<AdvancedParam> advancedList,Boolean isgd);
	
}
