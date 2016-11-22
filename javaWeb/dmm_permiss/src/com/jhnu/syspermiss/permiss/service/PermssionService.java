package com.jhnu.syspermiss.permiss.service;

import java.util.List;

import com.jhnu.syspermiss.permiss.entity.Operate;
import com.jhnu.syspermiss.permiss.entity.Resources;
import com.jhnu.syspermiss.permiss.entity.RolePermssion;
import com.jhnu.syspermiss.permiss.entity.UserPermssion;

public interface PermssionService {

    

    
    /**
     * 添加用户-权限关系
     * @param userPermssion
     * @throws ParamException 
     * @throws AddException 
     */
    public UserPermssion addUserPermssion(UserPermssion userPermssion) ;
    
    /**
     * 添加角色-权限关系
     * @param userRoles
     * @throws ParamException 
     * @throws AddException 
     */
    public RolePermssion addRolePermssion(RolePermssion rolePermssion) ;
    
    /**
     * 获得角色权限关系
     * @param userPermssion
     * @return
     */
    public List<RolePermssion> getRolePermssion(RolePermssion rolePermssion);
    
    /**
     * 删除角色权限
     * @param rolePermssionId 角色ID
     * @return
     */
    public void deleteRolePermssionByRoleId(Long roleId);
    

    /**
     * 修改角色权限(shiro权限通配符)
     * @param operate 操作
     */
	public void updateRolePermssion(Operate operate);

	/**
	 * 修改用户权限(shiro权限通配符)
	 * @param operate  操作
	 */
	public void updateUserPermssion(Operate operate);
	
	/**
	 *  修改角色权限(shiro权限通配符)
	 * @param resource 资源
	 */
	public void updateRolePermssion(Resources resource);

	/**
	 * 修改用户权限(shiro权限通配符)
	 * @param resource 资源
	 */
	public void updateUserPermssion(Resources resource);

	List<UserPermssion> getUserPermssion(UserPermssion userPermssion);

}
