package com.jhnu.syspermiss.permiss.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.jhnu.syspermiss.permiss.dao.PermssionDao;
import com.jhnu.syspermiss.permiss.entity.Operate;
import com.jhnu.syspermiss.permiss.entity.Resources;
import com.jhnu.syspermiss.permiss.entity.RolePermssion;
import com.jhnu.syspermiss.permiss.entity.UserPermssion;
import com.jhnu.syspermiss.util.StringUtils;
import com.jhnu.syspermiss.util.jdbcUtil.BaseDao;


public class PermssionDaoImpl implements PermssionDao{
	private PermssionDaoImpl() {
		
	}  
    private static PermssionDaoImpl PermssionDaoImpl=null;
	
	public static PermssionDaoImpl getInstance() {
		if (PermssionDaoImpl == null){
			synchronized (new String()) {
				if (PermssionDaoImpl == null){
					PermssionDaoImpl = new PermssionDaoImpl();
				}
			}
		}
		return PermssionDaoImpl;
	}
	private BaseDao baseDao=BaseDao.getInstance();
	

	@SuppressWarnings("unchecked")
	@Override
	public List<UserPermssion> getUserPermssion(UserPermssion userPermssion) {
		StringBuffer sql=new StringBuffer("select srp.id as id ,srp.wirldcard ,sr.id userid,sr.username userName, "
				+ "sr.real_name userRealName,sr.istrue userIStrue,"
				+ "sds.id dataId,sds.name_ dataName,sds.servicename dataServiceName,"
				+ "sre.id resId,sre.name_ resName,sre.url_ resurl,so.id soid,so.name_ soName,so.description soDesc "
				+ "from t_sys_user_perm srp "
				+ "inner join t_sys_user sr on srp.user_id=sr.id "+
					" inner join t_sys_data_service sds on srp.data_service_id=sds.id "+
					" inner join T_SYS_OPERATE so on srp.operate_id=so.id "+
					" inner join t_sys_resources sre on srp.resource_id=sre.id where sre.istrue=1 ");
		if(userPermssion!=null){
			if(userPermssion.getId()!=null){
				sql.append("and srp.id="+userPermssion.getId());
			}
			if(userPermssion.getUser()!=null){
				if(userPermssion.getUser().getId()!=null){
					sql.append("and sr.id="+userPermssion.getUser().getId());
				}
				if(StringUtils.hasLength(userPermssion.getUser().getUsername())){
					sql.append("and sr.username='"+userPermssion.getUser().getUsername()+"' ");
				}
				if(userPermssion.getUser().getIstrue()!=null){
					sql.append("and sr.istrue="+userPermssion.getUser().getIstrue());
				}
			}
			if(userPermssion.getDataServe()!=null){
				if(userPermssion.getDataServe().getId()!=null){
					sql.append("and sds.id="+userPermssion.getDataServe().getId());
				}
				if(StringUtils.hasLength(userPermssion.getDataServe().getName_())){
					sql.append("and sds.name_='"+userPermssion.getDataServe().getName_()+"' ");
				}
			}
			if(userPermssion.getOperate()!=null){
				if(userPermssion.getOperate().getId()!=null){
					sql.append("and so.id="+userPermssion.getOperate().getId());
				}
				if(StringUtils.hasLength(userPermssion.getOperate().getName_())){
					sql.append("and so.name_='"+userPermssion.getOperate().getName_()+"' ");
				}
			}
			if(userPermssion.getResource()!=null){
				if(userPermssion.getResource().getId()!=null){
					sql.append("and sre.id="+userPermssion.getResource().getId());
				}
				if(StringUtils.hasLength(userPermssion.getResource().getName_())){
					sql.append("and sre.name_='"+userPermssion.getResource().getName_()+"' ");
				}
				if(StringUtils.hasLength(userPermssion.getResource().getShiro_tag())){
					sql.append("and sre.shiro_tag='"+userPermssion.getResource().getShiro_tag()+"' ");
				}
			}
		}
	    final List<UserPermssion> permList = baseDao.query(sql.toString(),UserPermssion.class);  
		
		return permList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RolePermssion> getRolePermssion(RolePermssion rolePermssion) {
		StringBuffer sql=new StringBuffer("select srp.id as id ,srp.wirldcard ,sr.id roleId,sr.name_ roleName,"
				+ "sr.description roleN,sds.id dataId,sds.name_ dataName,sds.servicename dataServiceName,"
				+ "sre.id resId,sre.name_ resName,sre.url_ resurl,so.id soid,so.name_ soName,so.description soDesc "
				+ "from t_sys_role_perm srp "
				+ "inner join t_sys_role sr on srp.role_id=sr.id "+
					" inner join t_sys_data_service sds on srp.data_service_id=sds.id "+
					" inner join T_SYS_OPERATE so on srp.operate_id=so.id "+
					" inner join t_sys_resources sre on srp.resource_id=sre.id where sre.istrue=1 ");
		if(rolePermssion!=null){
			if(rolePermssion.getId()!=null){
				sql.append("and srp.id="+rolePermssion.getId());
			}
			if(rolePermssion.getRole()!=null){
				if(rolePermssion.getRole().getId()!=null){
					sql.append("and sr.id="+rolePermssion.getRole().getId());
				}
				if(StringUtils.hasLength(rolePermssion.getRole().getName_())){
					sql.append("and sr.name_='"+rolePermssion.getRole().getName_()+"' ");
				}
				if(rolePermssion.getRole().getIstrue()!=null){
					sql.append("and sr.istrue="+rolePermssion.getRole().getIstrue());
				}
				if(StringUtils.hasLength(rolePermssion.getRole().getDescription())){
					sql.append("and sr.description like '%"+rolePermssion.getRole().getDescription()+"%' ");
				}
			}
			if(rolePermssion.getDataServe()!=null){
				if(rolePermssion.getDataServe().getId()!=null){
					sql.append("and sds.id="+rolePermssion.getDataServe().getId());
				}
				if(StringUtils.hasLength(rolePermssion.getDataServe().getName_())){
					sql.append("and sds.name_='"+rolePermssion.getDataServe().getName_()+"' ");
				}
			}
			if(rolePermssion.getOperate()!=null){
				if(rolePermssion.getOperate().getId()!=null){
					sql.append("and so.id="+rolePermssion.getOperate().getId());
				}
				if(StringUtils.hasLength(rolePermssion.getOperate().getName_())){
					sql.append("and so.name_='"+rolePermssion.getOperate().getName_()+"' ");
				}
			}
			if(rolePermssion.getResource()!=null){
				if(rolePermssion.getResource().getId()!=null){
					sql.append("and sre.id="+rolePermssion.getResource().getId());
				}
				if(StringUtils.hasLength(rolePermssion.getResource().getName_())){
					sql.append("and sre.name_='"+rolePermssion.getResource().getName_()+"' ");
				}
				if(StringUtils.hasLength(rolePermssion.getResource().getShiro_tag())){
					sql.append("and sre.shiro_tag='"+rolePermssion.getResource().getShiro_tag()+"' ");
				}
			}
		}
	    final List<RolePermssion> permList=baseDao.query(sql.toString(), RolePermssion.class);  
		
		return permList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public RolePermssion addRolePermssion(RolePermssion rolePermssion) {
		final String SQL = "insert into t_sys_role_perm values(?,?,?,?,?,?)";
		String wirldcard = rolePermssion.getResource().getShiro_tag()+":"+rolePermssion.getOperate().getName_();
		List<Map<String, Object>> l=baseDao.queryForList("select * from t_sys_role_perm where role_id='"+rolePermssion.getRole().getId()+"' and resource_id='"+rolePermssion.getResource().getId()+"'and data_service_id='"+rolePermssion.getDataServe().getId()+"'");
		long ID=0;
		if(l.size()>0){
			ID= ((BigDecimal)l.get(0).get("ID")).longValue();
			rolePermssion.setId(ID);
			updateRolePermssion(rolePermssion.getResource());
			updateRolePermssion(rolePermssion.getOperate());
		}else{
			 ID=baseDao.getSeq();
			 rolePermssion.setId(ID);
			 baseDao.excute(SQL, new Object[]{ID,rolePermssion.getRole().getId(),rolePermssion.getResource().getId(),
		        		rolePermssion.getOperate().getId(),rolePermssion.getDataServe().getId(),wirldcard});
		}
        return rolePermssion;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserPermssion addUserPermssion(UserPermssion userPermssion) {
		final String SQL = "insert into t_sys_user_perm values(?,?,?,?,?,?)";
		String wirldcard = userPermssion.getResource().getShiro_tag()+":"+userPermssion.getOperate().getName_();
		List<Map<String, Object>> l=baseDao.queryForList("select * from t_sys_user_perm where user_id='"+userPermssion.getUser().getId()+"' and resource_id='"+userPermssion.getResource().getId()+"'and data_service_id='"+userPermssion.getDataServe().getId()+"'");
		long ID=0;
		if(l.size()>0){
			ID= ((BigDecimal)l.get(0).get("ID")).longValue();
			 userPermssion.setId(ID);
			updateUserPermssion(userPermssion.getResource());
			updateUserPermssion(userPermssion.getOperate());
		}else{
			 ID=baseDao.getSeq();
			 userPermssion.setId(ID);
			   baseDao.excute(SQL, new Object[]{ID,userPermssion.getUser().getId(),userPermssion.getResource().getId(),
		        		userPermssion.getOperate().getId(),userPermssion.getDataServe().getId(),wirldcard});
		}
     
        return userPermssion;
	}

	@Override
	public void updateUserPermssion(Resources resources) {
		String sql = "update t_sys_user_perm t set t.wirldcard = concat(?,substr(t.wirldcard,instr(t.wirldcard,':',1,1)) )  where t.resource_id = ?";
		baseDao.excute(sql, new Object[]{resources.getShiro_tag(),resources.getId()});
	}

	@Override
	public void updateUserPermssion(Operate operate) {
		String sql = "update t_sys_user_perm t set t.wirldcard = concat(substr(t.wirldcard,1,instr(t.wirldcard,':')),? )  where t.operate_id = ?";
		baseDao.excute(sql, new Object[]{operate.getName_(),operate.getId()});
	}

	@Override
	public void updateRolePermssion(Resources resources) {
		String sql = "update t_sys_role_perm t set t.wirldcard = concat(?,substr(t.wirldcard,instr(t.wirldcard,':',1,1)) )  where t.resource_id = ?";
		baseDao.excute(sql, new Object[]{resources.getShiro_tag(),resources.getId()});
	}

	@Override
	public void updateRolePermssion(Operate operate) {
		String sql = "update t_sys_role_perm t set t.wirldcard = concat(substr(t.wirldcard,1,instr(t.wirldcard,':')),? )  where t.operate_id = ?";
		baseDao.excute(sql, new Object[]{operate.getName_(),operate.getId()});
	}

	@Override
	public void depeteRolePermssion(Long rolePermssionId) {
		String sql = "delete from t_sys_role_perm t where t.id = ?";
		baseDao.excute(sql, new Object[]{rolePermssionId});
	}

	@Override
	public void depeteUserPermssion(Long userPermssionId) {
		String sql = "delete from t_sys_user_perm t where t.id = ?";
		baseDao.excute(sql, new Object[]{userPermssionId});
	}

	@Override
	public void deleteRolePermssionByRoleId(Long roleId) {
		String sql = "delete from t_sys_role_perm t where t.role_id = ?";
		baseDao.excute(sql, new Object[]{roleId});
	}
}
