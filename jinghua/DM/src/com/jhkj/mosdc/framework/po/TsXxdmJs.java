package com.jhkj.mosdc.framework.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TsXxdmJs entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TS_XXDM_JS")
public class TsXxdmJs implements java.io.Serializable {

	// Fields

	private Long xxdm;
	private Long js;

	// Constructors

	/** default constructor */
	public TsXxdmJs() {
	}

	/** full constructor */
	public TsXxdmJs(Long xxdm, Long js) {
		this.xxdm = xxdm;
		this.js = js;
	}

	// Property accessors
	@Id
	@Column(name = "XXDM", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getXxdm() {
		return this.xxdm;
	}

	public void setXxdm(Long xxdm) {
		this.xxdm = xxdm;
	}

	@Column(name = "JS", nullable = false, precision = 22, scale = 0)
	public Long getJs() {
		return this.js;
	}

	public void setJs(Long js) {
		this.js = js;
	}

}