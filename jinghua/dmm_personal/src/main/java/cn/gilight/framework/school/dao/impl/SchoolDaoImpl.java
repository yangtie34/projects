package cn.gilight.framework.school.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.school.dao.SchoolDao;
import cn.gilight.framework.uitl.common.MapUtils;



@Repository("schoolDao")
public class SchoolDaoImpl implements SchoolDao{
	@Autowired
	private BaseDao baseDao;

	@Override
	public String getStartSchool(String year, String term) {
		String sql="select start_date from t_school_start where school_year='"+year+"' and term_code='"+term+"'";
		List<Map<String,Object>> list = baseDao.queryForList(sql);
		String result = "";
		if(list != null && list.size()>0){
			result = MapUtils.getString(list.get(0), "START_DATE");
		}
		return result;
	}

}
