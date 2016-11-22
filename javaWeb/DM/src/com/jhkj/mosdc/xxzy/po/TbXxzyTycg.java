package com.jhkj.mosdc.xxzy.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbXxzyTycg entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_XXZY_TYCG")
public class TbXxzyTycg implements java.io.Serializable {

	// Fields

	private Long id;
	private Long xqId;
	private Long gclxId;
	private String cgmc;
	private Long rs;
	private Short rnbjs;
	private String mj;
	private String tp;
	private Boolean sfky;
	private String cgsj;
	private String cjsj;
	private String cjr;
	private String xgsj;
	private String xgr;

	// Constructors

	/** default constructor */
	public TbXxzyTycg() {
	}

	/** minimal constructor */
	public TbXxzyTycg(Long id) {
		this.id = id;
	}

	

	public TbXxzyTycg(Long id, Long xqId, Long gclxId, String cgmc, Long rs,
			Short rnbjs, String mj, String tp, Boolean sfky, String cgsj,
			String cjsj, String cjr, String xgsj, String xgr) {
		super();
		this.id = id;
		this.xqId = xqId;
		this.gclxId = gclxId;
		this.cgmc = cgmc;
		this.rs = rs;
		this.rnbjs = rnbjs;
		this.mj = mj;
		this.tp = tp;
		this.sfky = sfky;
		this.cgsj = cgsj;
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

	@Column(name = "XQ_ID", precision = 16, scale = 0)
	public Long getXqId() {
		return this.xqId;
	}

	public void setXqId(Long xqId) {
		this.xqId = xqId;
	}

	@Column(name = "GCLX_ID", precision = 16, scale = 0)
	public Long getGclxId() {
		return this.gclxId;
	}

	public void setGclxId(Long gclxId) {
		this.gclxId = gclxId;
	}

	@Column(name = "CGMC", length = 60)
	public String getCgmc() {
		return this.cgmc;
	}

	public void setCgmc(String cgmc) {
		this.cgmc = cgmc;
	}

	@Column(name = "RS", precision = 16, scale = 0)
	public Long getRs() {
		return this.rs;
	}

	public void setRs(Long rs) {
		this.rs = rs;
	}

	@Column(name = "MJ", length = 20)
	public String getMj() {
		return this.mj;
	}

	public void setMj(String mj) {
		this.mj = mj;
	}

	@Column(name = "TP", length = 60)
	public String getTp() {
		return this.tp;
	}

	public void setTp(String tp) {
		this.tp = tp;
	}

	@Column(name = "SFKY", precision = 1, scale = 0)
	public Boolean getSfky() {
		return this.sfky;
	}

	public void setSfky(Boolean sfky) {
		this.sfky = sfky;
	}

	@Column(name = "CGSJ", length = 60)
	public String getCgsj() {
		return this.cgsj;
	}

	public void setCgsj(String cgsj) {
		this.cgsj = cgsj;
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