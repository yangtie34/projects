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
 * TableName: TP_USER_ROLE<br>
 * @author Hibernate Tools 3.4.0.CR1
 * @version 1.0
 * @since 2014-4-26 18:07:56
 */
@Entity
@Table(name = "TP_USER_ROLE")
public class TpUserRole implements Serializable {
	/**
	 * ID
	 */
	private Long id;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 角色ID
	 */
	private Long roleId;
	/**
	 * 用户组ID
	 */
	private Long usergroupId;

	public TpUserRole() {
	}

	public TpUserRole(Long id) {
		this.id = id;
	}

	public TpUserRole(Long id, Long userId, Long roleId, Long usergroupId) {
		this.id = id;
		this.userId = userId;
		this.roleId = roleId;
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
