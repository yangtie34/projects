package com.jhkj.mosdc.permission.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TS_USER_CDZY_GROUP")
public class TsUserCdzyGroup {
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

	private Long userId;
	
	/**
	 * 下放菜单角色ID
	 * @return
	 */
	private Long xfRoleId;
	/**
	 * 下方菜单资源ID
	 * @return
	 */
	private Long xfUserId;

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

	@Column(name = "USER_ID", precision = 16, scale = 0)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Column(name = "XF_ROLE_ID", precision = 16, scale = 0)
	public Long getXfRoleId() {
		return xfRoleId;
	}

	public void setXfRoleId(Long xfRoleId) {
		this.xfRoleId = xfRoleId;
	}
	@Column(name = "XF_USER_ID", precision = 16, scale = 0)
	public Long getXfUserId() {
		return xfUserId;
	}

	public void setXfUserId(Long xfUserId) {
		this.xfUserId = xfUserId;
	}
}
