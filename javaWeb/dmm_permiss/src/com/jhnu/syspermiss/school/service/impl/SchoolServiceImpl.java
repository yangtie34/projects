package com.jhnu.syspermiss.school.service.impl;

import com.jhnu.syspermiss.school.dao.SchoolDao;
import com.jhnu.syspermiss.school.dao.impl.SchoolDaoImpl;
import com.jhnu.syspermiss.school.service.SchoolService;


public class SchoolServiceImpl implements SchoolService{
	private SchoolServiceImpl() {
		
	}  
    private static SchoolServiceImpl SchoolServiceImpl=null;
	
	public static SchoolServiceImpl getInstance() {
		if (SchoolServiceImpl == null){
			synchronized (new String()) {
				if (SchoolServiceImpl == null){
					SchoolServiceImpl = new SchoolServiceImpl();
				}
			}
		}
		return SchoolServiceImpl;
	}
	private SchoolDao schoolDao= SchoolDaoImpl.getInstance();

	@Override
	public String getStartSchool(String year, String term) {
		return schoolDao.getStartSchool(year, term);
	}
	

}
