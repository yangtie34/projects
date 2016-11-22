package com.jhkj.mosdc.xxzy.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_XXZY_BJXXB")
public class TbXxzyBjxxbEntity implements java.io.Serializable {
	private Long id;
	private String dm;
	private String mc;
	private Boolean sfky;
	public TbXxzyBjxxbEntity() {
		super();
	}
	public TbXxzyBjxxbEntity(Long id, String dm,String mc, Boolean sfky) {
		super();
		this.id = id;
		this.dm = dm;
		this.mc = mc;
		this.sfky = sfky;
	}
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "BH", length = 20)
	public String getDm() {
		return this.dm;
	}

	public void setDm(String dm) {
		this.dm = dm;
	}
	
	
	@Column(name = "BM", length = 60)
	public String getMc() {
		return this.mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}
	
	@Column(name = "SFKY", precision = 1, scale = 0)
	public Boolean getSfky() {
		return sfky;
	}
	public void setSfky(Boolean sfky) {
		this.sfky = sfky;
	}

}
