package com.jhnu.edu.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.edu.dao.EduImportDao;
import com.jhnu.edu.entity.TEDUSCHOOLDETAILS;
import com.jhnu.edu.entity.TEDUSCHOOLS;
import com.jhnu.framework.base.dao.BaseDao;
import com.jhnu.framework.util.getSql.SqlByBean;
import com.jhnu.framework.util.getSql.SqlByBeans;


@Repository("eduImportDao")
public class EduImportDaoImpl implements EduImportDao {
	@Autowired
	private BaseDao baseDao;

	@Override
	public void insertSchool(TEDUSCHOOLS school) {
		String sql=new SqlByBean().getInsertSql(school);
		baseDao.execute(sql);
	}
	@Override
	public void insertSchooldetails(List<TEDUSCHOOLDETAILS> schooldetails) {
		String sql=new SqlByBeans().insertSql(schooldetails);
		baseDao.execute(sql);
	}
}
