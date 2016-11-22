package com.jhkj.mosdc.pano.po;

// Generated 2013-10-30 15:26:24 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbBaseBjxxb generated by hbm2java
 */
@Entity
@Table(name = "TB_BASE_BJXXB", schema = "MOSDC_PANO")
public class TbBaseBjxxb implements java.io.Serializable {

	private long id;
	private Long fjdId;
	private String bh;
	private String mc;
	private Long xslbId;
	private String rxsj;
	private String yjbysj;
	private Long xzId;
	private Long rs;
	private Long pcId;
	private Long bzrId;
	private Boolean sfky;
	private Long njId;
	private String qdmc;
	private Long ccId;
	private String sjtbsj;
	private String tbfs;

	public TbBaseBjxxb() {
	}

	public TbBaseBjxxb(long id) {
		this.id = id;
	}

	public TbBaseBjxxb(long id, Long fjdId, String bh, String mc, Long xslbId,
			String rxsj, String yjbysj, Long xzId, Long rs, Long pcId,
			Long bzrId, Boolean sfky, Long njId, String qdmc, Long ccId,
			String sjtbsj, String tbfs) {
		this.id = id;
		this.fjdId = fjdId;
		this.bh = bh;
		this.mc = mc;
		this.xslbId = xslbId;
		this.rxsj = rxsj;
		this.yjbysj = yjbysj;
		this.xzId = xzId;
		this.rs = rs;
		this.pcId = pcId;
		this.bzrId = bzrId;
		this.sfky = sfky;
		this.njId = njId;
		this.qdmc = qdmc;
		this.ccId = ccId;
		this.sjtbsj = sjtbsj;
		this.tbfs = tbfs;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "FJD_ID", precision = 16, scale = 0)
	public Long getFjdId() {
		return this.fjdId;
	}

	public void setFjdId(Long fjdId) {
		this.fjdId = fjdId;
	}

	@Column(name = "BH", length = 20)
	public String getBh() {
		return this.bh;
	}

	public void setBh(String bh) {
		this.bh = bh;
	}

	@Column(name = "MC", length = 60)
	public String getMc() {
		return this.mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	@Column(name = "XSLB_ID", precision = 16, scale = 0)
	public Long getXslbId() {
		return this.xslbId;
	}

	public void setXslbId(Long xslbId) {
		this.xslbId = xslbId;
	}

	@Column(name = "RXSJ", length = 60)
	public String getRxsj() {
		return this.rxsj;
	}

	public void setRxsj(String rxsj) {
		this.rxsj = rxsj;
	}

	@Column(name = "YJBYSJ", length = 60)
	public String getYjbysj() {
		return this.yjbysj;
	}

	public void setYjbysj(String yjbysj) {
		this.yjbysj = yjbysj;
	}

	@Column(name = "XZ_ID", precision = 16, scale = 0)
	public Long getXzId() {
		return this.xzId;
	}

	public void setXzId(Long xzId) {
		this.xzId = xzId;
	}

	@Column(name = "RS", precision = 16, scale = 0)
	public Long getRs() {
		return this.rs;
	}

	public void setRs(Long rs) {
		this.rs = rs;
	}

	@Column(name = "PC_ID", precision = 16, scale = 0)
	public Long getPcId() {
		return this.pcId;
	}

	public void setPcId(Long pcId) {
		this.pcId = pcId;
	}

	@Column(name = "BZR_ID", precision = 16, scale = 0)
	public Long getBzrId() {
		return this.bzrId;
	}

	public void setBzrId(Long bzrId) {
		this.bzrId = bzrId;
	}

	@Column(name = "SFKY", precision = 1, scale = 0)
	public Boolean getSfky() {
		return this.sfky;
	}

	public void setSfky(Boolean sfky) {
		this.sfky = sfky;
	}

	@Column(name = "NJ_ID", precision = 16, scale = 0)
	public Long getNjId() {
		return this.njId;
	}

	public void setNjId(Long njId) {
		this.njId = njId;
	}

	@Column(name = "QDMC", length = 60)
	public String getQdmc() {
		return this.qdmc;
	}

	public void setQdmc(String qdmc) {
		this.qdmc = qdmc;
	}

	@Column(name = "CC_ID", precision = 16, scale = 0)
	public Long getCcId() {
		return this.ccId;
	}

	public void setCcId(Long ccId) {
		this.ccId = ccId;
	}

	@Column(name = "SJTBSJ", length = 30)
	public String getSjtbsj() {
		return this.sjtbsj;
	}

	public void setSjtbsj(String sjtbsj) {
		this.sjtbsj = sjtbsj;
	}

	@Column(name = "TBFS", length = 30)
	public String getTbfs() {
		return this.tbfs;
	}

	public void setTbfs(String tbfs) {
		this.tbfs = tbfs;
	}

}
