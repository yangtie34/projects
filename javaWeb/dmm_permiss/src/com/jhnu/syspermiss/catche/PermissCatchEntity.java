package com.jhnu.syspermiss.catche;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jhnu.syspermiss.permiss.entity.DataServe;
import com.jhnu.syspermiss.permiss.entity.Resources;
import com.jhnu.syspermiss.permiss.entity.Role;
import com.jhnu.syspermiss.permiss.entity.User;

public class PermissCatchEntity {
	private User user;//用户
	private Set<String> roles;//角色
	private List<Role> roleList;//角色
	private String rootRole;//根本角色
	private Resources mainPage;//主页
	private List<Resources> AllMenus;//菜单
	private List<Resources> sysMenus;//菜单
	private Set<String> permissions;//权限
	private Map<String,List<DataServe>> dataServeMap;//权限数据范围
	
	public PermissCatchEntity(User user, Set<String> roles, String rootRole,
			Resources mainPage, List<Resources> menus,List<Resources> sysMenus, Set<String> permissions,
			Map<String, List<DataServe>> dataServeMap) {
		super();
		this.user = user;
		this.roles = roles;
		this.rootRole = rootRole;
		this.mainPage = mainPage;
		this.AllMenus = menus;
		this.sysMenus=sysMenus;
		this.permissions = permissions;
		this.dataServeMap = dataServeMap;
	}
	public PermissCatchEntity(User user, Set<String> roles, String rootRole,
			Resources mainPage, List<Resources> menus,Set<String> permissions,List<Role> roleList,
			Map<String, List<DataServe>> dataServeMap) {
		super();
		this.user = user;
		this.roles = roles;
		this.rootRole = rootRole;
		this.mainPage = mainPage;
		this.roleList=roleList;
		this.AllMenus = menus;
		this.permissions = permissions;
		this.dataServeMap = dataServeMap;
	}
	public PermissCatchEntity(){
		
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	public String getRootRole() {
		return rootRole;
	}
	public void setRootRole(String rootRole) {
		this.rootRole = rootRole;
	}
	public Resources getMainPage() {
		return mainPage;
	}
	public void setMainPage(Resources mainPage) {
		this.mainPage = mainPage;
	}
	public List<Resources> getAllMenus() {
		return AllMenus;
	}
	public void setMenus(List<Resources> allMenus) {
		this.AllMenus = allMenus;
	}
	public Set<String> getPermissions() {
		return permissions;
	}
	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}
	public Map<String, List<DataServe>> getDataServeMap() {
		return dataServeMap;
	}
	public void setDataServeMap(Map<String, List<DataServe>> dataServeMap) {
		this.dataServeMap = dataServeMap;
	}
	public List<Resources> getSysMenus() {
		return sysMenus;
	}
	public void setSysMenus(List<Resources> sysMenus) {
		this.sysMenus = sysMenus;
	}
	public List<Role> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
}
