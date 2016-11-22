package com.jhnu.system.permiss.dao;

import java.util.List;

import com.jhnu.framework.entity.NodeAngularTree;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.permiss.entity.Resources;
import com.jhnu.system.permiss.entity.Role;
import com.jhnu.system.permiss.entity.User;

public interface ResourcesDao {

public Resources createResource(Resources resource);
    
    /**
     * 通过条件获取资源
     * @param resources
     * @return
     */
    public List<Resources> getResourcesByThis(Resources resources);
    
    /**
     * 通过条件获取资源Page
     * @param resources
     * @return
     */
    public Page getResourcesPageByThis(int currentPage, int numPerPage,Resources resources);
    
    /**
     * 获取所有资源
     * @return
     */
    public List<Resources> getAllResources();
    
    /**
     * 删除资源
     * @param resourcesId
     */
    public void deleteResources(Long resourcesId);
    /**
     * 修改资源
     * @param resources
     */
    public void updateResources(Resources resources);

    /**
     * 查询资源
     * @param id
     * @return
     */
    public Resources findById(Long id);
    
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
     * 获取资源列表angular中的tree集合
     * @param resources
     * @return
     */
    public List<NodeAngularTree> getResourceAngularTree(Resources resources);
    
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
    
}
