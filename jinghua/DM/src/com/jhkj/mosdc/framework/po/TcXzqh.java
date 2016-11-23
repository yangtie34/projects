package com.jhkj.mosdc.framework.po;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TcXzqh entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TC_XZQH")
public class TcXzqh implements java.io.Serializable {

	// Fields

	private Long id;
	private String dm;
	private String mc;
	private Long fjdId;
	private Integer cc;
	private String sfyzjd;
	private String sfky;
	private String bz;
	private String cclx;
	private Long djcs; //点击次
	private String jp;
	private String qp;

	// Constructors

	/** default constructor */
	public TcXzqh() {
	}

	/** minimal constructor */
	public TcXzqh(Long id, String dm, String mc) {
		this.id = id;
		this.dm = dm;
		this.mc = mc;
	}

	/** full constructor */
	public TcXzqh(Long id, String dm, String mc, Long fjdId, Integer cc,
			String sfyzjd, String sfky, String bz, String cclx,String jp, String qp) {
		this.id = id;
		this.dm = dm;
		this.mc = mc;
		this.fjdId = fjdId;
		this.cc = cc;
		this.sfyzjd = sfyzjd;
		this.sfky = sfky;
		this.bz = bz;
		this.cclx = cclx;
		this.jp = jp;
		this.qp = qp;
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

	@Column(name = "DM", nullable = false, length = 60)
	public String getDm() {
		return this.dm;
	}

	public void setDm(String dm) {
		this.dm = dm;
	}

	@Column(name = "MC", nullable = false, length = 200)
	public String getMc() {
		return this.mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	@Column(name = "FJD_ID", precision = 16, scale = 0)
	public Long getFjdId() {
		return this.fjdId;
	}

	public void setFjdId(Long fjdId) {
		this.fjdId = fjdId;
	}

	@Column(name = "CC", precision = 4, scale = 0)
	public Integer getCc() {
		return this.cc;
	}

	public void setCc(Integer cc) {
		this.cc = cc;
	}

	@Column(name = "SFYZJD", length = 1)
	public String getSfyzjd() {
		return this.sfyzjd;
	}

	public void setSfyzjd(String sfyzjd) {
		this.sfyzjd = sfyzjd;
	}

	@Column(name = "SFKY", length = 1)
	public String getSfky() {
		return this.sfky;
	}

	public void setSfky(String sfky) {
		this.sfky = sfky;
	}

	@Column(name = "BZ", length = 300)
	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	@Column(name = "CCLX", length = 20)
	public String getCclx() {
		return this.cclx;
	}

	public void setCclx(String cclx) {
		this.cclx = cclx;
	}
	
	@Column(name = "DJCS", precision = 10, scale = 0)
	public Long getDjcs() {
		return this.djcs;
	}

	public void setDjcs(Long djcs) {
		this.djcs = djcs;
	}
	@Column(name = "JP", length=20)
	public String getJp() {
		return jp;
	}

	public void setJp(String jp) {
		this.jp = jp;
	}
	@Column(name = "QP", length=100)
	public String getQp() {
		return qp;
	}

	public void setQp(String qp) {
		this.qp = qp;
	}
	
	
}