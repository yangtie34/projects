package com.jhkj.mosdc.xxzy.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbXxzyJxl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_XXZY_JXL")
public class TbXxzyJxl implements java.io.Serializable {

	// Fields

	private Long id;
	private Long xqId;
	private String lh;
	private String mc;
	private Long lcs;
	private Long ssdwId;
	private Long lxId;
	private Long sfky;
	private String cjr;
	private String cjsj;
	private String xgr;
	private String xgsj;
	private String bz;

	// Constructors

	/** default constructor */
	public TbXxzyJxl() {
	}

	/** minimal constructor */
	public TbXxzyJxl(Long id, String lh) {
		this.id = id;
		this.lh = lh;
	}

	/** full constructor */
	public TbXxzyJxl(Long id, Long xqId, String lh, String mc, Long lcs,
			Long ssdwId, Long lxId, Long sfky, String cjr, String cjsj,
			String xgr, String xgsj, String bz) {
		this.id = id;
		this.xqId = xqId;
		this.lh = lh;
		this.mc = mc;
		this.lcs = lcs;
		this.ssdwId = ssdwId;
		this.lxId = lxId;
		this.sfky = sfky;
		this.cjr = cjr;
		this.cjsj = cjsj;
		this.xgr = xgr;
		this.xgsj = xgsj;
		this.bz = bz;
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

	@Column(name = "XQ_ID", precision = 16, scale = 0)
	public Long getXqId() {
		return this.xqId;
	}

	public void setXqId(Long xqId) {
		this.xqId = xqId;
	}

	@Column(name = "LH",  length = 20)
	public String getLh() {
		return this.lh;
	}

	public void setLh(String lh) {
		this.lh = lh;
	}

	@Column(name = "MC", length = 20)
	public String getMc() {
		return this.mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	@Column(name = "LCS", precision = 16, scale = 0)
	public Long getLcs() {
		return this.lcs;
	}

	public void setLcs(Long lcs) {
		this.lcs = lcs;
	}

	@Column(name = "SSDW_ID", precision = 16, scale = 0)
	public Long getSsdwId() {
		return this.ssdwId;
	}

	public void setSsdwId(Long ssdwId) {
		this.ssdwId = ssdwId;
	}

	@Column(name = "LX_ID", precision = 16, scale = 0)
	public Long getLxId() {
		return this.lxId;
	}

	public void setLxId(Long lxId) {
		this.lxId = lxId;
	}

	@Column(name = "SFKY", precision = 1, scale = 0)
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

	@Column(name = "BZ", length = 200)
	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

}