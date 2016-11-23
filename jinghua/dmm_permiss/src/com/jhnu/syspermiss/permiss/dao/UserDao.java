package com.jhnu.syspermiss.permiss.dao;

import java.util.List;
import java.util.Set;

import com.jhnu.syspermiss.permiss.entity.Role;
import com.jhnu.syspermiss.permiss.entity.User;
import com.jhnu.syspermiss.permiss.entity.UserRole;

public interface UserDao {

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
     * 通过用户ID查找用户
     * @param userId 用户ID
     * @return
     */
    public User findOne(Long userId);

    /**
     * 通过用户名查找用户
     * @param username 用户名
     * @return
     */
    public User findByUsername(String username);

    /**
     * 通过用户名获取角色
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
     * 通过用户名获取权限
     * @param username
     * @return
     */
    public Set<String> findPermissions(String username);
    
    /**
     * 通过用户ID查找角色名集合
     * @param userId 用户ID
     * @return
     */
    public List<String> getRoleNamesByUserId(long userId);
 
    /**
     * 查找用户最后一次登录时间
     * @param userName
     * @return
     */
    public String getUserLastLoginTime(String userName);
    
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
