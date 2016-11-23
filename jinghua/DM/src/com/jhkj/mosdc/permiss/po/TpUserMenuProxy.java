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
 * TableName: TP_USER_MENU_PROXY<br>
 * @author Hibernate Tools 3.4.0.CR1
 * @version 1.0
 * @since 2014-4-26 18:07:56
 */
@Entity
@Table(name = "TP_USER_MENU_PROXY")
public class TpUserMenuProxy implements Serializable {
	/**
	 * ID
	 */
	private Long id;
	/**
	 * 用户ID
	 */
	private Long ownerUserId;
	/**
	 * 接受权限用户ID
	 */
	private Long xfUserId;
	/**
	 * 下放用户姓名
	 */
	private String xfusername;
	/**
	 * 下放用户登录名
	 */
	private String xfloginname;
	/**
	 * 备注
	 */
	private String bz;
	/**
	 * 委派开始时间
	 */
	private String startdate;
	/**
	 * 委派结束时间
	 */
	private String enddate;

	public TpUserMenuProxy() {
	}

	public TpUserMenuProxy(Long id) {
		this.id = id;
	}

	@Column(name = "XFLOGINNAME", length = 60)
	public String getXfloginname() {
		return xfloginname;
	}

	public void setXfloginname(String xfloginname) {
		this.xfloginname = xfloginname;
	}
	@Column(name = "BZ", length = 60)
	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
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

	@Column(name = "OWNER_USER_ID", precision = 16, scale = 0)
	public Long getOwnerUserId() {
		return this.ownerUserId;
	}

	/**
	 * get 用户ID.
	 * @param ownerUserId 用户ID
	 */
	public void setOwnerUserId(Long ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	/**  
	 * set 接受权限用户ID.
	 * @return 接受权限用户ID
	 */

	@Column(name = "XF_USER_ID", precision = 16, scale = 0)
	public Long getXfUserId() {
		return this.xfUserId;
	}

	/**
	 * get 接受权限用户ID.
	 * @param xfUserId 接受权限用户ID
	 */
	public void setXfUserId(Long xfUserId) {
		this.xfUserId = xfUserId;
	}
	
	@Column(name = "XFUSERNAME", length = 60)
	public String getXfusername() {
		return xfusername;
	}

	public void setXfusername(String xfusername) {
		this.xfusername = xfusername;
	}

	/**  
	 * set 委派开始时间.
	 * @return 委派开始时间
	 */

	@Column(name = "STARTDATE", length = 30)
	public String getStartdate() {
		return this.startdate;
	}

	/**
	 * get 委派开始时间.
	 * @param startdate 委派开始时间
	 */
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	/**  
	 * set 委派结束时间.
	 * @return 委派结束时间
	 */

	@Column(name = "ENDDATE", length = 30)
	public String getEnddate() {
		return this.enddate;
	}

	/**
	 * get 委派结束时间.
	 * @param enddate 委派结束时间
	 */
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

}
