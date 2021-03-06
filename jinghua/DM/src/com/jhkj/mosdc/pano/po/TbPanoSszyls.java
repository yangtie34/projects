package com.jhkj.mosdc.pano.po;

// Generated 2013-10-30 15:26:24 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbPanoSszyls generated by hbm2java
 */
@Entity
@Table(name = "TB_PANO_SSZYLS", schema = "MOSDC_PANO")
public class TbPanoSszyls implements java.io.Serializable {

	private long id;
	private Long zyId;
	private Long bglxId;
	private Long bgqcwId;
	private Long bghcwId;
	private String bgsj;
	private String czr;
	private String bz;
	private String sjtbsj;
	private String tbfs;

	public TbPanoSszyls() {
	}

	public TbPanoSszyls(long id) {
		this.id = id;
	}

	public TbPanoSszyls(long id, Long zyId, Long bglxId, Long bgqcwId,
			Long bghcwId, String bgsj, String czr, String bz, String sjtbsj,
			String tbfs) {
		this.id = id;
		this.zyId = zyId;
		this.bglxId = bglxId;
		this.bgqcwId = bgqcwId;
		this.bghcwId = bghcwId;
		this.bgsj = bgsj;
		this.czr = czr;
		this.bz = bz;
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

	@Column(name = "ZY_ID", precision = 16, scale = 0)
	public Long getZyId() {
		return this.zyId;
	}

	public void setZyId(Long zyId) {
		this.zyId = zyId;
	}

	@Column(name = "BGLX_ID", precision = 16, scale = 0)
	public Long getBglxId() {
		return this.bglxId;
	}

	public void setBglxId(Long bglxId) {
		this.bglxId = bglxId;
	}

	@Column(name = "BGQCW_ID", precision = 16, scale = 0)
	public Long getBgqcwId() {
		return this.bgqcwId;
	}

	public void setBgqcwId(Long bgqcwId) {
		this.bgqcwId = bgqcwId;
	}

	@Column(name = "BGHCW_ID", precision = 16, scale = 0)
	public Long getBghcwId() {
		return this.bghcwId;
	}

	public void setBghcwId(Long bghcwId) {
		this.bghcwId = bghcwId;
	}

	@Column(name = "BGSJ", length = 20)
	public String getBgsj() {
		return this.bgsj;
	}

	public void setBgsj(String bgsj) {
		this.bgsj = bgsj;
	}

	@Column(name = "CZR", length = 60)
	public String getCzr() {
		return this.czr;
	}

	public void setCzr(String czr) {
		this.czr = czr;
	}

	@Column(name = "BZ", length = 300)
	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
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
