package com.jhnu.product.manager.teacher.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.manager.teacher.dao.TeachingTaskStatisticsDao;
import com.jhnu.product.manager.teacher.service.TeachingTaskStatisticsService;
import com.jhnu.util.common.StringUtils;
import com.jhnu.util.product.EduUtils;

@Service("teachingTaskStatisticsService")
public class TeachingTaskStatisticsServiceImpl implements TeachingTaskStatisticsService{

	@Autowired
	private TeachingTaskStatisticsDao teachingTaskStatisticsDao;
	
	@Override
	public List<Map<String, Object>> getZcJxrw(String school_year,String term_code,String dept_id) {
		String[] strs = EduUtils.getSchoolYearTerm(new Date());
		if(!StringUtils.hasText(school_year)){
			school_year = strs[0];
		}
		if(!StringUtils.hasText(term_code)){
			term_code = strs[1];
		}
		return teachingTaskStatisticsDao.getZcJxrw(school_year, term_code, dept_id);
	}

	@Override
	public List<Map<String, Object>> getBzlbJxrw(String school_year,String term_code,String dept_id) {
		String[] strs = EduUtils.getSchoolYearTerm(new Date());
		if(!StringUtils.hasText(school_year)){
			school_year = strs[0];
		}
		if(!StringUtils.hasText(term_code)){
			term_code = strs[1];
		}
		return teachingTaskStatisticsDao.getBzlbJxrw(school_year, term_code, dept_id);
	}

	@Override
	public List<Map<String, Object>> getXyZcJxrw(String dept_id,String school_year,String term_code) {
		String[] strs = EduUtils.getSchoolYearTerm(new Date());
		if(!StringUtils.hasText(school_year)){
			school_year = strs[0];
		}
		if(!StringUtils.hasText(term_code)){
			term_code = strs[1];
		}
		return teachingTaskStatisticsDao.getXyZcJxrw(dept_id,school_year, term_code);
	}

	@Override
	public List<Map<String, Object>> getXyBzlbJxrw(String dept_id,String school_year,String term_code) {
		String[] strs = EduUtils.getSchoolYearTerm(new Date());
		if(!StringUtils.hasText(school_year)){
			school_year = strs[0];
		}
		if(!StringUtils.hasText(term_code)){
			term_code = strs[1];
		}
		return teachingTaskStatisticsDao.getXyBzlbJxrw(dept_id,school_year, term_code);
	}

}
