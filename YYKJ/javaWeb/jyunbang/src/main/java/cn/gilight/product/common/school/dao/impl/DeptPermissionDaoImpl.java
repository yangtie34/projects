package cn.gilight.product.common.school.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.product.common.school.dao.DeptPermissionDao;
import cn.gilight.product.common.school.entity.Dept;
import cn.gilight.product.common.school.entity.DeptTeach;


@Repository("deptPermissionDao")
public class DeptPermissionDaoImpl implements DeptPermissionDao{

	@Autowired
	private BaseDao baseDao;
	
	@Override
	public void resetRoleDept(final long rolePermId, final String[] depts) {
		final int COUNT = depts.length;
		String delSql="delete t_sys_role_dept where role_perm_id ="+rolePermId;
		baseDao.delete(delSql);
		baseDao.getJdbcTemplate().batchUpdate(
				"insert into t_sys_role_dept values (ID_SEQ.NEXTVAL, ?, ?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {  
		        	/*Long id = baseDao.getBaseDao().getSeqGenerator().nextLongValue();
	        		ps.setLong(1, id); */ 
	                ps.setLong(1, rolePermId);    
	                ps.setString(2, depts[i]);    
	              }    
	              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 	
		        }    
		 }); 
	}

	@Override
	public void resetUserDept(final long userPermId, final String[] depts) {
		final int COUNT = depts.length;
		String delSql="delete t_sys_user_dept where user_perm_id ="+userPermId;
		baseDao.delete(delSql);
		baseDao.getJdbcTemplate().batchUpdate(
				"insert into t_sys_user_dept values (ID_SEQ.NEXTVAL, ?, ?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
		        //	Long id = baseDao.getBaseDao().getSeqGenerator().nextLongValue();
	                ps.setLong(1, userPermId);    
	                ps.setString(2, depts[i]);    
	              }    
	              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 	
		        }    
		 }); 
	}

	@Override
	public void resetRoleDeptTeach(final long rolePermId, final String[] deptTeachs) {
		final int COUNT = deptTeachs.length;
		String delSql="delete t_sys_role_deptteach where role_perm_id ="+rolePermId;
		baseDao.delete(delSql);
		baseDao.getJdbcTemplate().batchUpdate(
				"insert into t_sys_role_deptteach values (ID_SEQ.NEXTVAL, ?, ?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException { 
	        		//Long id = baseDao.getBaseDao().getSeqGenerator().nextLongValue();
	        		//ps.setLong(1, id);  
	                ps.setLong(1, rolePermId);    
	                ps.setString(2, deptTeachs[i]);    
		        }    
		        //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 	
		        }    
		 }); 
	}

	@Override
	public void resetUserDeptTeach(final long userPermId, final String[] deptTeachs) {
		final int COUNT = deptTeachs.length;
		String delSql="delete t_sys_user_deptteach where user_perm_id ="+userPermId;
		baseDao.delete(delSql);
		baseDao.getJdbcTemplate().batchUpdate(
				"insert into t_sys_user_deptteach values (ID_SEQ.NEXTVAL, ?, ?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {  
		        	//Long id = baseDao.getBaseDao().getSeqGenerator().nextLongValue();
	        		//ps.setLong(1, id);  
	                ps.setLong(1, userPermId);    
	                ps.setString(2, deptTeachs[i]);    
	              }    
	              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 	
		        }    
		 }); 
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Dept> getDeptByUserPermsId(long userPermId) {
		String sql="select d.* from t_sys_user_dept u "+
					"inner join t_code_dept d on 1=1 "+
					"where  u.dept_id like d.id||',%' or u.dept_id like '%,'||d.id||',%' and u.user_perm_id = ?";
		return baseDao.getJdbcTemplate().query(sql, new Object[] {userPermId},(RowMapper) new BeanPropertyRowMapper(Dept.class));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<DeptTeach> getDeptTeachByUserPermsId(long userPermId) {
		String sql="select t.* from "+
				"( select c.no_ id,c.name_ name_ ,dt.id pid,dt.level_+1 level_ from t_code_dept_teach dt  "+
				"inner join t_classes c on dt.code_=c.teach_dept_id where dt.istrue =1  "+
				"union all  "+
	            "select id,name_,pid,level_ from t_code_dept_teach where istrue=1 ) t "+
	            "inner join t_sys_user_deptteach u on 1=1 "+
				"where u.dept_teach_id like t.id||',%' or u.dept_teach_id like '%,'||t.id||',%' and u.user_perm_id = ? ";
		return baseDao.getJdbcTemplate().query(sql, new Object[] {userPermId},(RowMapper) new BeanPropertyRowMapper(DeptTeach.class));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Dept> getDeptByRolePermsId(long rolePermId) {
		String sql="select d.* from t_sys_role_dept u "+
					"inner join t_code_dept d on 1=1 "+
					"where  u.dept_id like d.id||',%' or u.dept_id like '%,'||d.id||',%' and u.role_perm_id = ?";
		return baseDao.getJdbcTemplate().query(sql, new Object[] {rolePermId},(RowMapper) new BeanPropertyRowMapper(Dept.class));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<DeptTeach> getDeptTeachByRolePermsId(long rolePermId) {
		String sql="select t.* from "+
					"( select c.no_ id,c.name_ name_ ,dt.id pid,dt.level_+1 level_ from t_code_dept_teach dt  "+
					"inner join t_classes c on dt.code_=c.teach_dept_id where dt.istrue =1  "+
					"union all  "+
		            "select id,name_,pid,level_ from t_code_dept_teach where istrue=1 ) t "+
		            "inner join t_sys_role_deptteach u on 1=1 "+
					"where u.dept_teach_id like t.id||',%' or u.dept_teach_id like '%,'||t.id||',%' and u.role_perm_id = ?";
		return baseDao.getJdbcTemplate().query(sql, new Object[] {rolePermId},(RowMapper) new BeanPropertyRowMapper(DeptTeach.class));
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<DeptTeach> getAllDeptTeachPerms(){
		String sql="select c.no_ id,c.name_ name_ ,dt.id pid,dt.level_+1 level_ from t_code_dept_teach dt "+
					"inner join t_classes c on dt.code_=c.teach_dept_id where dt.istrue =1 "+
					"union all "+
					"select id,name_,pid,level_ from t_code_dept_teach where istrue=1 "+
					"order by level_,id";
		return baseDao.getJdbcTemplate().query(sql, (RowMapper) new BeanPropertyRowMapper(DeptTeach.class));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Dept> getAllDeptPerms(){
		String sql="select * from t_code_dept  order by level_, id";
		return baseDao.getJdbcTemplate().query(sql, (RowMapper) new BeanPropertyRowMapper(Dept.class));
	}

	@Override
	public String getDeptIdSqlByUserPermsId(long userPermId) {
		String sql="select d.id from t_sys_role_dept u "+
				"inner join t_code_dept d on 1=1 "+
				"where u.dept_id like d.id||',%' or u.dept_id like '%,'||d.id||',%' and  u.role_perm_id = "+userPermId;
		return sql;
	}

	@Override
	public String getDeptTeachIdSqlByUserPermsId(long userPermId) {
		String sql="select u.dept_teach_id id from t_sys_user_deptteach u "+
				"where u.user_perm_id = "+userPermId;
		return sql;
	}

	@Override
	public String getDeptIdSqlByRolePermsId(long rolePermId) {
		String sql="select d.id from t_sys_role_dept u "+
				"inner join t_code_dept d on 1=1  "+
				"where u.dept_id like d.id||',%' or u.dept_id like '%,'||d.id||',%' and u.role_perm_id = "+rolePermId ;
		return sql;
	}

	@Override
	public String getDeptTeachIdSqlByRolePermsId(long rolePermId) {
		String sql="select u.dept_teach_id id from t_sys_role_deptteach u "+
				"where u.role_perm_id = "+rolePermId;
		return sql;
	}

}
