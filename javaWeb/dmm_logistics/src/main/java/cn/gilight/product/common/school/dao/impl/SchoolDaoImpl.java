package cn.gilight.product.common.school.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.product.common.school.dao.SchoolDao;


@Repository("schoolDao")
public class SchoolDaoImpl implements SchoolDao{
	@Autowired
	private BaseDao baseDao;

	@Override
	public String getStartSchool(String year, String term) {
		String sql="select start_date from t_school_start where school_year=? and term_code=? ";
		return baseDao.getJdbcTemplate().queryForObject(sql, new Object[] {year,term}, String.class);
	}

}
