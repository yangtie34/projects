package com.jhnu.product.manager.score.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.manager.score.dao.ScoreAndBreakfastDao;
import com.jhnu.util.common.MapUtils;
import com.jhnu.util.common.StringUtils;
import com.jhnu.util.product.EduUtils;

@Repository("scoreAndBreakfastDao")
public class ScoreAndBreakfastDaoImpl implements ScoreAndBreakfastDao{
	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Map<String, Object>> getScoreAndBreakfast(String school_year,String term_code) {
		String startDate = EduUtils.getTimeQuantum(school_year, term_code)[0];
		String endDate = EduUtils.getTimeQuantum(school_year, term_code)[1];
		
		String sql = "select stu.no_ stu_id,stu.sex_code stu_sex,stu.dept_id,stu.major_id,s.school_year,s.term_code,r.numbers,s.score_avg from ("
				+ " select d.card_id,count(*) numbers from ("
				+ " select t.card_id,t.pay_date from ("
				+ " select pay.card_id,substr(pay.time_ ,0,10) pay_date ,substr(pay.time_,12,2) pay_time from t_card_pay pay "
				+ " where pay.time_ between '"+ startDate +"' and '"+ endDate +"' ) t where t.pay_time <= 9 "
				+ " group by t.card_id,t.pay_date ) d group by d.card_id order by numbers desc ) r "
				+ " left join t_card card on card.no_ = r.card_id"
				+ " left join t_stu stu on stu.no_ = card.people_id"
				+ " inner join (select t.stu_id,t.school_year,t.term_code ,nvl(round(avg(t.centesimal_score),2),0) score_avg from"
				+ " t_stu_score t where t.school_year='"+ school_year +"' and t.term_code = '"+ term_code +"' and t.hierarchical_score_code is null "
				+ " group by t.stu_id,t.school_year,t.term_code order by t.stu_id desc) s on s.stu_id = stu.no_";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public void saveScoreAndBreakfast(List<Map<String, Object>> results) {
		final int COUNT = results.size();
		final List<Map<String,Object>> LIST=results;
		final String sql = "insert into tl_score_breakfast values(?,?,?,?,?,?,?,?)";
        baseDao.getLogDao().getJdbcTemplate().batchUpdate(sql,
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
			        	ps.setString(1, MapUtils.getString(LIST.get(i),"STU_ID").toString());    
		                ps.setString(2, MapUtils.getString(LIST.get(i),"STU_SEX").toString());    
		                ps.setString(3, MapUtils.getString(LIST.get(i),"DEPT_ID").toString()); 
		                ps.setString(4, MapUtils.getString(LIST.get(i),"MAJOR_ID").toString());    
		                ps.setString(5, MapUtils.getString(LIST.get(i),"SCHOOL_YEAR").toString());  
		                ps.setString(6, MapUtils.getString(LIST.get(i),"TERM_CODE").toString());  
		                ps.setString(7, MapUtils.getString(LIST.get(i),"NUMBERS").toString());  
		                ps.setString(8, MapUtils.getDouble(LIST.get(i),"SCORE_AVG").toString());  
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 });
	}

	@Override
	public List<Map<String, Object>> getBoyScoreAndBreakfastLog(String school_year, String term_code, String dept_id,boolean isLeaf) {
		String sql2 = "";
		if(!StringUtils.hasText(dept_id)){
			return null;
		}else if("0".equals(dept_id)){
			sql2 = "";//显示全校数据
		}else if(!dept_id.contains(",")){
			sql2="and (sb.dept_id = '"+ dept_id +"' or sb.major_id = '"+ dept_id +"' )";//显示某个院系或某个专业数据
		}else{
			if(isLeaf){
				sql2 = "and sb.major_id in ("+ dept_id +") "; //显示某些专业数据
			}else{
				sql2 = "and sb.dept_id in ("+ dept_id +") "; //显示某些学院数据
			}
		}

		String sql = "select sb.stu_id,sb.numbers ,sb.score_avg from tl_score_breakfast sb where sb.school_year = '"+ school_year +"' and sb.term_code = '"+ term_code +"' and sb.stu_sex = '1'" + sql2;
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getGrilScoreAndBreakfastLog(String school_year, String term_code, String dept_id,boolean isLeaf) {
		String sql2 = "";
		if(!StringUtils.hasText(dept_id)){
			return null;
		}else if("0".equals(dept_id)){
			sql2 = "";//显示全校数据
		}else if(!dept_id.contains(",")){
			sql2="and (sb.dept_id = '"+ dept_id +"' or sb.major_id = '"+ dept_id +"' )";//显示某个院系或某个专业数据
		}else{
			if(isLeaf){
				sql2 = "and sb.major_id in ("+ dept_id +") "; //显示某些专业数据
			}else{
				sql2 = "and sb.dept_id in ("+ dept_id +") "; //显示某些学院数据
			}
		}
		
		String sql = "select sb.stu_id,sb.numbers ,sb.score_avg from tl_score_breakfast sb where sb.school_year = '"+ school_year +"' and sb.term_code = '"+ term_code +"' and sb.stu_sex = '2'" + sql2;
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql);
	}

}
