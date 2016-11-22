package com.jhnu.product.manager.score.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.manager.score.dao.ScoreAndLibraryDao;
import com.jhnu.util.common.MapUtils;
import com.jhnu.util.common.StringUtils;

@Repository("scoreAndLibraryDao")
public class ScoreAndLibraryDaoImpl implements ScoreAndLibraryDao{

	@Autowired
	private BaseDao baseDao;
	
	@Override
	public List<Map<String, Object>> getScoreAndLibrary(String school_year,String term_code) {
		
		String sql = "select s.stu_id,stu.sex_code,stu.dept_id,stu.major_id,s.school_year,s.term_code,br.numbers,s.score_avg from ("
				+ " select t.stu_id,t.school_year,t.term_code ,nvl(round(avg(t.centesimal_score),2),0) score_avg from"
				+ " t_stu_score t where t.school_year='"+ school_year +"' and t.term_code = '"+ term_code +"' and t.hierarchical_score_code is null group by t.stu_id,t.school_year,t.term_code order by t.stu_id desc) s"
				+ " inner join  (select rke.book_reader_id stu_id,rke.school_year,rke.term_code,count(*) numbers from t_book_rke rke "
				+ " where rke.school_year='"+ school_year +"' and rke.term_code = '"+ term_code +"'"
				+ " group by rke.school_year,rke.term_code,rke.book_reader_id order by rke.book_reader_id desc) br on s.stu_id = br.stu_id"
				+ " left join t_stu stu on stu.no_ = s.stu_id";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public void saveScoreAndLibraryLog(List<Map<String, Object>> results) {
		final int COUNT = results.size();
		final List<Map<String,Object>> LIST=results;
		final String sql = "insert into tl_score_library values(?,?,?,?,?,?,?,?)";
        baseDao.getLogDao().getJdbcTemplate().batchUpdate(sql,
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
			        	ps.setString(1, MapUtils.getString(LIST.get(i),"STU_ID").toString());    
		                ps.setString(2, MapUtils.getString(LIST.get(i),"SEX_CODE").toString());    
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
	public List<Map<String, Object>> getBoyScoreAndLibraryLog(String school_year, String term_code, String dept_id,boolean isLeaf) {
		String sql2 = "";
		if(!StringUtils.hasText(dept_id)){
			return null;
		}else if("0".equals(dept_id)){
			sql2 = "";//显示全校数据
		}else if(!dept_id.contains(",")){
			sql2="and (sl.dept_id = '"+ dept_id +"' or sl.major_id = '"+ dept_id +"' )";//显示某个院系或某个专业数据
		}else{
			if(isLeaf){
				sql2 = "and sl.major_id in ("+ dept_id +") "; //显示某些专业数据
			}else{
				sql2 = "and sl.dept_id in ("+ dept_id +") "; //显示某些学院数据
			}
		}
		
		String sql = "select sl.stu_id,sl.numbers ,sl.score_avg from tl_score_library sl where sl.school_year = '"+ school_year +"' and sl.term_code = '"+ term_code +"' and sl.stu_sex = '1'" + sql2;
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getGrilScoreAndLibraryLog(String school_year, String term_code, String dept_id,boolean isLeaf) {
		String sql2 = "";
		if(!StringUtils.hasText(dept_id)){
			return null;
		}else if("0".equals(dept_id)){
			sql2 = "";//显示全校数据
		}else if(!dept_id.contains(",")){
			sql2="and (sl.dept_id = '"+ dept_id +"' or sl.major_id = '"+ dept_id +"' )";//显示某个院系或某个专业数据
		}else{
			if(isLeaf){
				sql2 = "and sl.major_id in ("+ dept_id +") "; //显示某些专业数据
			}else{
				sql2 = "and sl.dept_id in ("+ dept_id +") "; //显示某些学院数据
			}
		}
		
		String sql = "select sl.stu_id,sl.numbers ,sl.score_avg from tl_score_library sl where sl.school_year = '"+ school_year +"' and sl.term_code = '"+ term_code +"' and sl.stu_sex = '2'" + sql2;
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql);
	}

}
