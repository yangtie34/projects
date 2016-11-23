package com.jhnu.syspermiss.permiss.service.impl;


import java.util.List;

import com.jhnu.syspermiss.permiss.dao.PermssionDao;
import com.jhnu.syspermiss.permiss.dao.impl.PermssionDaoImpl;
import com.jhnu.syspermiss.permiss.entity.Operate;
import com.jhnu.syspermiss.permiss.entity.Resources;
import com.jhnu.syspermiss.permiss.entity.RolePermssion;
import com.jhnu.syspermiss.permiss.entity.UserPermssion;
import com.jhnu.syspermiss.permiss.service.PermssionService;

public class PermssionServiceImpl implements PermssionService{

	private PermssionServiceImpl() {
		
	}  
    private static PermssionServiceImpl PermssionServiceImpl=null;
	
	public static PermssionServiceImpl getInstance() {
		if (PermssionServiceImpl == null){
			synchronized (new String()) {
				if (PermssionServiceImpl == null){
					PermssionServiceImpl = new PermssionServiceImpl();
				}
			}
		}
		return PermssionServiceImpl;
	}
	private PermssionDao permssionDao= PermssionDaoImpl.getInstance();

	@Override
	public List<UserPermssion> getUserPermssion(UserPermssion userPermssion) {
		return permssionDao.getUserPermssion(userPermssion);
	}
	@Override
	public List<RolePermssion> getRolePermssion(RolePermssion rolePermssion) {
		return permssionDao.getRolePermssion(rolePermssion);
	}

	@Override
	public UserPermssion addUserPermssion(UserPermssion userPermssion) {
		if(userPermssion != null){
			return permssionDao.addUserPermssion(userPermssion);
		}
		return userPermssion;
	}

	@Override
	public RolePermssion addRolePermssion(RolePermssion rolePermssion) {
		if(rolePermssion != null){
			return permssionDao.addRolePermssion(rolePermssion);
		}
		return rolePermssion;
	}


	@Override
	public void updateRolePermssion(Operate operate) {
		permssionDao.updateRolePermssion(operate);
	}

	@Override
	public void updateUserPermssion(Operate operate) {
		permssionDao.updateUserPermssion(operate);
	}

	@Override
	public void updateRolePermssion(Resources resource) {
		permssionDao.updateRolePermssion(resource);
	}

	@Override
	public void updateUserPermssion(Resources resource) {
		permssionDao.updateUserPermssion(resource);
	}


	@Override
	public void deleteRolePermssionByRoleId(Long roleId) {
		permssionDao.deleteRolePermssionByRoleId(roleId);
	}


}

