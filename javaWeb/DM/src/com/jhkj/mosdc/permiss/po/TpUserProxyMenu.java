/*
 * 
 * Copyright (c) 2009 HZH All Rights Reserved.
 */
package com.jhkj.mosdc.permiss.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;

/**
 * 系统表-用户数据权限表<br>
 * TableName: TP_USER_PROXY_MENU<br>
 * @author Hibernate Tools 3.4.0.CR1
 * @version 1.0
 * @since 2014-4-26 18:07:56
 */
@Entity
@Table(name = "TP_USER_PROXY_MENU")
public class TpUserProxyMenu implements Serializable {
	/**
	 * ID
	 */
	private Long id;
	/**
	 * 下放任务ID
	 */
	private Long xfId;
	/**
	 * 下放菜单资源ID
	 */
	private Long menuId;

	public TpUserProxyMenu() {
	}

	public TpUserProxyMenu(Long id) {
		this.id = id;
	}

	public TpUserProxyMenu(Long id, Long xfId, Long menuId) {
		this.id = id;
		this.xfId = xfId;
		this.menuId = menuId;
	}

	/**  
	 * set ID.
	 * @return ID
	 */
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return this.id;
	}

	/**
	 * get ID.
	 * @param id ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**  
	 * set 下放任务ID.
	 * @return 下放任务ID
	 */

	@Column(name = "XF_ID", precision = 16, scale = 0)
	public Long getXfId() {
		return this.xfId;
	}

	/**
	 * get 下放任务ID.
	 * @param xfId 下放任务ID
	 */
	public void setXfId(Long xfId) {
		this.xfId = xfId;
	}

	/**  
	 * set 下放菜单资源ID.
	 * @return 下放菜单资源ID
	 */

	@Column(name = "MENU_ID", precision = 16, scale = 0)
	public Long getMenuId() {
		return this.menuId;
	}

	/**
	 * get 下放菜单资源ID.
	 * @param menuId 下放菜单资源ID
	 */
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

}
