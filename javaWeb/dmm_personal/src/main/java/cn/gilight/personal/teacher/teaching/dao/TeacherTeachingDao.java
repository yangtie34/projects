package cn.gilight.personal.teacher.teaching.dao;

import java.util.List;
import java.util.Map;


public interface TeacherTeachingDao {
	public List<Map<String,Object>> getTodayClass(String school_year,String term_code,String tea_id,int zc,int week);
	
	/**
	 * 获取今日的课程明细
	 * @param courseArrangementId 课程安排ID
	 * @return
	 */
	public List<Map<String,Object>> getTodayCourse(String courseArrangementId,String course_id);
	
	public List<Map<String,Object>> getTermClass(String school_year,String term_code,String tea_id);

	public List<Map<String, Object>> getClassSchedule(String tea_id, String school_year,String term_code,int zc,int jc);
}
