package com.jhkj.mosdc.permission.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TsQxUserRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TS_QX_USER_ROLE")
public class TsQxUserRole implements java.io.Serializable {

	// Fields

	private Long id;
	private Long yhId;
	private Long userId;

	// Constructors

	/** default constructor */
	public TsQxUserRole() {
	}

	/** minimal constructor */
	public TsQxUserRole(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TsQxUserRole(Long id, Long yhId, Long userId) {
		this.id = id;
		this.yhId = yhId;
		this.userId = userId;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "YH_ID", precision = 16, scale = 0)
	public Long getYhId() {
		return this.yhId;
	}

	public void setYhId(Long yhId) {
		this.yhId = yhId;
	}

	@Column(name = "USER_ID", precision = 16, scale = 0)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}