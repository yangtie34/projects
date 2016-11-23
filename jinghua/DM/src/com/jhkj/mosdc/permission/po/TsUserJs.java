package com.jhkj.mosdc.permission.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户角色id
 */
@Entity
@Table(name = "TS_USER_JS")
public class TsUserJs implements java.io.Serializable {

//	private static final long serialVersionUID = -4228837838003077482L;

	/**
	 * 标识
	 */

	private Long id;

	/**
	 * 用户ID
	 */

	private Long userId;

	/**
	 * 角色ID
	 */

	private Long jsId;

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "USER_ID", precision = 16, scale = 0)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "JS_ID", precision = 16, scale = 0)
	public Long getJsId() {
		return jsId;
	}

	public void setJsId(Long jsId) {
		this.jsId = jsId;
	}

}