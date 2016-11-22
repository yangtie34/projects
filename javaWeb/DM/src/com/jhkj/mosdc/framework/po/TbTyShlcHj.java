package com.jhkj.mosdc.framework.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbXgShlchj entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_TY_SHLC_HJ")
public class TbTyShlcHj implements java.io.Serializable {

	// Fields

	private Long id;
	private String mc;
	private Long sslcId;
	private Long jsId;
	private Long hjsx;
	private Long sfky;
	private String cjr;
	private String cjsj;
	private String xgr;
	private String xgsj;

	// Constructors

	/** default constructor */
	public TbTyShlcHj() {
	}

	/** minimal constructor */
	public TbTyShlcHj(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TbTyShlcHj(Long id, String mc, Long sslcId, Long jsId, Long hjsx,
			Long sfky, String cjr, String cjsj, String xgr, String xgsj) {
		this.id = id;
		this.mc = mc;
		this.sslcId = sslcId;
		this.jsId = jsId;
		this.hjsx = hjsx;
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

	@Column(name = "SSLC_ID", precision = 16, scale = 0)
	public Long getSslcId() {
		return this.sslcId;
	}

	public void setSslcId(Long sslcId) {
		this.sslcId = sslcId;
	}

	@Column(name = "JS_ID", precision = 16, scale = 0)
	public Long getJsId() {
		return this.jsId;
	}

	public void setJsId(Long jsId) {
		this.jsId = jsId;
	}

	@Column(name = "HJSX", precision = 16, scale = 0)
	public Long getHjsx() {
		return this.hjsx;
	}

	public void setHjsx(Long hjsx) {
		this.hjsx = hjsx;
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

}