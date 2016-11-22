package com.jhnu.product.wechat.teacher.instructor.classbase.dao;

import java.util.List;
import java.util.Map;

public interface ClassBaseDao {
	
	/**
	 * 获取辅导员所管辖班级list。包含性别组成，班长以及联系方式，班级名称和专业
	 * @param teacherId 辅导员ID
	 * @return
	 */
	public List<Map<String,Object>> getClassBaseByTeacherId(String teacherId);
	
	/**
	 * 获取通过班级ID，获取学生list。以及学生详细属性
	 * @param classId 班级ID
	 * @return
	 */
	public List<Map<String,Object>> getClassStuByClassId(String classId,boolean isDuties);
	
}
