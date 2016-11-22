package com.jhnu.system.permiss.service;

import java.util.List;
import java.util.Map;

import com.jhnu.framework.entity.ResultBean;
import com.jhnu.framework.exception.handle.AddException;
import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.permiss.entity.Role;

public interface RoleService {


	/**
	 * 新增角色
	 * @param role
	 * @return
	 * @throws AddException
	 * @throws ParamException
	 */
    public Role createRole(Role role) throws AddException, ParamException;
    /**
     * 删除角色
     * @param roleId
     */
    public void deleteRole(Long roleId);
    /**
     * 修改角色
     * @param role
     * @throws ParamException
     */
    public void updateRole(Role role) throws AddException,ParamException;
    /**
     * 根据条件查找角色
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
     * 修改角色是否可用
     * @param roleId
     * @param istrue
     */
    public ResultBean updateRoleIstureAjax(Long roleId,int istrue);
    /**
     * 查找角色
     * @param roleId 角色ID
     * @return
     */
    public Role findByRoleId(Long roleId);
    /**
     * 新增角色Ajax请求
     * @param role
     * @return
     */
    public ResultBean createRoleAjax(Role role);
    
    /**
     * 更新角色Ajax请求
     * @param role
     * @return
     */
    public ResultBean updateRoleAjax(Role role);
    
    /**
     * 删除角色Ajax请求
     * @param roleId
     * @return
     */
    public ResultBean deleteRoleAjax(Long roleId);
    /**
     * 设置基本角色主页面Ajax请求
     * @param roleId
     * @return
     */
    public ResultBean setRoleResourceAjax(Long roleId,Long resourcedId);
    
    /**
     * 添加角色-用户关系
     * @param roleId
     * @param userIds
     */
    public void addRoleUsers(Long roleId, Long... userIds);
    
    /**
     * 删除角色用户
     * @param roleId 角色ID
     */
    public void deleteRoleUsers(Long roleId);
    /**
     * 删除角色权限
     * @param roleId 角色ID
     */
    public void deleteRolePermssion(Long roleId);
    /**
     * 添加用户角色Ajax
     * @param roleIds
     * @param userIds
     * @return
     */
    public ResultBean addUserRolesAjax(String roleIds, String userIds);
    
    /**
     * 查询所有角色类型
     * @return
     */
    public List<Map<String,Object>> findRoleType();
    
    public List<Role> getRoles();
    
}
