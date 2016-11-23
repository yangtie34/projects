package com.jhkj.mosdc.permiss.service;

/**
 * 角色管理
 * @author Administrator
 *
 */
public interface RoleService {
	
	/**
	 * 添加角色
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String addRole(String params) throws Exception;
	
	/**
	 * 修改角色
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String updateRole(String params) throws Exception;
	/**
	 * 删除角色
	 * @param params
	 * @return
	 */
	public String deleteRole(String params);
	/**
	 * 根据用户组信息查询角色列表(根据groupID为null)
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String queryRolesBygroupId(String params) throws Exception;
	/**
	 * 修改角色菜单权限
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String updateRoleMenuPermiss(String params) throws Exception;
	/**
	 * 查询角色菜单权限
	 */
	public String queryRoleMenuPermiss(String params) throws Exception;

}
