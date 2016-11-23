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
 * TableName: TP_USER_PROXY_DATA_EXCEPT<br>
 * @author Hibernate Tools 3.4.0.CR1
 * @version 1.0
 * @since 2014-4-26 18:07:56
 */
@Entity
@Table(name = "TP_USER_PROXY_DATA_EXCEPT")
public class TpUserProxyDataExcept implements Serializable {
	/**
	 * UID
	 */
	private static final Long serialVersionUID = 1L;
	/**
	 * ID
	 */
	private Long id;
	/**
	 * 用户ID
	 */
	private Long xfId;
	/**
	 * 排除班级ID
	 */
	private Long bjId;
	/**
	 * 菜单ID
	 */
	private Long menuId;

	public TpUserProxyDataExcept() {
	}

	public TpUserProxyDataExcept(Long id) {
		this.id = id;
	}

	public TpUserProxyDataExcept(Long id, Long xfId, Long bjId, Long menuId) {
		this.id = id;
		this.xfId = xfId;
		this.bjId = bjId;
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
	 * set 用户ID.
	 * @return 用户ID
	 */

	@Column(name = "XF_ID", precision = 16, scale = 0)
	public Long getXfId() {
		return this.xfId;
	}

	/**
	 * get 用户ID.
	 * @param xfId 用户ID
	 */
	public void setXfId(Long xfId) {
		this.xfId = xfId;
	}

	/**  
	 * set 排除班级ID.
	 * @return 排除班级ID
	 */

	@Column(name = "BJ_ID", precision = 16, scale = 0)
	public Long getBjId() {
		return this.bjId;
	}

	/**
	 * get 排除班级ID.
	 * @param bjId 排除班级ID
	 */
	public void setBjId(Long bjId) {
		this.bjId = bjId;
	}

	/**  
	 * set 菜单ID.
	 * @return 菜单ID
	 */

	@Column(name = "MENU_ID", precision = 16, scale = 0)
	public Long getMenuId() {
		return this.menuId;
	}

	/**
	 * get 菜单ID.
	 * @param menuId 菜单ID
	 */
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

}
