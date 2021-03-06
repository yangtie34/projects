package com.jhkj.mosdc.xggl.xjgl.po;

// Generated 2013-11-5 13:49:26 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbXjdaZzmm generated by hbm2java
 */
@Entity
@Table(name = "TB_XJDA_ZZMM")
public class TbXjdaZzmm implements java.io.Serializable {

	private Long id;
	private Long xsId;
	private Long zzmmId;
	private String jrrq;
	private String cjr;
	private String cjsj;
	private String xgr;
	private String xgsj;
	
	public TbXjdaZzmm() {
	}

	public TbXjdaZzmm(Long id) {
		this.id = id;
	}

	public TbXjdaZzmm(Long id, Long xsId, Long zzmmId, String jrrq,String cjr, String cjsj,
			String xgr, String xgsj) {
		this.id = id;
		this.xsId = xsId;
		this.zzmmId = zzmmId;
		this.jrrq = jrrq;
		this.cjr = cjr;
		this.cjsj = cjsj;
		this.xgr = xgr;
		this.xgsj = xgsj;
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

	@Column(name = "ZZMM_ID", precision = 16, scale = 0)
	public Long getZzmmId() {
		return this.zzmmId;
	}

	public void setZzmmId(Long zzmmId) {
		this.zzmmId = zzmmId;
	}

	@Column(name = "JRRQ", length = 30)
	public String getJrrq() {
		return this.jrrq;
	}

	public void setJrrq(String jrrq) {
		this.jrrq = jrrq;
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
}
