package com.jhkj.mosdc.xxzy.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbXxzyKcdysj entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_XXZY_KCDYSJ")
public class TbXxzyKcdysj implements java.io.Serializable {

	// Fields

	private Long id;
	private Long jsId;
	private Long kcId;
	private Boolean sfky;

	// Constructors

	/** default constructor */
	public TbXxzyKcdysj() {
	}

	/** minimal constructor */
	public TbXxzyKcdysj(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TbXxzyKcdysj(Long id, Long jsId, Long kcId, Boolean sfky) {
		this.id = id;
		this.jsId = jsId;
		this.kcId = kcId;
		this.sfky = sfky;
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

	@Column(name = "JS_ID", precision = 16, scale = 0)
	public Long getJsId() {
		return this.jsId;
	}

	public void setJsId(Long jsId) {
		this.jsId = jsId;
	}

	@Column(name = "KC_ID", precision = 16, scale = 0)
	public Long getKcId() {
		return this.kcId;
	}

	public void setKcId(Long kcId) {
		this.kcId = kcId;
	}

	@Column(name = "SFKY", precision = 1, scale = 0)
	public Boolean getSfky() {
		return this.sfky;
	}

	public void setSfky(Boolean sfky) {
		this.sfky = sfky;
	}

}