package com.jhnu.system.menu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.system.menu.dao.MenuDao;
import com.jhnu.system.menu.entity.Menu;
import com.jhnu.system.menu.service.MenuService;

@Service("menuService")
public class MenuServiceImpl implements MenuService{

	@Autowired
	private MenuDao menuDao;
	
	@Override
	public List<Menu> getAllMenus() {
		return menuDao.getAllMenus();
	}

	@Override
	public List<Menu> getMenuByThis(Menu menu) {
		return menuDao.getMenuByThis(menu);
	}

	@Override
	public Menu saveOrUpdateMenu(Menu menu) {
		// TODO 添加或更新菜单
		return null;
	}

	@Override
	public void deleteMenuById(Long id) {
		menuDao.deleteMenuById(id);
		
	}

	@Override
	public List<Menu> getMenuByThis(Menu menu, Long userId) {
		return menuDao.getMenuByThis(menu, userId);
	}

	@Override
	public Menu getOneMenuByThis(Menu menu) {
		return menuDao.getOneMenuByThis(menu);
	}

}
