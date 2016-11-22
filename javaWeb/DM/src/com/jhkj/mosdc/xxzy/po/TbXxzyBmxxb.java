package com.jhkj.mosdc.xxzy.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbXxzyBmxxb entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_XXZY_BMXXB")
public class TbXxzyBmxxb implements java.io.Serializable {

	// Fields

	private Long id;
	private String mc;
	private String bmdm;
	private Long fzrId;
	private String dh;
	private String cz;
	private String yxdz;
	private String bz;
	private Long sfky;

	// Constructors

	/** default constructor */
	public TbXxzyBmxxb() {
	}

	/** minimal constructor */
	public TbXxzyBmxxb(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TbXxzyBmxxb(Long id, String mc, String bmdm, Long fzrId, String dh,
			String cz, String yxdz, String bz,Long sfky) {
		this.id = id;
		this.mc = mc;
		this.bmdm = bmdm;
		this.fzrId = fzrId;
		this.dh = dh;
		this.cz = cz;
		this.yxdz = yxdz;
		this.bz = bz;
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

	@Column(name = "MC", length = 60)
	public String getMc() {
		return this.mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	@Column(name = "BMDM", length = 60)
	public String getBmdm() {
		return this.bmdm;
	}

	public void setBmdm(String bmdm) {
		this.bmdm = bmdm;
	}

	@Column(name = "FZR_ID", precision = 16, scale = 0)
	public Long getFzrId() {
		return this.fzrId;
	}

	public void setFzrId(Long fzrId) {
		this.fzrId = fzrId;
	}

	@Column(name = "DH", length = 60)
	public String getDh() {
		return this.dh;
	}

	public void setDh(String dh) {
		this.dh = dh;
	}

	@Column(name = "CZ", length = 60)
	public String getCz() {
		return this.cz;
	}

	public void setCz(String cz) {
		this.cz = cz;
	}

	@Column(name = "YXDZ", length = 60)
	public String getYxdz() {
		return this.yxdz;
	}

	public void setYxdz(String yxdz) {
		this.yxdz = yxdz;
	}

	@Column(name = "BZ", length = 200)
	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}
	@Column(name = "SFKY", length = 1)
	public Long getSfky() {
		return sfky;
	}

	public void setSfky(Long sfky) {
		this.sfky = sfky;
	}
}