package com.jhkj.mosdc.framework.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbBjxx entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_BJXX")
public class TbBjxx implements java.io.Serializable {

	// Fields

	private Long id;
	private String bh;
	private String bjmc;
	private Long yxId;
	private Long zyId;
	private Long xslbId;
	private String rxsj;
	private String xz;
	private String rs;
	private Long bzId;
	private String bzrzgId;
	private Long lqlbId;
	private String sfqy;
	private String cjsj;
	private String cjr;
	private String xgsj;
	private String xgr;
	private Long cjrId;
	private Long xgrId;

	// Constructors

	/** default constructor */
	public TbBjxx() {
	}

	/** minimal constructor */
	public TbBjxx(Long id, String bjmc) {
		this.id = id;
		this.bjmc = bjmc;
	}

	/** full constructor */
	public TbBjxx(Long id, String bh, String bjmc, Long yxId, Long zyId,
			Long xslbId, String rxsj, String xz, String rs, Long bzId,
			String bzrzgId, String sfqy, String cjsj, String cjr, String xgsj,
			String xgr, Long cjrId, Long xgrId) {
		this.id = id;
		this.bh = bh;
		this.bjmc = bjmc;
		this.yxId = yxId;
		this.zyId = zyId;
		this.xslbId = xslbId;
		this.rxsj = rxsj;
		this.xz = xz;
		this.rs = rs;
		this.bzId = bzId;
		this.bzrzgId = bzrzgId;
		this.sfqy = sfqy;
		this.cjsj = cjsj;
		this.cjr = cjr;
		this.xgsj = xgsj;
		this.xgr = xgr;
		this.cjrId = cjrId;
		this.xgrId = xgrId;
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

	@Column(name = "BH", length = 20)
	public String getBh() {
		return this.bh;
	}

	public void setBh(String bh) {
		this.bh = bh;
	}

	@Column(name = "BJMC",  length = 100)
	public String getBjmc() {
		return this.bjmc;
	}

	public void setBjmc(String bjmc) {
		this.bjmc = bjmc;
	}

	@Column(name = "YX_ID", precision = 16, scale = 0)
	public Long getYxId() {
		return this.yxId;
	}

	public void setYxId(Long yxId) {
		this.yxId = yxId;
	}

	@Column(name = "ZY_ID", precision = 16, scale = 0)
	public Long getZyId() {
		return this.zyId;
	}

	public void setZyId(Long zyId) {
		this.zyId = zyId;
	}

	@Column(name = "XSLB_ID", precision = 16, scale = 0)
	public Long getXslbId() {
		return this.xslbId;
	}

	public void setXslbId(Long xslbId) {
		this.xslbId = xslbId;
	}

	@Column(name = "RXSJ", length = 10)
	public String getRxsj() {
		return this.rxsj;
	}

	public void setRxsj(String rxsj) {
		this.rxsj = rxsj;
	}

	@Column(name = "XZ", length = 16)
	public String getXz() {
		return this.xz;
	}

	public void setXz(String xz) {
		this.xz = xz;
	}

	@Column(name = "RS", length = 10)
	public String getRs() {
		return this.rs;
	}

	public void setRs(String rs) {
		this.rs = rs;
	}

	@Column(name = "BZ_ID", precision = 16, scale = 0)
	public Long getBzId() {
		return this.bzId;
	}

	public void setBzId(Long bzId) {
		this.bzId = bzId;
	}

	@Column(name = "BZRZG_ID", length = 20)
	public String getBzrzgId() {
		return this.bzrzgId;
	}

	public void setBzrzgId(String bzrzgId) {
		this.bzrzgId = bzrzgId;
	}

	@Column(name = "SFQY", length = 1)
	public String getSfqy() {
		return this.sfqy;
	}

	public void setSfqy(String sfqy) {
		this.sfqy = sfqy;
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

	@Column(name = "CJR_ID", precision = 16, scale = 0)
	public Long getCjrId() {
		return this.cjrId;
	}

	public void setCjrId(Long cjrId) {
		this.cjrId = cjrId;
	}

	@Column(name = "XGR_ID", precision = 16, scale = 0)
	public Long getXgrId() {
		return this.xgrId;
	}

	public void setXgrId(Long xgrId) {
		this.xgrId = xgrId;
	}
	
	@Column(name = "LQLB_ID", precision = 16, scale = 0)
	public Long getLqlbId() {
		return lqlbId;
	}

	public void setLqlbId(Long lqlbId) {
		this.lqlbId = lqlbId;
	}

}