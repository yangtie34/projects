package com.jhkj.mosdc.permiss.service;

/**
 * 菜单管理
 * @author Administrator
 *
 */
public interface MenuService {

	/**
	 * 添加菜单
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String addMenu(String params) throws Exception;
	
	/**
	 * 移除菜单
	 * @param params
	 * @return
	 */
	public String deleteMenu(String params);
	
	/**
	 * 修改菜单
	 * @throws Exception 
	 */
	public String updateMenu(String params) throws Exception;
	
	/**
	 * 根据菜单ID获取TpMenu对象
	 * @param params
	 * @return
	 */
	public String getMenuById(String params);
	
	/**
	 * 查询所有菜单
	 * @param params
	 * @return
	 */
	public String queryAllMenus(String params);
	/**
	 * 根据菜单ID查询功能明细
	 * @throws Exception 
	 */
	public String queryChildrenByMenuId(String params) throws Exception;
	/**
	 * 根据用户组ID,查询用户组的菜单
	 * @param params {usergroupId : ''}
	 * @return 
	 */
	public String queryUsergroupMenus(String params);
	/**
	 * 查询用户组拥有的菜单权限
	 * @throws Exception 
	 */
	public String queryUsergroupMenuPermiss(String params) throws Exception;
	/**
	 * 设置子用户组菜单权限
	 * @param params 
		  {
		     usergroupId : '',
		     menuIds : [1,2,3,4,5,6,7,8]
		  }
	 * @throws Exception 
		  
	 */
	public String updateUsergroupMenuPermiss(String params) throws Exception;
	/**
	 * 在用户组菜单权限修改后，删除已经被赋予给用户的用户组菜单权限-赋予的菜单已经从用户组菜单权限中移除
	 * @param params {usergroupId : ''}
	 */
	public String deleteUserOldMenuPermissInUserGroup(String params) throws Exception;
}
