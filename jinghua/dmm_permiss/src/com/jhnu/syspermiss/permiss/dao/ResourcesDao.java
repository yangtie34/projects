package com.jhnu.syspermiss.permiss.dao;

import java.util.List;

import com.jhnu.syspermiss.permiss.entity.Resources;
import com.jhnu.syspermiss.permiss.entity.Role;
import com.jhnu.syspermiss.permiss.entity.User;

public interface ResourcesDao {

    
    /**
     * 通过条件获取资源
     * @param resources
     * @return
     */
    public List<Resources> getResourcesByThis(Resources resources);
    
    
    /**
     * 获取所有资源
     * @return
     */
    public List<Resources> getAllResources();
    

    /**
     * 查询资源
     * @param id
     * @return
     */
    public Resources findById(Long id);
    
    public Resources findByURL(String basePath,String path);
    
    /**
     * 通过角色获取主页信息
     * @param role
     * @return
     */
    public Resources getMainPageByRole(Role role);
    
    /**
     * 通过角色获取主页信息
     * @param user
     * @return
     */
    public Resources getMainPageByUser(User user);
    
    
    /**
     * 通过用户名获取所拥有的菜单
     * @param username
     * @return
     */
    public List<Resources> getMenusByUserName(String username);
    
    /**
     * 获取该用户名所有拥有的权限
     * @param username
     * @return
     */
    public List<String> getAllPermssionByUserName(String username);
    
    /**
     * 该用户名是否拥有该权限。
     * @param username
     * @param shiroTag
     * @return
     */
    public List<Resources> hasPermssion(String username,String shiroTag);


	List<Resources> getMenusByUserName(String username, String sys);


	List<Resources> getMenusByUserNameShiroTag(String username, String shiroTag);


	List<Resources> getAllResources(String sys);
    
}
