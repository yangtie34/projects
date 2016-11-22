package com.jhkj.mosdc.xxzy.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbXxzyJszjxxb entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_XXZY_JSZJXXB")
public class TbXxzyJszjxxb implements java.io.Serializable {

	// Fields

	private Long id;
	private Long xnId;
	private Long xqId;
	private String zc;
	private Long xqjId;
	private Long dszId;
	private Long jcId;
	private Long jsId;
	private Long jyrId;
	private String djrq;
	private String lxdh;
	private String cjsj;
	private String cjr;
	private String xgsj;
	private String xgr;
	private String jyyt;

	// Constructors

	/** default constructor */
	public TbXxzyJszjxxb() {
	}

	/** minimal constructor */
	public TbXxzyJszjxxb(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TbXxzyJszjxxb(Long id, Long xnId, Long xqId, String zc, Long xqjId,
			Long dszId, Long jcId, Long jsId, Long jyrId, String djrq,
			String lxdh, String cjsj, String cjr, String xgsj, String xgr,
			String jyyt) {
		this.id = id;
		this.xnId = xnId;
		this.xqId = xqId;
		this.zc = zc;
		this.xqjId = xqjId;
		this.dszId = dszId;
		this.jcId = jcId;
		this.jsId = jsId;
		this.jyrId = jyrId;
		this.djrq = djrq;
		this.lxdh = lxdh;
		this.cjsj = cjsj;
		this.cjr = cjr;
		this.xgsj = xgsj;
		this.xgr = xgr;
		this.jyyt = jyyt;
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

	@Column(name = "ZC", length = 20)
	public String getZc() {
		return this.zc;
	}

	public void setZc(String zc) {
		this.zc = zc;
	}

	@Column(name = "XQJ_ID", precision = 16, scale = 0)
	public Long getXqjId() {
		return this.xqjId;
	}

	public void setXqjId(Long xqjId) {
		this.xqjId = xqjId;
	}

	@Column(name = "DSZ_ID", precision = 16, scale = 0)
	public Long getDszId() {
		return this.dszId;
	}

	public void setDszId(Long dszId) {
		this.dszId = dszId;
	}

	@Column(name = "JC_ID", precision = 16, scale = 0)
	public Long getJcId() {
		return this.jcId;
	}

	public void setJcId(Long jcId) {
		this.jcId = jcId;
	}

	@Column(name = "JS_ID", precision = 16, scale = 0)
	public Long getJsId() {
		return this.jsId;
	}

	public void setJsId(Long jsId) {
		this.jsId = jsId;
	}

	@Column(name = "JYR_ID", precision = 16, scale = 0)
	public Long getJyrId() {
		return this.jyrId;
	}

	public void setJyrId(Long jyrId) {
		this.jyrId = jyrId;
	}

	@Column(name = "DJRQ", length = 60)
	public String getDjrq() {
		return this.djrq;
	}

	public void setDjrq(String djrq) {
		this.djrq = djrq;
	}

	@Column(name = "LXDH", length = 20)
	public String getLxdh() {
		return this.lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
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

	@Column(name = "JYYT", length = 200)
	public String getJyyt() {
		return this.jyyt;
	}

	public void setJyyt(String jyyt) {
		this.jyyt = jyyt;
	}

}