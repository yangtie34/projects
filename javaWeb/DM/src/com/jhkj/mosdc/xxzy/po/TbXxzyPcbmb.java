package com.jhkj.mosdc.xxzy.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbXxzyPcbmb entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_XXZY_PCBMB")
public class TbXxzyPcbmb implements java.io.Serializable {

	// Fields

	private Long id;
	private String rxqsrq;
	private String rxjzrq;
	private String mc;
	private Boolean sfky;
	private Long xnId;
	private Long xqId;

	// Constructors

	/** default constructor */
	public TbXxzyPcbmb() {
	}

	/** minimal constructor */
	public TbXxzyPcbmb(Long id) {
		this.id = id;
	}

	

	public TbXxzyPcbmb(Long id, String rxqsrq, String rxjzrq, String mc,
			Boolean sfky, Long xnId, Long xqId) {
		super();
		this.id = id;
		this.rxqsrq = rxqsrq;
		this.rxjzrq = rxjzrq;
		this.mc = mc;
		this.sfky = sfky;
		this.xnId = xnId;
		this.xqId = xqId;
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

	@Column(name = "RXQSRQ", length = 60)
	public String getRxqsrq() {
		return this.rxqsrq;
	}

	public void setRxqsrq(String rxqsrq) {
		this.rxqsrq = rxqsrq;
	}

	@Column(name = "RXJZRQ", length = 60)
	public String getRxjzrq() {
		return this.rxjzrq;
	}

	public void setRxjzrq(String rxjzrq) {
		this.rxjzrq = rxjzrq;
	}

	@Column(name = "MC", length = 20)
	public String getMc() {
		return this.mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	@Column(name = "SFKY", precision = 1, scale = 0)
	public Boolean getSfky() {
		return this.sfky;
	}

	public void setSfky(Boolean sfky) {
		this.sfky = sfky;
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

}