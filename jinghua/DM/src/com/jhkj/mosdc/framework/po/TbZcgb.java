package com.jhkj.mosdc.framework.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbZcgb entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZCGB")
public class TbZcgb implements java.io.Serializable {
	/**
	 * ID
	 */
	private Long id;

	public TbZcgb() {
		super();
	}

	public TbZcgb(Long id, String xm) {
		super();
		this.id = id;
		this.xm = xm;
	}

	/**
	 * 姓名
	 */
	private String xm;

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "XM", nullable = false)
	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

}
