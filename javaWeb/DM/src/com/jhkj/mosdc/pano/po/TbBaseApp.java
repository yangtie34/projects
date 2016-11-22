package com.jhkj.mosdc.pano.po;

// Generated 2013-10-30 15:26:24 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbBaseApp generated by hbm2java
 */
@Entity
@Table(name = "TB_BASE_APP", schema = "MOSDC_PANO")
public class TbBaseApp implements java.io.Serializable {

	private long id;
	private String dm;
	private String mc;
	private String jj;
	private String appservice;
	private String dataservice;
	private String glmk;
	private String bq;

	public TbBaseApp() {
	}

	public TbBaseApp(long id) {
		this.id = id;
	}

	public TbBaseApp(long id, String dm, String mc, String jj,
			String appservice, String dataservice, String glmk, String bq) {
		this.id = id;
		this.dm = dm;
		this.mc = mc;
		this.jj = jj;
		this.appservice = appservice;
		this.dataservice = dataservice;
		this.glmk = glmk;
		this.bq = bq;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "DM", length = 60)
	public String getDm() {
		return this.dm;
	}

	public void setDm(String dm) {
		this.dm = dm;
	}

	@Column(name = "MC", length = 120)
	public String getMc() {
		return this.mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	@Column(name = "JJ", length = 2000)
	public String getJj() {
		return this.jj;
	}

	public void setJj(String jj) {
		this.jj = jj;
	}

	@Column(name = "APPSERVICE", length = 150)
	public String getAppservice() {
		return this.appservice;
	}

	public void setAppservice(String appservice) {
		this.appservice = appservice;
	}

	@Column(name = "DATASERVICE", length = 150)
	public String getDataservice() {
		return this.dataservice;
	}

	public void setDataservice(String dataservice) {
		this.dataservice = dataservice;
	}

	@Column(name = "GLMK", length = 60)
	public String getGlmk() {
		return this.glmk;
	}

	public void setGlmk(String glmk) {
		this.glmk = glmk;
	}

	@Column(name = "BQ", length = 200)
	public String getBq() {
		return this.bq;
	}

	public void setBq(String bq) {
		this.bq = bq;
	}

}
