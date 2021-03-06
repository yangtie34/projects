package com.jhkj.mosdc.xggl.xjgl.po;

// Generated 2013-11-29 17:53:32 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbXjdaZctjsz generated by hbm2java
 */
@Entity
@Table(name = "TB_XJDA_ZCTJSZ")
public class TbXjdaZctjsz implements java.io.Serializable {

	private Long id;
	private String mc;
	private String tjms;
	private Long tjzt;
	private String cjr;
	private String cjsj;
	private String xgr;
	private String xgsj;
	private Long sfqzzc;
	public TbXjdaZctjsz(){
		
	}
	public TbXjdaZctjsz(Long id) {
		this.id = id;
	}

	public TbXjdaZctjsz(Long id, String mc, String tjms, Long tjzt,
			String cjr, String cjsj, String xgr, String xgsj,Long sfqzzc) {
		this.id = id;
		this.mc = mc;
		this.tjms = tjms;
		this.tjzt = tjzt;
		this.cjr = cjr;
		this.cjsj = cjsj;
		this.xgr = xgr;
		this.xgsj = xgsj;
		this.sfqzzc=sfqzzc;
	}

	@Id
	@Column(name = "ID", precision = 16, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "MC", length = 60)
	public String getMc() {
		return this.mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	@Column(name = "TJMS", length = 200)
	public String getTjms() {
		return this.tjms;
	}

	public void setTjms(String tjms) {
		this.tjms = tjms;
	}

	@Column(name = "TJZT", precision = 1, scale = 0)
	public Long getTjzt() {
		return this.tjzt;
	}

	public void setTjzt(Long tjzt) {
		this.tjzt = tjzt;
	}

	@Column(name = "CJR", length = 60)
	public String getCjr() {
		return this.cjr;
	}

	public void setCjr(String cjr) {
		this.cjr = cjr;
	}

	@Column(name = "CJSJ", length = 60)
	public String getCjsj() {
		return this.cjsj;
	}

	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
	}

	@Column(name = "XGR", length = 60)
	public String getXgr() {
		return this.xgr;
	}

	public void setXgr(String xgr) {
		this.xgr = xgr;
	}

	@Column(name = "XGSJ", length = 60)
	public String getXgsj() {
		return this.xgsj;
	}

	public void setXgsj(String xgsj) {
		this.xgsj = xgsj;
	}
	
	@Column(name = "SFQZZC", precision = 1, scale = 0)
	public Long getSfqzzc() {
		return this.sfqzzc;
	}
	public void setSfqzzc(Long sfqzzc) {
		this.sfqzzc = sfqzzc;
	}
}
