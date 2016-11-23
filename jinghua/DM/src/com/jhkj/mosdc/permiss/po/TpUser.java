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
 * TableName: TP_USER<br>
 * @author Hibernate Tools 3.4.0.CR1
 * @version 1.0
 * @since 2014-4-26 18:07:56
 */
@Entity
@Table(name = "TP_USER")
public class TpUser implements Serializable {
	/**
	 * ID
	 */
	private Long id;
	/**
	 * 用户ID
	 */
	private Long ryId;
	/**
	 * 姓名
	 */
	private String username;
	/**
	 * 登录名
	 */
	private String loginname;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 部门ID
	 */
	private Long bmId;
	/**
	 * 部门名称
	 */
	private String bmmc;
	/**
	 * 人员类别ID
	 */
	private Long rylbId;
	/**
	 * 学校代码
	 */
	private String xxdm;
	
	/**
	 * 是否可用
	 */
	private Boolean sfky;
	
	private String ghxh;
	
	private Integer yhly;

	public TpUser() {
	}

	public TpUser(Long id) {
		this.id = id;
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
	 * set 人员ID.
	 * @return 人员ID.
	 */

	@Column(name = "RY_ID", precision = 16, scale = 0)
	public Long getRyId() {
		return this.ryId;
	}

	/**
	 * get 人员ID.
	 * @param userId 人员ID.
	 */
	public void setRyId(Long ryId) {
		this.ryId = ryId;
	}

	/**  
	 * set 姓名.
	 * @return 姓名
	 */

	@Column(name = "USERNAME", length = 100)
	public String getUsername() {
		return this.username;
	}

	/**
	 * get 姓名.
	 * @param userName 姓名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**  
	 * set 登录名.
	 * @return 登录名
	 */

	@Column(name = "LOGINNAME", length = 100)
	public String getLoginname() {
		return this.loginname;
	}

	/**
	 * get 登录名.
	 * @param loginName 登录名
	 */
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	/**  
	 * set 密码.
	 * @return 密码
	 */

	@Column(name = "PASSWORD", length = 100)
	public String getPassword() {
		return this.password;
	}

	/**
	 * get 密码.
	 * @param password 密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**  
	 * set 部门ID.
	 * @return 部门ID
	 */

	@Column(name = "BM_ID", precision = 16, scale = 0)
	public Long getBmId() {
		return this.bmId;
	}

	/**
	 * get 部门ID.
	 * @param bmId 部门ID
	 */
	public void setBmId(Long bmId) {
		this.bmId = bmId;
	}
	
	/**  
	 * set 部门名称.
	 * @return 部门名称
	 */

	@Column(name = "BMMC", length = 100)
	public String getBmmc() {
		return this.bmmc;
	}

	/**
	 * get 部门名称.
	 * @param bmId 部门名称
	 */
	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	/**  
	 * set 人员类别ID.
	 * @return 人员类别ID
	 */

	@Column(name = "RYLB_ID", precision = 16, scale = 0)
	public Long getRylbId() {
		return this.rylbId;
	}

	/**
	 * get 人员类别ID.
	 * @param rylbId 人员类别ID
	 */
	public void setRylbId(Long rylbId) {
		this.rylbId = rylbId;
	}

	/**  
	 * set 学校代码.
	 * @return 学校代码
	 */

	@Column(name = "XXDM", length = 20)
	public String getXxdm() {
		return this.xxdm;
	}

	/**
	 * get 学校代码.
	 * @param xxdm 学校代码
	 */
	public void setXxdm(String xxdm) {
		this.xxdm = xxdm;
	}

	/**  
	 * set 学校代码.
	 * @return 学校代码
	 */

	@Column(name = "SFKY", precision = 1, scale = 0)
	public Boolean getSfky() {
		return this.sfky;
	}

	/**
	 * get 学校代码.
	 * @param xxdm 学校代码
	 */
	public void setSfky(Boolean sfky) {
		this.sfky = sfky;
	}

	@Column(name = "GHXH",length = 50)
	public String getGhxh() {
		return ghxh;
	}

	public void setGhxh(String ghxh) {
		this.ghxh = ghxh;
	}
	
	@Column(name = "YHLY", precision = 5, scale = 0)
	public Integer getYhly() {
		return yhly;
	}

	public void setYhly(Integer yhly) {
		this.yhly = yhly;
	}
	

}
