package com.jhnu.product.manager.scientific.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TResOutcomeAppraisalAuth entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_RES_OUTCOME_APPRAISAL_AUTH", schema = "DM_MOSDC_HTU")
public class TResOutcomeAppraisalAuth implements java.io.Serializable {

	// Fields

	@CloumnAs(name = "ID")
	private String id;
	@CloumnAs(name = "鉴定成果ID")
	private String appraisalId;
	@CloumnAs(name = "作者ID")
	private String peopleId;
	@CloumnAs(name = "排名")
	private Short order;
	@CloumnAs(name = "作者身份")
	private String peopleIdentityCode;
	@CloumnAs(name = "导师职工号")
	private String teaNo;
	@CloumnAs(name = "作者名称")
	private String peopleName;
	@CloumnAs(name = "角色名称代码")
	private String appraisalAuthRoleCode;

	// Constructors

	/** default constructor */
	public TResOutcomeAppraisalAuth() {
	}

	/** minimal constructor */
	public TResOutcomeAppraisalAuth(String id, String appraisalId) {
		this.id = id;
		this.appraisalId = appraisalId;
	}

	/** full constructor */
	public TResOutcomeAppraisalAuth(String id, String appraisalId,
			String peopleId, Short order, String peopleIdentityCode,
			String teaNo,  String peopleName,
			String roleCode) {
		this.id = id;
		this.appraisalId = appraisalId;
		this.peopleId = peopleId;
		this.order = order;
		this.peopleIdentityCode = peopleIdentityCode;
		this.teaNo = teaNo;
		this.peopleName = peopleName;
		this.appraisalAuthRoleCode = roleCode;
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

	@Column(name = "APPRAISAL_ID", nullable = false, length = 20)
	public String getAppraisalId() {
		return this.appraisalId;
	}

	public void setAppraisalId(String appraisalId) {
		this.appraisalId = appraisalId;
	}

	@Column(name = "PEOPLE_ID", length = 20)
	public String getPeopleId() {
		return this.peopleId;
	}

	public void setPeopleId(String peopleId) {
		this.peopleId = peopleId;
	}

	@Column(name = "ORDER_", precision = 3, scale = 0)
	public Short getOrder() {
		return this.order;
	}

	public void setOrder(Short order) {
		this.order = order;
	}

	@Column(name = "PEOPLE_IDENTITY_CODE", length = 10)
	public String getPeopleIdentityCode() {
		return this.peopleIdentityCode;
	}

	public void setPeopleIdentityCode(String peopleIdentityCode) {
		this.peopleIdentityCode = peopleIdentityCode;
	}

	@Column(name = "TEA_NO", length = 20)
	public String getTeaNo() {
		return this.teaNo;
	}

	public void setTeaNo(String teaNo) {
		this.teaNo = teaNo;
	}

	@Column(name = "PEOPLE_NAME", length = 60)
	public String getPeopleName() {
		return this.peopleName;
	}

	public void setPeopleName(String peopleName) {
		this.peopleName = peopleName;
	}

	@Column(name = "APPRAISAL_AUTH_ROLE_CODE", length = 10)
	public String getAppraisalAuthRoleCode() {
		return this.appraisalAuthRoleCode;
	}

	public void setAppraisalAuthRoleCode(String roleCode) {
		this.appraisalAuthRoleCode = roleCode;
	}

}