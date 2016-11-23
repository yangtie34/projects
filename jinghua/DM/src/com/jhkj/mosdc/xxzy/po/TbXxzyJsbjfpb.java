package com.jhkj.mosdc.xxzy.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbXxzyJsbjfpb entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_XXZY_JSBJFPB")
public class TbXxzyJsbjfpb implements java.io.Serializable {

	// Fields

	private Long id;
	private Long jsId;
	private Long bjId;
	private Long xnId;
	private Long xqId;
	private Long sfky;
	private String cjr;
	private String cjsj;
	private String xgr;
	private String xgsj;

	// Constructors

	/** default constructor */
	public TbXxzyJsbjfpb() {
	}

	/** minimal constructor */
	public TbXxzyJsbjfpb(Long id) {
		this.id = id;
	}	

	public TbXxzyJsbjfpb(Long id, Long jsId, Long bjId, Long xnId, Long xqId,
			Long sfky, String cjr, String cjsj, String xgr, String xgsj) {
		super();
		this.id = id;
		this.jsId = jsId;
		this.bjId = bjId;
		this.xnId = xnId;
		this.xqId = xqId;
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

	@Column(name = "BJ_ID", precision = 16, scale = 0)
	public Long getBjId() {
		return this.bjId;
	}

	public void setBjId(Long bjId) {
		this.bjId = bjId;
	}

	@Column(name = "XN_ID", precision = 16, scale = 0)
	public Long getXnId() {
		return this.xnId;
	}

	public void setXnId(Long xnId) {
		this.xnId = xnId;
	}

	@Column(name = "XQ_ID", precision = 16, scale = 0)
	public Long getXqId() {
		return this.xqId;
	}

	public void setXqId(Long xqId) {
		this.xqId = xqId;
	}

	@Column(name = "SFKY", precision = 1, scale = 0)
	public Long getSfky() {
		return sfky;
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