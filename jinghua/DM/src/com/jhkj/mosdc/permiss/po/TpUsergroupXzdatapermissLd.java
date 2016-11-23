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
 * TableName: TP_USERGROUP_DATAPERMISS<br>
 * @author Hibernate Tools 3.4.0.CR1
 * @version 1.0
 * @since 2014-4-26 18:07:56
 */
@Entity
@Table(name = "TP_USERGROUP_XZDATAPERMISS_LD")
public class TpUsergroupXzdatapermissLd implements Serializable {
	/**
	 * ID
	 */
	private Long id;
	/**
	 * 用户组ID
	 */
	private Long usergroupId;
	/**
	 * 行政组织结构ID
	 */
	private Long xzzzjgId;

	public TpUsergroupXzdatapermissLd() {
	}

	public TpUsergroupXzdatapermissLd(Long id) {
		this.id = id;
	}

	public TpUsergroupXzdatapermissLd(Long id, Long usergroupId, Long xzzzjgId) {
		this.id = id;
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
	 * @param xzzzjgId 行政组织结构ID
	 */
	public void setXzzzjgId(Long xzzzjgId) {
		this.xzzzjgId = xzzzjgId;
	}

}
