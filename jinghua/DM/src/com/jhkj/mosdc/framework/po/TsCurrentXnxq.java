package com.jhkj.mosdc.framework.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TS_CURRENT_XNXQ")
public class TsCurrentXnxq {

	private Long id;
	private Long xnId;
	private Long xqId;
	private String xnMc;
	private String xqMc;	
	private String cjr;
	private String cjsj;
	private String xgr;
	private String xgsj;

	public TsCurrentXnxq() {
		super();
	}

	public TsCurrentXnxq(Long id) {
		super();
		this.id = id;
	}

	public TsCurrentXnxq(Long id, Long xnId, Long xqId, String xnMc,
			String xqMc, String cjr, String cjsj, String xgr, String xgsj) {
		super();
		this.id = id;
		this.xnId = xnId;
		this.xqId = xqId;
		this.xnMc = xnMc;
		this.xqMc = xqMc;
		this.cjr = cjr;
		this.cjsj = cjsj;
		this.xgr = xgr;
		this.xgsj = xgsj;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "XN_ID", precision = 16, scale = 0)
	public Long getXnId() {
		return xnId;
	}

	public void setXnId(Long xnId) {
		this.xnId = xnId;
	}

	@Column(name = "XQ_ID", precision = 16, scale = 0)
	public Long getXqId() {
		return xqId;
	}

	public void setXqId(Long xqId) {
		this.xqId = xqId;
	}
	@Column(name = "XNMC", length = 60)
	public String getXnMc() {
		return xnMc;
	}

	public void setXnMc(String xnMc) {
		this.xnMc = xnMc;
	}
	@Column(name = "XQMC", length = 60)
	public String getXqMc() {
		return xqMc;
	}

	public void setXqMc(String xqMc) {
		this.xqMc = xqMc;
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
