package com.jhkj.mosdc.xggl.xjgl.po;

// Generated 2013-11-5 13:49:26 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbXjdaXsjcxx generated by hbm2java
 */
@Entity
@Table(name = "TB_XJDA_XSJCXX")
public class TbXjdaXsjcxx implements java.io.Serializable {

	private Long id;
	private Long xsId;
	private String xqah;
	private String zytc;
	private Long jtjjId;
	private Long tsznId;
	private String sfgc;
	private String sfdq;
	private String sfdb;
	private String cjr;
	private String cjsj;
	private String xgr;
	private String xgsj;
	private Long srlyId;
	private String bz;
	
	public TbXjdaXsjcxx() {
	}

	public TbXjdaXsjcxx(Long id) {
		this.id = id;
	}

	public TbXjdaXsjcxx(Long id, Long xsId, String xqah, String zytc,
			Long jtjjId, Long tsznId, String sfgc, String sfdq,String sfdb,String cjr, String cjsj,
			String xgr, String xgsj) {
		this.id = id;
		this.xsId = xsId;
		this.xqah = xqah;
		this.zytc = zytc;
		this.jtjjId = jtjjId;
		this.tsznId = tsznId;
		this.sfgc = sfgc;
		this.sfdq = sfdq;
		this.sfdb = sfdb;
		this.cjr = cjr;
		this.cjsj = cjsj;
		this.xgr = xgr;
		this.xgsj = xgsj;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "XS_ID", precision = 16, scale = 0)
	public Long getXsId() {
		return this.xsId;
	}

	public void setXsId(Long xsId) {
		this.xsId = xsId;
	}

	@Column(name = "XQAH", length = 100)
	public String getXqah() {
		return this.xqah;
	}

	public void setXqah(String xqah) {
		this.xqah = xqah;
	}

	@Column(name = "ZYTC", length = 100)
	public String getZytc() {
		return this.zytc;
	}

	public void setZytc(String zytc) {
		this.zytc = zytc;
	}

	@Column(name = "JTJJ_ID", precision = 16, scale = 0)
	public Long getJtjjId() {
		return this.jtjjId;
	}

	public void setJtjjId(Long jtjjId) {
		this.jtjjId = jtjjId;
	}

	@Column(name = "TSZN_ID", precision = 16, scale = 0)
	public Long getTsznId() {
		return this.tsznId;
	}

	public void setTsznId(Long tsznId) {
		this.tsznId = tsznId;
	}

	@Column(name = "SFGC", precision = 1, scale = 0)
	public String getSfgc() {
		return this.sfgc;
	}

	public void setSfgc(String sfgc) {
		this.sfgc = sfgc;
	}

	@Column(name = "SFDQ", precision = 1, scale = 0)
	public String getSfdq() {
		return this.sfdq;
	}

	public void setSfdq(String sfdq) {
		this.sfdq = sfdq;
	}
	@Column(name = "SFDB", precision = 1, scale = 0)
	public String getSfdb() {
		return this.sfdb;
	}

	public void setSfdb(String sfdb) {
		this.sfdb = sfdb;
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
	@Column(name = "SRLY_ID", precision = 16, scale = 0)
	public Long getSrlyId() {
		return srlyId;
	}

	public void setSrlyId(Long srlyId) {
		this.srlyId = srlyId;
	}
	@Column(name = "BZ", length = 500)
	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}
	
	
}
