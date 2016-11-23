package com.jhkj.mosdc.permiss.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import com.jhkj.mosdc.framework.util.StringUtils;
import com.jhkj.mosdc.permiss.po.TpMenu;
import com.jhkj.mosdc.permiss.po.TpRole;

/**
 * 用户组权限模型类
 * @author Administrator
 *
 */
public class UserGroupPermission {
	private Base base;
	private Long userId;
	private Long usergroupId;//用户组信息
	private List<TpRole> roles;//用户组内用户拥有的角色集合
	private Map<Long,Node> nodeMap;//菜单键值对集合
	private List<TpMenu> menuList;//菜单集合
	protected UserGroupDataPermission gdp;//用户在用户组拥有的数据权限 
	
	public UserGroupPermission(Long userId,Long usergroupId,Base base){
		this.base = base;
		this.userId = userId;
		this.usergroupId = usergroupId;
		gdp = new UserGroupDataPermission(base,userId, usergroupId);//初始化数据权限
		this.initRoles();//初始化角色
		this.initMenuPermiss();//初始化菜单权限
	}
	/**
	 * 初始化该用户组下-用户菜单权限
	 */
	private void initMenuPermiss() {
		// TODO Auto-generated method stub
		List<TpMenu> userMenus = this.getUserMenus();
		List<TpMenu> roleMenus = this.getRoleMenus();
		userMenus.addAll(roleMenus);
		menuList = userMenus;
		nodeMap = Node.translateNodeHashForTpMenu(userMenus);
	}
	/**
	 * 获取用户的直接权限
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<TpMenu> getUserMenus(){
		String sql = "select tm.* from TP_USERGROUP_MENU_USER tumu "
				+ " inner join TP_MENU tm on tumu.menu_id = tm.id and tumu.user_id="+userId
				+ " and tumu.usergroup_id  " + base.getUserGroupCondition(usergroupId);
		List<TpMenu> list = base.getEntityListBySql(TpMenu.class, sql);
		return list;
	}
	/**
	 * 初始化该用户组角色-对应的所有菜单权限
	 * @return
	 */
	private List<TpMenu> getRoleMenus(){
		String sql = "select distinct tm.* from TP_USER_ROLE tur inner join TP_ROLE tr on tur.role_id = tr.id and tur.user_id = "+userId 
				+ "and tur.usergroup_id "+base.getUserGroupCondition(usergroupId)
				+ "inner join TP_ROLE_MENU trm on trm.role_id = tr.id inner join TP_MENU tm on trm.menu_id = tm.id";
		List<TpMenu> list = base.getEntityListBySql(TpMenu.class, sql);
		return list;
	}
	/**
	 * 初始化角色
	 */
	public void initRoles(){
		String sql = "select tr.* from TP_USER_ROLE tur inner join TP_ROLE tr on tur.role_id = tr.id and tur.user_id = "+userId 
				+ "and tur.usergroup_id "+base.getUserGroupCondition(usergroupId);
		roles = base.getEntityListBySql(TpRole.class, sql);
	}
	/**
	 * 以sql的形式，获取菜单对应的数据权限
	 * @param menuId
	 * @return
	 */
	public String getSqlMenuDataPermiss(Long menuId){
		if(nodeMap.get(menuId)!=null){
			return gdp.getSqlMenuDataPermiss(menuId);
		}else{
			return gdp.getSqlMenuDataPermiss(111l);
		}
	}
	/**
	 * 以hql的形式，获取菜单对应的数据权限
	 * @param menuId
	 * @return
	 */
	public String getHqlMenuDataPermiss(Long menuId){
		if(nodeMap.get(menuId)!=null){
			return gdp.getHqlMenuDataPermissCondition(menuId);
		}else{
			return gdp.getHqlMenuDataPermissCondition(111l);
		}
	}
	/**
	 * 以sql的形式，获取菜单对应的行政组织结构数据权限
	 * @return
	 */
	public String getSqlMenuXzDataPermiss(Long menuId){
		if(nodeMap.get(menuId)!=null){
			return gdp.getSqlMenuXzDataPermiss(menuId);
		}else{
			return gdp.getSqlMenuXzDataPermiss(111l);
		}
	}
	/**
	 * 以hql的形式，获取菜单对应的行政组织结构数据权限
	 * @param menuId
	 * @return
	 */
	public String getHqlMenuXzDataPermiss(Long menuId){
		if(nodeMap.get(menuId)!=null){
			return gdp.getHqlMenuXzDataPermissCondition(menuId);
		}else{
			return gdp.getHqlMenuXzDataPermissCondition(111l);
		}
	}
	public Map<Long,Node> getNodeHash(){
		return nodeMap;
	}
	public List<TpRole> getRoles(){
		return roles;
	}
	public List<TpMenu> getMenuList() {
		return menuList;
	}
	
}
