package com.jhnu.product.wechat.parent.menu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.wechat.parent.menu.dao.WechatMenuDao;
import com.jhnu.product.wechat.parent.menu.entity.WechatMenu;
import com.jhnu.product.wechat.parent.menu.service.WechatMenuService;



@Service("wechatMenuService")
public class WechatMenuServiceImpl implements WechatMenuService{

	@Autowired
	private WechatMenuDao wechatMenuDao;
	
	@Override
	public List<WechatMenu> getAllWechatMenus() {
		return wechatMenuDao.getAllWechatMenus();
	}

	@Override
	public List<WechatMenu> getWechatMenuByThis(WechatMenu wechatMenu) {
		return wechatMenuDao.getWechatMenuByThis(wechatMenu);
	}

	@Override
	public WechatMenu saveOrUpdateWechatMenu(WechatMenu wechatMenu) {
		// TODO 添加或更新菜单
		return null;
	}

	@Override
	public void deleteWechatMenuById(Long id) {
		wechatMenuDao.deleteWechatMenuById(id);
		
	}

	@Override
	public WechatMenu getOneWechatMenuByThis(WechatMenu wechatMenu) {
		return wechatMenuDao.getOneWechatMenuByThis(wechatMenu);
	}

}
