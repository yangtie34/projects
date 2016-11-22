package com.jhnu.edu.service.impl;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.edu.dao.SchoolQJDao;
import com.jhnu.edu.service.SchoolQJService;

@Repository("schoolQJService")
public class SchoolQJServiceImpl implements SchoolQJService {

	@Autowired
	private SchoolQJDao schoolQJDao;

	@Override
	public Map<String, Object> getSchoolInfo(String schoolId) {
		return schoolQJDao.getSchoolInfo(schoolId);
	}

	@Override
	public Map<String, Object> getSchoolInfoDetails(String schoolId) {
		return schoolQJDao.getSchoolInfoDetails(schoolId);
	}

	@Override
	public Map<String, Object> getSchoolInfoDetails(String schoolId,
			List<String> titleIds, String start, String end) {
		int size = titleIds.size();  
		String[] arr = (String[])titleIds.toArray(new String[size]);
		return schoolQJDao.getSchoolInfoDetails(schoolId,arr,start,end);
	}

	@Override
	public Map<String, Object> getSchoolInfoDetails(String schoolId,
			List<String> titleIds) {
		int size = titleIds.size();  
		String[] arr = (String[])titleIds.toArray(new String[size]);
		return schoolQJDao.getSchoolInfoDetails(schoolId,arr);
	}

	
}
