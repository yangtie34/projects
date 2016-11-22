package com.jhnu.product.common.school.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.framework.entity.NodeAngularTree;
import com.jhnu.framework.entity.NodeTree;
import com.jhnu.product.common.school.dao.DeptPermissionDao;
import com.jhnu.product.common.school.entity.Dept;
import com.jhnu.product.common.school.entity.DeptTeach;
import com.jhnu.system.permiss.service.DataServeService;

@Repository("deptPermissionDao")
public class DeptPermissionDaoImpl implements DeptPermissionDao{

	@Autowired
	private BaseDao baseDao;
	
	@Autowired
	private DataServeService dataServeService;
	
	@Override
	public void resetRoleDept(final long rolePermId, final List<Map> depts) {
		final int COUNT = depts.size();
		String delSql="delete t_sys_role_dept where role_perm_id ="+rolePermId;
		baseDao.getBaseDao().executeSql(delSql);
		baseDao.getBaseDao().getJdbcTemplate().batchUpdate(
				"insert into t_sys_role_dept values (ID_SEQ.NEXTVAL, ?, ?, ?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {  
		        	/*Long id = baseDao.getBaseDao().getSeqGenerator().nextLongValue();
	        		ps.setLong(1, id); */ 
	                ps.setLong(1, rolePermId);    
	                ps.setString(2, depts.get(i).get("ids").toString());    
	                ps.setString(3, depts.get(i).get("level").toString());  
	              }    
	              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 	
		        }    
		 }); 
	}

	@Override
	public void resetUserDept(final long userPermId, final List<Map> depts) {
		final int COUNT = depts.size();
		String delSql="delete t_sys_user_dept where user_perm_id ="+userPermId;
		baseDao.getBaseDao().executeSql(delSql);
		baseDao.getBaseDao().getJdbcTemplate().batchUpdate(
				"insert into t_sys_user_dept values (ID_SEQ.NEXTVAL, ?, ?, ?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
		        //	Long id = baseDao.getBaseDao().getSeqGenerator().nextLongValue();
	                ps.setLong(1, userPermId);    
	                ps.setString(2, depts.get(i).get("ids").toString());    
	                ps.setString(3, depts.get(i).get("level").toString());    
	              }    
	              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 	
		        }    
		 }); 
	}

	@Override
	public void resetRoleDeptTeach(final long rolePermId, final List<Map> deptTeachs) {
		final int COUNT = deptTeachs.size();
		String delSql="delete t_sys_role_deptteach where role_perm_id ="+rolePermId;
		baseDao.getBaseDao().executeSql(delSql);
		baseDao.getBaseDao().getJdbcTemplate().batchUpdate(
				"insert into t_sys_role_deptteach values (ID_SEQ.NEXTVAL, ?, ?, ?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException { 
	        		//Long id = baseDao.getBaseDao().getSeqGenerator().nextLongValue();
	        		//ps.setLong(1, id);  
	                ps.setLong(1, rolePermId);    
	                ps.setString(2, deptTeachs.get(i).get("ids").toString());    
	                ps.setString(3, deptTeachs.get(i).get("level").toString());    
		        }    
		        //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 	
		        }    
		 }); 
	}

	@Override
	public void resetUserDeptTeach(final long userPermId, final List<Map> deptTeachs) {
		final int COUNT = deptTeachs.size();
		String delSql="delete t_sys_user_deptteach where user_perm_id ="+userPermId;
		baseDao.getBaseDao().executeSql(delSql);
		baseDao.getBaseDao().getJdbcTemplate().batchUpdate(
				"insert into t_sys_user_deptteach values (ID_SEQ.NEXTVAL, ?, ?,?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {  
		        	//Long id = baseDao.getBaseDao().getSeqGenerator().nextLongValue();
	        		//ps.setLong(1, id);  
	                ps.setLong(1, userPermId);    
	                ps.setString(2, deptTeachs.get(i).get("ids").toString());    
	                ps.setString(3, deptTeachs.get(i).get("level").toString());      
	                
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
					"left join t_code_dept d on  u.dept_id like d.id||',%' "
					+ "or u.dept_id like '%,'||d.id||',%' "
					+ "or u.dept_id like d.id "
					+ "or u.dept_id like '%,'||d.id "
					+ " where u.user_perm_id = ?";
		return baseDao.getBaseDao().getJdbcTemplate().query(sql, new Object[] {userPermId},(RowMapper) new BeanPropertyRowMapper(Dept.class));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<DeptTeach> getDeptTeachByUserPermsId(long userPermId) {
		String sql="select t.* from ( "+
			//	"select c.no_ id,c.name_ name_ ,dt.id pid,dt.level_+1 level_ from t_code_dept_teach dt  "+
				//"inner join t_classes c on dt.code_=c.teach_dept_id where dt.istrue =1  "+
				//"union all  "+
	            "select id,name_,pid,level_ from t_code_dept_teach where istrue=1 ) t "+
	            "inner join t_sys_user_deptteach u on  u.dept_teach_id like t.id||',%'"
				+ " or u.dept_teach_id like '%,'||t.id||',%' "
				+ "or u.dept_teach_id like t.id "
				+ "or u.dept_teach_id like '%,'||t.id where u.user_perm_id = ? ";
		return baseDao.getBaseDao().getJdbcTemplate().query(sql, new Object[] {userPermId},(RowMapper) new BeanPropertyRowMapper(DeptTeach.class));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Dept> getDeptByRolePermsId(long rolePermId) {
		String sql="select d.* from t_sys_role_dept u "+
					"inner join t_code_dept d on  u.dept_id like d.id||',%'"
					+ " or u.dept_id like '%,'||d.id||',%' "
					+ " or u.dept_id like d.id "
					+ " or u.dept_id like '%,'||d.id "
					+ "where u.role_perm_id = ?";
		return baseDao.getBaseDao().getJdbcTemplate().query(sql, new Object[] {rolePermId},(RowMapper) new BeanPropertyRowMapper(Dept.class));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<DeptTeach> getDeptTeachByRolePermsId(long rolePermId) {
		String sql="select t.* from ("+
					//"( select c.no_ id,c.name_ name_ ,dt.id pid,dt.level_+1 level_ from t_code_dept_teach dt  "+
					//"inner join t_classes c on dt.code_=c.teach_dept_id where dt.istrue =1  "+
					//"union all  "+
		            "select id,name_,pid,level_ from t_code_dept_teach where istrue=1 ) t "+
		            " inner join t_sys_role_deptteach u on u.dept_teach_id like t.id||',%'"
					+ " or u.dept_teach_id like '%,'||t.id||',%' "
					+ "or u.dept_teach_id like t.id "
					+ "or u.dept_teach_id like '%,'||t.id where u.role_perm_id = ?";
		return baseDao.getBaseDao().getJdbcTemplate().query(sql, new Object[] {rolePermId},(RowMapper) new BeanPropertyRowMapper(DeptTeach.class));
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<DeptTeach> getAllDeptTeachPerms(){
	/*	String sql="select c.no_ id,c.name_ name_ ,dt.id pid,dt.level_+1 level_ from t_code_dept_teach dt "+
					"inner join t_classes c on dt.code_=c.teach_dept_id where dt.istrue =1 "+
					"union all "+
					"select id,name_,pid,level_ from t_code_dept_teach where istrue=1 "+
					"order by level_,id"; */
		
		String sql="select id,name_,pid,level_ from t_code_dept_teach where istrue=1 "+
				"order by level_,id";
		return baseDao.getBaseDao().getJdbcTemplate().query(sql, (RowMapper) new BeanPropertyRowMapper(DeptTeach.class));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Dept> getAllDeptPerms(){
		String sql="select * from t_code_dept  order by level_, id";
		return baseDao.getBaseDao().getJdbcTemplate().query(sql, (RowMapper) new BeanPropertyRowMapper(Dept.class));
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<NodeAngularTree> getAllDeptPermsAgl(){
		String sql="select  id,name_ mc,pid from t_code_dept  order by level_, id";
		return baseDao
				.getBaseDao()
				.getJdbcTemplate()
				.query(sql,
						(RowMapper) new BeanPropertyRowMapper(
								NodeAngularTree.class));
	}	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<NodeTree> getAllDeptPermsJL(){
		String sql="select  id,name_ mc,pid,level_ from t_code_dept  order by level_, id";
		return baseDao
				.getBaseDao()
				.getJdbcTemplate()
				.query(sql,
						(RowMapper) new BeanPropertyRowMapper(
								NodeTree.class));
	}
	@Override
	public String getDeptIdSqlByUserPermsId(long userPermId) {
		String sql="select u.dept_id id,u.level_  from t_sys_user_dept u "+
				"where u.user_perm_id = "+userPermId;
		return sql;
	}

	@Override
	public String getDeptTeachIdSqlByUserPermsId(long userPermId) {
		String sql="select u.dept_teach_id id,u.level_  from t_sys_user_deptteach u "+
				"where u.user_perm_id = "+userPermId;
		return sql;
	}

	@Override
	public String getDeptIdSqlByRolePermsId(long rolePermId) {
		String sql="select u.dept_id id,u.level_  from t_sys_role_dept u "+
				"where u.role_perm_id = "+rolePermId ;
		return sql;
	}

	@Override
	public String getDeptTeachIdSqlByRolePermsId(long rolePermId) {
		String sql="select u.dept_teach_id id,u.level_  from t_sys_role_deptteach u "+
				"where u.role_perm_id = "+rolePermId;
		return sql;
	}

	@Override
	public String getDeptClassIdSqlbyUserName(String username) {
		String sql="select class_id id,'4' level_  from t_classes_instructor where tea_id='"+username+"'";
		return sql;
	}

	public String getMyDeptSqlbyUsername(String username){
		String sql="select t.dept_id id,nvl(c.level_,1) level_ from t_tea t left join t_code_dept c on t.dept_id=c.id where t.tea_no='"+username+"'";
		return sql;
	}
	
	
	@Override
	public List<NodeAngularTree> getAllDeptTeachAgl() {
		String sql="select c.no_ id,c.name_ mc ,dt.id pid "//,dt.level_+1 level_ "
				+ " from t_code_dept_teach dt "+
				"inner join t_classes c on dt.code_=c.teach_dept_id where dt.istrue =1 "+
				"union all "+
				"select id,name_ mc,pid  from t_code_dept_teach where istrue=1 "+
				"order by id";
	return baseDao.getBaseDao().getJdbcTemplate().query(sql, (RowMapper) new BeanPropertyRowMapper(NodeAngularTree.class));
	}

	@Override
	public List<NodeTree> getAllDeptTeachJL() {
		String sql="select c.no_ id,c.name_ mc ,dt.id pid ,dt.level_ "//,dt.level_+1 level_ "
				+ " from t_code_dept_teach dt "+
				"inner join t_classes c on dt.code_=c.teach_dept_id where dt.istrue =1 "+
				"union all "+
				"select id,name_ mc,pid ,level_ from t_code_dept_teach where istrue=1 "+
				"order by level_,id";
	return baseDao.getBaseDao().getJdbcTemplate().query(sql, (RowMapper) new BeanPropertyRowMapper(NodeTree.class));
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<DeptTeach> getDeptTeachByUserNameAndShiroTag(String username,String shiroTag) {
		String sql=getSqlByUserNameAndShiroTag(username, shiroTag, "t_code_dept_teach");                                                                                
		return baseDao.getBaseDao().getJdbcTemplate().query(sql,(RowMapper) new BeanPropertyRowMapper(DeptTeach.class));
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Dept> getDeptByUserNameAndShiroTag(String username,String shiroTag) {
		           String sql=getSqlByUserNameAndShiroTag(username, shiroTag, "t_code_dept");                                                                      
		return baseDao.getBaseDao().getJdbcTemplate().query(sql,(RowMapper) new BeanPropertyRowMapper(Dept.class));
	}
	private String getSqlByUserNameAndShiroTag(String username, String shiroTag,String table){
		List<String> datas= dataServeService.getDataServeSqlbyUserIdShrio(username, shiroTag); 
		String qids="";
		for (int i = 0; i < datas.size(); i++) {
			if(!"''".equals(datas.get(i))){
				qids+=datas.get(i)+",";
			}
		}
		if(!StringUtils.hasText(qids)){
			qids="'',";
		}
		qids=qids.substring(0,qids.length()-1);
		String sql=" select  id,name_,pid,level_,sum(istrue) istrue from                                                                          "+
					"(select a.id,a.name_,a.pid,a.level_,'1' istrue  from                                                                         "+
					"(select distinct * from "+table+" t  start with  t.id in("+qids+")  connect by prior t.id = t.pid  ) a  "+
					"left  join                                                                                                                   "+
					"(select distinct * from "+table+" t start with   t.id in("+qids+") connect by prior t.pid = t.id   ) b  "+
					"on a.id=b.id                                                                                                                 "+
					"union all                                                                                                                    "+
					"select b.id,b.name_,b.pid,b.level_,'0' istrue from                                                                           "+
					"(select distinct * from "+table+" t  start with  t.id in("+qids+")  connect by prior t.id = t.pid  ) a  "+
					"right  join                                                                                                                  "+
					"(select distinct * from "+table+" t start with   t.id in("+qids+") connect by prior t.pid = t.id   ) b  "+
					"on a.id=b.id ) group by id,name_,pid,level_ "; 
		return sql;
	}
}
