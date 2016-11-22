package com.jhkj.mosdc.xxzy.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbXxzyYx entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_XXZY_YX")
public class TbXxzyYx implements java.io.Serializable {

	// Fields

	private Long id;
	private Long xqId;
	private String dm;
	private String mc;
	private String jc;
	private String ywmc;
	private String yxjs;
	private Long fzrId;
	private String lxfs;
	private String xxdm;
	private String cjsj;
	private String cjr;
	private String xgsj;
	private String xgr;
	private Long sfky;

	// Constructors

	/** default constructor */
	public TbXxzyYx() {
	}

	/** minimal constructor */
	public TbXxzyYx(Long id, String dm, String mc) {
		this.id = id;
		this.dm = dm;
		this.mc = mc;
	}

	/** full constructor */
	public TbXxzyYx(Long id, Long xqId, String dm, String mc, String jc,
			String ywmc, String yxjs, Long fzrId, String lxfs, String xxdm,
			String cjsj, String cjr, String xgsj, String xgr,Long sfky) {
		this.id = id;
		this.xqId = xqId;
		this.dm = dm;
		this.mc = mc;
		this.jc = jc;
		this.ywmc = ywmc;
		this.yxjs = yxjs;
		this.fzrId = fzrId;
		this.lxfs = lxfs;
		this.xxdm = xxdm;
		this.cjsj = cjsj;
		this.cjr = cjr;
		this.xgsj = xgsj;
		this.xgr = xgr;
		this.sfky=sfky;
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

	@Column(name = "DM", nullable = false, length = 60)
	public String getDm() {
		return this.dm;
	}

	public void setDm(String dm) {
		this.dm = dm;
	}

	@Column(name = "MC", nullable = false, length = 60)
	public String getMc() {
		return this.mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	@Column(name = "JC", length = 20)
	public String getJc() {
		return this.jc;
	}

	public void setJc(String jc) {
		this.jc = jc;
	}

	@Column(name = "YWMC", length = 60)
	public String getYwmc() {
		return this.ywmc;
	}

	public void setYwmc(String ywmc) {
		this.ywmc = ywmc;
	}

	@Column(name = "YXJS", length = 200)
	public String getYxjs() {
		return this.yxjs;
	}

	public void setYxjs(String yxjs) {
		this.yxjs = yxjs;
	}

	@Column(name = "FZR_ID", precision = 16, scale = 0)
	public Long getFzrId() {
		return this.fzrId;
	}

	public void setFzrId(Long fzrId) {
		this.fzrId = fzrId;
	}

	@Column(name = "LXFS", length = 30)
	public String getLxfs() {
		return this.lxfs;
	}

	public void setLxfs(String lxfs) {
		this.lxfs = lxfs;
	}

	@Column(name = "XXDM", length = 60)
	public String getXxdm() {
		return this.xxdm;
	}

	public void setXxdm(String xxdm) {
		this.xxdm = xxdm;
	}

	@Column(name = "CJSJ", length = 60)
	public String getCjsj() {
		return this.cjsj;
	}

	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
	}

	@Column(name = "CJR", length = 60)
	public String getCjr() {
		return this.cjr;
	}

	public void setCjr(String cjr) {
		this.cjr = cjr;
	}

	@Column(name = "XGSJ", length = 60)
	public String getXgsj() {
		return this.xgsj;
	}

	public void setXgsj(String xgsj) {
		this.xgsj = xgsj;
	}

	@Column(name = "XGR", length = 60)
	public String getXgr() {
		return this.xgr;
	}

	public void setXgr(String xgr) {
		this.xgr = xgr;
	}
	@Column(name = "SFKY", length = 1)
	public Long getSfky() {
		return sfky;
	}

	public void setSfky(Long sfky) {
		this.sfky = sfky;
	}
}