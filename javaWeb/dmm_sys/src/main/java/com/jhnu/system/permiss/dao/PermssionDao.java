package com.jhnu.system.permiss.dao;

import java.util.List;

import com.jhnu.system.common.page.Page;
import com.jhnu.system.permiss.entity.Operate;
import com.jhnu.system.permiss.entity.Resources;
import com.jhnu.system.permiss.entity.RolePermssion;
import com.jhnu.system.permiss.entity.UserPermssion;

public interface PermssionDao {

	/**
     * 批量添加用户-权限关系
     * @param userRoles
     */
    public void addUserPermssion(List<UserPermssion> userPermssion);
    
    /**
     * 批量添加角色-权限关系
     * @param userRoles
     */
    public void addRolePermssion(List<RolePermssion> rolePermssion);
    
    /**
     * 获得用户权限关系
     * @param userPermssion
     * @return
     */
    public List<UserPermssion> getUserPermssion(UserPermssion userPermssion);
    public Page getUserPermssionPage(int currentPage,int numPerPage,UserPermssion userPermssion);
    
    /**
     * 获得角色权限关系
     * @param userPermssion
     * @return
     */
    public List<RolePermssion> getRolePermssion(RolePermssion rolePermssion);

    /**
     * 查询角色权限Page
     * @param currentPage
     * @param numPerPage
     * @param rolePermssion
     * @return
     */
	public Page getRolePermssionPage(int currentPage,int numPerPage,RolePermssion rolePermssion);
    /**
     * 添加角色-权限关系
     * @param rolePermssion
     */
	public RolePermssion addRolePermssion(RolePermssion rolePermssion);
	
	/**
	 * 添加用户-权限关系
	 * @param userPermssion
	 */
	public UserPermssion addUserPermssion(UserPermssion userPermssion);
	
	/**
	 * 根据资源的Shiro资源标识符修改用户权限的shiro权限通配符
	 * @param resources
	 */
	public void updateUserPermssion(Resources resources);
	
	/**
	 * 根据操作名称修改用户权限shiro权限通配符
	 * @param resources
	 */
	public void updateUserPermssion(Operate operate);
	/**
	 * 根据资源的Shiro资源标识符修改角色权限的shiro权限通配符
	 * @param resources
	 */
	public void updateRolePermssion(Resources resources);
	
	/**
	 * 根据操作名称修改角色权限shiro权限通配符
	 * @param resources
	 */
	public void updateRolePermssion(Operate operate);
	/**
	 * 删除角色权限
	 * @param rolePermssionId
	 */
	public void depeteRolePermssion(Long rolePermssionId);
	/**
	 * 删除用户权限
	 * @param userPermssionId
	 */
	public void depeteUserPermssion(Long userPermssionId);

	public void deleteRolePermssionByRoleId(Long roleId);

    
}
