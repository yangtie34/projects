package com.jhkj.mosdc.xggl.xjgl.po;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbXjdaJnjdBjsbpc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_XJGL_JNJD_BJSBPC")
public class TbXjglJnjdBjsbpc implements java.io.Serializable {
	private static final long serialVersionUID = -7463773665746045854L;

	private Long id;
	private String pcmc;
	private String cjsj;

	// Constructors

	/** default constructor */
	public TbXjglJnjdBjsbpc() {
	}

	/** minimal constructor */
	public TbXjglJnjdBjsbpc(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TbXjglJnjdBjsbpc(Long id, String pcmc, String cjsj) {
		this.id = id;
		this.pcmc = pcmc;
		this.cjsj = cjsj;
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

	@Column(name = "PCMC", length = 100)
	public String getPcmc() {
		return this.pcmc;
	}

	public void setPcmc(String pcmc) {
		this.pcmc = pcmc;
	}

	@Column(name = "CJSJ", length = 60)
	public String getCjsj() {
		return this.cjsj;
	}

	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
	}

}