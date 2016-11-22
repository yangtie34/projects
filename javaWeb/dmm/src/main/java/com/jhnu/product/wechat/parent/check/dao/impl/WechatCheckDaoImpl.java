package com.jhnu.product.wechat.parent.check.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.wechat.parent.check.dao.WechatCheckDao;
import com.jhnu.util.common.MapUtils;


@Repository("checkDao")
public class WechatCheckDaoImpl implements WechatCheckDao {
	
	@Autowired
	private BaseDao baseDao;
	@Override
	public List<Map<String, Object>> getCheck() {
		String sql = "select c.stu_id,c.school_year,c.term_code,c.class_leave,c.cut_class,c.class_early,c.class_late from "
				+ " (select stu_id ,school_year,term_code,"
				+ " sum(case check_code when '1' then numbers else 0 end) class_leave,"
				+ " sum(case check_code when '2' then numbers  else 0 end) cut_class,"
				+ " sum(case check_code when '4' then numbers  else 0 end) class_early,"
				+ " sum(case check_code when '3' then numbers  else 0 end) class_late"
				+ "  from (select stu_check.stu_id stu_id,stu_check.check_code, stu_check.school_year,stu_check.term_code,count(*) numbers"
				+ " from t_stu_check stu_check"
				+ "  group by stu_check.stu_id,school_year,term_code,stu_check.check_code) "
				+ " group by stu_id ,school_year,term_code order by stu_id) c";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String,Object>> getCheckLog(String stuId) {
		String sql = "select c.stu_id,nvl(c.class_late,0) class_late,nvl(c.class_early,0) class_early,nvl(c.cut_class,0) cut_class,nvl(c.class_leave,0) class_leave,nvl(c.class_number,0) class_number,c.school_year,c.term_code from tl_check_class c where stu_id = ?";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[]{stuId});
	}

	@Override
	public void saveCheckLog(List<Map<String, Object>> list) {
		final int COUNT = list.size();
		final List<Map<String,Object>> LIST=list;
		String delSql="delete  tl_check_class";
		baseDao.getLogDao().executeSql(delSql);
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(
				"insert into tl_check_class values (?, ?, ?, ?,?,?,?,?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
		                ps.setString(1, MapUtils.getString(LIST.get(i),"stu_id").toString());    
		                ps.setString(2, MapUtils.getString(LIST.get(i),"class_late").toString());    
		                ps.setString(3, MapUtils.getString(LIST.get(i),"class_early").toString()); 
		                ps.setString(4, MapUtils.getString(LIST.get(i),"cut_class").toString());    
		                ps.setString(5, MapUtils.getString(LIST.get(i),"class_leave").toString());  
		                ps.setString(6, MapUtils.getString(LIST.get(i),"class_number").toString());  
		                ps.setString(7, MapUtils.getString(LIST.get(i),"school_year").toString());  
		                ps.setString(8, MapUtils.getString(LIST.get(i),"term_code").toString());  
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 }); 
	}

	@Override
	public List<Map<String, Object>> getLateClassCourse() {
		String sql1 = "select t.stu_id ,course.id course_id,t.school_year,t.term_code, count(*) sums from t_stu_check t "
				+ " inner join t_course course on course.id = t.course_id and check_code = '3'"
				+ " group by stu_id,school_year,term_code, course.id";
		
		String sql = "select a.stu_id,a.school_year,a.term_code ,c.name_ often_late_class from("
		        + sql1 +" ) a inner join (select max(sums) maxsums,stu_id,school_year,term_code from (" 
				+ sql1 + " ) group by stu_id,school_year,term_code) b"
				+ " on a.stu_id = b.stu_id and a.school_year = b.school_year and a.term_code = b.term_code and a.sums = b.maxsums "
				+ " inner join t_course c on c.id = course_id order by stu_id ";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getCutClassCourse() {
		String sql1 = "select t.stu_id ,course.id course_id,t.school_year,t.term_code, count(*) sums from t_stu_check t "
				+ " inner join t_course course on course.id = t.course_id and check_code = '2'"
				+ " group by stu_id,school_year,term_code, course.id";
		
		String sql = " select a.stu_id,a.school_year,a.term_code ,c.name_ often_cut_class from("
		        + sql1 +" ) a inner join (select max(sums) maxsums,stu_id,school_year,term_code from (" 
				+ sql1 + " ) group by stu_id,school_year,term_code) b"
				+ " on a.stu_id = b.stu_id and a.school_year = b.school_year and a.term_code = b.term_code and a.sums = b.maxsums "
				+ " inner join t_course c on c.id = course_id order by stu_id";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public void saveStuCutClassLog(List<Map<String, Object>> list) {
		final int COUNT = list.size();
		final List<Map<String,Object>> LIST=list;
		String delSql="delete  tl_stu_cut_class";
		baseDao.getLogDao().executeSql(delSql);
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(
				"insert into tl_stu_cut_class values (?, ?, ?, ?,?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
		                ps.setString(1, MapUtils.getString(LIST.get(i),"stu_id").toString());    
		                ps.setString(2, MapUtils.getString(LIST.get(i),"often_late_class").toString());    
		                ps.setString(3, MapUtils.getString(LIST.get(i),"often_cut_class").toString()); 
		                ps.setString(4, MapUtils.getString(LIST.get(i),"school_year").toString());  
		                ps.setString(5, MapUtils.getString(LIST.get(i),"term_code").toString());  
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 }); 
	}

	@Override
	public List<Map<String, Object>> getStuCutClassLog(String stuId) {
		String sql = "select * from tl_stu_cut_class where stu_id = ?";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[]{stuId});
	}

	@Override
	public List<Map<String, Object>> getClasses() {
		String sql = "select t.no_ stu_id,ch.school_year,ch.term_code,ch.week_start_end from t_stu t "
				+ " inner join t_class_teaching_xzb xzb on t.class_id = xzb.class_id"
				+ " inner join t_course_arrangement ch on ch.teachingclass_id = xzb.teach_class_id where t.stu_state_code = '1' order by  stu_id,school_year,term_code";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
}
