package com.jhnu.product.wechat.parent.menu.dao;

import java.util.List;

import com.jhnu.product.wechat.parent.menu.entity.WechatMenu;


public interface WechatMenuDao {
	
	/**
	 * 通过条件获取菜单集合
	 * @param menu 对应条件
	 * @return 菜单集合
	 */
	public List<WechatMenu> getAllWechatMenus();
	
	/**
	 * 通过条件获取菜单集合
	 * @param menu 对应条件
	 * @return 菜单集合
	 */
	public List<WechatMenu> getWechatMenuByThis(WechatMenu menu);
	
	/**
	 * 获取单个菜单
	 * @param menu 根据菜单对象
	 * @return
	 */
	public WechatMenu getOneWechatMenuByThis(WechatMenu menu); 
	
	/**
	 * 添加菜单
	 * @param menu 菜单
	 */
	public WechatMenu saveWechatMenu(WechatMenu menu);
	
	/**
	 * 更新菜单
	 * @param menu 菜单
	 */
	public WechatMenu updateWechatMenu(WechatMenu menu);
	
	/**
	 * 通过ID删除菜单
	 * @param id
	 */
	public void deleteWechatMenuById(Long id); 
	
}
