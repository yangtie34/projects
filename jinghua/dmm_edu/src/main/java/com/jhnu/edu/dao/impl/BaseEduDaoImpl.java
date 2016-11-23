package com.jhnu.edu.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.edu.dao.BaseEduDao;
import com.jhnu.edu.util.DataCovert;
import com.jhnu.framework.base.dao.BaseDao;

@Repository("baseEduDao")
public class BaseEduDaoImpl implements BaseEduDao {
	@Autowired
	private BaseDao baseDao;

	@Override
	public Map<String, Object> getAval(String schId, String titleId,
			String start, String end) {
		String sql = getSql(schId, getInIds(new String[] { titleId }), start,
				end);
		return DataCovert.coverList(baseDao.queryForList(sql));
	}

	@Override
	public Map<String, Object> getValsInIds(String schId, String[] titleId,
			String start, String end) {
		String sql = getSql(schId, getInIds(titleId), start, end);

		return DataCovert.coverList(baseDao.queryForList(sql));
	}
	@Override
	public Map<String, Object> getValsInIds(String schId, String[] titleId) {
		String sql = getSql(schId, getInIds(titleId), "", "").replace("and ts.year_ =''","");

		return DataCovert.coverList(baseDao.queryForList(sql));
	}
	@Override
	public Map<String, Object> getValsLikeId(String schId, String titleId,
			String start, String end) {
		String sql = getSql(schId, "", start, end).replace("in()",
				"like '" + titleId + "%'");
		return DataCovert.coverList(baseDao.queryForList(sql));
	}
	@Override
	public Map<String, Object> getValsLikeId(String schId, String titleId) {
		String sql = getSql(schId, "", "", "").replace("and ts.year_ =''","").replace("in()",
				"like '" + titleId + "%'");
		return DataCovert.coverList(baseDao.queryForList(sql));
	}
	@Override
	public Map<String, Object> getVals(String schId,
			String start, String end) {
		String sql = getSql(schId, "", start, end).replace("and ts.titleid in()",
				"");
		return DataCovert.coverList(baseDao.queryForList(sql));
	}
	public String getInIds(String[] titleId) {
		String inIds = "";
		for (int i = 0; i < titleId.length; i++) {
			inIds += ",'" + titleId[i] + "'";
		}
		return inIds.replaceFirst(",", "");
	}

	public String getSql(String schId, String titleIds, String start, String end) {
		String time= " and ts.year_ between '" + start + "' and '" + end + "'";
		boolean group=true;
		if(start.equalsIgnoreCase(end)){
			 time= " and ts.year_ ='" + start + "'";
			 group=false;
		}
		String sql = "select distinct ts.titleid id, "
				+ " tct.name_ name,  "
				+ " CASE tct.ISCODE WHEN 0 THEN ts.value_ ELSE tc.name_ END as value "
				+ " from  T_EDU_SCHOOL_DETAILS ts "
				+ " left join t_edu_code_title tct on tct.code_=ts.titleid  "
				+ " left join t_edu_code tc on tc.code_=ts.value_ and tc.code_type=tct.code_type "
				+ " where ts.schoolid='" + schId + "'"
				+ time
				+ " and ts.titleid in(" + titleIds + ")";
		if(group){
			sql="select id,name,sum(value) value from ("+sql+")group by id,name";
		}
		return sql;
	}
}
