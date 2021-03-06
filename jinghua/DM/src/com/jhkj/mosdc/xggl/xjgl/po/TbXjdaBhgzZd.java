package com.jhkj.mosdc.xggl.xjgl.po;

// Generated 2013-11-5 13:49:26 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbXjdaBhgzZd generated by hbm2java
 */
@Entity
@Table(name = "TB_XJDA_BHGZ_ZD")
public class TbXjdaBhgzZd implements java.io.Serializable {

	private Long id;
	private Long gzId;
	private Long zdId;
	private Integer zdcd;
	private Integer pxh;
	private String dmsl;

	public TbXjdaBhgzZd() {
	}

	public TbXjdaBhgzZd(Long id) {
		this.id = id;
	}

	public TbXjdaBhgzZd(Long id, Long gzId, Long zdId, Integer zdcd, Integer pxh, String dmsl) {
		this.id = id;
		this.gzId = gzId;
		this.zdId = zdId;
		this.zdcd = zdcd;
		this.pxh = pxh;
		this.dmsl = dmsl;
	}
	
	public TbXjdaBhgzZd(Long zdId, Integer zdcd, Integer pxh, String dmsl) {
		this.zdId = zdId;
		this.zdcd = zdcd;
		this.pxh = pxh;
		this.dmsl = dmsl;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "GZ_ID", precision = 16, scale = 0)
	public Long getGzId() {
		return this.gzId;
	}

	public void setGzId(Long gzId) {
		this.gzId = gzId;
	}

	@Column(name = "ZD_ID", precision = 16, scale = 0)
	public Long getZdId() {
		return this.zdId;
	}

	public void setZdId(Long zdId) {
		this.zdId = zdId;
	}

	@Column(name = "ZDCD", precision = 2, scale = 0)
	public Integer getZdcd() {
		return this.zdcd;
	}

	public void setZdcd(Integer zdcd) {
		this.zdcd = zdcd;
	}

	@Column(name = "PXH", precision = 2, scale = 0)
	public Integer getPxh() {
		return this.pxh;
	}

	public void setPxh(Integer pxh) {
		this.pxh = pxh;
	}

	@Column(name = "DMSL", precision = 10, scale = 0)
	public String getDmsl() {
		return dmsl;
	}

	public void setDmsl(String dmsl) {
		this.dmsl = dmsl;
	}

}
