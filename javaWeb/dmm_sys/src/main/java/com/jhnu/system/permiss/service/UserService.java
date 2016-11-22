package com.jhnu.system.permiss.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jhnu.framework.entity.ResultBean;
import com.jhnu.framework.exception.handle.AddException;
import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.permiss.entity.Role;
import com.jhnu.system.permiss.entity.User;
import com.jhnu.system.permiss.entity.UserExpandInfo;
import com.jhnu.system.permiss.entity.UserRole;

public interface UserService {
	
    /**
     * 创建用户
     * @param user
     */
    public User createUser(User user) throws AddException,ParamException;
    
    /**
     * 验证密码
     * @param userId
     * @param password
     */
    public boolean checkPassword(Long userId, String password);

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    public void changePassword(Long userId, String newPassword);
    
    /**
     * 重置密码
     * @param userId
     */
    public void resetPassword(Long userId);
    /**
     * 根据条件查询角色对象集合
     * @param role
     * @return
     */
	public List<Role> findRolesList(String username);
    /**
     * 添加用户-角色关系
     * @param userId
     * @param roleIds
     * @throws ParamException 
     */
    public void correlationRoles(Long userId, Long... roleIds) throws ParamException;
    
    /**
     * 批量添加用户-角色关系
     * @param userRoles
     */
    public void correlationRoles(List<UserRole> userRoles);
    
    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public User findByUsername(String username);

    /**
     * 根据用户名查找其角色
     * @param username
     * @return
     */
    public Set<String> findRoles(String username);
    
    /**
     * 通过用户ID查找角色名集合
     * @param userId 用户ID
     * @return
     */
    public List<String> getRoleNamesByUserId(long userId);

    /**
     * 根据用户名查找其权限
     * @param username
     * @return
     */
    public Set<String> findPermissions(String username);

    /**
     * 保存用户登录时间和方式
     * @param username
     * @param loginWay
     */
    public void addUserLogging(String username,String loginWay);
    /**
     * 冻结用户列表
     * @param userIds
     * @return 
     */
    public int[] freezeUsers(List<String> userIds);
    /**
     * 解冻用户列表
     * @param userIds
     */
    public void unfreezeUsers(List<String> userIds);
    /**
     * 批量新增用户
     * @param users
     */
    public List<User> createUsers(List<User> users);
    /**
     * 获取用户扩展信息
     * @param userName
     * @return
     */
    public UserExpandInfo getUserExpandInfo(String userName) ;
    /**
     * 获取用户最后一次登录时间
     * @param userName
     * @return
     */
    public String getUserLastLoginTime(String userName);
    
    /**
     * 获取用户分页
     * @param user 筛选用户的条件
     * @return
     */
    public Page getPageUsers(int currentPage, int numPerPage,User user);
    
    /**
     * 获取所有用户
     * @param user 筛选用户的条件
     * @return
     */
    public List<User> getAllUsers(User user);
    
    /**
     * 删除用户
     * @param user 筛选用户的条件
     */
    public void deleteUserById(Long id);
    
    /**
     * 获取该用户的根本角色 即返回student，teacher，admin
     * @param username 用户名
     * @return
     */
    public String getUserRootRole(String username);
    /**
     * 添加用户角色Ajax请求
     * @param userId
     * @param roleIds
     * @return
     */
    public ResultBean addUserRoleAjax(String userId,String roleIds);
    /**
     * 修改密码Ajax请求
     * @param username
     * @param oldpwd
     * @param newpwd
     * @return
     */
    public ResultBean updateUserpwdAjax(String username,String oldpwd,String newpwd);
    List<Map<String, Object>> getUserRoles(String username);
    
    public List<Long> getUserIdsByAddTime(String addTime);
}
