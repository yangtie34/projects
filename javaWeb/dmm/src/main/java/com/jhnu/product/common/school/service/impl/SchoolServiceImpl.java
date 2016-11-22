package com.jhnu.product.common.school.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.common.school.dao.SchoolDao;
import com.jhnu.product.common.school.service.SchoolService;

@Service("schoolService")
public class SchoolServiceImpl implements SchoolService{
	@Autowired
	private SchoolDao schoolDao;

	@Override
	public String getStartSchool(String year, String term) {
		return schoolDao.getStartSchool(year, term);
	}
	

}
