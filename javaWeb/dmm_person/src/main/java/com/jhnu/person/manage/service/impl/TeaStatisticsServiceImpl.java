package com.jhnu.person.manage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.person.manage.dao.TeaStatisticsDao;
import com.jhnu.person.manage.service.TeaStatisticsService;
@Service("teaStatisticsService")
public class TeaStatisticsServiceImpl implements TeaStatisticsService {

	@Autowired
	private TeaStatisticsDao statisticsDao;

	@Override
	public List<Map<String, Object>> getSchoolName() {
		return statisticsDao.getSchoolName();
	}

	@Override
	public List<Map<String, Object>> getRs(String pid) {
		return statisticsDao.getRs(pid);
	}

	@Override
	public List<Map<String, Object>> getJszt(String pid) {
		return statisticsDao.getJszt(pid);
	}

	@Override
	public List<Map<String, Object>> getNl(String pid) {
		return statisticsDao.getNl(pid);
	}

	@Override
	public List<Map<String, Object>> getMz(String pid) {
		return statisticsDao.getMz(pid);
	}

	@Override
	public List<Map<String, Object>> getZzmm(String pid) {
		return statisticsDao.getZzmm(pid);
	}

	@Override
	public List<Map<String, Object>> getLyd(String pid) {
		return statisticsDao.getLyd(pid);
	}

	@Override
	public List<Map<String, Object>> getXl(String pid) {
		return statisticsDao.getXl(pid);
	}

	@Override
	public List<Map<String, Object>> getZcjb(String pid) {
		return statisticsDao.getZcjb(pid);
	}

	@Override
	public List<Map<String, Object>> getZc(String pid) {
		return statisticsDao.getZc(pid);
	}

	@Override
	public List<Map<String, Object>> getMzCount(String pid) {
		return statisticsDao.getMzCount(pid);
	}

	@Override
	public List<Map<String, Object>> queryZzmm1(String pid) {
		return statisticsDao.queryZzmm1(pid);
	}

}
