package com.jhnu.product.common.stu.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.common.stu.dao.StuDao;
import com.jhnu.product.common.stu.entity.LateDormStudent;
import com.jhnu.product.common.stu.entity.Student;
import com.jhnu.product.common.stu.service.StuService;
import com.jhnu.util.common.DateUtils;

@Service("stuService")
public class StuServiceImpl implements StuService {
	@Autowired
	private StuDao stuDao;

	@Override
	public List<Student> getStusInSchoolNotInUser() {
		return stuDao.getStusInSchoolNotInUser();
	}
	@Override
	public List<Student> getStusInSchool() {
		return stuDao.getStusInSchool();
	}

	@Override
	public List<Student> getStusGraduated() {
		return stuDao.getStusGraduated();
	}

	@Override
	public List<Student> getStusByEndyear(int endYear) {
		return stuDao.getStusByEndyear(endYear);
	}

	@Override
	public int getStuCByEndYear(int endYear) {
		return stuDao.getStuCByEndYear(endYear);
	}

	@Override
	public int getStuCByEndYearAndSexcode(int endYear, String sexCode) {
		return stuDao.getStuCByEndYearAndSexcode(endYear, sexCode);
	}

	@Override
	public int getStuCByEndYearAndMajor(int endYear, String majorId) {
		return stuDao.getStuCByEndYearAndMajor(endYear, majorId);
	}

	@Override
	public int getEndYearByStuId(String stuId) {
		return stuDao.getEndYearByStuId(stuId);
	}
	
	@Override
	public Student getStudentInfo(String stuId) {
		return stuDao.getStudentInfo(stuId);
	}
	@Override
	public int getSchoolYearsByStuId(String stuId) {
		return stuDao.getSchoolYearByStuId(stuId);
	}
	@Override
	public Student getStudentInfoByIdno(String stuIdNo) {
		return stuDao.getStudentInfoByIdno(stuIdNo);
	}
	@Override
	public List<LateDormStudent> getLateDormStudents(LateDormStudent lds) {
		return stuDao.getLateDormStudents(lds);
	}
	@Override
	public void saveLateDormStudentByYestDay(Date date) {
		Date yestDay=DateUtils.getYesterday(date);
		//TODO 该时间后期通过配置项灵活配置获取
		String startTime="22:30:00",endTime="05:00:00";
		startTime=DateUtils.SDF.format(yestDay)+" "+startTime;
		endTime=DateUtils.SDF.format(date)+" "+endTime;
		stuDao.saveLateDormStudent(stuDao.getLateDormStudentBytime(startTime, endTime,DateUtils.SDF.format(yestDay), DateUtils.SSS.format(date)));
	}
	@Override
	public boolean isInClassForStudent(String stuId,List list) {
		return stuDao.isInClassForStudent(stuId, list);
	}
}
