package com.jhkj.mosdc.permiss.service;

/**
 * 权限代理
 * @author Administrator
 *
 */
public interface ProxyPermiss {

	/**
	 * 查询我的委托任务
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String queryMyProxy(String params) throws Exception;
	/**
	 * 新增委派
	 * @throws Exception 
	 */
	public String addProxy(String params) throws Exception;
	/**
	 * 修改委派
	 * @throws Exception 
	 */
	public String updateProxy(String params) throws Exception;
	/**
	 * 取消委派
	 */
	public String deleteProxy(String params);
	/**
	 * 设置委派菜单权限
	 * @throws Exception 
	 */
	public String updateProxyMenuPermiss(String params) throws Exception;
	/**
	 * 查询代理任务拥有的权限
	 */
	public String queryProxyMenus(String params);
	/**
	 * 查询用户拥有的菜单结构
	 * @param params
	 * @return
	 */
	public String queryUserMenus(String params);
	/**
	 * 查询需要被委托权限的用户
	 * @throws Exception 
	 */
	public String queryUsers(String params) throws Exception;
}
