package com.jhkj.mosdc.xggl.xjgl.po;

// Generated 2013-11-5 13:49:26 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbTxnrfl generated by hbm2java
 */
@Entity
@Table(name = "TB_TXNRFL")
public class TbTxnrfl implements java.io.Serializable {

	private Long id;
	private String dm;
	private String mc;
	private Long fjdId;
	private Long cc;
	private String cclx;
	private String sfyzjd;
	private String sfky;
	private String xxdm;
	private String cjsj;
	private String cjr;
	private String xgsj;
	private String xgr;
	private String qxm;
	private Long xh;

	public TbTxnrfl() {
	}

	public TbTxnrfl(Long id, String mc) {
		this.id = id;
		this.mc = mc;
	}

	public TbTxnrfl(Long id, String dm, String mc, Long fjdId, Long cc,
			String cclx, String sfyzjd, String sfky, String xxdm, String cjsj,
			String cjr, String xgsj, String xgr, String qxm, Long xh) {
		this.id = id;
		this.dm = dm;
		this.mc = mc;
		this.fjdId = fjdId;
		this.cc = cc;
		this.cclx = cclx;
		this.sfyzjd = sfyzjd;
		this.sfky = sfky;
		this.xxdm = xxdm;
		this.cjsj = cjsj;
		this.cjr = cjr;
		this.xgsj = xgsj;
		this.xgr = xgr;
		this.qxm = qxm;
		this.xh = xh;
	}

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

	@Column(name = "XXDM", length = 12)
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

	@Column(name = "QXM", length = 200)
	public String getQxm() {
		return this.qxm;
	}

	public void setQxm(String qxm) {
		this.qxm = qxm;
	}

	@Column(name = "XH", precision = 16, scale = 0)
	public Long getXh() {
		return this.xh;
	}

	public void setXh(Long xh) {
		this.xh = xh;
	}

}
