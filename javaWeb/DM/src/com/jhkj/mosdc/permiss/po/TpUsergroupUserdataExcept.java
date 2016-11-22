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
 * TableName: TP_USERGROUP_USERDATA_EXCEPT<br>
 * @author Hibernate Tools 3.4.0.CR1
 * @version 1.0
 * @since 2014-4-26 18:07:56
 */
@Entity
@Table(name = "TP_USERGROUP_USERDATA_EXCEPT")
public class TpUsergroupUserdataExcept implements Serializable {
	/**
	 * ID
	 */
	private Long id;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 排除班级ID
	 */
	private Long exceptBjId;
	/**
	 * 菜单ID
	 */
	private Long menuId;
	/**
	 * 用户组ID
	 */
	private Long usergroupId;

	public TpUsergroupUserdataExcept() {
	}

	public TpUsergroupUserdataExcept(Long id) {
		this.id = id;
	}

	public TpUsergroupUserdataExcept(Long id, Long userId, Long exceptBjId,
			Long menuId, Long usergroupId) {
		this.id = id;
		this.userId = userId;
		this.exceptBjId = exceptBjId;
		this.menuId = menuId;
		this.usergroupId = usergroupId;
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

	@Column(name = "USER_ID", precision = 16, scale = 0)
	public Long getUserId() {
		return this.userId;
	}

	/**
	 * get 用户ID.
	 * @param userId 用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**  
	 * set 排除班级ID.
	 * @return 排除班级ID
	 */

	@Column(name = "EXCEPT_BJ_ID", precision = 16, scale = 0)
	public Long getExceptBjId() {
		return this.exceptBjId;
	}

	/**
	 * get 排除班级ID.
	 * @param exceptBjId 排除班级ID
	 */
	public void setExceptBjId(Long exceptBjId) {
		this.exceptBjId = exceptBjId;
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

	/**  
	 * set 用户组ID.
	 * @return 用户组ID
	 */

	@Column(name = "USERGROUP_ID", precision = 16, scale = 0)
	public Long getUsergroupId() {
		return this.usergroupId;
	}

	/**
	 * get 用户组ID.
	 * @param usergroupId 用户组ID
	 */
	public void setUsergroupId(Long usergroupId) {
		this.usergroupId = usergroupId;
	}

}
