package com.jhkj.mosdc.xggl.xjgl.po;

// Generated 2013-11-29 17:53:32 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbXjdaZctjsz generated by hbm2java
 */
@Entity
@Table(name = "TB_XJDA_WZDTJ")
public class TbXjdaWzdtj implements java.io.Serializable {

	private Long id;
	private Long xsId;
	private Integer xjdawzd;
	private String fields;
	private String cjr;
	private String cjsj;
	private String xgr;
	private String xgsj;
	
	public TbXjdaWzdtj(){
		
	}
	public TbXjdaWzdtj(Long id) {
		this.id = id;
	}

	public TbXjdaWzdtj(Long id, Long xsId, Integer xjdawzd, String fields,
			String cjr, String cjsj, String xgr, String xgsj) {
		this.id = id;
		this.xsId = xsId;
		this.xjdawzd = xjdawzd;
		this.fields = fields;
		this.cjr = cjr;
		this.cjsj = cjsj;
		this.xgr = xgr;
		this.xgsj = xgsj;
	}

	@Id
	@Column(name = "ID", precision = 16, scale = 0)
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

	
	@Column(name = "FIELDS", length = 500)
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}

	@Column(name = "XJDAWZD", precision = 3, scale = 0)
	public Integer getXjdawzd() {
		return xjdawzd;
	}
	public void setXjdawzd(Integer xjdawzd) {
		this.xjdawzd = xjdawzd;
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

}
