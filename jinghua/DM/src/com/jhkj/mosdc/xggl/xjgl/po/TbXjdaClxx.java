package com.jhkj.mosdc.xggl.xjgl.po;

// Generated 2013-11-5 13:49:26 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbXjdaClxx generated by hbm2java
 */
@Entity
@Table(name = "TB_XJDA_CLXX")
public class TbXjdaClxx implements java.io.Serializable {

	private Long id;
	private Long ydId;
	private String wjm;
	private String wjsjm;
	private String cclj;

	public TbXjdaClxx() {
	}

	public TbXjdaClxx(Long id) {
		this.id = id;
	}

	public TbXjdaClxx(Long id, Long ydId, String wjm, String wjsjm, String cclj) {
		this.id = id;
		this.ydId = ydId;
		this.wjm = wjm;
		this.wjsjm = wjsjm;
		this.cclj = cclj;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "YD_ID", precision = 16, scale = 0)
	public Long getYdId() {
		return this.ydId;
	}

	public void setYdId(Long ydId) {
		this.ydId = ydId;
	}

	@Column(name = "WJM", length = 60)
	public String getWjm() {
		return this.wjm;
	}

	public void setWjm(String wjm) {
		this.wjm = wjm;
	}

	@Column(name = "WJSJM", length = 100)
	public String getWjsjm() {
		return this.wjsjm;
	}

	public void setWjsjm(String wjsjm) {
		this.wjsjm = wjsjm;
	}

	@Column(name = "CCLJ", length = 60)
	public String getCclj() {
		return this.cclj;
	}

	public void setCclj(String cclj) {
		this.cclj = cclj;
	}

}
