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
@Table(name = "TP_USERGROUP_DATAPERMISS")
public class TpUsergroupDatapermiss implements Serializable {
	/**
	 * ID
	 */
	private Long id;
	/**
	 * 用户组ID
	 */
	private Long usergroupId;
	/**
	 * 班级ID
	 */
	private Long bjId;

	public TpUsergroupDatapermiss() {
	}

	public TpUsergroupDatapermiss(Long id) {
		this.id = id;
	}

	public TpUsergroupDatapermiss(Long id, Long usergroupId, Long bjId) {
		this.id = id;
		this.usergroupId = usergroupId;
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
