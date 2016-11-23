package com.jhkj.mosdc.permission.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TsJsToCdzy entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TS_JS_TO_CDZY")
public class TsJsToCdzy implements java.io.Serializable {

	// Fields

	private Long id;
	private Long userId;
	private Long cdId;

	// Constructors

	/** default constructor */
	public TsJsToCdzy() {
	}

	/** minimal constructor */
	public TsJsToCdzy(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TsJsToCdzy(Long id, Long userId, Long cdId) {
		this.id = id;
		this.userId = userId;
		this.cdId = cdId;
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

	@Column(name = "USER_ID", precision = 16, scale = 0)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "CD_ID", precision = 16, scale = 0)
	public Long getCdId() {
		return this.cdId;
	}

	public void setCdId(Long cdId) {
		this.cdId = cdId;
	}

}