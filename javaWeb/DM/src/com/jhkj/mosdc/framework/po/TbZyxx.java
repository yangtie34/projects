package com.jhkj.mosdc.framework.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbZyxx entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZYXX")
public class TbZyxx implements java.io.Serializable {

	// Fields

	private Long id;
	private String dm;
	private String mc;
	private String jc;
	private String ywmc;
	private Long xqId;
	private Long zyklId;
	private Long zyfxId;
	private String jlny;
//	private Long xz;
	private String xz;
	private Long szyxId;
	private String jbzydm;
	private String cjsj;
	private String cjr;
	private String xgsj;
	private String xgr;
	private String gjzydm;
	private Long cjrId;
	private Long xgrId;
	private String sfky;

	// Constructors

	/** default constructor */
	public TbZyxx() {
	}

	/** minimal constructor */
	public TbZyxx(Long id, String dm, String mc) {
		this.id = id;
		this.dm = dm;
		this.mc = mc;
	}

	public TbZyxx(Long id, String dm, String mc, String jc, String ywmc,
			Long xqId, Long zyklId, Long zyfxId, String jlny, String xz,
			Long szyxId, String jbzydm, String cjsj, String cjr, String xgsj,
			String xgr, String gjzydm, Long cjrId, Long xgrId, String sfky) {
		super();
		this.id = id;
		this.dm = dm;
		this.mc = mc;
		this.jc = jc;
		this.ywmc = ywmc;
		this.xqId = xqId;
		this.zyklId = zyklId;
		this.zyfxId = zyfxId;
		this.jlny = jlny;
		this.xz = xz;
		this.szyxId = szyxId;
		this.jbzydm = jbzydm;
		this.cjsj = cjsj;
		this.cjr = cjr;
		this.xgsj = xgsj;
		this.xgr = xgr;
		this.gjzydm = gjzydm;
		this.cjrId = cjrId;
		this.xgrId = xgrId;
		this.sfky = sfky;
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

	@Column(name = "DM", nullable = false, length = 20)
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

	@Column(name = "JC", length = 60)
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

	@Column(name = "XQ_ID", precision = 16, scale = 0)
	public Long getXqId() {
		return xqId;
	}

	public void setXqId(Long xqId) {
		this.xqId = xqId;
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
	@Column(name = "XZ", length = 10)
	public String getXz() {
		return xz;
	}

	public void setXz(String xz) {
		this.xz = xz;
	}

	@Column(name = "SZYX_ID", precision = 16, scale = 0)
	public Long getSzyxId() {
		return this.szyxId;
	}

	public void setSzyxId(Long szyxId) {
		this.szyxId = szyxId;
	}

	@Column(name = "JBZYDM", length = 20)
	public String getJbzydm() {
		return this.jbzydm;
	}

	public void setJbzydm(String jbzydm) {
		this.jbzydm = jbzydm;
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

	@Column(name = "GJZYDM", length = 60)
	public String getGjzydm() {
		return this.gjzydm;
	}

	public void setGjzydm(String gjzydm) {
		this.gjzydm = gjzydm;
	}

	@Column(name = "CJR_ID", precision = 16, scale = 0)
	public Long getCjrId() {
		return this.cjrId;
	}

	public void setCjrId(Long cjrId) {
		this.cjrId = cjrId;
	}

	@Column(name = "XGR_ID", precision = 16, scale = 0)
	public Long getXgrId() {
		return this.xgrId;
	}

	public void setXgrId(Long xgrId) {
		this.xgrId = xgrId;
	}

	@Column(name = "SFKY", length = 2)
	public String getSfky() {
		return this.sfky;
	}

	public void setSfky(String sfky) {
		this.sfky = sfky;
	}

}