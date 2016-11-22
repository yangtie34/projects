package com.jhkj.mosdc.framework.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TcXzqh entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TC_XZ")
public class TcXz implements java.io.Serializable {

	// Fields

	private Long id;
	private String dm;
	private String mc;
	private String zxz;
	private String pxh;
	private String xzdxmc;
	private String cjr;
	private String cjsj;
	private String xgr;
	private String xgsj;
	private String sfky;

	// Constructors

	/** default constructor */
	public TcXz() {
	}

	/** minimal constructor */
	public TcXz(Long id, String dm, String mc) {
		this.id = id;
		this.dm = dm;
		this.mc = mc;
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

	@Column(name = "DM", nullable = false, length = 20)
	public String getDm() {
		return this.dm;
	}

	public void setDm(String dm) {
		this.dm = dm;
	}

	@Column(name = "MC", nullable = false, length = 20)
	public String getMc() {
		return this.mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}
	@Column(name = "ZXZ", nullable = false, length = 20)
	public String getZxz() {
		return zxz;
	}

	public void setZxz(String zxz) {
		this.zxz = zxz;
	}
	@Column(name = "PXH", nullable = false, precision = 16, scale = 0)
	public String getPxh() {
		return pxh;
	}

	public void setPxh(String pxh) {
		this.pxh = pxh;
	}
	@Column(name = "XZDXMC", nullable = false, length = 20)
	public String getXzdxmc() {
		return xzdxmc;
	}

	public void setXzdxmc(String xzdxmc) {
		this.xzdxmc = xzdxmc;
	}
	@Column(name = "CJR", nullable = false, length = 60)
	public String getCjr() {
		return cjr;
	}

	public void setCjr(String cjr) {
		this.cjr = cjr;
	}
	@Column(name = "CJSJ", nullable = false, length = 60)
	public String getCjsj() {
		return cjsj;
	}

	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
	}
	@Column(name = "XGR", nullable = false, length = 60)
	public String getXgr() {
		return xgr;
	}

	public void setXgr(String xgr) {
		this.xgr = xgr;
	}
	@Column(name = "XGSJ", nullable = false, length = 60)
	public String getXgsj() {
		return xgsj;
	}

	public void setXgsj(String xgsj) {
		this.xgsj = xgsj;
	}
	@Column(name = "SFKY", length = 1)
	public String getSfky() {
		return sfky;
	}

	public void setSfky(String sfky) {
		this.sfky = sfky;
	}

	
}