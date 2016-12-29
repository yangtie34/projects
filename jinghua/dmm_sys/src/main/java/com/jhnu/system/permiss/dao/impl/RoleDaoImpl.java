package com.jhnu.system.permiss.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.permiss.dao.RoleDao;
import com.jhnu.system.permiss.entity.Role;
import com.jhnu.util.common.StringUtils;

@Repository("roleDao")
public class RoleDaoImpl implements RoleDao {
	@Autowired
	private BaseDao baseDao;

    public Role createRole(final Role Role) {
        final String SQL = "insert into t_sys_role(id,name_, description, istrue,role_type_code,ismain,resourceid) values(?,?,?,?,?,?,?)";
        final long ID=baseDao.getBaseDao().getSeqGenerator().nextLongValue();
        baseDao.getBaseDao().getJdbcTemplate().update(SQL, new Object[]{ID,Role.getName_(),
        		Role.getDescription(),Role.getIstrue(),Role.getRole_type(),Role.getIsmain(),Role.getResourceid()});
        Role.setId(ID);
        return Role;
    }

    public void deleteRole(Long roleId) {
        //首先把和role关联的相关表数据删掉
        String sql = "delete from t_sys_user_role where role_id=?";
        baseDao.getBaseDao().getJdbcTemplate().update(sql, roleId);

        sql = "delete from t_sys_role where id=?";
        baseDao.getBaseDao().getJdbcTemplate().update(sql, roleId);
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
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
		return baseDao.getBaseDao().getJdbcTemplate().query(sql.toString(), (RowMapper) new BeanPropertyRowMapper(Role.class));
	}

	@Override
	public Page getPageRole(int currentPage, int numPerPage,Role role) {
		StringBuffer sql =new StringBuffer("select t.id,t.name_,t.description,t.istrue,co.name_ role_type,t.ismain from t_sys_role t"
				+ " left join t_code co on co.code_type = 'ROLE_TYPE_CODE' and co.code_ = t.role_type_code  where 1=1");
		if(role != null){
			if(role.getId() != null){
				sql.append(" and t.id = '"+role.getId()+"'");
			}
			if(StringUtils.hasLength(role.getName_())){
				sql.append(" and t.name_ = '"+ role.getName_() +"'");
			}
			if(StringUtils.hasLength(role.getDescription())){
				sql.append(" and t.description like '%"+ role.getDescription() +"%'");
			}
			if(role.getIstrue() != null){
				sql.append(" and t.istrue = '"+ role.getIstrue() +"'");
			}
			if(role.getIsmain() != null){
				sql.append(" and t.ismain = '"+ role.getIsmain() +"'");
			}
			if(StringUtils.hasLength(role.getName_OrDescription())){
				sql.append(" AND (t.name_ LIKE '%"+role.getName_OrDescription()+"%' or t.description LIKE '%"+role.getName_OrDescription()+"%') ");
			}
		}
		return new Page(sql.toString()+" order by t.id ",currentPage, numPerPage ,baseDao.getBaseDao().getJdbcTemplate(),null,role);
	}

	@Override
	public void updateRole(Role role) {
		if(role != null){
			String sql = "update t_sys_role t set t.name_ = ? , t.description = ? , t.istrue = ? ,t.role_type_code=? ,t.ismain=?,t.resourceid=? where t.id = ?";
			baseDao.getBaseDao().getJdbcTemplate().update(sql, new Object[]{role.getName_(),
					role.getDescription(),role.getIstrue(),role.getRole_type(),role.getIsmain(),role.getResourceid(),role.getId()});
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Role findByRoleId(Long roleId) {
		String sql = "select t.id,t.name_,t.description,t.istrue,co.name_ role_type from t_sys_role t"
				+ " left join t_code co on co.code_type = 'ROLE_TYPE_CODE' and co.code_ = t.role_type_code  where t.id = ?";
		List<Role> roleList=baseDao.getBaseDao().getJdbcTemplate().query(sql, (RowMapper)new BeanPropertyRowMapper(Role.class),new Object[]{roleId});
		return roleList.size()==0?null:roleList.get(0);
	}

	@Override
	public void addUserRoles(final Long roleIds, final Long... userId) {
		String delsql="delete from t_sys_user_role where role_id= ? ";
		baseDao.getBaseDao().getJdbcTemplate().update(delsql,new Object[]{roleIds});
		String sql ="insert into t_sys_user_role values(?,?,?)";
		baseDao.getBaseDao().getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Long id = baseDao.getBaseDao().getSeqGenerator().nextLongValue();
				ps.setLong(1, id);
				ps.setLong(2, userId[i]);
				ps.setLong(3, roleIds);
			}
			
			@Override
			public int getBatchSize() {
				return userId.length;
			}
		});
    }

	@Override
	public void updateRoleIsture(Long roleId, int istrue) {
		String sql = "update t_sys_role t set t.istrue = ?  where t.id = ?";
		baseDao.getBaseDao().getJdbcTemplate().update(sql, new Object[]{istrue,roleId});
	}
	@Override
	public void updateRoleResourceId(Long roleId, Long resourcedID) {
		String sql = "update t_sys_role t set t.resourceid = ?  where t.id = ?";
		baseDao.getBaseDao().getJdbcTemplate().update(sql, new Object[]{resourcedID,roleId});
	}
	@Override
	public List<Map<String, Object>> findRoleType() {
		String sql = "select t.code_ type_code ,t.name_ type_name from t_code t where t.code_type = 'ROLE_TYPE_CODE'";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public void deleteRoleUsers(Long roleId) {
		String sql = "delete from t_sys_user_role t where t.role_id = ?";
		baseDao.getBaseDao().getJdbcTemplate().update(sql, new Object[]{roleId});
	}

}
