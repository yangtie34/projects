package com.jhnu.edu.dao;

import java.util.List;
import java.util.Set;

import com.jhnu.edu.entity.User;
import com.jhnu.framework.page.Page;
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
     * 用户关联多角色ID
     * @param userId 用户ID
     * @param roleIds 角色IDs
     */
    public void correlationRoles(Long userId, Long... roleIds);

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
    public List<String> getRoleNamesByUserId(String userId);
    /**
     * 保存用户登录时间及方式
     * @param username
     * @param loginDate
     * @param loginWay
     */
    public void addUserLogging(String username,String loginDate,String loginWay);
    /**
     * 冻结用户
     * @param userIds
     * @return 
     */
    public void freezeUsers(List<Long> userIds);
    /**
     * 解冻用户
     * @param userIds
     */
    public void unfreezeUsers(List<Long> userIds);
    /**
     * 批量新增用户
     * @param users
     */
    public void createUsers(List<User> users);
    /**
     * 批量添加用户-角色关系
     * @param userRoles
     */
    public void correlationRoles(List<UserRole> userRoles);
    /**
     * 查找用户最后一次登录时间
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
     * 获取用户总数
     * @param user
     * @return
     */
    public int getUserCount(User user);
    
}
