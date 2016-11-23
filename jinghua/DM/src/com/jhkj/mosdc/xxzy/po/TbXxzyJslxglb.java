package com.jhkj.mosdc.xxzy.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbXxzyJslxglb entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_XXZY_JSLXGLB")
public class TbXxzyJslxglb implements java.io.Serializable {

	// Fields

	private Long id;
	private Long jsId;
	private Long jslxId;
	private Short jsrl;
	private Long sfky;
	private String cjr;
	private String cjsj;
	private String xgr;
	private String xgsj;

	// Constructors

	/** default constructor */
	public TbXxzyJslxglb() {
	}

	/** minimal constructor */
	public TbXxzyJslxglb(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TbXxzyJslxglb(Long id, Long jsId, Long jslxId, Short jsrl,
			Long sfky, String cjr, String cjsj, String xgr, String xgsj) {
		this.id = id;
		this.jsId = jsId;
		this.jslxId = jslxId;
		this.jsrl = jsrl;
		this.sfky = sfky;
		this.cjr = cjr;
		this.cjsj = cjsj;
		this.xgr = xgr;
		this.xgsj = xgsj;
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

	@Column(name = "JSLX_ID", precision = 16, scale = 0)
	public Long getJslxId() {
		return this.jslxId;
	}

	public void setJslxId(Long jslxId) {
		this.jslxId = jslxId;
	}

	@Column(name = "JSRL", precision = 4, scale = 0)
	public Short getJsrl() {
		return this.jsrl;
	}

	public void setJsrl(Short jsrl) {
		this.jsrl = jsrl;
	}

	@Column(name = "SFKY", precision = 1, scale = 0)
	public Long getSfky() {
		return this.sfky;
	}

	public void setSfky(Long sfky) {
		this.sfky = sfky;
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