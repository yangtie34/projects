package com.jhkj.mosdc.pano.po;

// Generated 2013-10-30 15:26:24 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbJxXssxkcb generated by hbm2java
 */
@Entity
@Table(name = "TB_JX_XSSXKCB", schema = "MOSDC_PANO")
public class TbJxXssxkcb implements java.io.Serializable {

	private long id;
	private Long xsId;
	private Long xnId;
	private Long xqId;
	private Long kmId;
	private Long skfsId;
	private String sjtbsj;
	private String tbfs;

	public TbJxXssxkcb() {
	}

	public TbJxXssxkcb(long id) {
		this.id = id;
	}

	public TbJxXssxkcb(long id, Long xsId, Long xnId, Long xqId, Long kmId,
			Long skfsId, String sjtbsj, String tbfs) {
		this.id = id;
		this.xsId = xsId;
		this.xnId = xnId;
		this.xqId = xqId;
		this.kmId = kmId;
		this.skfsId = skfsId;
		this.sjtbsj = sjtbsj;
		this.tbfs = tbfs;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "XS_ID", precision = 16, scale = 0)
	public Long getXsId() {
		return this.xsId;
	}

	public void setXsId(Long xsId) {
		this.xsId = xsId;
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

	@Column(name = "KM_ID", precision = 16, scale = 0)
	public Long getKmId() {
		return this.kmId;
	}

	public void setKmId(Long kmId) {
		this.kmId = kmId;
	}

	@Column(name = "SKFS_ID", precision = 16, scale = 0)
	public Long getSkfsId() {
		return this.skfsId;
	}

	public void setSkfsId(Long skfsId) {
		this.skfsId = skfsId;
	}

	@Column(name = "SJTBSJ", length = 30)
	public String getSjtbsj() {
		return this.sjtbsj;
	}

	public void setSjtbsj(String sjtbsj) {
		this.sjtbsj = sjtbsj;
	}

	@Column(name = "TBFS", length = 30)
	public String getTbfs() {
		return this.tbfs;
	}

	public void setTbfs(String tbfs) {
		this.tbfs = tbfs;
	}

}
