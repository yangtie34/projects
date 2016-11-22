package com.jhkj.mosdc.xxzy.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbXxzyBmjzg entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_XXZY_BMJZG")
public class TbXxzyBmjzg implements java.io.Serializable {

	// Fields

	private Long id;
	private Long bmId;
	private Long jsId;
	private Long bmlb;
	

	// Constructors
	
	/** default constructor */
	public TbXxzyBmjzg() {
	}

	/** minimal constructor */
	public TbXxzyBmjzg(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TbXxzyBmjzg(Long id, Long bmId, Long jsId, Long bmlb) {
		this.id = id;
		this.bmId = bmId;
		this.jsId = jsId;
		this.bmlb = bmlb;
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

	@Column(name = "BM_ID", precision = 16, scale = 0)
	public Long getBmId() {
		return this.bmId;
	}

	public void setBmId(Long bmId) {
		this.bmId = bmId;
	}

	@Column(name = "JS_ID", precision = 16, scale = 0)
	public Long getJsId() {
		return this.jsId;
	}

	public void setJsId(Long jsId) {
		this.jsId = jsId;
	}

	@Column(name = "BMLB", precision = 16, scale = 0)
	public Long getBmlb() {
		return this.bmlb;
	}

	public void setBmlb(Long bmlb) {
		this.bmlb = bmlb;
	}

}