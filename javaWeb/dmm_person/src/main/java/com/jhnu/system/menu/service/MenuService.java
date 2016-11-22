package com.jhnu.system.menu.service;

import java.util.List;

import com.jhnu.system.menu.entity.Menu;

public interface MenuService {
	/**
	 * 获取所有菜单
	 * @return 菜单集合
	 */
	public List<Menu> getAllMenus();
	
	/**
	 * 通过条件获取菜单集合
	 * @param menu 对应条件
	 * @return 菜单集合
	 */
	public List<Menu> getMenuByThis(Menu menu);
	
	/**
	 * 通过条件获取菜单集合
	 * @param menu 菜单条件
	 * @param userId 登录者ID
	 * @return
	 */
	public List<Menu> getMenuByThis(Menu menu,Long userId);
	
	/**
	 * 添加或更新菜单
	 * @param menu 如果ID为空则为添加，反之为更新
	 */
	public Menu saveOrUpdateMenu(Menu menu);
	
	/**
	 * 通过ID删除菜单
	 * @param id
	 */
	public void deleteMenuById(Long id);

	/**
	 * 获取单个菜单
	 * @param menu 根据菜单对象
	 * @return
	 */
	public Menu getOneMenuByThis(Menu menu); 
	
}
