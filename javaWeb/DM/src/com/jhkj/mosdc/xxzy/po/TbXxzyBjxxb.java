package com.jhkj.mosdc.xxzy.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbXxzyBjxxb entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_XXZY_BJXXB")
public class TbXxzyBjxxb implements java.io.Serializable {

	// Fields

	private Long id;
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
	private Long sfky;
	private Long njId;
	private String cjsj;
	private String cjr;
	private String xgsj;
	private String xgr;
	private String qdmc;
	private Long cc;
	private Long xxfsId;
	private Long sfqy;

	// Constructors



	/** default constructor */
	public TbXxzyBjxxb() {
	}

	/** minimal constructor */
	public TbXxzyBjxxb(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TbXxzyBjxxb(Long id, Long fjdId, String bh, String mc, Long xslbId,
			String rxsj, String yjbysj, Long xzId, Long rs, Long pcId,
			Long bzrId, Long sfky, Long njId, String cjsj, String cjr,
			String xgsj, String xgr, String qdmc,Long cc,Long sfqy,Long xxfsId) {
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
		this.cjsj = cjsj;
		this.cjr = cjr;
		this.xgsj = xgsj;
		this.xgr = xgr;
		this.qdmc = qdmc;
		this.cc=cc;
		this.xxfsId=xxfsId;
		this.sfqy=sfqy;
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

	@Column(name = "BM", length = 60)
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

	@Column(name = "XXFS_ID", length = 60)
	public Long getXxfsId() {
		return this.xxfsId;
	}
	
	public void setXxfsId(Long xxfsId) {
		this.xxfsId = xxfsId;
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
	public Long getSfky() {
		return this.sfky;
	}

	public void setSfky(Long sfky) {
		this.sfky = sfky;
	}
	
	@Column(name = "SFQY", precision = 1, scale = 0)
	public Long getSfqy() {
		return this.sfqy;
	}

	public void setSfqy(Long sfqy) {
		this.sfqy = sfqy;
	}

	@Column(name = "NJ_ID", precision = 16, scale = 0)
	public Long getNjId() {
		return this.njId;
	}

	public void setNjId(Long njId) {
		this.njId = njId;
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

	@Column(name = "QDMC", length = 60)
	public String getQdmc() {
		return this.qdmc;
	}

	public void setQdmc(String qdmc) {
		this.qdmc = qdmc;
	}
	
	@Column(name = "CC_ID", length = 20)
	public Long getCc() {
		return cc;
	}
	public void setCc(Long cc) {
		this.cc = cc;
	}
}