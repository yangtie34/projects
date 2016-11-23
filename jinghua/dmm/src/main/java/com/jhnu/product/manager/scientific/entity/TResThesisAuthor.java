package com.jhnu.product.manager.scientific.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TResThesisAuthor entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_RES_THESIS_AUTHOR", schema = "DM_MOSDC_HTU")
public class TResThesisAuthor implements java.io.Serializable {

	// Fields
	@CloumnAs(name = "ID")
	private String id;
	@CloumnAs(name = "论文ID")
	private String thesisId;
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
	private String thesisAuthorRoleCode;

	// Constructors

	/** default constructor */
	public TResThesisAuthor() {
	}

	/** minimal constructor */
	public TResThesisAuthor(String id, String thesisId) {
		this.id = id;
		this.thesisId = thesisId;
	}

	/** full constructor */
	public TResThesisAuthor(String id, String thesisId, String peopleId,
			Short order, String peopleIdentityCode, String teaNo,
			String peopleName, String roleCode) {
		this.id = id;
		this.thesisId = thesisId;
		this.peopleId = peopleId;
		this.order = order;
		this.peopleIdentityCode = peopleIdentityCode;
		this.teaNo = teaNo;
		this.peopleName = peopleName;
		this.thesisAuthorRoleCode = roleCode;
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

	@Column(name = "THESIS_ID", nullable = false, length = 20)
	public String getThesisId() {
		return this.thesisId;
	}

	public void setThesisId(String thesisId) {
		this.thesisId = thesisId;
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

	@Column(name = "THESIS_AUTHOR_ROLE_CODE", length = 10)
	public String getThesisAuthorRoleCode() {
		return this.thesisAuthorRoleCode;
	}

	public void setThesisAuthorRoleCode(String roleCode) {
		this.thesisAuthorRoleCode = roleCode;
	}

}