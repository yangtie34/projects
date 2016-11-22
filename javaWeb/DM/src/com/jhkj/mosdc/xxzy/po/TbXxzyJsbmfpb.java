package com.jhkj.mosdc.xxzy.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbXxzyJsbmfpb entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_XXZY_JSBMFPB")
public class TbXxzyJsbmfpb implements java.io.Serializable {

	// Fields

	private Long id;
	private Long jsId;
	private Long zzjgId;
	private Long sfky;
	private Boolean sfpk;
	private Boolean sfpkao;
	private Boolean ssdwzy;
	private String begindate;
	private String enddate;
	private String cjsj;
	private String cjr;
	private String xgsj;
	private String xgr;

	// Constructors

	/** default constructor */
	public TbXxzyJsbmfpb() {
	}

	/** minimal constructor */
	public TbXxzyJsbmfpb(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TbXxzyJsbmfpb(Long id, Long jsId, Long zzjgId, Long sfky,
			Boolean sfpk, Boolean sfpkao, Boolean ssdwzy, String begindate,
			String enddate, String cjsj, String cjr, String xgsj, String xgr) {
		this.id = id;
		this.jsId = jsId;
		this.zzjgId = zzjgId;
		this.sfky = sfky;
		this.sfpk = sfpk;
		this.sfpkao = sfpkao;
		this.ssdwzy = ssdwzy;
		this.begindate = begindate;
		this.enddate = enddate;
		this.cjsj = cjsj;
		this.cjr = cjr;
		this.xgsj = xgsj;
		this.xgr = xgr;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "JS_ID", precision = 16, scale = 0)
	public Long getJsId() {
		return this.jsId;
	}

	public void setJsId(Long jsId) {
		this.jsId = jsId;
	}

	@Column(name = "ZZJG_ID", precision = 16, scale = 0)
	public Long getZzjgId() {
		return this.zzjgId;
	}

	public void setZzjgId(Long zzjgId) {
		this.zzjgId = zzjgId;
	}

	@Column(name = "SFKY", precision = 1, scale = 0)
	public Long getSfky() {
		return this.sfky;
	}

	public void setSfky(Long sfky) {
		this.sfky = sfky;
	}

	@Column(name = "SFPK", precision = 1, scale = 0)
	public Boolean getSfpk() {
		return this.sfpk;
	}

	public void setSfpk(Boolean sfpk) {
		this.sfpk = sfpk;
	}

	@Column(name = "SFPKAO", precision = 1, scale = 0)
	public Boolean getSfpkao() {
		return this.sfpkao;
	}

	public void setSfpkao(Boolean sfpkao) {
		this.sfpkao = sfpkao;
	}

	@Column(name = "SSDWZY", precision = 1, scale = 0)
	public Boolean getSsdwzy() {
		return this.ssdwzy;
	}

	public void setSsdwzy(Boolean ssdwzy) {
		this.ssdwzy = ssdwzy;
	}

	@Column(name = "BEGINDATE", length = 60)
	public String getBegindate() {
		return this.begindate;
	}

	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}

	@Column(name = "ENDDATE", length = 60)
	public String getEnddate() {
		return this.enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	@Column(name = "CJSJ", length = 60)
	public String getCjsj() {
		return this.cjsj;
	}

	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
	}

	@Column(name = "CJR", length = 60)
	public String getCjr() {
		return this.cjr;
	}

	public void setCjr(String cjr) {
		this.cjr = cjr;
	}

	@Column(name = "XGSJ", length = 60)
	public String getXgsj() {
		return this.xgsj;
	}

	public void setXgsj(String xgsj) {
		this.xgsj = xgsj;
	}

	@Column(name = "XGR", length = 60)
	public String getXgr() {
		return this.xgr;
	}

	public void setXgr(String xgr) {
		this.xgr = xgr;
	}

}