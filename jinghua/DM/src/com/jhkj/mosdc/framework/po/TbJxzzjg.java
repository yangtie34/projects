package com.jhkj.mosdc.framework.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbJxzzjg entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_JXZZJG")
public class TbJxzzjg implements java.io.Serializable {

	// Fields

	private Long id;
	private String dm;
	private String mc;
	private Long fjdId;
	private Long cc;
	private String cclx;
	private String sfyzjd;
	private String sfky;
	private String cjsj;
	private String cjr;
	private String xgsj;
	private String xgr;
	private String qxm;
	private Integer pxh;
	private String jdbb;
	private Boolean jdbs;

	// Constructors

	/** default constructor */
	public TbJxzzjg() {
	}

	/** minimal constructor */
	public TbJxzzjg(Long id, String mc) {
		this.id = id;
		this.mc = mc;
	}

	

	public TbJxzzjg(Long id, String dm, String mc, Long fjdId, Long cc,
			String cclx, String sfyzjd, String sfky, String cjsj, String cjr,
			String xgsj, String xgr, String qxm, Integer pxh, String jdbb,
			Boolean jdbs) {
		super();
		this.id = id;
		this.dm = dm;
		this.mc = mc;
		this.fjdId = fjdId;
		this.cc = cc;
		this.cclx = cclx;
		this.sfyzjd = sfyzjd;
		this.sfky = sfky;
		this.cjsj = cjsj;
		this.cjr = cjr;
		this.xgsj = xgsj;
		this.xgr = xgr;
		this.qxm = qxm;
		this.pxh = pxh;
		this.jdbb = jdbb;
		this.jdbs = jdbs;
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

	@Column(name = "DM", length = 60)
	public String getDm() {
		return this.dm;
	}

	public void setDm(String dm) {
		this.dm = dm;
	}

	@Column(name = "MC", nullable = false, length = 100)
	public String getMc() {
		return this.mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	@Column(name = "FJD_ID", precision = 16, scale = 0)
	public Long getFjdId() {
		return this.fjdId;
	}

	public void setFjdId(Long fjdId) {
		this.fjdId = fjdId;
	}

	@Column(name = "CC", precision = 16, scale = 0)
	public Long getCc() {
		return this.cc;
	}

	public void setCc(Long cc) {
		this.cc = cc;
	}

	@Column(name = "CCLX", length = 20)
	public String getCclx() {
		return this.cclx;
	}

	public void setCclx(String cclx) {
		this.cclx = cclx;
	}

	@Column(name = "SFYZJD", length = 1)
	public String getSfyzjd() {
		return this.sfyzjd;
	}

	public void setSfyzjd(String sfyzjd) {
		this.sfyzjd = sfyzjd;
	}

	@Column(name = "SFKY", length = 1)
	public String getSfky() {
		return this.sfky;
	}

	public void setSfky(String sfky) {
		this.sfky = sfky;
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

	@Column(name = "QXM", length = 60)
	public String getQxm() {
		return this.qxm;
	}

	public void setQxm(String qxm) {
		this.qxm = qxm;
	}

	@Column(name = "PXH", length = 20)
	public Integer getPxh() {
		return this.pxh;
	}

	public void setPxh(Integer pxh) {
		this.pxh = pxh;
	}

	@Column(name = "JDBB", length = 20)
	public String getJdbb() {
		return this.jdbb;
	}

	public void setJdbb(String jdbb) {
		this.jdbb = jdbb;
	}

	@Column(name = "JDBS", precision = 1, scale = 0)
	public Boolean getJdbs() {
		return this.jdbs;
	}

	public void setJdbs(Boolean jdbs) {
		this.jdbs = jdbs;
	}

}