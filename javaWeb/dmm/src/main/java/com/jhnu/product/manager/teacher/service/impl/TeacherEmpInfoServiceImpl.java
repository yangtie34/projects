package com.jhnu.product.manager.teacher.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.manager.teacher.dao.ITeacherEmpInfoDao;
import com.jhnu.product.manager.teacher.service.ITeacherEmpInfoService;

/**
 * @title 教职工概况Service实现类
 * @description 教职工基本概况统计
 * @author Administrator
 * @date 2015/10/14 11:31
 */
@Service("teacherEmpInfoService")
public class TeacherEmpInfoServiceImpl implements ITeacherEmpInfoService {

	// 自动注入ITeacherEmpInfoDao
	@Autowired
	private ITeacherEmpInfoDao teacherEmpInfoDao;

	@Override
	public List<Map<String, Object>> teachersSexComposition(String departmentId) {
		return teacherEmpInfoDao.teachersSexComposition(departmentId);
	}

	@Override
	public List<Map<String, Object>> teachersAgesComposition(String departmentId) {
		return teacherEmpInfoDao.teachersAgesComposition(departmentId);
	}

	@Override
	public List<Map<String, Object>> teachersNationsComposition(
			String departmentId) {
		return teacherEmpInfoDao.teachersNationsComposition(departmentId);
	}

	@Override
	public List<Map<String, Object>> teachersPoliticalStatus(String departmentId) {
		return teacherEmpInfoDao.teachersPoliticalStatus(departmentId);
	}

	@Override
	public List<Map<String, Object>> teachersSourceLand(int level,
			String departmentId, String sexthIdCard) {
		return teacherEmpInfoDao.teachersSourceLand(level, departmentId,
				sexthIdCard);
	}

	@Override
	public List<Map<String, Object>> teachersSubjectsComposition(
			String departmentId) {
		return teacherEmpInfoDao.teachersSubjectsComposition(departmentId);
	}

	@Override
	public List<Map<String, Object>> teachersEducationalBackground(
			String departmentId) {
		return teacherEmpInfoDao.teachersEducationalBackground(departmentId);
	}

	@Override
	public List<Map<String, Object>> teachersDegreeComposition(
			String departmentId) {
		return teacherEmpInfoDao.teachersDegreeComposition(departmentId);
	}

}
