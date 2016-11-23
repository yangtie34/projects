package com.jhkj.mosdc.permission.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色tab权限
 */
@Entity
@Table(name = "TS_JS_TABS")
public class TsJsTabs implements java.io.Serializable {

	private static final long serialVersionUID = -5627486624038282727L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * tab标题中文名称
	 */

	private String mc;

	/**
	 * tab英文名称
	 */

	private String tabmc;
	
	/*
	 * 角色ID
	 */
	private Long jsId;
	
	/*
	 * 人员类别
	 */
	private Long rylb;
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "MC", nullable = false, length = 60)
	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	@Column(name = "TABMC", nullable = false, length = 60)
	public String getTabmc() {
		return tabmc;
	}

	public void setTabmc(String tabmc) {
		this.tabmc = tabmc;
	}
	
	@Column(name = "JS_ID", nullable = false, precision = 16, scale = 0)
	public Long getJsId() {
		return jsId;
	}

	public void setJsId(Long jsId) {
		this.jsId = jsId;
	}
	
	@Column(name = "RYLB", precision = 1, scale = 0)
	public Long getRylb() {
		return rylb;
	}

	public void setRylb(Long rylb) {
		this.rylb = rylb;
	}
}