package com.jhnu.product.manager.student.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.manager.student.dao.StuScoreDao;
import com.jhnu.util.common.MapUtils;
import com.jhnu.util.common.StringUtils;

@Repository("stuScoreDao")
public class StuScoreDaoImpl implements StuScoreDao{

	@Autowired
	private BaseDao baseDao;
	
	@Override
	public List<Map<String,Object>> getDept(){
		String sql = "select * from t_code_dept_teach where level_ = 1 and istrue = 1";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	
	@Override
	public List<Map<String,Object>> getMajor(String dept_id){
		String sql = "select * from t_code_dept_teach where level_ = 2 and istrue = 1 and pid = '"+ dept_id +"' order by id";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	
	@Override
	public List<Map<String, Object>> getDeptScore(String school_year,String term_code) {
		String sql = "select th.id dept_id,th.name_ dept_name,nvl(t.centesimal_score,0) score from t_stu_score t "
				+ " inner join t_stu stu on t.stu_id = stu.no_"
				+ " left join t_code_dept_teach th on th.id = stu.dept_id"
				+ " where t.school_year = '"+ school_year+"' and term_code = '"+ term_code +"' and t.hierarchical_score_code is null"
				+ " order by dept_id";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getMajorScore(String school_year,String term_code) {
		String sql = "select th.id major_id,th.name_ major_name,nvl(t.centesimal_score,0) score from t_stu_score t "
				+ " inner join t_stu stu on t.stu_id = stu.no_"
				+ " left join t_code_dept_teach th on th.id = stu.major_id"
				+ " where t.school_year = '"+ school_year +"' and term_code = '"+ term_code +"' and t.hierarchical_score_code is null"
				+ " order by major_id";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public void saveStuScoreLog(List<Map<String, Object>> results) {
		final int COUNT = results.size();
		final List<Map<String,Object>> LIST=results;
		final String sql = "insert into tl_score_dept_major values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        baseDao.getLogDao().getJdbcTemplate().batchUpdate(sql,
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
			        	ps.setString(1, MapUtils.getString(LIST.get(i),"ID").toString());    
		                ps.setString(2, MapUtils.getString(LIST.get(i),"PID").toString());    
		                ps.setString(3, MapUtils.getString(LIST.get(i),"MC").toString()); 
		                ps.setString(4, MapUtils.getString(LIST.get(i),"LEVEL_").toString());    
		                ps.setString(5, MapUtils.getString(LIST.get(i),"SCHOOL_YEAR").toString());  
		                ps.setString(6, MapUtils.getString(LIST.get(i),"TERM_CODE").toString());  
		                ps.setString(7, MapUtils.getDouble(LIST.get(i),"AVG").toString());  
		                ps.setString(8, MapUtils.getDouble(LIST.get(i),"MAX").toString());  
		                ps.setString(9, MapUtils.getDouble(LIST.get(i),"JC").toString());  
		                ps.setString(10, MapUtils.getDouble(LIST.get(i),"ZWS").toString());  
		                ps.setString(11, MapUtils.getDouble(LIST.get(i),"FC").toString());  
		                ps.setString(12, MapUtils.getDouble(LIST.get(i),"BZC").toString());  
		                ps.setString(13, MapUtils.getDouble(LIST.get(i),"HGL").toString());  
		                ps.setString(14, MapUtils.getDouble(LIST.get(i),"YXL").toString());  
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 });
	}

	@Override
	public List<Map<String, Object>> getScoreLog(String dept_id,boolean isLeaf,String school_year, String term_code) {
		String sql2 = "";
		if(!StringUtils.hasText(dept_id)){
			return null;
		}else if(!dept_id.contains(",")){
			if(isLeaf){
				sql2="and dm.id = '"+ dept_id +"'";
			}else{
				sql2="and dm.pid = '"+ dept_id +"'";
			}
		}else{
			sql2 = "and dm.id in ("+ dept_id +") "; 
		}
		
		String sql = "select * from tl_score_dept_major dm where dm.school_year = '"+ school_year +"' and dm.term_code = '"+ term_code +"'" + sql2;
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getTermScoreByDept(String dept_id) {
		String sql = "select * from tl_score_dept_major dm where id = '"+ dept_id +"'";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getScoreFb(String dept_id,String school_year, String term_code) {
		String sql = "select t.score,sum(t.numbers) numbers from "
				+ " (select case  "
				+ " when s.score >=90  then '90-100'"
				+ " when s.score < 90 and s.score >= 80 then '80-89'"
				+ " when s.score < 80 and s.score >=70 then '70-79'"
				+ " when s.score <70 and s.score >=60 then '60-69' "
				+ " when s.score <60 then '0-59'  end as score,  numbers"
				+ " from (select nvl(sc.centesimal_score,0) score,count(*) numbers from t_stu_score sc"
				+ " left join t_stu stu on stu.no_ = sc.stu_id "
				+ " where sc.school_year = '"+ school_year +"' and sc.term_code = '"+ term_code +"'"
				+ " and (stu.dept_id = '"+ dept_id +"' or stu.major_id = '"+ dept_id +"')"
				+ " group by nvl(sc.centesimal_score,0) order by nvl(sc.centesimal_score,0) desc) s) t group by t.score order by score desc";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	
	

}
