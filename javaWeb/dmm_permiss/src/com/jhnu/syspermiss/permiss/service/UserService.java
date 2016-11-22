package com.jhnu.syspermiss.permiss.service;

import java.util.List;
import java.util.Set;

import com.jhnu.syspermiss.permiss.entity.Role;
import com.jhnu.syspermiss.permiss.entity.User;
import com.jhnu.syspermiss.permiss.entity.UserRole;

public interface UserService {
	/**
	 * 创建用户
	 * @param user 用户信息
	 * @return
	 */
    public User createUser(User user);
    
    /**
     * 更新用户
     * @param user 用户信息
     */
    public void updateUser(User user);
    
    /**
     * 通过用户ID删除用户
     * @param userId 用户ID
     */
    public void deleteUserById(Long userId);
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
     * 根据条件查询角色对象集合
     * @param role
     * @return
     */
	public List<Role> findRolesList(String username);
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
     * 获取该用户的根本角色 即返回student，teacher，admin
     * @param username 用户名
     * @return
     */
    public String getUserRootRole(String username);
    
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
     * 添加用户-角色关系
     * @param userRoles
     */
    public void correlationRole(UserRole userRole);
    
    /**
     * 删除用户-角色关系
     * @param userRoles
     */
    public void noCorrelationRole(Long userId,Long roleId);
    
    /**
     * 删除用户-角色关系
     * @param userRoles
     */
    public void clearCorrelationRole(Long userId);
    
}
