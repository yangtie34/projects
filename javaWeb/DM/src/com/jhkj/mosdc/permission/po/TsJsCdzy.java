package com.jhkj.mosdc.permission.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色 菜单资源表
 * 
 * @author evan
 * 
 */
@Entity
@Table(name = "TS_JS_CDZY")
public class TsJsCdzy implements java.io.Serializable {

	private static final long serialVersionUID = -2284656840129336950L;

	/**
	 * id
	 */

	private Long id;

	/**
	 * 菜单资源id
	 */

	private Long cdzyId;

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

	@Column(name = "CDZY_ID", precision = 16, scale = 0)
	public Long getCdzyId() {
		return cdzyId;
	}

	public void setCdzyId(Long cdzyId) {
		this.cdzyId = cdzyId;
	}

	@Column(name = "JS_ID", precision = 16, scale = 0)
	public Long getJsId() {
		return jsId;
	}

	public void setJsId(Long jsId) {
		this.jsId = jsId;
	}

}