package com.jhnu.product.wechat.teacher.instructor.classwarn.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.wechat.teacher.instructor.classwarn.dao.ClassWarnDao;
import com.jhnu.product.wechat.teacher.instructor.classwarn.service.ClassWarnService;

@Service("classWarnService")
public class ClassWarnServiceimpl implements ClassWarnService {
	@Autowired
	private ClassWarnDao classWarnDao;

	@Override
	public List<Map<String, Object>> getClassLateDormByTeacherId(
			String teacherId, String action_date) {
		return classWarnDao.getClassLateDormByTeacherId(teacherId, action_date);
	}
	
	
}
