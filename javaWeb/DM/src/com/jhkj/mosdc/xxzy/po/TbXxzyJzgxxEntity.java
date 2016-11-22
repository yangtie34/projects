package com.jhkj.mosdc.xxzy.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_JZGXX")
public class TbXxzyJzgxxEntity {
	private Long id;
	private String mc;
	private Long yxId;
	private Long ksId;
	private Long xbId;
	private Long jzglbId;
	private Long rylbId;
	private Boolean sfky;

	public TbXxzyJzgxxEntity() {
		super();
	}

	public TbXxzyJzgxxEntity(Long id, String mc, Long yxId, Long ksId,
			Long xbId, Long jzglbId, Long rylbId, Boolean sfky) {
		super();
		this.id = id;
		this.mc = mc;
		this.yxId = yxId;
		this.ksId = ksId;
		this.xbId = xbId;
		this.jzglbId = jzglbId;
		this.rylbId = rylbId;
		this.sfky = sfky;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "YX_ID", precision = 16)
	public Long getYxId() {
		return yxId;
	}

	public void setYxId(Long yxId) {
		this.yxId = yxId;
	}

	@Column(name = "KS_ID", precision = 16)
	public Long getKsId() {
		return ksId;
	}

	public void setKsId(Long ksId) {
		this.ksId = ksId;
	}

	@Column(name = "XM", nullable = false)
	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	@Column(name = "XB_ID", precision = 16)
	public Long getXbId() {
		return xbId;
	}

	public void setXbId(Long xbId) {
		this.xbId = xbId;
	}

	@Column(name = "JZGLB_ID", precision = 16)
	public Long getJzglbId() {
		return jzglbId;
	}

	public void setJzglbId(Long jzglbId) {
		this.jzglbId = jzglbId;
	}

	@Column(name = "RYLB_ID", precision = 16)
	public Long getRylbId() {
		return rylbId;
	}

	public void setRylbId(Long rylbId) {
		this.rylbId = rylbId;
	}

	@Column(name = "SFKY", precision = 1, scale = 0)
	public Boolean getSfky() {
		return sfky;
	}

	public void setSfky(Boolean sfky) {
		this.sfky = sfky;
	}

}
