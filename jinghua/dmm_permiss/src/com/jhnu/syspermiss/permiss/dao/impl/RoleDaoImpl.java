package com.jhnu.syspermiss.permiss.dao.impl;

import java.util.List;
import java.util.Map;

import com.jhnu.syspermiss.permiss.dao.RoleDao;
import com.jhnu.syspermiss.permiss.entity.Role;
import com.jhnu.syspermiss.util.StringUtils;
import com.jhnu.syspermiss.util.jdbcUtil.BaseDao;

public class RoleDaoImpl implements RoleDao {
	private RoleDaoImpl() {
		
	}  
    private static RoleDaoImpl RoleDaoImpl=null;
	
	public static RoleDaoImpl getInstance() {
		if (RoleDaoImpl == null){
			synchronized (new String()) {
				if (RoleDaoImpl == null){
					RoleDaoImpl = new RoleDaoImpl();
				}
			}
		}
		return RoleDaoImpl;
	}
	private BaseDao baseDao=BaseDao.getInstance();
    @Override
    public Role createRole(final Role Role) {
        final String SQL = "insert into t_sys_role(id,name_, description, istrue,role_type_code) values(?,?,?,?,?)";
        final long ID=baseDao.getSeq();
        baseDao.excute(SQL, new Object[]{ID,Role.getName_(),Role.getDescription(),Role.getIstrue(),Role.getRole_type()});
        Role.setId(ID);
        return Role;
    }
    @Override
    public void deleteRole(Long roleId) {
        //首先把和role关联的相关表数据删掉
        String sql = "delete from t_sys_user_role where role_id=?";
        baseDao.excute(sql,new Object[]{roleId});

        sql = "delete from t_sys_role where id=?";
        baseDao.excute(sql,new Object[]{roleId});
    }
    @Override
	public void updateRole(Role role) {
		if(role != null){
			String sql = "update t_sys_role t set t.name_ = ? , t.description = ? , t.istrue = ? ,t.role_type_code=?  where t.id = ?";
			baseDao.excute(sql, new Object[]{role.getName_(),role.getDescription(),role.getIstrue(),role.getRole_type(),role.getId()});
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Role> getAllRole(Role role) {
		StringBuffer sql =new StringBuffer("select t.id,t.name_,t.description,t.istrue,co.name_ role_type from t_sys_role t"
				+ " left join t_code co on co.code_type = 'ROLE_TYPE_CODE' and co.code_ = t.role_type_code  where 1=1");
		if(role != null){
			if(role.getId() != null){
				sql.append(" and t.id = '"+role.getId()+"'");
			}
			if(StringUtils.hasLength(role.getName_())){
				sql.append(" and t.name_ = '"+ role.getName_() +"'");
			}
			if(StringUtils.hasLength(role.getDescription())){
				sql.append(" and t.description = '"+ role.getDescription() +"'");
			}
			if(role.getIstrue() != null){
				sql.append(" and t.istrue = '"+ role.getIstrue() +"'");
			}
		}
		return baseDao.query(sql.toString(),Role.class);
	}



	@SuppressWarnings({ "unchecked" })
	@Override
	public Role findByRoleId(Long roleId) {
		String sql = "select t.id,t.name_,t.description,t.istrue,co.name_ role_type from t_sys_role t"
				+ " left join t_code co on co.code_type = 'ROLE_TYPE_CODE' and co.code_ = t.role_type_code  where t.id = ?";
		List<Role> roleList=baseDao.query(sql, Role.class,new Object[]{roleId});
		return roleList.size()==0?null:roleList.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findRoleType() {
		String sql = "select t.code_ type_code ,t.name_ type_name from t_code t where t.code_type = 'ROLE_TYPE_CODE'";
		return baseDao.queryForList(sql);
	}
}
