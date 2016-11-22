package com.jhkj.mosdc.xxzy.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbXxzyBjssgxb entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_XXZY_BJSSGXB")
public class TbXxzyBjssgxb implements java.io.Serializable {

	// Fields

	private Long id;
	private Long bjId;
	private Long fjdId;
	private Long jdlx;

	// Constructors

	/** default constructor */
	public TbXxzyBjssgxb() {
	}

	/** minimal constructor */
	public TbXxzyBjssgxb(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TbXxzyBjssgxb(Long id, Long bjId, Long fjdId, Long jdlx) {
		this.id = id;
		this.bjId = bjId;
		this.fjdId = fjdId;
		this.jdlx = jdlx;
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

	@Column(name = "BJ_ID", precision = 16, scale = 0)
	public Long getBjId() {
		return this.bjId;
	}

	public void setBjId(Long bjId) {
		this.bjId = bjId;
	}

	@Column(name = "FJD_ID", precision = 16, scale = 0)
	public Long getFjdId() {
		return this.fjdId;
	}

	public void setFjdId(Long fjdId) {
		this.fjdId = fjdId;
	}

	@Column(name = "JDLX", precision = 1, scale = 0)
	public Long getJdlx() {
		return this.jdlx;
	}

	public void setJdlx(Long jdlx) {
		this.jdlx = jdlx;
	}

}