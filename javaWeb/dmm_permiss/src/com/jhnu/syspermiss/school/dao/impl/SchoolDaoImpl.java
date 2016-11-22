package com.jhnu.syspermiss.school.dao.impl;

import com.jhnu.syspermiss.school.dao.SchoolDao;
import com.jhnu.syspermiss.util.jdbcUtil.BaseDao;


public class SchoolDaoImpl implements SchoolDao{
	private SchoolDaoImpl() {
		
	}  
    private static SchoolDaoImpl SchoolDaoImpl=null;
	
	public static SchoolDaoImpl getInstance() {
		if (SchoolDaoImpl == null){
			synchronized (new String()) {
				if (SchoolDaoImpl == null){
					SchoolDaoImpl = new SchoolDaoImpl();
				}
			}
		}
		return SchoolDaoImpl;
	}
	private BaseDao baseDao=BaseDao.getInstance();

	@Override
	public String getStartSchool(String year, String term) {
		String sql="select start_date from t_school_start where school_year=? and term_code=? ";
		return baseDao.queryForObject(sql, String.class, new Object[] {year,term});
	}

}
