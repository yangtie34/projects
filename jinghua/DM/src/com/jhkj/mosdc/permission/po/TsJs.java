package com.jhkj.mosdc.permission.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色：有同一组功能模块操作能力的一类用户。
 */
@Entity
@Table(name = "TS_JS")
public class TsJs implements java.io.Serializable {

	//private static final long serialVersionUID = -5627486624038282727L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 角色名称
	 */

	private Long jslxId;

	/**
	 * 角色描述
	 */

	private String ms;
	/*
	 * 用户ID
	 */
	private Long userId;
	/**
	 */
//	private boolean groupFlag;
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "JSLX_ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getJslxId() {
		return jslxId;
	}

	public void setJslxId(Long jslxId) {
		this.jslxId =jslxId;
	}

	@Column(name = "JS_MS", length = 256)
	public String getMs() {
		return ms;
	}

	public void setMs(String ms) {
		this.ms = ms;
	}
	
	@Column(name = "USER_ID",  precision = 16, scale = 0)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
/*	@Column(name = "GROUP_FLAG",  precision = 1, scale = 0)
	public boolean getGroupFlag() {
		return groupFlag;
	}

	public void setGroupFlag(boolean groupFlag) {
		this.groupFlag = groupFlag;
	}*/
	
	
}