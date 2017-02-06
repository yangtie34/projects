package cn.gilight.dmm.teaching.dao;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xuebl
 * @date 2016年6月22日 下午12:13:56
 */
public interface ScoreDao {

	
	/**
	 * 获取学生的平均绩点【从绩点结果表查数据】
	 * @param stuSql 学生sql
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param gpaCode GPA code
	 * @return List<Double>
	 */
	public List<Double> queryScoreList(String stuSql, String schoolYear, String termCode, String gpaCode);

	/**
	 * 查询课程 列表
	 * @param stuSql 学生sql
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryCourseList(String stuSql, String schoolYear, String termCode);

	/**
	 * 查询授课教师 列表
	 * @param stuSql 学生sql
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryTeachList(String stuSql, String schoolYear, String termCode);
    /**
     * 根据学年学期和学生查询课程sql
     * @param stuSql 学生sql
     * @param schoolYear 学年
     * @param termCode 学期
     * @return String
     */
	public String queryCourseSql(String stuSql, String schoolYear, String termCode);
	/**
     * 根据学年学期和学生查询教师sql
     * @param stuSql 学生sql
     * @param schoolYear 学年
     * @param termCode 学期
     * @return String
     */
	public String queryTeachSql(String stuSql, String schoolYear, String termCode);
	
}