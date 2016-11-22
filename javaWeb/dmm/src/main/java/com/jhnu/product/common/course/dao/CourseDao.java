package com.jhnu.product.common.course.dao;

import java.util.List;
import java.util.Map;

public interface CourseDao {
	
	public void updateCourseWeek(final List<Map<String,Object>> zcList);
	
	public List<Map<String, Object>> getCourseWeek();
	
}
