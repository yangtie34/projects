package com.jhkj.mosdc.xggl.xjgl.po;

// Generated 2014-5-13 14:35:19 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 社团活动
 */
@Entity
@Table(name = "TB_XJDA_STHD")
public class TbXjdaSthd implements java.io.Serializable {

	private Long id;
	private Long xsId;
	private String kssj;
	private String jssj;
	private String stmc;
	private String hdmc;
	private String zzmc;
	private String hdnr;
	private String ryjl;
//	private String cjr;
//	private String cjsj;
//	private String xgr;
//	private String xgsj;

	public TbXjdaSthd() {
	}

	public TbXjdaSthd(Long id) {
		this.id = id;
	}

	public TbXjdaSthd(Long id, Long xsId, String kssj, String jssj,
			String stmc, String hdmc, String zzMc, String hdnr, String ryjl,
			String cjr, String cjsj, String xgr, String xgsj) {
		this.id = id;
		this.xsId = xsId;
		this.kssj = kssj;
		this.jssj = jssj;
		this.stmc = stmc;
		this.hdmc = hdmc;
		this.zzmc = zzmc;
		this.hdnr = hdnr;
		this.ryjl = ryjl;
//		this.cjr = cjr;
//		this.cjsj = cjsj;
//		this.xgr = xgr;
//		this.xgsj = xgsj;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "XS_ID", precision = 16, scale = 0)
	public Long getXsId() {
		return this.xsId;
	}

	public void setXsId(Long xsId) {
		this.xsId = xsId;
	}

	@Column(name = "KSSJ", length = 60)
	public String getKssj() {
		return this.kssj;
	}

	public void setKssj(String kssj) {
		this.kssj = kssj;
	}

	@Column(name = "JSSJ", length = 60)
	public String getJssj() {
		return this.jssj;
	}

	public void setJssj(String jssj) {
		this.jssj = jssj;
	}

	@Column(name = "STMC", length = 60)
	public String getStmc() {
		return this.stmc;
	}

	public void setStmc(String stmc) {
		this.stmc = stmc;
	}

	@Column(name = "HDMC", length = 60)
	public String getHdmc() {
		return this.hdmc;
	}

	public void setHdmc(String hdmc) {
		this.hdmc = hdmc;
	}

	@Column(name = "ZZMC", length = 60)
	public String getZzmc() {
		return this.zzmc;
	}

	public void setZzmc(String zzmc) {
		this.zzmc = zzmc;
	}

	@Column(name = "HDNR", length = 200)
	public String getHdnr() {
		return this.hdnr;
	}

	public void setHdnr(String hdnr) {
		this.hdnr = hdnr;
	}

	@Column(name = "RYJL", length = 100)
	public String getRyjl() {
		return this.ryjl;
	}

	public void setRyjl(String ryjl) {
		this.ryjl = ryjl;
	}

/*	@Column(name = "CJR", length = 60)
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
	}*/

}
