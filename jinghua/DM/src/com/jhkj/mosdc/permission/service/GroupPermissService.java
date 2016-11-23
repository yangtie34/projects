package com.jhkj.mosdc.permission.service;

/**
 * 组权限管理Service
 * @author Administrator
 *
 */
public interface GroupPermissService {
	
	/**
	 * 查询个人赋予的组权限的人员的信息(根据角色进行查询)
	 */
	public String queryDevolvedGroupPermissPerson(String params);
	/**
	 * 修改赋予某个人的组权限
	 */
	public String updateGroupPermissForPerson(String params);
	/**
	 * 删除赋予给某个人的组权限
	 */
	public String deleteGroupPermissWithPerson(String params);
	/**
	 * 为某个人添加对应的组权限
	 */
	public String addGroupPermissForPerson(String params);
	/**
	 * 根据赋予用户Id，获取赋予其菜单权限
	 */
	public String getCdIds(String params);
}
