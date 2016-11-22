package cn.gilight.personal.teacher.score.service;

import java.util.List;
import java.util.Map;

public interface ScoreService {

	/**
	 * 获取辅导员所带各班级的成绩分析
	 * @param tea_id
	 * @return
	 */
	public List<Map<String, Object>> getScoreClasses(String school_year,String term_code,String tea_id);

	/**
	 * 获取班级学生成绩单
	 * @param school_year
	 * @param term_code
	 * @param class_id
	 * @return
	 */
	public List<Map<String, Object>> getStuScore(String school_year, String term_code,String class_id,String course_id,String param,String paramFlag);
	
	/**
	 * 获取授课教师所带班级成绩
	 * @param school_year
	 * @param term_code
	 * @param tea_id
	 * @return
	 */
	public List<Map<String, Object>> getCourseScore(String school_year,String term_code,String tea_id);

	/**
	 * 获取学生总成绩
	 * @param school_year
	 * @param term_code
	 * @param stu_id
	 * @return
	 */
	public List<Map<String, Object>> getStuTotalScore(String school_year, String term_code, String stu_id);

	/**
	 * 获取学生成绩明细
	 * @param school_year
	 * @param term_code
	 * @param stu_id
	 * @return
	 */
	public List<Map<String, Object>> getStuScoreDetail(String school_year, String term_code, String stu_id);
	
	
	
	
}
