package com.jhnu.product.warn.score.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.warn.score.dao.ScoreWarnDao;

@Repository("scoreWarnDao")
public class ScoreWarnDaoImpl implements ScoreWarnDao{

	@Autowired
	private BaseDao baseDao;
	
	@Override
	public List<Map<String, Object>> getScores(String stuId) {
		String sql = "select t.stu_id,t.school_year,t.term_code,course.name_,t.centesimal_score from t_stu_score t "
				+ " left join t_course course on t.coure_code = course.code_"
				+ " where t.stu_id = ? and t.centesimal_score<60 ";
		
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql, new Object[]{stuId});
	}

}
