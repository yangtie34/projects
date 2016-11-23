package com.jhkj.mosdc.permission.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TS_USERDATAPERMISS")
public class TsUserDataPermiss implements java.io.Serializable {

	private static final long serialVersionUID = -3416446433086805369L;

	private Long id;

	/**
	 * 用户ID
	 */

	private Long userId;
	
	/**
	 * 组织机构ID
	 */
	private Long zzjgId;
	

	/**
	 * 职务ID
	 */

	private Long zwId;

	/**
	 * 组织机构类别（1教学组织机构2行政组织机构）
	 */

	private Long zzjglb;
	

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "USER_ID", nullable = false,precision = 16, scale = 0)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "ZZJG_ID", nullable = false,precision = 16, scale = 0)
	public Long getZzjgId() {
		return zzjgId;
	}

	public void setZzjgId(Long zzjgId) {
		this.zzjgId = zzjgId;
	}

	@Column(name = "ZW_ID", precision = 16, scale = 0)
	public Long getZwId() {
		return zwId;
	}

	public void setZwId(Long zwId) {
		this.zwId = zwId;
	}

	@Column(name = "ZZJGLB", precision = 1, scale = 0)
	public Long getZzjglb() {
		return zzjglb;
	}

	public void setZzjglb(Long zzjglb) {
		this.zzjglb = zzjglb;
	}

	
}