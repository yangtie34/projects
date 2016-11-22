package com.jhkj.mosdc.xxzy.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbXxzyXq entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_XXZY_XQ")
public class TbXxzyXq implements java.io.Serializable {

	// Fields

	private Long id;
	private String xqbm;
	private String mc;
	private String xqyb;
	private String xqdz;
	private String zdmj;
	private Long sfky;
	private String xqcjsj;
	private String cjr;
	private String cjsj;
	private String xgr;
	private String xgsj;
	private String bz;

	// Constructors

	/** default constructor */
	public TbXxzyXq() {
	}

	/** minimal constructor */
	public TbXxzyXq(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TbXxzyXq(Long id, String xqbm, String mc, String xqyb,
			String xqdz, String zdmj, Long sfky, String xqcjsj, String cjr,
			String cjsj, String xgr, String xgsj, String bz) {
		this.id = id;
		this.xqbm = xqbm;
		this.mc = mc;
		this.xqyb = xqyb;
		this.xqdz = xqdz;
		this.zdmj = zdmj;
		this.sfky = sfky;
		this.xqcjsj = xqcjsj;
		this.cjr = cjr;
		this.cjsj = cjsj;
		this.xgr = xgr;
		this.xgsj = xgsj;
		this.bz = bz;
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

	@Column(name = "XQBM", length = 20)
	public String getXqbm() {
		return this.xqbm;
	}

	public void setXqbm(String xqbm) {
		this.xqbm = xqbm;
	}

	@Column(name = "XQMC", length = 40)
	public String getMc() {
		return this.mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}
	@Column(name = "XQYB", length = 20)
	public String getXqyb() {
		return this.xqyb;
	}

	public void setXqyb(String xqyb) {
		this.xqyb = xqyb;
	}

	@Column(name = "XQDZ", length = 200)
	public String getXqdz() {
		return this.xqdz;
	}

	public void setXqdz(String xqdz) {
		this.xqdz = xqdz;
	}

	@Column(name = "ZDMJ", length = 20)
	public String getZdmj() {
		return this.zdmj;
	}

	public void setZdmj(String zdmj) {
		this.zdmj = zdmj;
	}

	@Column(name = "SFKY")
	public Long getSfky() {
		return this.sfky;
	}

	public void setSfky(Long sfky) {
		this.sfky = sfky;
	}

	@Column(name = "XQCJSJ", length = 60)
	public String getXqcjsj() {
		return this.xqcjsj;
	}

	public void setXqcjsj(String xqcjsj) {
		this.xqcjsj = xqcjsj;
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

	@Column(name = "BZ", length = 500)
	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

}