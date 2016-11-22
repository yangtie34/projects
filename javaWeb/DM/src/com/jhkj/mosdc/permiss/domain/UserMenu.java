package com.jhkj.mosdc.permiss.domain;

/**
 * 当前用户ID以及菜单ID
 * @author Administrator
 *
 */
public class UserMenu {
	private Long menuId;
	private Long userId;
	private Long proxyMenuId;
	
	public UserMenu(Long menuId, Long userId) {
		super();
		this.menuId = menuId;
		this.userId = userId;
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
