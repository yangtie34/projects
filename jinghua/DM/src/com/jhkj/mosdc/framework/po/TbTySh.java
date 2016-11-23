package com.jhkj.mosdc.framework.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbDkSp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_TY_SH")
public class TbTySh implements java.io.Serializable {

	// Fields

	private Long id;
	private Long shxxId;
	private String lsmk;
	private Long hjId;
	private String shr;
	private String shyj;
	private Long shztId;
	private String shsj;
	private String cjr;
	private String cjsj;
	private String xgr;
	private String xgsj;
	private Long shsx;

	// Constructors

	/** default constructor */
	public TbTySh() {
	}

	/** minimal constructor */
	public TbTySh(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TbTySh(Long id, Long shxxId, Long hjId, String shr, String shyj,
			Long shztId, Long zzshztId, String shsj, String cjr, String cjsj,
			String xgr, String xgsj,Long shsh) {
		this.id = id;
		this.shxxId = shxxId;
		this.hjId = hjId;
		this.shr = shr;
		this.shyj = shyj;
		this.shztId = shztId;
		this.shsj = shsj;
		this.cjr = cjr;
		this.cjsj = cjsj;
		this.xgr = xgr;
		this.xgsj = xgsj;
		this.shsx = shsx;
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

	@Column(name = "SHXX_ID", precision = 16, scale = 0)
	public Long getShxxId() {
		return this.shxxId;
	}

	public void setShxxId(Long shxxId) {
		this.shxxId = shxxId;
	}
	
	@Column(name = "LSMK", length = 20)
	public String getLsmk() {
		return this.lsmk;
	}

	public void setLsmk(String lsmk) {
		this.lsmk = lsmk;
	}
	
	@Column(name = "HJ_ID", precision = 16, scale = 0)
	public Long getHjId() {
		return this.hjId;
	}

	public void setHjId(Long hjId) {
		this.hjId = hjId;
	}

	@Column(name = "SHR", length = 20)
	public String getShr() {
		return this.shr;
	}

	public void setShr(String shr) {
		this.shr = shr;
	}

	@Column(name = "SHYJ", length = 300)
	public String getShyj() {
		return this.shyj;
	}

	public void setShyj(String shyj) {
		this.shyj = shyj;
	}

	@Column(name = "SHZT_ID", precision = 16, scale = 0)
	public Long getShztId() {
		return this.shztId;
	}

	public void setShztId(Long shztId) {
		this.shztId = shztId;
	}

	@Column(name = "SHSJ", length = 60)
	public String getShsj() {
		return this.shsj;
	}

	public void setShsj(String shsj) {
		this.shsj = shsj;
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

	public Long getShsx() {
		return shsx;
	}
	@Column(name = "SHSX", precision = 16, scale = 0)
	public void setShsx(Long shsx) {
		this.shsx = shsx;
	}
	

}