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
 * 系统表-用户行政组织结构数据权限表<br>
 * TableName: TP_USERGROUP_USERDATAPERMISS<br>
 * @author Hibernate Tools 3.4.0.CR1
 * @version 1.0
 * @since 2014-4-26 18:07:56
 */
@Entity
@Table(name = "TP_USERGROUP_XZ_UDPERMISS_LD")
public class TpUsergroupUserXzDatapermissLd implements Serializable {
	/**
	 * ID
	 */
	private Long id;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 用户组ID
	 */
	private Long usergroupId;
	/**
	 * 行政组织结构ID
	 */
	private Long xzzzjgId;

	public TpUsergroupUserXzDatapermissLd() {
	}

	public TpUsergroupUserXzDatapermissLd(Long id) {
		this.id = id;
	}

	public TpUsergroupUserXzDatapermissLd(Long id, Long userId, Long usergroupId,
			Long xzzzjgId) {
		this.id = id;
		this.userId = userId;
		this.usergroupId = usergroupId;
		this.xzzzjgId = xzzzjgId;
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
	 * set 行政组织结构ID.
	 * @return 行政组织结构ID
	 */

	@Column(name = "XZZZJG_ID", precision = 16, scale = 0)
	public Long getXzzzjgId() {
		return this.xzzzjgId;
	}

	/**
	 * get 行政组织结构ID.
	 * @param jxzzjgId 行政组织结构ID
	 */
	public void setXzzzjgId(Long xzzzjgId) {
		this.xzzzjgId = xzzzjgId;
	}

}
