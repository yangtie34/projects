package com.jhnu.syspermiss.school.dao.impl;


import java.util.List;

import com.jhnu.syspermiss.GetCachePermiss;
import com.jhnu.syspermiss.school.dao.DeptPermissionDao;
import com.jhnu.syspermiss.school.entity.Dept;
import com.jhnu.syspermiss.school.entity.DeptTeach;
import com.jhnu.syspermiss.util.StringUtils;
import com.jhnu.syspermiss.util.jdbcUtil.BaseDao;


public class DeptPermissionDaoImpl implements DeptPermissionDao{

	private DeptPermissionDaoImpl() {
		
	}  
    private static DeptPermissionDaoImpl DeptPermissionDaoImpl=null;
	
	public static DeptPermissionDaoImpl getInstance() {
		if (DeptPermissionDaoImpl == null){
			synchronized (new String()) {
				if (DeptPermissionDaoImpl == null){
					DeptPermissionDaoImpl = new DeptPermissionDaoImpl();
				}
			}
		}
		return DeptPermissionDaoImpl;
	}
	private BaseDao baseDao=BaseDao.getInstance();

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Dept> getDeptByUserPermsId(long userPermId) {
		String sql="select d.* from t_sys_user_dept u "+
				"left join t_code_dept d on  u.dept_id like d.id||',%' "
				+ "or u.dept_id like '%,'||d.id||',%' "
				+ "or u.dept_id like d.id "
				+ "or u.dept_id like '%,'||d.id "
				+ " where u.user_perm_id = ?";
		return baseDao.query(sql,Dept.class, new Object[] {userPermId});
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<DeptTeach> getDeptTeachByUserPermsId(long userPermId) {
		String sql=getDeptTeachByPermsIdSql("user");
		return baseDao.query(sql,DeptTeach.class, new Object[] {userPermId});
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Dept> getDeptByRolePermsId(long rolePermId) {
		String sql="select d.* from t_sys_role_dept u "+
				"inner join t_code_dept d on u.dept_id like d.id||',%'"
				+ " or u.dept_id like '%,'||d.id||',%' "
				+ " or u.dept_id like d.id "
				+ " or u.dept_id like '%,'||d.id "
				+ " where u.role_perm_id = ?";
		return baseDao.query(sql,Dept.class, new Object[] {rolePermId});
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<DeptTeach> getDeptTeachByRolePermsId(long rolePermId) {
		String sql=getDeptTeachByPermsIdSql("role");
		return baseDao.query(sql,DeptTeach.class, new Object[] {rolePermId});
	}
public String getDeptTeachByPermsIdSql(String lb){
	String sql=null;
	if(lb.equalsIgnoreCase("user")){
		 sql="select t.* from "+
				"( select c.no_ id,c.name_ name_ ,dt.id pid,dt.level_+1 level_ from t_code_dept_teach dt  "+
				"inner join t_classes c on dt.code_=c.teach_dept_id where dt.istrue =1  "+
				"union all  "+
	            "select id,name_,pid,level_ from t_code_dept_teach where istrue=1 ) t "+
	            "inner join t_sys_user_deptteach u on u.dept_teach_id like t.id||',%'"
				+ " or u.dept_teach_id like '%,'||t.id||',%' "
				+ "or u.dept_teach_id like t.id "
				+ "or u.dept_teach_id like '%,'||t.id where u.user_perm_id = ? ";
	}else if(lb.equalsIgnoreCase("role")){
		 sql="select t.* from "+
				"( select c.no_ id,c.name_ name_ ,dt.id pid,dt.level_+1 level_ from t_code_dept_teach dt  "+
				"inner join t_classes c on dt.code_=c.teach_dept_id where dt.istrue =1  "+
				"union all  "+
	            "select id,name_,pid,level_ from t_code_dept_teach where istrue=1 ) t "+
	            "inner join t_sys_role_deptteach u on u.dept_teach_id like t.id||',%'"
				+ " or u.dept_teach_id like '%,'||t.id||',%' "
				+ "or u.dept_teach_id like t.id "
				+ "or u.dept_teach_id like '%,'||t.id where u.role_perm_id = ?";
	}
	return sql;
}
	
	@SuppressWarnings({ "unchecked" })
	public List<DeptTeach> getAllDeptTeachPerms(){
		/*		String sql="select c.no_ id,c.name_ name_ ,dt.id pid,dt.level_+1 level_ from t_code_dept_teach dt "+
					"inner join t_classes c on dt.code_=c.teach_dept_id where dt.istrue =1 "+
					"union all "+
					"select id,name_,pid,level_ from t_code_dept_teach where istrue=1 "+
					"order by level_,id";*/
		String sql="select id,name_,pid,level_ from t_code_dept_teach where istrue=1 "+
				"order by level_,id";
		return baseDao.query(sql,DeptTeach.class);
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<Dept> getAllDeptPerms(){
		String sql="select * from t_code_dept  order by level_, id";
		return baseDao.query(sql, Dept.class);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<DeptTeach> getDeptTeachByUserNameAndShiroTag(String username,String shiroTag) {
	/*	List params=new LinkedList();
		String sql="";
		for(int i=0;i<userPermssions.size();i++){
			Long id=userPermssions.get(i).getId();
			params.add(id);
			sql+=" union all "+getDeptTeachByPermsIdSql("user");
		}
		for(int i=0;i<rolePermssions.size();i++){
			Long id=rolePermssions.get(i).getId();
			params.add(id);
			sql+=" union all "+getDeptTeachByPermsIdSql("role");
		}
		Object[] array = new Object[params.size()];
		params.toArray(array);
		if(sql.equalsIgnoreCase("")){
			return new LinkedList<DeptTeach>();
		}
		sql=sql.replaceFirst(" union all ", ""); */
		String sql=getSqlByUserNameAndShiroTag(username, shiroTag, "t_code_dept_teach");                                                                                
		return baseDao.query(sql,DeptTeach.class);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Dept> getDeptByUserNameAndShiroTag(String username,String shiroTag) {
		           String sql=getSqlByUserNameAndShiroTag(username, shiroTag, "t_code_dept");                                                                      
		return baseDao.query(sql,Dept.class);
	}
	private String getSqlByUserNameAndShiroTag(String username, String shiroTag,String table){
		List<String> datas=GetCachePermiss.getDataServeSqlbyUserIdShrio(username, shiroTag); 
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

	@Override
	public String getMyDeptSqlbyUsername(String username){
		String sql="select t.dept_id id,nvl(c.level_,1) level_ from t_tea t left join t_code_dept c on t.dept_id=c.id where t.tea_no='"+username+"'";
		return sql;
	}
}
