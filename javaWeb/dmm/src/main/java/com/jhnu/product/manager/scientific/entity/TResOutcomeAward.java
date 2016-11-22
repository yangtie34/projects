package com.jhnu.product.manager.scientific.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TResOutcomeAward entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_RES_OUTCOME_AWARD", schema = "DM_MOSDC_HTU")
public class TResOutcomeAward implements java.io.Serializable {

	// Fields
	@CloumnAs(name = "ID")
	private String id;
	@CloumnAs(name = "获奖人单位")
	private String deptId;
	@CloumnAs(name = "获奖人")
	private String prizewinner;
	@CloumnAs(name = "成果名称")
	private String name;
	@CloumnAs(name = "学科门类")
	private String projectId;
	@CloumnAs(name = "获奖名称")
	private String awardName;
	@CloumnAs(name = "获奖水平")
	private String levelCode;
	@CloumnAs(name = "获奖类别")
	private String categoryCode;
	@CloumnAs(name = "获奖等级")
	private String rankCode;
	@CloumnAs(name = "授奖单位")
	private String awardDept;
	@CloumnAs(name = "授奖时间")
	private String awardTime;
	@CloumnAs(name = "证书编号")
	private String certificateNo;

	// Constructors

	/** default constructor */
	public TResOutcomeAward() {
	}

	/** minimal constructor */
	public TResOutcomeAward(String id) {
		this.id = id;
	}

	/** full constructor */
	public TResOutcomeAward(String id, String deptId, String prizewinner,
			String name, String projectId, String awardName, String levelCode,
			String categoryCode, String rankCode, String awardDept,
			String awardTime, String certificateNo) {
		this.id = id;
		this.deptId = deptId;
		this.prizewinner = prizewinner;
		this.name = name;
		this.projectId = projectId;
		this.awardName = awardName;
		this.levelCode = levelCode;
		this.categoryCode = categoryCode;
		this.rankCode = rankCode;
		this.awardDept = awardDept;
		this.awardTime = awardTime;
		this.certificateNo = certificateNo;
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

	@Column(name = "DEPT_ID", length = 200)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "PRIZEWINNER", length = 200)
	public String getPrizewinner() {
		return this.prizewinner;
	}

	public void setPrizewinner(String prizewinner) {
		this.prizewinner = prizewinner;
	}

	@Column(name = "NAME_", length = 200)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PROJECT_ID", length = 20)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "AWARD_NAME", length = 200)
	public String getAwardName() {
		return this.awardName;
	}

	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}

	@Column(name = "LEVEL_CODE", length = 10)
	public String getLevelCode() {
		return this.levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	@Column(name = "CATEGORY_CODE", length = 30)
	public String getCategoryCode() {
		return this.categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	@Column(name = "RANK_CODE", length = 30)
	public String getRankCode() {
		return this.rankCode;
	}

	public void setRankCode(String rankCode) {
		this.rankCode = rankCode;
	}

	@Column(name = "AWARD_DEPT", length = 200)
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

	@Column(name = "CERTIFICATE_NO", length = 30)
	public String getCertificateNo() {
		return this.certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

}