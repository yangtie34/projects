package com.jhnu.product.four.relation.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.four.relation.dao.FourRelationDao;
import com.jhnu.util.common.DateUtils;
import com.jhnu.util.common.MapUtils;

@Repository("fourRelationDao")
public class FourRelationDaoImpl implements FourRelationDao{

	@Autowired
	private BaseDao baseDao;
	
	
	@Override
	public List<Map<String, Object>> getRoommate() {
		String nowDate=DateUtils.SDF.format(new Date());
		String sql="select s.no_ stu_id,s.name_ name_,db.dorm_id ,'"+nowDate+"' add_date "+
				"from t_dorm_berth db "+
				"inner join t_dorm_berth_stu dbs on db.id=dbs.berth_id "+
				"inner join t_stu s on dbs.stu_id=s.no_ "+
				"order by db.dorm_id ";
		return baseDao.getBaseDao().querySqlList(sql);
	}

	@Override
	public void saveRoommateLog(List<Map<String, Object>> list) {
		final int COUNT = list.size();
		final List<Map<String,Object>> LIST=list;
		String delSql="delete tl_relation_roommate";
		baseDao.getLogDao().executeSql(delSql);
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(
				"insert into tl_relation_roommate values (?, ?, ?, ?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {
		                ps.setString(1, MapUtils.getString(LIST.get(i),"stu_id").toString());    
		                ps.setString(2, MapUtils.getString(LIST.get(i),"name_").toString());    
		                ps.setString(3, MapUtils.getString(LIST.get(i),"dorm_id").toString()); 
		                ps.setString(4, MapUtils.getString(LIST.get(i),"add_date").toString());    
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 }); 
	}

	@Override
	public List<Map<String, Object>> getRoommateLog(String id) {
		String sql="select t2.* from tl_relation_roommate t1 "+
					"inner join tl_relation_roommate t2 on t1.dorm_id=t2.dorm_id "+
					"where t1.stu_id=? and t2.stu_id<>? ";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[]{id,id});
	}

	@Override
	public List<Map<String, Object>> getTutor() {
		String sql="select s.no_ stu_id,s.name_ stu_name,t.tea_no tea_id,t.name_ tea_name,t.Sex_Code ,"+
				"ci.school_year,ci.term_code from t_stu s  "+
				"inner join T_CLASSES_INSTRUCTOR ci on s.class_id=ci.class_id "+
				"inner join t_tea t on ci.tea_id=t.tea_no ";
		return baseDao.getBaseDao().querySqlList(sql);
	}

	@Override
	public void saveTutorLog(List<Map<String, Object>> list) {
		final int COUNT = list.size();
		final List<Map<String,Object>> LIST=list;
		String delSql="delete tl_relation_tutor";
		baseDao.getLogDao().executeSql(delSql);
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(
				"insert into tl_relation_tutor values (?, ?, ?, ? ,? ,?,?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {
		                ps.setString(1, MapUtils.getString(LIST.get(i),"stu_id").toString());
		                ps.setString(2, MapUtils.getString(LIST.get(i),"stu_name").toString());
		                ps.setString(3, MapUtils.getString(LIST.get(i),"tea_id").toString());
		                ps.setString(4, MapUtils.getString(LIST.get(i),"tea_name").toString());
		                ps.setString(5, MapUtils.getString(LIST.get(i),"school_year").toString());
		                ps.setString(6, MapUtils.getString(LIST.get(i),"term_code").toString());
		                ps.setString(7, MapUtils.getString(LIST.get(i),"sex_code").toString());
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 }); 
	}

	@Override
	public List<Map<String, Object>> getTutorLog(String id) {
		String sql="select * from tl_relation_tutor where stu_id= ?";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[]{id});
	}
	
}
