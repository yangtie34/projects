package com.jhkj.mosdc.pano.po;

// Generated 2013-10-30 15:26:24 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbPanoQxjjgxxb generated by hbm2java
 */
@Entity
@Table(name = "TB_PANO_QXJJGXXB", schema = "MOSDC_PANO")
public class TbPanoQxjjgxxb implements java.io.Serializable {

	private long id;
	private Long xnId;
	private Long xqId;
	private Long xsId;
	private String qjsj;
	private Long qjlxId;
	private String fxsj;
	private Long dqztId;
	private String sjtbsj;
	private String tbfs;

	public TbPanoQxjjgxxb() {
	}

	public TbPanoQxjjgxxb(long id) {
		this.id = id;
	}

	public TbPanoQxjjgxxb(long id, Long xnId, Long xqId, Long xsId,
			String qjsj, Long qjlxId, String fxsj, Long dqztId, String sjtbsj,
			String tbfs) {
		this.id = id;
		this.xnId = xnId;
		this.xqId = xqId;
		this.xsId = xsId;
		this.qjsj = qjsj;
		this.qjlxId = qjlxId;
		this.fxsj = fxsj;
		this.dqztId = dqztId;
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

	@Column(name = "XS_ID", precision = 16, scale = 0)
	public Long getXsId() {
		return this.xsId;
	}

	public void setXsId(Long xsId) {
		this.xsId = xsId;
	}

	@Column(name = "QJSJ", length = 20)
	public String getQjsj() {
		return this.qjsj;
	}

	public void setQjsj(String qjsj) {
		this.qjsj = qjsj;
	}

	@Column(name = "QJLX_ID", precision = 16, scale = 0)
	public Long getQjlxId() {
		return this.qjlxId;
	}

	public void setQjlxId(Long qjlxId) {
		this.qjlxId = qjlxId;
	}

	@Column(name = "FXSJ", length = 20)
	public String getFxsj() {
		return this.fxsj;
	}

	public void setFxsj(String fxsj) {
		this.fxsj = fxsj;
	}

	@Column(name = "DQZT_ID", precision = 16, scale = 0)
	public Long getDqztId() {
		return this.dqztId;
	}

	public void setDqztId(Long dqztId) {
		this.dqztId = dqztId;
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
