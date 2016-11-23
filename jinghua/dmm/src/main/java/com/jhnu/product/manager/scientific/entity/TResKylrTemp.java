package com.jhnu.product.manager.scientific.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TResKylrTemp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_RES_KYLR_TEMP", schema = "DM_MOSDC_HTU")
public class TResKylrTemp implements java.io.Serializable {

	// Fields
	@CloumnAs(name = "ID")
	private String id;
	@CloumnAs(name = "科研id")
	private String kyId;
	@CloumnAs(name = "科研录入人员id")
	private String peopleId;
	@CloumnAs(name = "科研录入人员部门id")
	private String deptId;
	@CloumnAs(name = "科研类别代码")
	private String kylbCode;
	@CloumnAs(name = "审核状态")
	private String flagCode;

	// Constructors

	/** default constructor */
	public TResKylrTemp() {
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 20)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "KY_ID", nullable = false, length = 20)
	public String getKyId() {
		return this.kyId;
	}

	public void setKyId(String kyId) {
		this.kyId = kyId;
	}
	@Column(name = "PEOPLE_ID", nullable = false, length = 20)
	public String getPeopleId() {
		return this.peopleId;
	}

	public void setPeopleId(String kylb) {
		this.peopleId = kylb;
	}
	@Column(name = "DEPT_ID", nullable = false, length = 20)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String kylb) {
		this.deptId = kylb;
	}
	@Column(name = "KYLB_CODE", nullable = false, length = 20)
	public String getKylbCode() {
		return this.kylbCode;
	}

	public void setKylbCode(String kylb) {
		this.kylbCode = kylb;
	}

	@Column(name = "FLAG_CODE", nullable = false, precision = 3, scale = 0)
	public String getFlagCode() {
		return this.flagCode;
	}

	public void setFlagCode(String flag) {
		this.flagCode = flag;
	}

}