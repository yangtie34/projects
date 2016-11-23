package com.jhnu.syspermiss.permiss.dao.impl;


import java.util.List;
import java.util.Map;

import com.jhnu.syspermiss.permiss.dao.DataServeDao;
import com.jhnu.syspermiss.permiss.entity.DataServe;
import com.jhnu.syspermiss.util.jdbcUtil.BaseDao;

public class DataServeDaoImpl implements DataServeDao {
	private DataServeDaoImpl() {
		
	}  
    private static DataServeDaoImpl DataServeDaoImpl=null;
	
	public static DataServeDaoImpl getInstance() {
		if (DataServeDaoImpl == null){
			synchronized (new String()) {
				if (DataServeDaoImpl == null){
					DataServeDaoImpl = new DataServeDaoImpl();
				}
			}
		}
		return DataServeDaoImpl;
	}
	private BaseDao baseDao=BaseDao.getInstance();

	@SuppressWarnings({ "unchecked" })
	@Override
	public DataServe findById(Long id) {
		String sql = "select * from t_sys_data_service t where t.id = ?";
		List<DataServe> dlist=baseDao.query(sql.toString(),DataServe.class,new Object[]{id});
		return dlist.size()==0?null:dlist.get(0);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<DataServe> getDataServe(String username, String shiroTag) {
		String checkString="",checkString2="";
		String star=shiroTag.substring(0,shiroTag.lastIndexOf(":")+1);
		String lastag=shiroTag.substring(shiroTag.lastIndexOf(":")+1,shiroTag.length());
		if(lastag.equalsIgnoreCase("*")){
			checkString=" like '"+star+"%' and INSTR(up.wirldcard , ':', -1, 1) ="+star.length();
			checkString2=" like '"+star+"%' and INSTR(rp.wirldcard , ':', -1, 1) ="+star.length();
		}else{
			checkString="in ('"+shiroTag+"','"+star+"*') ";
			checkString2=checkString;
		}
		String sql="select ds.id,ds.name_,ds.servicename,up.id perm_id,'user' perm_type ,'"+username+"' username from T_SYS_USER_PERM up "+
				"inner join T_SYS_DATA_SERVICE ds on up.data_service_id=ds.id "+
				"inner join t_sys_user u on up.user_id=u.id "+
				"where u.username=? and up.wirldcard "+checkString+" "+
				"union all "+
				"select ds.id,ds.name_,ds.servicename,rp.id perm_id,'role' perm_type,'"+username+"' username from T_SYS_USER_ROLE ur  "+
				"inner join T_SYS_Role_PERM rp on ur.role_id=rp.role_id  "+
				"inner join T_SYS_DATA_SERVICE ds on rp.data_service_id=ds.id "+
				"inner join t_sys_user u on ur.user_id=u.id "+
				"where u.username=? and rp.wirldcard "+checkString2+"";
		return baseDao.query(sql.toString(),DataServe.class,new Object[]{username,username});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findAll() {
		String sql = "select * from t_sys_data_service";
		return baseDao.queryForList(sql);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean isInClassForStudent(String stuId,List lists) {
		String andsql="";
		if(lists.get(0)!=null){
			return true;
		}else if(lists.get(1)!=null){
			andsql+=" and t.DEPT_ID in ("+lists.get(1)+")";
		}else if(lists.get(2)!=null){
			andsql+=" and t.MAJOR_ID in ("+lists.get(2)+")";
		}else if(lists.get(3)!=null){
			andsql+=" and t.CLASS_ID in ("+lists.get(3)+")";
		}
		String sql="select t.* from t_stu t where t.no_=? "+andsql;
		List<Map<String,Object>> list=baseDao.queryForList(sql,new Object[]{stuId});
		return list.size()>0?true:false;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean isInDeptForTeacher(String teaId,List lists) {
		String andsql="";
		if(lists.get(0)!=null){
			return true;
		}else if(lists.get(1)!=null){
			andsql+=" and t.DEPT_ID in ("+lists.get(1)+")";
		}
		/*else if(lists.get(2)!=null){
			andsql+=" and t.SUBJECT_ID in ("+lists.get(2)+")";
		}else if(lists.get(3)!=null){
			andsql+=" and t.CLASS_ID in ("+lists.get(3)+")";
		}*/
		String sql="select t.* from t_tea t where t.tea_no=? "+andsql;
		
		List<Map<String,Object>> list=baseDao.queryForList(sql,new Object[]{teaId});
		return list.size()>0?true:false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getDeptDataByDeptAndData(String deptId,
			String datas) {
		String sql="select id,name_,pid,level_ from t_code_dept t where "+
					"t.istrue =1 and t.id in("+datas+") start with t.pid='"+deptId+"' connect by prior t.id=t.pid";
		return baseDao.queryForList(sql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getDeptTeachDataByDeptAndData(
			String deptId, String datas) {
		String sql="select * from "+
				"(select id,name_,pid,level_ from t_code_dept_teach t where t.istrue =1 "+
				"union all "+
				"select no_ id,name_,TEACH_DEPT_ID pid,3 level_ from t_classes where islive=1 ) t  "+
				"where t.id in("+datas+") start with t.pid='"+deptId+"' connect by prior t.id=t.pid";
		return baseDao.queryForList(sql);
	}
}
