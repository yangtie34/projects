package com.jhnu.syspermiss.permiss.service.impl;

import java.util.List;
import java.util.Map;

import com.jhnu.syspermiss.permiss.dao.RoleDao;
import com.jhnu.syspermiss.permiss.dao.impl.RoleDaoImpl;
import com.jhnu.syspermiss.permiss.entity.Role;
import com.jhnu.syspermiss.permiss.service.RoleService;

public class RoleServiceImpl implements RoleService {
	private RoleServiceImpl() {
		
	}  
    private static RoleServiceImpl RoleServiceImpl=null;
	
	public static RoleServiceImpl getInstance() {
		if (RoleServiceImpl == null){
			synchronized (new String()) {
				if (RoleServiceImpl == null){
					RoleServiceImpl = new RoleServiceImpl();
				}
			}
		}
		return RoleServiceImpl;
	}
    private RoleDao roleDao= RoleDaoImpl.getInstance();
    @Override
    public Role createRole(final Role Role) {
        return roleDao.createRole(Role);
    }
    @Override
    public void deleteRole(Long roleId) {
    	roleDao.deleteRole(roleId);
    }
    @Override
	public void updateRole(Role role) {
    	roleDao.updateRole(role);
	}
	@Override
	public List<Role> getAllRole(Role role) {
		List<Role> roleList = roleDao.getAllRole(role);
		return roleList;
	}

	@Override
	public Role findByRoleId(Long roleId) {
		
		Role role = roleDao.findByRoleId(roleId);
		return role;
	}
	@Override
	public List<Map<String, Object>> findRoleType() {
		return roleDao.findRoleType();
	}
	@Override
	public List<Role> getRoles() {
		return getAllRole(new Role());
	}

}
