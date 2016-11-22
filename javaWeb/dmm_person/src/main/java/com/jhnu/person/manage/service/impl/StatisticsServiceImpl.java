package com.jhnu.person.manage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.person.manage.dao.StuStatisticsDao;
import com.jhnu.person.manage.service.StuStatisticsService;
@Service("stuStatisticsService")
public class StatisticsServiceImpl implements StuStatisticsService {

	@Autowired
	private StuStatisticsDao statisticsDao;
	@Override
	public  List<Map<String, Object>> querySchoolName(String pid){
		return statisticsDao.getSchoolName(pid);
	}
	@Override
	public  List<Map<String, Object>> queryYxdName(String pid){
		return statisticsDao.getYxdName(pid);
	}
	@Override
	public  List<Map<String, Object>> queryXl(String pid){
		return statisticsDao.getXl(pid);
		
	}
	@Override
	public  List<Map<String, Object>> queryRs(String pid,String stu_id){
		return statisticsDao.getRs(pid,stu_id);
	}
	@Override
	public  List<Map<String, Object>> querySyd(String pid,String stu_id){
		return statisticsDao.getSyd(pid,stu_id);
	}
	@Override
	public List<Map<String, Object>> queryNl(String pid,String stu_id){
		return statisticsDao.getNl(pid,stu_id);
	}
	@Override
	public List<Map<String, Object>> queryRydb(String pid){
		return statisticsDao.getRydb(pid);
	}
	@Override
	public List<Map<String, Object>> queryZzmm(String pid,String stu_id){
		return statisticsDao.getZzmm(pid,stu_id);
	}
	@Override
	public List<Map<String, Object>> queryMz(String pid,String stu_id){
		return statisticsDao.getMz(pid,stu_id);
	}
	@Override
	public List<Map<String, Object>> queryMzCount(String pid,String stu_id){
		return statisticsDao.getMzCount(pid,stu_id);
	}
	public List<Map<String, Object>> queryZzmm1(String pid,String stu_id){
		return statisticsDao.getZzmm1(pid,stu_id);
	}

}
