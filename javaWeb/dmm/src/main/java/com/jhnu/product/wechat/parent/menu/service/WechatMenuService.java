package com.jhnu.product.wechat.parent.menu.service;

import java.util.List;

import com.jhnu.product.wechat.parent.menu.entity.WechatMenu;


public interface WechatMenuService {
	/**
	 * 获取所有菜单
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
	 * 添加或更新菜单
	 * @param menu 如果ID为空则为添加，反之为更新
	 */
	public WechatMenu saveOrUpdateWechatMenu(WechatMenu menu);
	
	/**
	 * 通过ID删除菜单
	 * @param id
	 */
	public void deleteWechatMenuById(Long id);

	/**
	 * 获取单个菜单
	 * @param menu 根据菜单对象
	 * @return
	 */
	public WechatMenu getOneWechatMenuByThis(WechatMenu menu); 
	
}
