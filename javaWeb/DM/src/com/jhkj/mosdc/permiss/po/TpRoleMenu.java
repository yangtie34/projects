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
 * 系统表-角色资源关系表<br>
 * TableName: TP_ROLE_MENU<br>
 * @author Hibernate Tools 3.4.0.CR1
 * @version 1.0
 * @since 2014-4-26 18:07:56
 */
@Entity
@Table(name = "TP_ROLE_MENU")
public class TpRoleMenu implements Serializable {
	/**
	 * ID
	 */
	private Long id;
	/**
	 * 角色ID
	 */
	private Long roleId;
	/**
	 * 菜单id
	 */
	private Long menuId;

	public TpRoleMenu() {
	}

	public TpRoleMenu(Long id) {
		this.id = id;
	}

	public TpRoleMenu(Long id, Long roleId, Long menuId) {
		this.id = id;
		this.roleId = roleId;
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
	 * set 角色ID.
	 * @return 角色ID
	 */

	@Column(name = "ROLE_ID", precision = 16, scale = 0)
	public Long getRoleId() {
		return this.roleId;
	}

	/**
	 * get 角色ID.
	 * @param roleId 角色ID
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**  
	 * set 菜单id.
	 * @return 菜单id
	 */

	@Column(name = "MENU_ID", precision = 16, scale = 0)
	public Long getMenuId() {
		return this.menuId;
	}

	/**
	 * get 菜单id.
	 * @param menuId 菜单id
	 */
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

}
