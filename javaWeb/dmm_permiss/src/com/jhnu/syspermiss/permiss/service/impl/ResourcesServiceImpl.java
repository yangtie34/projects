package com.jhnu.syspermiss.permiss.service.impl;

import java.util.List;

import com.jhnu.syspermiss.permiss.dao.ResourcesDao;
import com.jhnu.syspermiss.permiss.dao.impl.ResourcesDaoImpl;
import com.jhnu.syspermiss.permiss.entity.Resources;
import com.jhnu.syspermiss.permiss.entity.Role;
import com.jhnu.syspermiss.permiss.entity.User;
import com.jhnu.syspermiss.permiss.service.ResourcesService;
import com.jhnu.syspermiss.util.StringUtils;

public class ResourcesServiceImpl implements ResourcesService {
	private ResourcesServiceImpl() {
		
	}  
    private static ResourcesServiceImpl ResourcesServiceImpl=null;
	
	public static ResourcesServiceImpl getInstance() {
		if (ResourcesServiceImpl == null){
			synchronized (new String()) {
				if (ResourcesServiceImpl == null){
					ResourcesServiceImpl = new ResourcesServiceImpl();
				}
			}
		}
		return ResourcesServiceImpl;
	}
    private ResourcesDao resourcesDao= ResourcesDaoImpl.getInstance();
    private UserServiceImpl userService=UserServiceImpl.getInstance();

	@Override
	public List<Resources> getResourcesByThis(Resources resources) {
		return resourcesDao.getResourcesByThis(resources);
	}

	@Override
	public List<Resources> getAllResources() {
		return resourcesDao.getAllResources();
	}

	@Override
	public Resources findById(Long id) {
		return resourcesDao.findById(id);
	}
	
	@Override
	public Resources findByURL(String basePath,String path) {
		return resourcesDao.findByURL(basePath,path);
	}

	@Override
	public Resources getMainPageByRole(Role role) {
		Resources res=null;
		if(role!=null){
			role.setIsmain(1);
			res= resourcesDao.getMainPageByRole(role);
		}
		return res;
	}

	@Override
	public Resources getMainPageByUser(User user) {
		return resourcesDao.getMainPageByUser(user);
	}

	@Override
	public List<Resources> getMenusByUserName(String username){
		String roolRole= userService.getUserRootRole(username);
		if("admin".equalsIgnoreCase(roolRole)){
			return resourcesDao.getAllResources();
		}
		return resourcesDao.getMenusByUserName(username);
	}
	
	@Override
	public List<String> getAllPermssionByUserName(String username) {
		return resourcesDao.getAllPermssionByUserName(username);
	}

	@Override
	public boolean hasPermssion(String username, String shiroTag) {
		List<Resources> list=resourcesDao.hasPermssion(username, shiroTag);
		if(list.size()>0){
			return true;
		}
		return false;
	}

	@Override
	public List<Resources> getMenusByUserName(String username, String sys) {
		String roolRole= userService.getUserRootRole(username);
		if("admin".equalsIgnoreCase(roolRole)){
			return resourcesDao.getAllResources(sys);
		}
		return resourcesDao.getMenusByUserName(username, sys);
	}

	@Override
	public List<Resources> getMenusByUserNameShiroTag(String username,String shiroTag) {
		if(StringUtils.hasText(shiroTag)){
			shiroTag=shiroTag.substring(0,shiroTag.lastIndexOf(":"));
			return resourcesDao.getMenusByUserNameShiroTag(username, shiroTag);
		}
		return null;
	}
}
