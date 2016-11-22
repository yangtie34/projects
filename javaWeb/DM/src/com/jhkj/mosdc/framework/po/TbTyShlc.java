package com.jhkj.mosdc.framework.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbXgShlc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_TY_SHLC")
public class TbTyShlc implements java.io.Serializable {

	// Fields

	private Long id;
	private String mc;
	private String lsmk;
	private Long sfky;
	private String cjr;
	private String cjsj;
	private String xgr;
	private String xgsj;
	private String mkmc;

	// Constructors

	/** default constructor */
	public TbTyShlc() {
	}

	/** minimal constructor */
	public TbTyShlc(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TbTyShlc(Long id, String mc, String lsmk, Long sfky, String cjr,
			String cjsj, String xgr, String xgsj) {
		this.id = id;
		this.mc = mc;
		this.lsmk = lsmk;
		this.sfky = sfky;
		this.cjr = cjr;
		this.cjsj = cjsj;
		this.xgr = xgr;
		this.xgsj = xgsj;
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

	@Column(name = "MC", length = 20)
	public String getMc() {
		return this.mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	@Column(name = "LSMK", length = 20)
	public String getLsmk() {
		return this.lsmk;
	}

	public void setLsmk(String lsmk) {
		this.lsmk = lsmk;
	}

	@Column(name = "SFKY", precision = 16, scale = 0)
	public Long getSfky() {
		return this.sfky;
	}

	public void setSfky(Long sfky) {
		this.sfky = sfky;
	}

	@Column(name = "CJR", length = 60)
	public String getCjr() {
		return this.cjr;
	}

	public void setCjr(String cjr) {
		this.cjr = cjr;
	}

	@Column(name = "CJSJ", length = 60)
	public String getCjsj() {
		return this.cjsj;
	}

	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
	}

	@Column(name = "XGR", length = 60)
	public String getXgr() {
		return this.xgr;
	}

	public void setXgr(String xgr) {
		this.xgr = xgr;
	}

	@Column(name = "XGSJ", length = 60)
	public String getXgsj() {
		return this.xgsj;
	}

	public void setXgsj(String xgsj) {
		this.xgsj = xgsj;
	}
	
	@Column(name = "MKMC", length = 60)
	public String getMkmc() {
		return mkmc;
	}

	public void setMkmc(String mkmc) {
		this.mkmc = mkmc;
	}
}