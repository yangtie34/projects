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
 * 系统表-系统用户<br>
 * TableName: TP_USERGROUP_MANAGER<br>
 * @author Hibernate Tools 3.4.0.CR1
 * @version 1.0
 * @since 2014-4-26 18:07:56
 */
@Entity
@Table(name = "TP_USERGROUP_MANAGER")
public class TpUsergroupManager implements Serializable {
	/**
	 * ID
	 */
	private Long id;
	/**
	 * 用户组ID
	 */
	private Long usergroupId;
	/**
	 * 组管理员用户ID
	 */
	private Long groupManagerId;

	public TpUsergroupManager() {
	}

	public TpUsergroupManager(Long id) {
		this.id = id;
	}

	public TpUsergroupManager(Long id, Long usergroupId, Long groupManagerId) {
		this.id = id;
		this.usergroupId = usergroupId;
		this.groupManagerId = groupManagerId;
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

	/**  
	 * set 组管理员用户ID.
	 * @return 组管理员用户ID
	 */

	@Column(name = "GROUP_MANAGER_ID", precision = 16, scale = 0)
	public Long getGroupManagerId() {
		return this.groupManagerId;
	}

	/**
	 * get 组管理员用户ID.
	 * @param groupManagerId 组管理员用户ID
	 */
	public void setGroupManagerId(Long groupManagerId) {
		this.groupManagerId = groupManagerId;
	}

}
