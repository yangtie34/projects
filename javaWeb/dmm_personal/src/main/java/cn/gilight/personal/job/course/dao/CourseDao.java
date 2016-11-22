package cn.gilight.personal.job.course.dao;

import java.util.List;
import java.util.Map;

public interface CourseDao {
	
	public void updateCourseWeek(final List<Map<String,Object>> zcList);
	
	public List<Map<String, Object>> getCourseWeek(String school_year,String term_code);
	
	public List<Map<String, Object>> getCourseWeek();
	
	public List<Map<String,Object>> getSchoolYear();
	
}
