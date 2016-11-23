package cn.gilight.personal.job.course.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.personal.job.course.dao.CourseDao;


@Repository("CourseDao")
public class CourseDaoImpl implements CourseDao{


	@Autowired
	private BaseDao baseDao;
	
	@Override
	public void updateCourseWeek(final List<Map<String, Object>> zcList) {
		final int COUNT = zcList.size();
		baseDao.batchUpdate(
				"update t_course_arrangement set weeks=?,period_start=?,period_end=? where id=?",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
		                ps.setString(1,MapUtils.getString(zcList.get(i), "WEEKS"));
		                ps.setString(2,MapUtils.getString(zcList.get(i), "FIRST_PERIOD"));
		                ps.setString(3,MapUtils.getString(zcList.get(i), "END_PERIOD")); 
		                ps.setString(4,MapUtils.getString(zcList.get(i), "COURSE_ARRANGEMENT_ID")); 
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 }); 
	}

	@Override
	public List<Map<String, Object>> getCourseWeek(String school_year,String term_code) {
		String sql="select id,week_start_end,t.lecture_type_code lecture_type,case when instr(period,'-')>0 then substr(substr(period,1,instr(period,'-')-1),2) else '' end first_period,"
				+ " case when instr(period,'-')>0 then substr(substr(period,instr(period,'-')+1,length(period)-instr(period,'-')),1,"
				+ " length(substr(period,instr(period,'-')+1,length(period)-instr(period,'-')))-1) else ''"
				+ " end end_period from t_course_arrangement t"
				+ " where school_year = '"+school_year+"' and term_code = '"+term_code+"'";
		return baseDao.queryForList(sql);
		
	}

	@Override
	public List<Map<String, Object>> getCourseWeek() {
		String sql="select id,week_start_end,t.lecture_type_code lecture_type,case when instr(period,'-')>0 then substr(substr(period,1,instr(period,'-')-1),2) else '' end first_period,"
				+ " case when instr(period,'-')>0 then substr(substr(period,instr(period,'-')+1,length(period)-instr(period,'-')),1,"
				+ " length(substr(period,instr(period,'-')+1,length(period)-instr(period,'-')))-1) else ''"
				+ " end end_period from t_course_arrangement t";
		return baseDao.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getSchoolYear() {
		String sql = "select t.school_year,t.term_code from t_course_arrangement t group by t.school_year,t.term_code";
		return baseDao.queryListInLowerKey(sql);
	}

}
