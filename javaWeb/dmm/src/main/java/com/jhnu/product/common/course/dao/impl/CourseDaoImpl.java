package com.jhnu.product.common.course.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.common.course.dao.CourseDao;
import com.jhnu.util.common.MapUtils;

@Repository("courseDao")
public class CourseDaoImpl implements CourseDao{


	@Autowired
	private BaseDao baseDao;
	
	@Override
	public void updateCourseWeek(final List<Map<String, Object>> zcList) {
		final int COUNT = zcList.size();
		baseDao.getBaseDao().getJdbcTemplate().batchUpdate(
				"update t_course_arrangement set weeks=? where id=?",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
		                ps.setString(1,MapUtils.getString(zcList.get(i), "WEEKS"));
		                ps.setString(2,MapUtils.getString(zcList.get(i), "COURSE_ARRANGEMENT_ID"));  
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 }); 
		
	}

	@Override
	public List<Map<String, Object>> getCourseWeek() {
		String sql="select id,week_start_end from t_course_arrangement ";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
		
	}

}
