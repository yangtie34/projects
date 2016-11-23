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
 * 系统表-角色<br>
 * TableName: TP_ROLE<br>
 * @author Hibernate Tools 3.4.0.CR1
 * @version 1.0
 * @since 2014-4-26 18:07:56
 */
@Entity
@Table(name = "TP_ROLE")
public class TpRole implements Serializable {
	/**
	 * 角色标识
	 */
	private Long id;
	/**
	 * 角色名称
	 */
	private String jsmc;
	/**
	 * 角色名称
	 */
	private Long jslxId;
	/**
	 * 角色描述
	 */
	private String jsms;
	/**
	 * 用户组ID（根节点为空）
	 */
	private Long usergroupId;

	public TpRole() {
	}

	public TpRole(Long id) {
		this.id = id;
	}

	public TpRole(Long id, String jsmc, Long jslxId, String jsms,
			Long usergroupId) {
		this.id = id;
		this.jsmc = jsmc;
		this.jslxId = jslxId;
		this.jsms = jsms;
		this.usergroupId = usergroupId;
	}

	/**  
	 * set 角色标识.
	 * @return 角色标识
	 */
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return this.id;
	}

	/**
	 * get 角色标识.
	 * @param id 角色标识
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**  
	 * set 角色名称.
	 * @return 角色名称
	 */

	@Column(name = "JSMC", length = 200)
	public String getJsmc() {
		return this.jsmc;
	}

	/**
	 * get 角色名称.
	 * @param jsmc 角色名称
	 */
	public void setJsmc(String jsmc) {
		this.jsmc = jsmc;
	}

	/**  
	 * set 角色名称.
	 * @return 角色名称
	 */

	@Column(name = "JSLX_ID", precision = 16, scale = 0)
	public Long getJslxId() {
		return this.jslxId;
	}

	/**
	 * get 角色名称.
	 * @param jslxId 角色名称
	 */
	public void setJslxId(Long jslxId) {
		this.jslxId = jslxId;
	}

	/**  
	 * set 角色描述.
	 * @return 角色描述
	 */

	@Column(name = "JSMS", length = 200)
	public String getJsms() {
		return this.jsms;
	}

	/**
	 * get 角色描述.
	 * @param desc 角色描述
	 */
	public void setJsms(String jsms) {
		this.jsms = jsms;
	}

	/**  
	 * set 用户组ID（根节点为空）.
	 * @return 用户组ID（根节点为空）
	 */

	@Column(name = "USERGROUP_ID", precision = 16, scale = 0)
	public Long getUsergroupId() {
		return this.usergroupId;
	}

	/**
	 * get 用户组ID（根节点为空）.
	 * @param usergroupId 用户组ID（根节点为空）
	 */
	public void setUsergroupId(Long usergroupId) {
		this.usergroupId = usergroupId;
	}

}
