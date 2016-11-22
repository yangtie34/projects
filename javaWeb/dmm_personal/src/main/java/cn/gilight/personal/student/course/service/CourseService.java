package cn.gilight.personal.student.course.service;

import java.util.List;
import java.util.Map;

public interface CourseService {

	/**
	 * 获取今日日期
	 * @return
	 */
	public Map<String, Object> getToday();

	/**
	 * 获取今日课程
	 * @param stu_id
	 * @return
	 */
	public List<Map<String, Object>> getTodayCourse(String stu_id);

	/**
	 * 获取课程表
	 * @param stu_id
	 * @return
	 */
	public List<Map<String, Object>> getSchedule(String stu_id,int zc);

	public Map<String, Object> getWeek(int week,String zyrq,String flag);
	
	/**
	 * 查某一天的空教室
	 * @param date
	 * @param period_start
	 * @param period_end
	 * @return
	 */
	public List<Map<String,Object>> getEmptyClassroom(String date,int period_start,int period_end);

}
