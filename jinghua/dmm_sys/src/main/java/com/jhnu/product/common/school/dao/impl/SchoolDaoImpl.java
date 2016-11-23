package com.jhnu.product.common.school.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.common.school.dao.SchoolDao;

@Repository("schoolDao")
public class SchoolDaoImpl implements SchoolDao{
	@Autowired
	private BaseDao baseDao;

	@Override
	public String getStartSchool(String year, String term) {
		String sql="select start_date from t_school_start where school_year=? and term_code=? ";
		return baseDao.getBaseDao().getJdbcTemplate().queryForObject(sql, new Object[] {year,term}, String.class);
	}

}
