package com.jhkj.mosdc.framework.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbOaGwfjb entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_XSJSPHOTO")
public class TbXsjsPhoto implements java.io.Serializable {

	// Fields

	private Long id;
	private String zpymc;
	private Long zpdx;
	private String zpbcmc;
	private String zpbclj;
	private String zplx;

	// Constructors

	/** default constructor */
	public TbXsjsPhoto() {
	}

	/** minimal constructor */
	public TbXsjsPhoto(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TbXsjsPhoto(Long id,String zpymc, Long zpdx, String zpbcmc,
			String zpbclj, String zplx) {
		this.id = id;
		this.zpymc = zpymc;
		this.zpdx = zpdx;
		this.zpbcmc = zpbcmc;
		this.zpbclj = zpbclj;
		this.zplx = zplx;
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

	@Column(name = "ZPYMC", length = 100)
	public String getZpymc() {
		return this.zpymc;
	}

	public void setZpymc(String zpymc) {
		this.zpymc = zpymc;
	}

	@Column(name = "ZPDX", precision = 16, scale = 0)
	public Long getZpdx() {
		return this.zpdx;
	}

	public void setZpdx(Long zpdx) {
		this.zpdx = zpdx;
	}

	@Column(name = "ZPBCMC", length = 100)
	public String getZpbcmc() {
		return this.zpbcmc;
	}

	public void setZpbcmc(String zpbcmc) {
		this.zpbcmc = zpbcmc;
	}

	@Column(name = "ZPBCLJ", length = 100)
	public String getZpbclj() {
		return this.zpbclj;
	}

	public void setZpbclj(String zpbclj) {
		this.zpbclj = zpbclj;
	}

	@Column(name = "ZPLX", length = 10)
	public String getZplx() {
		return this.zplx;
	}

	public void setZplx(String zplx) {
		this.zplx = zplx;
	}
}