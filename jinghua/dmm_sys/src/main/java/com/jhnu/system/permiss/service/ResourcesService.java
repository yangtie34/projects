package com.jhnu.system.permiss.service;


import java.util.List;
import java.util.Map;

import com.jhnu.framework.entity.NodeAngularTree;
import com.jhnu.framework.entity.NodeTree;
import com.jhnu.framework.entity.ResultBean;
import com.jhnu.framework.exception.handle.AddException;
import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.system.common.code.entity.Code;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.permiss.entity.Resources;
import com.jhnu.system.permiss.entity.Role;
import com.jhnu.system.permiss.entity.User;
import com.jhnu.util.common.TreeUtil;


public interface ResourcesService {

	/**
	 * 新建资源
	 * @param resource
	 * @return
	 * @throws AddException
	 * @throws ParamException
	 */
    public Resources createResource(Resources resource) throws AddException, ParamException;
    
    /**
     * 新建资源Ajax请求
     * @param resource
     * @return
     */
    public ResultBean createResourceAjax(Resources resource);
    
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
     * 获取资源列表angular中的tree
     * @param resources
     * @return
     */
    public Object getResourceAngularTree(Resources resources);
    

    /**
     * 删除资源
     * @param resourcesId
     * @throws ParamException
     */
    public void deleteResources(Long resourcesId) throws ParamException;

    /**
     * 获取所有资源的Json格式字符串
     * @return
     */
    public String getAllResNodeJson();

    /**
     * 修改资源
     * @param resources
     * @throws AddException
     * @throws ParamException
     */
    public void updateResources(Resources resources) throws AddException, ParamException;
    /**
     * 修改资源Ajax请求
     * @param resources
     * @return
     */
    public ResultBean updateResourcesAjax(Resources resources);
    /**
     * 删除资源Ajax请求
     * @param resourcesId
     * @return
     */
    public ResultBean deleteResourcesAjax(Long resourcesId);
    
    /**
     * 查询资源
     * @param id 资源ID
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
     * 通过用户名获取在该系统路径下的所拥有的菜单
     * @param username
     * @param sys 系统路径
     * @return
     */
    public List<Resources> getMenusByUserName(String username,String sys);
    
    /**
     * 通过用户名在该权限标识下的获取所拥有的菜单
     * @param username 用户名
     * @param shiroTag 权限标识
     * @return
     */
    public List<Resources> getMenusByUserNameShiroTag(String username,String shiroTag);
    
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
    public boolean hasPermssion(String username,String shiroTag);

	List<Code> getResType();

	Object getPermssionsById(String flag, String id);
	/**
     * 获取该用户当前网址是否有权限
     * @param username
     * @return
     */
	boolean hasMenuByUserName(String username,String basePath, String sys);

	Map<String, Object> getPermssionsByFlagAndId(String flag, String id);
}
