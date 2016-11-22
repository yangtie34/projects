package com.jhnu.product.manager.scientific.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TResThesisAward entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_RES_THESIS_AWARD", schema = "DM_MOSDC_HTU")
public class TResThesisAward implements java.io.Serializable {

	// Fields
	@CloumnAs(name = "ID")
	private String id;
	@CloumnAs(name = "论文编号")
	private String thesisId;
	@CloumnAs(name = "获奖名称")
	private String awardName;
	@CloumnAs(name = "获奖级别代码")
	private String awardLevelCode;
	@CloumnAs(name = "获奖等级代码")
	private String awardRankCode;
	@CloumnAs(name = "授奖单位")
	private String awardDept;
	@CloumnAs(name = "授奖日期")
	private String awardTime;
	@CloumnAs(name = "证书编号")
	private String certificateNo;
	@CloumnAs(name = "备注")
	private String remark;

	// Constructors

	/** default constructor */
	public TResThesisAward() {
	}

	/** minimal constructor */
	public TResThesisAward(String id, String thesisId) {
		this.id = id;
		this.thesisId = thesisId;
	}

	/** full constructor */
	public TResThesisAward(String id, String thesisId, String awardName,
			String awardLevelCode, String awardRankCode, String awardDept,
			String awardTime, String certificateNo, String remark) {
		this.id = id;
		this.thesisId = thesisId;
		this.awardName = awardName;
		this.awardLevelCode = awardLevelCode;
		this.awardRankCode = awardRankCode;
		this.awardDept = awardDept;
		this.awardTime = awardTime;
		this.certificateNo = certificateNo;
		this.remark = remark;
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

	@Column(name = "AWARD_NAME", length = 60)
	public String getAwardName() {
		return this.awardName;
	}

	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}

	@Column(name = "AWARD_LEVEL_CODE", length = 10)
	public String getAwardLevelCode() {
		return this.awardLevelCode;
	}

	public void setAwardLevelCode(String awardLevelCode) {
		this.awardLevelCode = awardLevelCode;
	}

	@Column(name = "AWARD_RANK_CODE", length = 10)
	public String getAwardRankCode() {
		return this.awardRankCode;
	}

	public void setAwardRankCode(String awardRankCode) {
		this.awardRankCode = awardRankCode;
	}

	@Column(name = "AWARD_DEPT", length = 60)
	public String getAwardDept() {
		return this.awardDept;
	}

	public void setAwardDept(String awardDept) {
		this.awardDept = awardDept;
	}

	@Column(name = "AWARD_TIME", length = 10)
	public String getAwardTime() {
		return this.awardTime;
	}

	public void setAwardTime(String awardTime) {
		this.awardTime = awardTime;
	}

	@Column(name = "CERTIFICATE_NO", length = 60)
	public String getCertificateNo() {
		return this.certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	@Column(name = "REMARK", length = 100)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}