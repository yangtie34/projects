package com.jhnu.product.four.score.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.four.score.dao.FourScoreDao;
import com.jhnu.system.common.page.Page;
import com.jhnu.util.common.MapUtils;

@Repository("fourScoreDao")
public class FourScoreDaoImpl implements FourScoreDao{

	@Autowired
	private BaseDao baseDao;
	
	@Override
	public List<Map<String, Object>> getAvgScore() {
		String sql="select e.*,rownum r from ( "+
					"select nvl(round(t.av,2),0) avg_,st.no_ stu_id,st.major_id major_id, "+
					"(st.enroll_grade+st.length_schooling) end_year from t_stu st left join  "+
					"(select avg(d.centesimal_score) av ,s.no_ from t_stu_score d  "+
					"inner join t_stu s on d.stu_id=s.no_ "+
					"where d.hierarchical_score_code is null "+
					"group by s.no_) t on st.no_=t.no_ "+
					"where st.stu_state_code='1' "+
					"order by end_year,major_id ,avg_)e ";
		return baseDao.getBaseDao().querySqlList(sql);
	}

	@Override
	public void saveAvgScoreLog(List<Map<String, Object>> list) {
		final int COUNT = list.size();
		final List<Map<String,Object>> LIST=list;
		String delSql="delete tl_score_avg";
		baseDao.getLogDao().executeSql(delSql);
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(
				"insert into tl_score_avg values (?, ?, ?, ?,?,?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
		                ps.setString(1, MapUtils.getString(LIST.get(i),"avg_").toString());    
		                ps.setString(2, MapUtils.getString(LIST.get(i),"more_than").toString());    
		                ps.setString(3, MapUtils.getString(LIST.get(i),"avg_all").toString()); 
		                ps.setString(4, MapUtils.getString(LIST.get(i),"major_id").toString());    
		                ps.setString(5, MapUtils.getString(LIST.get(i),"stu_id").toString());  
		                ps.setString(6, MapUtils.getString(LIST.get(i),"end_year").toString());  
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 }); 
	}

	@Override
	public List<Map<String, Object>> getAvgScoreLog(String id) {
		String sql="select * from tl_score_avg where stu_id= ?";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[]{id});
	}
	
	@Override
	public List<Map<String, Object>> getSumScore() {
		String sql="select e.*,rownum r from ( "+
					"select nvl(round(t.sum_,2),0) sum_,st.no_ stu_id,st.major_id major_id, "+
					"(st.enroll_grade+st.length_schooling) end_year from t_stu st left join  "+
					"(select sum(d.centesimal_score) sum_ ,s.no_ from t_stu_score d  "+
					"inner join t_stu s on d.stu_id=s.no_ "+
					"where d.hierarchical_score_code is null "+
					"group by s.no_) t on st.no_=t.no_ "+
					"where st.stu_state_code='1' "+
					"order by end_year,major_id ,sum_)e ";
		return baseDao.getBaseDao().querySqlList(sql);
	}

	@Override
	public void saveSumScoreLog(List<Map<String, Object>> list) {
		final int COUNT = list.size();
		final List<Map<String,Object>> LIST=list;
		String delSql="delete tl_score_sum";
		baseDao.getLogDao().executeSql(delSql);
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(
				"insert into tl_score_sum values (?, ?, ?, ?, ?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
		                ps.setString(1, MapUtils.getString(LIST.get(i),"sum_").toString());    
		                ps.setString(2, MapUtils.getString(LIST.get(i),"more_than").toString());    
		                ps.setString(3, MapUtils.getString(LIST.get(i),"major_id").toString());    
		                ps.setString(4, MapUtils.getString(LIST.get(i),"stu_id").toString());  
		                ps.setString(5, MapUtils.getString(LIST.get(i),"end_year").toString());  
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 }); 
	}

	@Override
	public List<Map<String, Object>> getSumScoreLog(String id) {
		String sql="select * from tl_score_sum where stu_id= ?";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[]{id});
	}
	@Override
	public List<Map<String, Object>> getGPAScoreLog(String id) {
		String sql="select * from tl_score_gpa where stu_id= ?";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[]{id});
	}
	@Override
	public List<Map<String, Object>> getAllAvgScore() {
		String sql="select nvl(round(avg(avg_),2),0) avg_,nvl(count(*),0) count_, end_year,major_id from ( "+
					"select round(t.av,2) avg_,st.no_ stu_id,st.major_id major_id, "+
					"(st.enroll_grade+st.length_schooling) end_year from t_stu st left join  "+
					"(select avg(d.centesimal_score) av ,s.no_ from t_stu_score d  "+
					"inner join t_stu s on d.stu_id=s.no_ "+
					"where (s.Enroll_Grade+s.LENGTH_SCHOOLING)>=to_number(to_char(sysdate,'yyyy')) and d.hierarchical_score_code is null "+
					"group by s.no_) t on st.no_=t.no_ "+
					"where st.stu_state_code='1' "+
					"order by end_year,major_id,avg_ ) group by end_year,major_id  "+
					"order by end_year,major_id ";
		return baseDao.getBaseDao().querySqlList(sql);
	}

	@Override
	public List<Map<String, Object>> getBestScore() {
		String sql="select nvl(round(t2.av,2),0) max_,t2.no_ stu_id,t2.mid major_id,t2.school_year,t2.term_code from "+
						"(select max(av) av,no_ from  "+
						"(select avg(d.CENTESIMAL_SCORE) av ,s.no_ ,s.MAJOR_ID mid,d.school_year,d.term_code from T_STU_SCORE d  "+
						"inner join t_stu s on d.STU_ID=s.no_ "+
						"group by s.no_,s.MAJOR_ID,d.school_year,d.term_code) group by no_ ) t1 "+
						"inner join  "+
						"(select avg(d.CENTESIMAL_SCORE) av ,s.no_ ,s.MAJOR_ID mid,d.school_year,d.term_code from T_STU_SCORE d  "+
						"inner join t_stu s on d.STU_ID=s.no_ "+
						"group by s.no_,s.MAJOR_ID,d.school_year,d.term_code ) t2 "+
						"on (t1.av=t2.av and t1.no_=t2.no_) "+
						"order by t2.av desc";
		return baseDao.getBaseDao().querySqlList(sql);
	}

	@Override
	public void saveBestScoreLog(List<Map<String, Object>> list) {
		final int COUNT = list.size();
		final List<Map<String,Object>> LIST=list;
		String delSql="delete tl_score_best";
		baseDao.getLogDao().executeSql(delSql);
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(
				"insert into tl_score_best values (?, ?, ?, ?,?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
		                ps.setString(1, MapUtils.getString(LIST.get(i),"max_").toString());    
		                ps.setString(2, MapUtils.getString(LIST.get(i),"school_year").toString());    
		                ps.setString(3, MapUtils.getString(LIST.get(i),"term_code").toString()); 
		                ps.setString(4, MapUtils.getString(LIST.get(i),"major_id").toString());    
		                ps.setString(5, MapUtils.getString(LIST.get(i),"stu_id").toString());  
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 }); 
	}

	@Override
	public List<Map<String, Object>> getBestScoreLog(String id) {
		String sql="select * from tl_score_best where stu_id= ?";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[]{id});
	}

	@Override
	public List<Map<String, Object>> getRankByTime(String schoolYear,String termCode) {
		String sql="select e.*,rownum r from ( "+
				"select stu_id,major_id,school_year ,term_code , nvl(round(avg(centesimal_score),2),0) avg_ from "+
				"(select ss.stu_id,ss.school_year,ss.term_code,ss.coure_code,ss.centesimal_score,c.name_,s.major_id  "+
				"from t_stu_score ss inner join t_course c on ss.coure_code=c.code_ "+
				"inner join t_stu s on ss.stu_id=s.no_ "+
				"where ss.school_year='"+schoolYear+"' and ss.term_code='"+termCode+"'  and ss.hierarchical_score_code is null "+
				") group by stu_id , major_id,school_year ,term_code order by major_id ,avg_ desc)e ";
		return baseDao.getBaseDao().querySqlList(sql);
	}

	@Override
	public void saveRankLog(List<Map<String, Object>> list) {
		final int COUNT = list.size();
		final List<Map<String,Object>> LIST=list;
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(
				"insert into tl_score_rank values (?, ?, ?, ?, ?, ?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
		                ps.setDouble(1, MapUtils.getDoubleValue(LIST.get(i),"avg_"));    
		                ps.setInt(2, MapUtils.getIntValue(LIST.get(i),"rank"));   
		                ps.setString(3, MapUtils.getString(LIST.get(i),"school_year").toString());    
		                ps.setString(4, MapUtils.getString(LIST.get(i),"term_code").toString()); 
		                ps.setString(5, MapUtils.getString(LIST.get(i),"major_id").toString());    
		                ps.setString(6, MapUtils.getString(LIST.get(i),"stu_id").toString());  
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 }); 
	}

	@Override
	public List<Map<String, Object>> getRankLogByStuId(String id) {
		String sql="select * from tl_score_rank where stu_id=? order by rank ";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[]{id});
	}

	@Override
	public List<Map<String, Object>> getScoureList(String id,String schoolYear, String termCode) {
		String sql="select ss.stu_id,ss.school_year,ss.term_code,ss.coure_code, "+
				"ss.centesimal_score,c.name_ score_name from  "+
				"T_STU_SCORE ss inner join T_COURSE c on ss.coure_code=c.code_ "+
				"where ss.stu_id='"+id+"' and ss.school_year='"+schoolYear+"'  "+
				"and ss.term_code='"+termCode+"'and ss.hierarchical_score_code is null "+
				"order by centesimal_score desc ";
		return baseDao.getBaseDao().querySqlList(sql);
	}

	@Override
	public Page getBsetCourseByMajor(int currentPage, int numPerPage,
			Integer totalRows, Integer status) {
		String sql="select t.*,c.name_ course_name from ( "+
				"select nvl(round(avg(d.centesimal_score), 2),0) max_,(s.enroll_grade+s.length_schooling) end_year, "+
				"s.major_id ,d.coure_code scode from t_stu_score  d "+
				"inner join t_stu s on d.stu_id=s.no_ "+
				"where d.hierarchical_score_code is null "+
				"group by (s.enroll_grade+s.length_schooling), s.major_id,d.coure_code "+
				"order by end_year,major_id,max_ desc,scode ) t "+
				"inner join t_course c on t.scode=c.code_ ";
		
		String tempTable="t_temp_bsetcoursebyma";
		String tempSql="select * from "+tempTable;
		Page page=null;
		String delSql="delete "+tempTable;
		if(status==0){
			baseDao.getBaseDao().executeSql(delSql);
			sql= "insert into "+tempTable+" value (" + sql+ " )";
			baseDao.getBaseDao().executeSql(sql);
			page=new Page(tempSql, currentPage, numPerPage,baseDao.getBaseDao().getJdbcTemplate(),totalRows) ;
		}else if(status==1){
			page=new Page(tempSql, currentPage, numPerPage,baseDao.getBaseDao().getJdbcTemplate(),totalRows) ;
		}else{
			page=new Page(tempSql, currentPage, numPerPage,baseDao.getBaseDao().getJdbcTemplate(),totalRows) ;
		}
		return page;
	}

	@Override
	public void saveBsetCourseByMajorLog(List<Map<String, Object>> list,boolean isFrist) {
		final int COUNT = list.size();
		final List<Map<String,Object>> LIST=list;
		if(isFrist){
			String delSql="delete tl_course_best_all";
			baseDao.getLogDao().executeSql(delSql);
		}
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(
				"insert into tl_course_best_all values (?, ?, ?, ?, ?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
		        	ps.setDouble(1, MapUtils.getDoubleValue(LIST.get(i),"max_"));
	                ps.setString(2, MapUtils.getString(LIST.get(i),"end_year").toString());    
	                ps.setString(3, MapUtils.getString(LIST.get(i),"major_id").toString());    
	                ps.setString(4, MapUtils.getString(LIST.get(i),"scode").toString());  
	                ps.setString(5, MapUtils.getString(LIST.get(i),"course_name").toString());  
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 }); 
	}

	@Override
	public List<Map<String, Object>> getBsetCourseByMajorLog(String endYear,String MajorId, int order) {
		String sql="select * from (select t.*,rownum r from ( select * from tl_course_best_all where end_year=? "
				+ "and major_id =? order by max_ desc ) t ) where r<= ?";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[]{endYear,MajorId,order});
	}

	@Override
	public Page getBsetCourseByStu(int currentPage, int numPerPage,
			Integer totalRows, Integer status) {
		String sql="select t.*,c.name_ course_name from ( "+
					"select nvl(max(d.CENTESIMAL_SCORE),0) max_,s.no_ stu_id,(s.Enroll_Grade+s.LENGTH_SCHOOLING) end_year,  "+
					"s.MAJOR_ID,d.coure_code scode from T_STU_SCORE  d "+
					"inner join t_stu s on d.STU_ID=s.no_ "+
					"where (s.Enroll_Grade+s.LENGTH_SCHOOLING)>=to_number(to_char(sysdate,'yyyy')) and d.hierarchical_score_code is null "+
					"group by s.no_,(s.Enroll_Grade+s.LENGTH_SCHOOLING), s.MAJOR_ID,d.coure_code "+
					"order by stu_id,max_ desc,scode) t "+
					"inner join T_COURSE c on t.scode=c.code_";
		
		String tempTable="t_temp_bsetcoursebyme";
		String tempSql="select * from "+tempTable;
		Page page=null;
		String delSql="delete "+tempTable;
		if(status==0){
			baseDao.getBaseDao().executeSql(delSql);
			sql= "insert into "+tempTable+" value (" + sql+ " )";
			baseDao.getBaseDao().executeSql(sql);
			page=new Page(tempSql, currentPage, numPerPage,baseDao.getBaseDao().getJdbcTemplate(),totalRows) ;
		}else if(status==1){
			page=new Page(tempSql, currentPage, numPerPage,baseDao.getBaseDao().getJdbcTemplate(),totalRows) ;
		}else{
			page=new Page(tempSql, currentPage, numPerPage,baseDao.getBaseDao().getJdbcTemplate(),totalRows) ;
		}
		return page;
	}

	@Override
	public void saveBsetCourseByStuLog(List<Map<String, Object>> list,boolean isFrist) {
		final int COUNT = list.size();
		final List<Map<String,Object>> LIST=list;
		if(isFrist){
			String delSql="delete tl_course_best";
			baseDao.getLogDao().executeSql(delSql);
		}
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(
				"insert into tl_course_best values (?, ?, ?, ?, ?, ?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
		        	ps.setDouble(1, MapUtils.getDoubleValue(LIST.get(i),"max_"));
		        	ps.setString(2, MapUtils.getString(LIST.get(i),"stu_id").toString());
	                ps.setString(3, MapUtils.getString(LIST.get(i),"end_year").toString());    
	                ps.setString(4, MapUtils.getString(LIST.get(i),"major_id").toString());    
	                ps.setString(5, MapUtils.getString(LIST.get(i),"scode").toString());  
	                ps.setString(6, MapUtils.getString(LIST.get(i),"course_name").toString());  
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 }); 
	}

	@Override
	public List<Map<String, Object>> getBsetCourseByStuLog(String id, int order) {
		String sql="select * from (select t.*,rownum r from ( select * from tl_course_best where stu_id=? order by max_ desc ) t ) where r<= ?";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[]{id,order});
	}

	@Override
	public List<Map<String, Object>> getFirstDown() {
		String sql=" select ss.stu_id,ss.school_year,ss.term_code,ss.coure_code,ss.centesimal_score,c.name_ course_name "+
				" from T_STU_SCORE ss  inner join ( "+
				" select min(t.school_year||t.term_code) yt,t.stu_id from T_STU_SCORE t  "+
				" where t.centesimal_score<60 and t.hierarchical_score_code is null "+
				" group by t.stu_id "+
				" ) a on ((ss.school_year||ss.term_code)=a.yt and ss.stu_id=a.stu_id)  "+
				" inner join T_COURSE c on ss.coure_code=c.code_ "+
				" where ss.centesimal_score<60  and ss.hierarchical_score_code is null ";
	return baseDao.getBaseDao().querySqlList(sql);
}

	@Override
	public void saveFirstDownLog(List<Map<String, Object>> list) {
		final int COUNT = list.size();
		final List<Map<String,Object>> LIST=list;
		String delSql="delete tl_score_first_down";
		baseDao.getLogDao().executeSql(delSql);
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(
				"insert into tl_score_first_down values (?, ?, ?, ?,?,?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
		                ps.setString(1, MapUtils.getString(LIST.get(i),"stu_id").toString());    
		                ps.setString(2, MapUtils.getString(LIST.get(i),"school_year").toString());    
		                ps.setString(3, MapUtils.getString(LIST.get(i),"term_code").toString()); 
		                ps.setString(4, MapUtils.getString(LIST.get(i),"coure_code").toString());    
		                ps.setString(5, MapUtils.getString(LIST.get(i),"centesimal_score").toString());  
		                ps.setString(6, MapUtils.getString(LIST.get(i),"course_name").toString());  
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 }); 
	}

	@Override
	public List<Map<String, Object>> getFirstDownLog(String id) {
		String sql="select * from tl_score_first_down where stu_id=? ";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[]{id});
	}

	@Override
	public void createTempScoreAvg() {
		String cTmpSql = "CREATE TABLE TEMP_SCORE_STUAVG AS "
				+ " SELECT STU_ID,STU.MAJOR_ID,STU.ENROLL_GRADE,SCHOOL_YEAR,TERM_CODE,SUM(CENTESIMAL_SCORE) SUM_SCORE,COUNT(coure_code) COUNT_SCOURE,"
				+ " AVG(CENTESIMAL_SCORE) AVG_SCORE FROM T_STU_SCORE SCORE,T_STU STU WHERE HIERARCHICAL_SCORE_CODE IS NULL "
				+ " AND STU.STU_STATE_CODE=1 AND STU.NO_=SCORE.STU_ID GROUP BY STU_ID,STU.MAJOR_ID,STU.ENROLL_GRADE,SCHOOL_YEAR,TERM_CODE";
		baseDao.getBaseDao().getJdbcTemplate().execute(cTmpSql);
	}
	
	@Override
	public void dropTempScoreAvg() {
		String dTmpSql ="DROP TABLE TEMP_SCORE_STUAVG";
		baseDao.getBaseDao().getJdbcTemplate().execute(dTmpSql);
	}
	
	@Override
	public List<Map<String, Object>> getScoreAvgFromTemp() {
		this.dropTempScoreAvg();
		this.createTempScoreAvg();
		String sql ="SELECT * FROM TEMP_SCORE_STUAVG";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	
	@Override
	public void saveScoreAvgLog(List<Map<String, Object>> list) {
		final List<Map<String, Object>> saveList = list;
		String delSql ="DELETE FROM TL_SCORE_STUAVG";
		baseDao.getLogDao().getJdbcTemplate().execute(delSql);
		String sql ="INSERT INTO TL_SCORE_STUAVG VALUES(?,?,?,?,?,?,?,?)";
		
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, MapUtils.getString(saveList.get(i),"STU_ID").toString());
				ps.setString(2, MapUtils.getString(saveList.get(i),"MAJOR_ID").toString());
				ps.setString(3, MapUtils.getString(saveList.get(i),"ENROLL_GRADE").toString());
				ps.setString(4, MapUtils.getString(saveList.get(i),"SCHOOL_YEAR").toString());
				ps.setString(5, MapUtils.getString(saveList.get(i),"TERM_CODE").toString());
				ps.setString(6, MapUtils.getString(saveList.get(i),"SUM_SCORE")==null?"":MapUtils.getString(saveList.get(i),"SUM_SCORE").toString());
				ps.setString(7, MapUtils.getString(saveList.get(i),"COUNT_SCOURE")==null?"":MapUtils.getString(saveList.get(i),"COUNT_SCOURE").toString());
				ps.setString(8, MapUtils.getString(saveList.get(i),"AVG_SCORE")==null?"":String.format("%.2f",Float.parseFloat(MapUtils.getString(saveList.get(i),"AVG_SCORE").toString())));
			}

			@Override
			public int getBatchSize() {
				return saveList.size();
			}
			
		});
	}
	
	@Override
	public List<Map<String, Object>> getScoreMajorLine(String majorId,String enrollGrade) {
		String sql = "SELECT SCHOOL_YEAR,TERM_CODE,ENROLL_GRADE,MAJOR_ID,ROUND(AVG(AVG_SCORE),2) AVG_ FROM TL_SCORE_STUAVG "
				+ " WHERE ENROLL_GRADE=? AND MAJOR_ID=? GROUP BY SCHOOL_YEAR,TERM_CODE,ENROLL_GRADE,MAJOR_ID";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[] { enrollGrade, majorId });
	}
	
	@Override
	public List<Map<String, Object>> getScoreStuLine(String id) {
		String sql = "SELECT SCHOOL_YEAR,TERM_CODE,ENROLL_GRADE,MAJOR_ID,ROUND(AVG(AVG_SCORE),2) AVG_ FROM TL_SCORE_STUAVG"
				+ " WHERE STU_ID=? GROUP BY SCHOOL_YEAR,TERM_CODE,ENROLL_GRADE,MAJOR_ID";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[] { id });
	}
	
	@Override
	public List<Map<String, Object>> getGPAScore() {
		String sql = "select rownum r,t.* from (select stu_id,major_id,end_year, sum(centesimal_score) as zcj, round(avg(centesimal_score), 2) pjcj,round(sum(hdxf*THEORY_CREDIT)/sum(THEORY_CREDIT) ,2) gpa "
				+ " from (select sc.*,case when sc.centesimal_score between 90 and 100 then 4 when sc.centesimal_score between 85 and 89 then 3.7 "
				+ " when sc.centesimal_score between 82 and 84 then 3.3 when sc.centesimal_score between 78 and 81 then 3.0 "
				+ " when sc.centesimal_score between 75 and 77 then 2.7 when sc.centesimal_score between 71 and 74 then 2.3 when sc.centesimal_score between 66 and 70 then 2.0 "
				+ " when sc.centesimal_score between 62 and 65 then 1.7 when sc.centesimal_score between 60 and 61 then 1.3 when sc.centesimal_score between 0 and 59 then 0 "
				+ " end as hdxf,cou.THEORY_CREDIT,stu.major_id,(stu.enroll_grade +stu.length_schooling) end_year from t_stu_score sc left join t_stu stu on stu.no_ = sc.stu_id "
				+ " left join t_course cou on sc.coure_code = cou.code_ where sc.hierarchical_score_code is null) group by stu_id,major_id,end_year order by end_year,major_id,gpa)t where end_year>=to_number(to_char(sysdate,'yyyy')) and end_year is not null";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	
	@Override
	public void saveGPAScore(final List<Map<String, Object>> saveList) {
		String delSql ="DELETE FROM TL_SCORE_GPA";
		baseDao.getLogDao().getJdbcTemplate().execute(delSql);
		String sql ="INSERT INTO TL_SCORE_GPA VALUES(?,?,?,?,?)";
		
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, MapUtils.getString(saveList.get(i),"GPA").toString());
				ps.setString(2, MapUtils.getString(saveList.get(i),"MORE_THAN").toString());
				ps.setString(3, MapUtils.getString(saveList.get(i),"MAJOR_ID").toString());
				ps.setString(4, MapUtils.getString(saveList.get(i),"STU_ID").toString());
				ps.setString(5, MapUtils.getString(saveList.get(i),"END_YEAR").toString());
			}

			@Override
			public int getBatchSize() {
				return saveList.size();
			}
			
		});
	}

	@Override
	public Page getScoreDetailLog(int currentPage, int numPerPage, String tj) {
		String sql="select * from tl_score_detail where 1=1 "+ tj;
		Page page = new Page(sql, currentPage, numPerPage, baseDao.getLogDao().getJdbcTemplate(),null);
		return page;
	}

	@Override
	public List<Map<String, Object>> getScoreDetailGroupBySY(String id) {
		String sql="select t.school_year id, t.school_year||'学年' mc from tl_score_detail t where stu_id ='"+id+"' group by t.school_year order by id";
		return baseDao.getLogDao().queryListMapInLowerKeyBySql(sql);
	}
}
