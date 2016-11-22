package cn.gilight.personal.teacher.teaching.service;

import java.util.List;
import java.util.Map;



public interface TeacherTeachingService {
	
	/**
	 * 获取今日课程
	 */
	public List<Map<String,Object>> getTodayClass(String tea_id);
	
	/**
	 * 获取今日的课程明细
	 * @param courseArrangementId 课程安排ID
	 * @return
	 */
	public List<Map<String,Object>> getTodayCourse(String courseArrangementId,String course_id);

	public List<Map<String, Object>> getTermClass(String tea_id);

	public List<Map<String, Object>> getClassSchedule(String tea_id,int zc);

	public Map<String, Object> getWeek(int week,String zyrq,String flag);
	
}
