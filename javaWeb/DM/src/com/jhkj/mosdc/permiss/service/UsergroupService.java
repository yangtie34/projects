package com.jhkj.mosdc.permiss.service;

/**
 * 用户组管理
 * @author Administrator
 *
 */
public interface UsergroupService {
	/**
	 * 添加子用户组
	 * @param params TpUsergroup{}
	 * @return
	 * @throws Exception 
	 */
	public String addUsergroup(String params) throws Exception;
	/**
	 * 修改子用户组
	 * @param params TpUsergroup{}
	 * @return
	 * @throws Exception 
	 */
	public String updateUsergroup(String params) throws Exception;
	/**
	 * 删除子用户组
	 * @param params {usergroupId : ''}
	 * @return
	 */
	public String deleteUsergroup(String params);
	/**
	 * 查询所有的子用户组  
	 * @param params {usergroupId : ''}
	 * @return
	 * @throws Exception 
	 */
	public String queryChildrenUsergroup(String params) throws Exception;
	
	/**
	 * 查询用户组组员,供超级管理员使用
	 * @throws Exception 
	 */
	public String queryUsersByUsergroup(String params) throws Exception;
	/**
	 * 查询用户组组员,供组管理员使用
	 * @throws Exception 
	 */
	public String queryUsersByUsergroupManager(String params) throws Exception;
	/**
	 * 修改子用户组管理员
	 * @param params {usergroupId:'',usermanagers : '1,2,3,4'}
	 * @return
	 * @throws Exception 
	 */
	public String updateUsergroupManager(String params) throws Exception;
	/**
	 * 查询我所管理的用户组 
	 * @param params {}
	 * @throws Exception 
	 */
	public String queryUsergroupManagerByMine(String params) throws Exception;
	
	/***
	 * 查询用户组的数据权限(粒度到班级)
	 * @param params {usergroupId : ''}
	 */
	public String queryUsergroupDataPermiss(String params);
	/***
	 * 查询用户组的数据权限，并且和教学组织结构进行绑定并返回
	 * @param params {usergroupId : ''}
	 */
	public String queryUsergroupDataPermissWithZzjg(String params);
	
	/**
	 * 设置用户组数据权限(粒度到班级)
	 	  {
	 	  	 usergroupId : ''
	 	  	 allDataPermiss : [1,2,3,4,5,6,7],
	 	  	 exceptDataPermiss : [
	 	  	      {menuIds : [1,2,3,4],except : [1,2,3,4,5]},
	 	  	      {menuIds : [4,5,6,7],except : [7,8,9,10,11]}
	 	  	 ]
	 	  }
	 * @throws Exception 
	 */
	public String updateUsergroupDataPermiss(String params) throws Exception;
	/**
	 * 在用户组数据权限修改后，删除已经被赋予给用户的用户组数据权限-赋予的菜单已经从用户组数据权限中移除
	 * @param params {usergroupId : ''}
	 */
	public String deleteUserOldDataPermissInUserGroup(String params) throws Exception;
	/**
	 * 按组织结构查询用户组数据权限(粒度到教学组织结构的任何一个节点)
	 * @param params {usergroupId : ''}
	 */
	public String queryUsergroupDataPermissByJxzzjg(String params);
	
	/**
	 * 查询用户组的数据权限（教学组织结构）并同时绑定教学组织结构树
	 */
	public String queryUsergroupDataPermissBindJxzzjg(String params);
	/**
	 * 按组织结构设置用户数据权限(粒度到教学组织结构的任何一个节点)
	 * @param params {usergroupId : ''}
	 * @throws Exception 
	 */
	public String updateUsergroupDataPermissByJxzzjg(String params) throws Exception;
	/**
	 * 修改用户数据权限（按教学组织结构）-而且需要根据实际上用户所在组的数据权限生成实际的数据权限
	 * @throws Exception 
	 */
	public String updateUsergroupDataPermissAndSaveWidthExactValue(String params) throws Exception;
	
	/**
	 * 按组织结构查询用户组数据权限(粒度到行政组织结构的任何一个节点)--针对超管
	 * @param params {usergroupId : ''}
	 */
	public String queryUsergroupDataPermissByXzzzjg(String params);
	
	/**
	 * 查询用户组的数据权限（行政组织结构）并同时绑定行政组织结构树--针对其他组管理员
	 */
	public String queryUsergroupDataPermissBindXzzzjg(String params);
	/**
	 * 按组织结构设置用户数据权限(粒度到行政组织结构的任何一个节点)--针对超管
	 * @param params {usergroupId : ''}
	 * @throws Exception 
	 */
	public String updateUsergroupDataPermissByXzzzjg(String params) throws Exception;
	/**
	 * 修改用户组数据权限（按行政组织结构）-而且需要根据实际上用户所在组的数据权限生成实际的数据权限--针对其他组管理员
	 * @throws Exception 
	 */
	public String updateUsergroupXzDataPermissAndSaveWidthExactValue(String params) throws Exception;
	
	
/***************************************************************************************/	
	/**
	 * 设置设置组员菜单权限
	 *  @param params {usergroupId : '',userId : '',menuIds : [1,2,3,4,5]}
	 * @throws Exception 
	 */
	public String updateGrouperMenuPermiss(String params) throws Exception;
	/**
	 * 设置组员角色
	 *  @param params {usergroupId : '',userId : '',roleIds : [1,2,3,4,5]}
	 * @throws Exception 
	 */
	public String updateGrouperRole(String params) throws Exception;
	/**
	 * 设置组员数据权限
	 *  @param params 
	 *  {
	 *   usergroupId : '',userId : '',
	 *   allDataPermiss : [1,2,3,4,5,6,7],
	 	 exceptDataPermiss : [
	 	  	  {menuIds : [1,2,3,4],except : [1,2,3,4,5]},
	 	  	  {menuIds : [4,5,6,7],except : [7,8,9,10,11]}
	 	 ]
	 *  }
	 * @throws Exception 
	 */
	public String updateGrouperDataPermiss(String params) throws Exception;
	
	

}
