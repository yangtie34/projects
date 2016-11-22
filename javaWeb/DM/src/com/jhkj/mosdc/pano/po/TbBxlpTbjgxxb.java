package com.jhkj.mosdc.pano.po;

// Generated 2013-10-30 15:26:24 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbBxlpTbjgxxb generated by hbm2java
 */
@Entity
@Table(name = "TB_BXLP_TBJGXXB", schema = "MOSDC_PANO")
public class TbBxlpTbjgxxb implements java.io.Serializable {

	private long id;
	private Long xnId;
	private Long xqId;
	private String bdh;
	private Long xsId;
	private Long bllxId;
	private BigDecimal bxje;
	private String tbrq;
	private String bxsxrq;
	private String jsrq;
	private String sjtbsj;
	private String tbfs;

	public TbBxlpTbjgxxb() {
	}

	public TbBxlpTbjgxxb(long id) {
		this.id = id;
	}

	public TbBxlpTbjgxxb(long id, Long xnId, Long xqId, String bdh, Long xsId,
			Long bllxId, BigDecimal bxje, String tbrq, String bxsxrq,
			String jsrq, String sjtbsj, String tbfs) {
		this.id = id;
		this.xnId = xnId;
		this.xqId = xqId;
		this.bdh = bdh;
		this.xsId = xsId;
		this.bllxId = bllxId;
		this.bxje = bxje;
		this.tbrq = tbrq;
		this.bxsxrq = bxsxrq;
		this.jsrq = jsrq;
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

	@Column(name = "XN_ID", precision = 16, scale = 0)
	public Long getXnId() {
		return this.xnId;
	}

	public void setXnId(Long xnId) {
		this.xnId = xnId;
	}

	@Column(name = "XQ_ID", precision = 16, scale = 0)
	public Long getXqId() {
		return this.xqId;
	}

	public void setXqId(Long xqId) {
		this.xqId = xqId;
	}

	@Column(name = "BDH", length = 20)
	public String getBdh() {
		return this.bdh;
	}

	public void setBdh(String bdh) {
		this.bdh = bdh;
	}

	@Column(name = "XS_ID", precision = 16, scale = 0)
	public Long getXsId() {
		return this.xsId;
	}

	public void setXsId(Long xsId) {
		this.xsId = xsId;
	}

	@Column(name = "BLLX_ID", precision = 16, scale = 0)
	public Long getBllxId() {
		return this.bllxId;
	}

	public void setBllxId(Long bllxId) {
		this.bllxId = bllxId;
	}

	@Column(name = "BXJE", precision = 8)
	public BigDecimal getBxje() {
		return this.bxje;
	}

	public void setBxje(BigDecimal bxje) {
		this.bxje = bxje;
	}

	@Column(name = "TBRQ", length = 10)
	public String getTbrq() {
		return this.tbrq;
	}

	public void setTbrq(String tbrq) {
		this.tbrq = tbrq;
	}

	@Column(name = "BXSXRQ", length = 10)
	public String getBxsxrq() {
		return this.bxsxrq;
	}

	public void setBxsxrq(String bxsxrq) {
		this.bxsxrq = bxsxrq;
	}

	@Column(name = "JSRQ", length = 10)
	public String getJsrq() {
		return this.jsrq;
	}

	public void setJsrq(String jsrq) {
		this.jsrq = jsrq;
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
