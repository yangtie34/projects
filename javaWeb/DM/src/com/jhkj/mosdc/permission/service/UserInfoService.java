package com.jhkj.mosdc.permission.service;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.jhkj.mosdc.permission.po.UserInfo;

/**
 * 
 * @Comments: 用户信息服务接口
 * Company: 河南精华科技有限公司
 * Created by gaodongjie(gaodongjie@126.com) 
 * @DATE:2012-5-15
 * @TIME: 上午11:38:53
 */
public interface UserInfoService { 
	/**
	 * 登录验证
	 * @param userStr
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * @throws ClassNotFoundException 
	 * @throws Exception 
	 * @throws Throwable 
	 */
	public UserInfo queryUser(String userStr) throws Exception;
	
	/**
	 * 获取用户信息
	 * @param userStr
	 * @return
	 * @throws Exception 
	 */
	public String queryUserInfo(String userStr) throws Exception;
	/**
	 * @throws Throwable 
	 * 根据Id获取用户信息
	 * @Title: queryUserPermissById 
	 * @param @param userId
	 * @param @return
	 * @return String
	 * @throws
	 */
	public UserInfo queryUserPermissById(Long userId) throws Throwable;
	/**
	 * 获取权限菜单功能树
	 * @param userStr
	 * @return
	 */
	public String queryUserTree(String userStr);
	/**
	 * 
	 * 功能说明：查询劳资系统部门树
	 * @param params
	 * @return
	 * @author: 党冬(mrdangdong@163.com)
	 * @DATE:2012-6-10 @TIME: 下午8:46:54
	 */
	public String queryLzBmTree(String params);
	/**
	 * 
	 * 功能说明：保存用户信息
	 * @param params
	 * @return
	 * @author: 党冬(mrdangdong@163.com)
	 * @throws Exception 
	 * @DATE:2012-6-12 @TIME: 下午11:43:00
	 */
	public String saveUserInfo(String params) throws Exception;
	/**
	 * 
	 * 功能说明：查询用户角色
	 * @param userId
	 * @return
	 * @author: 党冬(mrdangdong@163.com)
	 * @DATE:2012-6-16 @TIME: 下午3:18:21
	 */
	public String queryUserRoles(String params);
	/**
	 * 
	 * 功能说明：查询职工信息
	 * @param params
	 * @return
	 * @author: 党冬(mrdangdong@163.com)
	 * @DATE:2012-6-16 @TIME: 下午6:18:02
	 */
	public String queryTsUserList(String params)throws Exception;
	/**
	 * 
	 * 功能说明：用户重置密码
	 * @param params
	 * @return
	 * @throws Exception
	 * @author: 党冬(mrdangdong@163.com)
	 * @DATE:2012-7-8 @TIME: 下午12:59:59
	 */
	public String updateResetPassword(String params) throws Exception;
	/***
	 * 修改密码
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String updatePassword(String params) throws Exception;
	/**
	 * 
	 * 功能说明：用户状态设置
	 * @param params
	 * @return
	 * @throws Exception
	 * @author: 党冬(mrdangdong@163.com)
	 * @DATE:2012-7-15 @TIME: 下午12:59:59
	 */
	public String updateResetUserZt(String params) throws Exception;
	
	/**
	 * 获取教学组织机构树
	 * @param params
	 * @return
	 */
	public String queryJxzzjgTree(String params)throws Exception;
	
	/**
	 * 获取用户信息根据用户类别查询
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String queryUserByType(String params) throws Exception;
	/**
	 * 查询组织机构 树（带复选框）
	 * @param params
	 * @return
	 */
	public String queryZzjgTree(String params);
	/**
	 * 查询菜单树
	 * @param params
	 * @return
	 */
	public String queryMenuTree(String params);
	/**
	 * 查询组织机构 树（不带复选框）
	 * @param params
	 * @return
	 */
	public String queryZzjgTreeNoCheck(String params);
	/**
	 * 开放给其它服务调用保存用户信息
	 * @param id
	 * @param rylbId
	 * @return
	 * @throws Exception 
	 */
	public boolean saveTsUser(Long id, Long rylbId) throws Exception;
	//--------------------------------------------------2013-05-24-----------------------------------------------------
	/**
	 * 更新用户信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String updateUserInfo(String params) throws Exception;
	/**
	 * 删除用户信息
	 * @param params
	 * @return
	 */
	public String deleteTsuser(String params);
	/**
	 * 获取session中的用户信息
	 * @return　用户信息
	 */
	public String getUserInfo(String params);
	/**
	 * 根据用户名查询用户
	 * @throws Exception 
	 * @throws Throwable 
	 */
	public UserInfo queryUserByLoginName(String loginName) throws Exception, Throwable;
	
}
