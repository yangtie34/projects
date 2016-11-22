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
 * TableName: TP_USERGROUP<br>
 * @author Hibernate Tools 3.4.0.CR1
 * @version 1.0
 * @since 2014-4-26 18:07:56
 */
@Entity
@Table(name = "TP_USERGROUP")
public class TpUsergroup implements Serializable {
	private Long id;
	private String groupname;
	private String ms;
	private Long pgroupId;

	public TpUsergroup() {
	}

	public TpUsergroup(Long id) {
		this.id = id;
	}
    

	public TpUsergroup(Long id, String groupname, String ms, Long pgroupId) {
		super();
		this.id = id;
		this.groupname = groupname;
		this.ms = ms;
		this.pgroupId = pgroupId;
	}

	/**  
	 */
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return this.id;
	}

	/**
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**  
	 */

	@Column(name = "GROUPNAME", length = 100)
	public String getGroupname() {
		return this.groupname;
	}

	/**
	 */
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	/**  
	 */

	@Column(name = "MS", length = 200)
	public String getMs() {
		return this.ms;
	}

	/**
	 */
	public void setMs(String ms) {
		this.ms = ms;
	}

	/**  
	 */

	@Column(name = "PGROUP_ID", precision = 16, scale = 0)
	public Long getPgroupId() {
		return this.pgroupId;
	}

	/**
	 */
	public void setPgroupId(Long pgroupId) {
		this.pgroupId = pgroupId;
	}

}
