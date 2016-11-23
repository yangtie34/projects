package com.jhnu.edu.dao.impl;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.edu.dao.BaseEduDao;
import com.jhnu.edu.dao.SchoolQJDao;
import com.jhnu.edu.entity.TEDUSCHOOLS;
import com.jhnu.edu.util.DataCovert;
import com.jhnu.edu.util.SqlByEdu;
import com.jhnu.framework.base.dao.BaseDao;
import com.jhnu.syspermiss.util.DateUtils;

@Repository("schoolQJDao")
public class SchoolQJDaoImpl implements SchoolQJDao {

	@Autowired
	private BaseEduDao baseEduDao;
	@Autowired
	private BaseDao baseDao;

	@Override
	public Map<String, Object> getSchoolInfo(String schoolId) {
		TEDUSCHOOLS sch=new TEDUSCHOOLS();
		sch.setId(schoolId);
		String sql=SqlByEdu.getQuerySql(sch);
		List<Map<String, Object>> list=DataCovert.coverList(baseDao.queryForList(sql),sch);
		return  list.get(0);
	}

	@Override
	public Map<String, Object> getSchoolInfoDetails(String schoolId) {
		return baseEduDao.getValsLikeId(schoolId, "11");
	}

	@Override
	public Map<String, Object> getSchoolInfoDetails(String schoolId,
			String[] titleIds, String start, String end) {
		return baseEduDao.getValsInIds(schoolId, titleIds, start, end);
	}

	@Override
	public Map<String, Object> getSchoolInfoDetails(String schoolId,
			String[] titleIds) {
		String year=Integer.parseInt(DateUtils.getNowYear())-1+"";
		return baseEduDao.getValsInIds(schoolId, titleIds, year, year);
	}

	
}
