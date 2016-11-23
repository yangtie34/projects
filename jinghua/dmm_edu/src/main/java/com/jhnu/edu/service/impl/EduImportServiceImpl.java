package com.jhnu.edu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.edu.dao.EduImportDao;
import com.jhnu.edu.entity.TEDUSCHOOLDETAILS;
import com.jhnu.edu.entity.TEDUSCHOOLS;
import com.jhnu.edu.service.EduImportService;


@Repository("eduImportService")
public class EduImportServiceImpl implements EduImportService {
	@Autowired
	private EduImportDao eduImportDao;

	@Override
	public void insertSchool(TEDUSCHOOLS school) {
		eduImportDao.insertSchool(school);
	}

	@Override
	public void insertSchooldetails(List<TEDUSCHOOLDETAILS> schooldetails) {
		 eduImportDao.insertSchooldetails(schooldetails);
	}

}
