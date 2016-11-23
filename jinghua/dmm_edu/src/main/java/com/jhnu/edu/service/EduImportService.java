package com.jhnu.edu.service;


import java.util.List;
import java.util.Map;

import com.jhnu.edu.entity.TEDUSCHOOLDETAILS;
import com.jhnu.edu.entity.TEDUSCHOOLS;

public interface EduImportService {
	/*
	 * 插入school
	 */
	public void insertSchool(TEDUSCHOOLS school);
	/*
	 * 插入school
	 */
	public void insertSchooldetails(List<TEDUSCHOOLDETAILS> schooldetails);
}

