package com.jhkj.mosdc.xggl.xjgl.po;

// Generated 2014-5-13 14:35:19 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 原籍档案
 */
@Entity
@Table(name = "TB_XJDA_YJDA")
public class TbXjdaYjda implements java.io.Serializable {

	private Long id;
	private Long xsId;
	private Long dalbId;
	private String damc;
	private Boolean sfczda;

	public TbXjdaYjda() {
	}

	public TbXjdaYjda(Long id) {
		this.id = id;
	}

	public TbXjdaYjda(Long id, Long xsId, Long dalbId, String damc,
			Boolean sfczda) {
		this.id = id;
		this.xsId = xsId;
		this.dalbId = dalbId;
		this.damc = damc;
		this.sfczda = sfczda;
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

	@Column(name = "DALB_ID", precision = 16, scale = 0)
	public Long getDalbId() {
		return this.dalbId;
	}

	public void setDalbId(Long dalbId) {
		this.dalbId = dalbId;
	}

	@Column(name = "DAMC", length = 60)
	public String getDamc() {
		return this.damc;
	}

	public void setDamc(String damc) {
		this.damc = damc;
	}

	@Column(name = "SFCZDA", precision = 1, scale = 0)
	public Boolean getSfczda() {
		return this.sfczda;
	}

	public void setSfczda(Boolean sfczda) {
		this.sfczda = sfczda;
	}

}
