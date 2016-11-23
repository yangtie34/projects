package com.jhnu.product.wechat.teacher.instructor.classbase.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.wechat.teacher.instructor.classbase.dao.ClassBaseDao;
import com.jhnu.product.wechat.teacher.instructor.classbase.service.ClassBaseService;

@Service("classBaseService")
public class ClassBaseServiceImpl implements ClassBaseService{

	@Autowired
	private ClassBaseDao classBaseDao;
	
	@Override
	public List<Map<String, Object>> getClassBaseByTeacherId(String teacherId) {
		return classBaseDao.getClassBaseByTeacherId(teacherId);
	}

	@Override
	public List<Map<String, Object>> getClassStuByClassId(String classId,boolean isDuties) {
		return classBaseDao.getClassStuByClassId(classId, isDuties);
	}
	
}
