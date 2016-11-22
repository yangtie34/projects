package com.jhnu.person.stu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.person.stu.dao.StuStudyDao;
import com.jhnu.person.stu.service.StuStudyService;
import com.jhnu.system.common.page.Page;
@Repository("stuStudyService")
public class StuStudyServiceImpl implements StuStudyService {
	@Autowired
	private StuStudyDao stuStudyDao;
	@Override
	public List pushBooks(String id, String startTime, String endTime) {
		return stuStudyDao.pushBooks(id, startTime, endTime);
	}

	@Override
	public List jyfl(String id, String startTime, String endTime) {
		return stuStudyDao.jyfl(id, startTime, endTime);
	}

	@Override
	public List jysl(String id, String startTime, String endTime) {
		return stuStudyDao.jysl(id, startTime, endTime);
	}

	@Override
	public Page jymx(String id, String startTime, String endTime,
			int currentPage, int numPerPage) {
		return stuStudyDao.jymx(id, startTime, endTime, currentPage, numPerPage);
	}

	@Override
	public List grkb(String id) {
		return stuStudyDao.grkb(id);
	}

	@Override
	public Page skcj(String id, int currentPage, int numPerPage) {
		return stuStudyDao.skcj(id, currentPage, numPerPage);
	}

	@Override
	public List<Map<String, Object>> getTodayCourse(String stu_id) {
		return stuStudyDao.getTodayCourse(stu_id);
	}

}
