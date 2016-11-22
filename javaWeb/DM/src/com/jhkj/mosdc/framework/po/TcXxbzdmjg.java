package com.jhkj.mosdc.framework.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TcXxbzdmjg entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TC_XXBZDMJG")
public class TcXxbzdmjg implements java.io.Serializable {

	// Fields

	/**
	*学校代码
	*/ 
	 private String xxdm;  
	/**
	*是否系统级
	*/ 
	 private String sfxtj;  
	/**
	*名称
	*/ 
	 private String mc;  
	/**
	*标准代码名称
	*/ 
	 private String bzdmmc;  
	/**
	*代码
	*/ 
	 private String dm;  
	/**
	*ID
	*/ 
	 private Long id;  
	/**
	*是否可用
	*/ 
	 private String sfky;  
	/**
	*创建时间
	*/ 
	 private String cjsj;  
	/**
	*修改人
	*/ 
	 private String xgr;  
	/**
	*标准代码
	*/ 
	 private String bzdm;  
	/**
	*创建人
	*/ 
	 private String cjr;  
	/**
	*修改时间
	*/ 
	 private String xgsj;  

	// Constructors

	/** default constructor */
	public TcXxbzdmjg() {
	}

	/** minimal constructor */
	public TcXxbzdmjg(Long id, String bzdm, String dm, String mc) {
		this.id = id;
		this.bzdm = bzdm;
		this.dm = dm;
		this.mc = mc;
	}

	/** full constructor */
	public TcXxbzdmjg(Long id, String bzdm, String bzdmmc, String dm,
			String mc, String sfky, String sfxtj, String xxdm, String cjsj,
			String cjr, String xgsj, String xgr) {
		this.id = id;
		this.bzdm = bzdm;
		this.bzdmmc = bzdmmc;
		this.dm = dm;
		this.mc = mc;
		this.sfky = sfky;
		this.sfxtj = sfxtj;
		this.xxdm = xxdm;
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

	@Column(name = "BZDM", nullable = false, length = 60)
	public String getBzdm() {
		return this.bzdm;
	}

	public void setBzdm(String bzdm) {
		this.bzdm = bzdm;
	}

	@Column(name = "BZDMMC", length = 60)
	public String getBzdmmc() {
		return this.bzdmmc;
	}

	public void setBzdmmc(String bzdmmc) {
		this.bzdmmc = bzdmmc;
	}

	@Column(name = "DM", nullable = false, length = 60)
	public String getDm() {
		return this.dm;
	}

	public void setDm(String dm) {
		this.dm = dm;
	}

	@Column(name = "MC", nullable = false, length = 100)
	public String getMc() {
		return this.mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	@Column(name = "SFKY", length = 1)
	public String getSfky() {
		return this.sfky;
	}

	public void setSfky(String sfky) {
		this.sfky = sfky;
	}

	@Column(name = "SFXTJ", length = 300)
	public String getSfxtj() {
		return sfxtj;
	}
	
	public void setSfxtj(String sfxtj) {
		this.sfxtj = sfxtj;
	}
	

	@Column(name = "XXDM", length = 12)
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

}