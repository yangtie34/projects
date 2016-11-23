package com.jhnu.edu.dao;

import java.util.List;

import com.jhnu.edu.entity.TEDUSCHOOLDETAILS;
import com.jhnu.edu.entity.TEDUSCHOOLS;


public interface EduImportDao {
	/**
	 * 插入school
	 */
	public void insertSchool(TEDUSCHOOLS school);
	/**
	 * 插入school详情
	 */
	public void insertSchooldetails(List<TEDUSCHOOLDETAILS> schooldetails);
}
