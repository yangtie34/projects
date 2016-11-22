package com.jhkj.mosdc.permiss.service;

public interface PermissService {

	/**
	 * 查询当前登录用户的菜单权限
	 */
	public String queryUserMenuTree(String params);
	/**
	 * 查询当前用户信息(请不要使用该方法)
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String queryUser(String params) throws Exception;
	/**
	 * 查询教学组织-数据权限-树结构
	 */
	public String queryJxzzjgDataPermissionTree(String params) throws Exception;
}
