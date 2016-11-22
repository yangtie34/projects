package com.jhkj.mosdc.permiss.service;

/**
 * 用户管理Service
 * @author Administrator
 *
 */
public interface UserService {
	
	/**
	 * 添加用户
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String addUser(String params) throws Exception;
	
	/**
	 * 修改用户
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String updateUser(String params) throws Exception;
	/**
	 * 修改用户的密码
	 */
	public String updateUserPassWord(String params);
	
	/**
	 * 修改用户的组角色
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String updateUsergroupRoles(String params) throws Exception;
	
	/**
	 * 删除用户 (只有手动添加的用户才能被删除)
	 */
	public String deleteUser(String params);
	/**
	 * 禁用用户
	 */
	public String updateDisableUser(String params);
	/**
	 * 启用用户
	 */
	public String updateEnableUser(String params);
	
	/**
	 * 查询用户-用户组菜单权限
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String queryUserUsergroupMenuPermiss(String params) throws Exception;
	
	/**
	 * 修改用户的组菜单权限（可以暂时不实现）
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String updateUserUsergroupMenuPermiss(String params) throws Exception;
	
	/***
	 * 查询用户的数据权限(最细粒度-到班级)
	 * @param params {usergroupId : ''}
	 * @throws Exception 
	 */
	public String queryUserUsergroupDataPermiss(String params) throws Exception;
	/**
	 * 修改用户的数据权限(最细粒度-到班级)
	 * @throws Exception 
	 */
	public String updateUserUsergroupDataPermiss(String params) throws Exception;
	/***
	 * 查询用户的数据权限(按组织结构)
	 * @param params {usergroupId : ''}
	 * @throws Exception 
	 */
	public String queryUserUsergroupDataPermissByJxzzjg(String params) throws Exception;
	/***
	 * 查询用户的数据权限(按组织结构)(以树的形式)
	 * @param params {usergroupId : ''}
	 * @throws Exception 
	 */
	public String queryUserUsergroupDataPermissByJxzzjgAsTree(String params) throws Exception;
	/**
	 * 修改用户的数据权限(按组织结构)
	 * @throws Exception 
	 */
	public String updateUserUsergroupDataPermissByJxzzjg(String params) throws Exception;
	/**
	 * 修改用户数据权限（按组织结构）-而且需要根据实际上用户所在组的数据权限生成实际的数据权限
	 * @throws Exception 
	 */
	public String updateUserUsergroupDataPermissAndSaveWidthExactValue(String params) throws Exception;
	/***
	 * 查询用户的数据权限(按行政组织结构)
	 * @param params {usergroupId : ''}
	 * @throws Exception 
	 */
	public String queryUserUsergroupDataPermissByXzzzjg(String params) throws Exception;
	/***
	 * 查询用户的数据权限(按行政组织结构)(以树的形式)
	 * @param params {usergroupId : ''}
	 * @throws Exception 
	 */
	public String queryUserUsergroupDataPermissByXzzzjgAsTree(String params) throws Exception;
	/**
	 * 修改用户的数据权限(按行政组织结构)
	 * @throws Exception 
	 */
	public String updateUserUsergroupDataPermissByXzzzjg(String params) throws Exception;
	/**
	 * 修改用户数据权限（按行政组织结构）-而且需要根据实际上用户所在组的数据权限生成实际的数据权限
	 * @throws Exception 
	 */
	public String updateUserUsergroupXzDataPermissAndSaveWidthExactValue(String params) throws Exception;
	
	/**
	 * 根据用户名判断是否有用户
	 */
	public String queryUserByLoginName(String params);
	
	/**
	 * 查询当前排除特定人员之后的人员列表
	 * @throws Exception 
	 */
	public String queryUserExceptUserIds(String params) throws Exception;
/****************************************内部调用************************************************/	
	/**
	 * 查询用户是否存在
	 */
	public boolean queryUserExsit(String loginname,String password);
	
	
}
