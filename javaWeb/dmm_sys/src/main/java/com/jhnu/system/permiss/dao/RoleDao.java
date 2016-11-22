package com.jhnu.system.permiss.dao;

import java.util.List;
import java.util.Map;

import com.jhnu.system.common.page.Page;
import com.jhnu.system.permiss.entity.Role;

public interface RoleDao {

	/**
	 * 添加角色
	 * @param role
	 * @return
	 */
    public Role createRole(Role role);
    /**
     * 删除角色
     * @param roleId
     */
    public void deleteRole(Long roleId);
    /**
     * 根据条件查询角色
     * @param role
     * @return
     */
	public List<Role> getAllRole(Role role);

	/**
	 * 获取角色Page
	 * @param currentPage
	 * @param numPerPage
	 * @param role
	 * @return
	 */
	public Page getPageRole(int currentPage, int numPerPage,Role role);
	/**
	 * 修改角色
	 */
	public void updateRole(Role role);
	/**
	 * 查询角色
	 * @param roleId
	 * @return
	 */
	public Role findByRoleId(Long roleId);
	
	/**
     * 添加用户-角色关系
     * @param userId
     * @param roleIds
     */
    public void addUserRoles(Long roleIds, Long... userId);
    /**
     * 修改角色是否可用
     * @param roleId
     * @param istrue
     */
	public void updateRoleIsture(Long roleId, int istrue);
	/**
	 * 查询所有角色类型
	 * @return
	 */
	public List<Map<String, Object>> findRoleType();
	/**
	 * 删除角色用户
	 * @param roleId
	 */
	public void deleteRoleUsers(Long roleId);
	 /**
     * 修改角色主页
     * @param roleId
     * @param resourcedID
     */
	public void updateRoleResourceId(Long roleId, Long resourcedID);

}
