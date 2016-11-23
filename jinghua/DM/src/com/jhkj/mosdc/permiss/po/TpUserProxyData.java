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
 * TableName: TP_USER_PROXY_DATA<br>
 * @author Hibernate Tools 3.4.0.CR1
 * @version 1.0
 * @since 2014-4-26 18:07:56
 */
@Entity
@Table(name = "TP_USER_PROXY_DATA")
public class TpUserProxyData implements Serializable {
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
	 * 班级ID
	 */
	private Long bjId;

	public TpUserProxyData() {
	}

	public TpUserProxyData(Long id) {
		this.id = id;
	}

	public TpUserProxyData(Long id, Long xfId, Long bjId) {
		this.id = id;
		this.xfId = xfId;
		this.bjId = bjId;
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
	 * set 班级ID.
	 * @return 班级ID
	 */

	@Column(name = "BJ_ID", precision = 16, scale = 0)
	public Long getBjId() {
		return this.bjId;
	}

	/**
	 * get 班级ID.
	 * @param bjId 班级ID
	 */
	public void setBjId(Long bjId) {
		this.bjId = bjId;
	}

}
