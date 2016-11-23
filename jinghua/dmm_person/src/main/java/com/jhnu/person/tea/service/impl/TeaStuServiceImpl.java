package com.jhnu.person.tea.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.person.tea.dao.TeaStuDao;
import com.jhnu.person.tea.service.TeaStuService;
import com.jhnu.system.common.page.Page;

@Service("teaStuService")
public class TeaStuServiceImpl implements TeaStuService {
	@Autowired
	private TeaStuDao teaStuDao;

	@Override
	public Page stuxzbxx(String teaId,String gradeId,int currentPage, int numPerPage) {
		return teaStuDao.stuxzbxx(teaId,gradeId, currentPage, numPerPage);
	}

	@Override
	public List stuxzGrade(String id) {
		return teaStuDao.stuxzGrade(id);
	}

	@Override
	public Map getStuxx(String stuId) {
		return teaStuDao.getStuxx(stuId);
	}
	@Override
	public List<Map<String, Object>> stuxbbl(String teaId,String gradeId){
		return teaStuDao.stuxbbl(teaId,gradeId);
	}

	@Override
	public List jxStuGrade(String id) {
		return teaStuDao.jxStuGrade(id);
	}

	@Override
	public List jxStutgl(String teaId,String gradeId) {
		return teaStuDao.jxStutgl( teaId, gradeId);
	}

	@Override
	public List<Map<String, Object>> jxStuxbbl(String teaId,String gradeId) {
		return teaStuDao.jxStuxbbl(teaId,gradeId);
	}

	@Override
	public List<Map<String, Object>> jxStuqjxb(String teaId,String gradeId) {
		return teaStuDao.jxStuqjxb(teaId,gradeId);
	}

	@Override
	public Page getjxStuxx(String teaId,String stuid, int currentPage, int numPerPage) {
		return teaStuDao.getjxStuxx(teaId,stuid, currentPage, numPerPage);
	}

	@Override
	public List getStuxxmx(String id) {
		return teaStuDao.getStuxxmx(id);
	}

	@Override
	public List<Map<String, Object>> xzStuqjxb(String teaId, String gradeId) {
		return teaStuDao.xzStuqjxb(teaId,gradeId);
	}

}
