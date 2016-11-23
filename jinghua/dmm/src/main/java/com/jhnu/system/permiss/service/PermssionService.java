package com.jhnu.system.permiss.service;

import java.util.List;
import java.util.Map;

import com.jhnu.framework.exception.handle.AddException;
import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.product.four.common.entity.ResultBean;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.permiss.entity.Operate;
import com.jhnu.system.permiss.entity.Resources;
import com.jhnu.system.permiss.entity.RolePermssion;
import com.jhnu.system.permiss.entity.UserPermssion;

public interface PermssionService {

	/**
     * 批量添加用户-权限关系
     * @param userRoles
     */
    public void addUserPermssion(List<UserPermssion> userPermssions);
    
    /**
     * 添加用户-权限关系
     * @param userPermssion
     * @throws ParamException 
     * @throws AddException 
     */
    public UserPermssion addUserPermssion(UserPermssion userPermssion) throws AddException, ParamException;
    
    /**
     * 批量添加角色-权限关系
     * @param userRoles
     */
    public void addRolePermssion(List<RolePermssion> rolePermssion);
    /**
     * 添加角色-权限关系
     * @param userRoles
     * @throws ParamException 
     * @throws AddException 
     */
    public RolePermssion addRolePermssion(RolePermssion rolePermssion) throws AddException, ParamException;
    
    /**
     * 添加权限
     * @param map(人员类型：user/role,人员ID：userId/roleId,资源ID,数据服务ID,操作ID,组织机构ids)
     * @return
     */
    public ResultBean addPermssionAjax(Map<String,String> map);
    /**
     * 获取角色权限Page
     * @param currentPage
     * @param numPerPage
     * @param rolePermssion
     * @return
     */
    public Page getRolePermssionPage(int currentPage,int numPerPage,RolePermssion rolePermssion);
    
    /**
     * 获取用户权限Page
     * @param currentPage
     * @param numPerPage
     * @param userPermssion
     * @return
     */
    public Page getUserPermssionPage(int currentPage,int numPerPage,UserPermssion userPermssion);
    
    /**
     * 获得用户权限关系
     * @param userPermssion
     * @return
     */
    public List<UserPermssion> getUserPermssion(UserPermssion userPermssion);
    
    /**
     * 获得角色权限关系
     * @param userPermssion
     * @return
     */
    public List<RolePermssion> getRolePermssion(RolePermssion rolePermssion);
    
    /**
     * 删除角色权限
     * @param rolePermssionId 角色权限ID
     * @return
     */
    public ResultBean deleteRolePermssion(Long rolePermssionId);
    /**
     * 删除角色权限
     * @param rolePermssionId 角色ID
     * @return
     */
    public void deleteRolePermssionByRoleId(Long roleId);
    
    /**
     * 删除用户权限
     * @param userPermssionId
     * @return
     */
    public ResultBean deleteUserPermssion(Long userPermssionId);
    
    public ResultBean resetZzOrJxjg(Map<String,String> map);

    /**
     * 修改角色权限(shiro权限通配符)
     * @param operate 操作
     */
	public void updateRolePermssion(Operate operate);

	/**
	 * 修改用户权限(shiro权限通配符)
	 * @param operate  操作
	 */
	public void updateUserPermssion(Operate operate);
	
	/**
	 *  修改角色权限(shiro权限通配符)
	 * @param resource 资源
	 */
	public void updateRolePermssion(Resources resource);

	/**
	 * 修改用户权限(shiro权限通配符)
	 * @param resource 资源
	 */
	public void updateUserPermssion(Resources resource);


	ResultBean addPermssionsAjax(Map<String, String> map, List<Map> list);
	
    
}
