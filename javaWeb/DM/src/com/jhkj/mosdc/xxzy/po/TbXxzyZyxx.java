package com.jhkj.mosdc.xxzy.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbXxzyZyxx entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_XXZY_ZYXX")
public class TbXxzyZyxx implements java.io.Serializable {

	// Fields

	private Long id;
	private Long xqId;
	private String dm;
	private String mc;
	private String jc;
	private String ywmc;
	private Long zyklId;
	private Long zyfxId;
	private String jlny;
	private Long xzId;
	private Long szyxdm;
	private Long jbzydm;
	private String xxdm;
	private String cjsj;
	private String cjr;
	private String xgsj;
	private String xgr;
	private Long gjzydm;
	private Long sfky;

	// Constructors

	/** default constructor */
	public TbXxzyZyxx() {
	}

	/** minimal constructor */
	public TbXxzyZyxx(Long id, String dm, String mc) {
		this.id = id;
		this.dm = dm;
		this.mc = mc;
	}

	/** full constructor */
	public TbXxzyZyxx(Long id, Long xqId, String dm, String mc, String jc,
			String ywmc, Long zyklId, Long zyfxId, String jlny, Long xzId,
			Long szyxdm, Long jbzydm, String xxdm, String cjsj, String cjr,
			String xgsj, String xgr, Long gjzydm,Long sfky) {
		this.id = id;
		this.xqId = xqId;
		this.dm = dm;
		this.mc = mc;
		this.jc = jc;
		this.ywmc = ywmc;
		this.zyklId = zyklId;
		this.zyfxId = zyfxId;
		this.jlny = jlny;
		this.xzId = xzId;
		this.szyxdm = szyxdm;
		this.jbzydm = jbzydm;
		this.xxdm = xxdm;
		this.cjsj = cjsj;
		this.cjr = cjr;
		this.xgsj = xgsj;
		this.xgr = xgr;
		this.gjzydm = gjzydm;
		this.sfky=sfky;
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

	@Column(name = "DM",  length = 60)
	public String getDm() {
		return this.dm;
	}

	public void setDm(String dm) {
		this.dm = dm;
	}

	@Column(name = "MC", nullable = false, length = 60)
	public String getMc() {
		return this.mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	@Column(name = "JC", length = 20)
	public String getJc() {
		return this.jc;
	}

	public void setJc(String jc) {
		this.jc = jc;
	}

	@Column(name = "YWMC", length = 60)
	public String getYwmc() {
		return this.ywmc;
	}

	public void setYwmc(String ywmc) {
		this.ywmc = ywmc;
	}

	@Column(name = "ZYKL_ID", precision = 16, scale = 0)
	public Long getZyklId() {
		return this.zyklId;
	}

	public void setZyklId(Long zyklId) {
		this.zyklId = zyklId;
	}

	@Column(name = "ZYFX_ID", precision = 16, scale = 0)
	public Long getZyfxId() {
		return this.zyfxId;
	}

	public void setZyfxId(Long zyfxId) {
		this.zyfxId = zyfxId;
	}

	@Column(name = "JLNY", length = 10)
	public String getJlny() {
		return this.jlny;
	}

	public void setJlny(String jlny) {
		this.jlny = jlny;
	}

	@Column(name = "XZ_ID", precision = 16, scale = 0)
	public Long getXzId() {
		return this.xzId;
	}

	public void setXzId(Long xzId) {
		this.xzId = xzId;
	}

	@Column(name = "SZYXDM", precision = 16, scale = 0)
	public Long getSzyxdm() {
		return this.szyxdm;
	}

	public void setSzyxdm(Long szyxdm) {
		this.szyxdm = szyxdm;
	}

	@Column(name = "JBZYDM", precision = 16, scale = 0)
	public Long getJbzydm() {
		return this.jbzydm;
	}

	public void setJbzydm(Long jbzydm) {
		this.jbzydm = jbzydm;
	}

	@Column(name = "XXDM", length = 60)
	public String getXxdm() {
		return this.xxdm;
	}

	public void setXxdm(String xxdm) {
		this.xxdm = xxdm;
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

	@Column(name = "GJZYDM", precision = 16, scale = 0)
	public Long getGjzydm() {
		return this.gjzydm;
	}

	public void setGjzydm(Long gjzydm) {
		this.gjzydm = gjzydm;
	}
	@Column(name = "SFKY", length = 1)
	public Long getSfky() {
		return sfky;
	}

	public void setSfky(Long sfky) {
		this.sfky = sfky;
	}
}