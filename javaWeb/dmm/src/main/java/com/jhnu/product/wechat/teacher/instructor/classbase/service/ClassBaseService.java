package com.jhnu.product.wechat.teacher.instructor.classbase.service;

import java.util.List;
import java.util.Map;

public interface ClassBaseService {
	
	public List<Map<String,Object>> getClassBaseByTeacherId(String teacherId);
	
	public List<Map<String,Object>> getClassStuByClassId(String classId,boolean isDuties);
	
}
