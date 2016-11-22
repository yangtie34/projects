package com.jhkj.mosdc.jwgl.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbJwPkzb entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_JW_PK_PKZB")
public class TbJwPkzb implements java.io.Serializable {

	// Fields

	private Long id;
	private Long xnId;
	private Long xqId;
	private String xqkssj;
	private String xqjssj;
	private String cjr;
	private String cjsj;
	private String xgr;
	private String xgsj;

	// Constructors

	/** default constructor */
	public TbJwPkzb() {
	}

	/** minimal constructor */
	public TbJwPkzb(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TbJwPkzb(Long id, Long xnId, Long xqId, String xqkssj,
			String xqjssj, String cjr, String cjsj, String xgr, String xgsj) {
		this.id = id;
		this.xnId = xnId;
		this.xqId = xqId;
		this.xqkssj = xqkssj;
		this.xqjssj = xqjssj;
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

	@Column(name = "XN_ID", length = 16)
	public Long getXnId() {
		return xnId;
	}

	public void setXnId(Long xnId) {
		this.xnId = xnId;
	}

	@Column(name = "XQ_ID", length = 20)
	public Long getXqId() {
		return this.xqId;
	}

	public void setXqId(Long xqId) {
		this.xqId = xqId;
	}

	@Column(name = "XQKSSJ", length = 60)
	public String getXqkssj() {
		return this.xqkssj;
	}

	public void setXqkssj(String xqkssj) {
		this.xqkssj = xqkssj;
	}

	@Column(name = "XQJSSJ", length = 60)
	public String getXqjssj() {
		return this.xqjssj;
	}

	public void setXqjssj(String xqjssj) {
		this.xqjssj = xqjssj;
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