package cn.gilight.personal.student.course.dao;

import java.util.List;
import java.util.Map;

public interface CourseDao {

	/**
	 * 查今日课程
	 * @param stu_id
	 * @param school_year
	 * @param term_code
	 * @param zc
	 * @param week
	 * @return
	 */
	public List<Map<String, Object>> getTodayCourse(String stu_id,String school_year,String term_code,int zc,int week);

	/**
	 * 查课程表
	 * @param stu_id
	 * @param school_year
	 * @param term_code
	 * @param zc
	 * @param jc
	 * @return
	 */
	public List<Map<String, Object>> getSchedule(String stu_id,String school_year,String term_code,int zc,int jc);
	
	/**
	 * 查空教室
	 * @param school_year
	 * @param term_code
	 * @param zc
	 * @param week
	 * @param period_start
	 * @param period_end
	 * @return
	 */
	public List<Map<String,Object>> getEmptyClassroom(String school_year,String term_code,int zc,int week,int period_start,int period_end);

}
