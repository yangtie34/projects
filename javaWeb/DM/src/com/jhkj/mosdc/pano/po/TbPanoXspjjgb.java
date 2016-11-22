package com.jhkj.mosdc.pano.po;

// Generated 2013-10-30 15:26:24 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbPanoXspjjgb generated by hbm2java
 */
@Entity
@Table(name = "TB_PANO_XSPJJGB", schema = "MOSDC_PANO")
public class TbPanoXspjjgb implements java.io.Serializable {

	private long id;
	private Long xsId;
	private Long kcId;
	private Long xnId;
	private Long xqId;
	private Boolean sfypj;
	private String sjtbsj;
	private String tbfs;

	public TbPanoXspjjgb() {
	}

	public TbPanoXspjjgb(long id) {
		this.id = id;
	}

	public TbPanoXspjjgb(long id, Long xsId, Long kcId, Long xnId, Long xqId,
			Boolean sfypj, String sjtbsj, String tbfs) {
		this.id = id;
		this.xsId = xsId;
		this.kcId = kcId;
		this.xnId = xnId;
		this.xqId = xqId;
		this.sfypj = sfypj;
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

	@Column(name = "XS_ID", precision = 16, scale = 0)
	public Long getXsId() {
		return this.xsId;
	}

	public void setXsId(Long xsId) {
		this.xsId = xsId;
	}

	@Column(name = "KC_ID", precision = 16, scale = 0)
	public Long getKcId() {
		return this.kcId;
	}

	public void setKcId(Long kcId) {
		this.kcId = kcId;
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

	@Column(name = "SFYPJ", precision = 1, scale = 0)
	public Boolean getSfypj() {
		return this.sfypj;
	}

	public void setSfypj(Boolean sfypj) {
		this.sfypj = sfypj;
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
