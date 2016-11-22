package com.jhkj.mosdc.permiss.util;

import java.util.HashMap;
import java.util.Map;

import com.jhkj.mosdc.permiss.domain.UserMenu;

public class CurrentUserMenuUtil {
	private static Map<Long,UserMenu> map = new HashMap<Long, UserMenu>();
	
	protected static UserMenu getCurrentUserMenu() {
		UserMenu um = null;
		synchronized (map) {
			um = map.get(Thread.currentThread().getId());
		}
		return um;
	}
	/**
	 * 设置当前线程用户
	 * @param loginName
	 */
	public static void setThreadUserMenu(Long userId,Long menuId){
		UserMenu um = new UserMenu(menuId, userId);
		synchronized (map) {
			map.put(Thread.currentThread().getId(), um);
		}
	}
	public static UserMenu getThreadUserMenu(){
		UserMenu um = null;
		synchronized (map) {
			um = map.get(Thread.currentThread().getId());
		}
		return um;
	}
//	/**
//	 * 移除当前线程用户Menu
//	 */
//	public static void removeThreadUserMenu(){
//		synchronized (map) {
//			map.remove(Thread.currentThread().getId());
//		}
//	}
}
