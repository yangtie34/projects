package com.jhkj.mosdc.xxzy.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbXxzyJsxxb entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_XXZY_JSXXB")
public class TbXxzyJsxxb implements java.io.Serializable {

	// Fields

	private Long id;
	private String mph;
	private String jsmc;
	private Long ssdwId;
	private Long sfky;
	private Long jsrl;
	private Short rnbjs;
	private Long sfpk;
	private Long sfpkao;
	private Long ssdwzy;
	private String cjsj;
	private String cjr;
	private String xgsj;
	private String xgr;

	// Constructors

	/** default constructor */
	public TbXxzyJsxxb() {
	}

	/** minimal constructor */
	public TbXxzyJsxxb(Long id) {
		this.id = id;
	}


	public TbXxzyJsxxb(Long id, String mph, String jsmc, Long ssdwId,
			Long sfky, Long jsrl, Short rnbjs, Long sfpk, Long sfpkao,
			Long ssdwzy, String cjsj, String cjr, String xgsj, String xgr) {
		super();
		this.id = id;
		this.mph = mph;
		this.jsmc = jsmc;
		this.ssdwId = ssdwId;
		this.sfky = sfky;
		this.jsrl = jsrl;
		this.rnbjs = rnbjs;
		this.sfpk = sfpk;
		this.sfpkao = sfpkao;
		this.ssdwzy = ssdwzy;
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

	@Column(name = "MPH", length = 20)
	public String getMph() {
		return this.mph;
	}

	public void setMph(String mph) {
		this.mph = mph;
	}

	@Column(name = "JSMC", length = 20)
	public String getJsmc() {
		return this.jsmc;
	}

	public void setJsmc(String jsmc) {
		this.jsmc = jsmc;
	}

	@Column(name = "SSDW_ID", precision = 16, scale = 0)
	public Long getSsdwId() {
		return this.ssdwId;
	}

	public void setSsdwId(Long ssdwId) {
		this.ssdwId = ssdwId;
	}

	@Column(name = "SFKY", precision = 1, scale = 0)
	public Long getSfky() {
		return this.sfky;
	}

	public void setSfky(Long sfky) {
		this.sfky = sfky;
	}

	@Column(name = "JSRL", precision = 4, scale = 0)
	public Long getJsrl() {
		return this.jsrl;
	}

	public void setJsrl(Long jsrl) {
		this.jsrl = jsrl;
	}

	@Column(name = "SFPK", precision = 1, scale = 0)
	public Long getSfpk() {
		return this.sfpk;
	}

	public void setSfpk(Long sfpk) {
		this.sfpk = sfpk;
	}

	@Column(name = "SFPKAO", precision = 1, scale = 0)
	public Long getSfpkao() {
		return this.sfpkao;
	}

	public void setSfpkao(Long sfpkao) {
		this.sfpkao = sfpkao;
	}

	@Column(name = "SSDWZY", precision = 1, scale = 0)
	public Long getSsdwzy() {
		return this.ssdwzy;
	}

	public void setSsdwzy(Long ssdwzy) {
		this.ssdwzy = ssdwzy;
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

	@Column(name = "RNBJS", precision = 4, scale = 0)
	public Short getRnbjs() {
		return rnbjs;
	}

	public void setRnbjs(Short rnbjs) {
		this.rnbjs = rnbjs;
	}

}