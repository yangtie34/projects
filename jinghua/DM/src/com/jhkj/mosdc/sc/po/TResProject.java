package com.jhkj.mosdc.sc.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TResProject entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_RES_PROJECT")
public class TResProject implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String compere;
	private String deptId;
	private String proNo;
	private String category;
	private String levelCode;
	private String rankCode;
	private String issuedDept;
	private String teamworkDept;
	private String startTime;
	private String endTime;
	private Double fund;
	private String setupYear;
	private String projectId;
	private String remark;
	private String stateCode;

	// Constructors

	/** default constructor */
	public TResProject() {
	}

	/** minimal constructor */
	public TResProject(String id) {
		this.id = id;
	}

	/** full constructor */
	public TResProject(String id, String name, String compere, String deptId,
			String proNo, String category, String levelCode, String rankCode,
			String issuedDept, String teamworkDept, String startTime,
			String endTime, Double fund, String setupYear, String projectId,
			String remark, String stateCode) {
		this.id = id;
		this.name = name;
		this.compere = compere;
		this.deptId = deptId;
		this.proNo = proNo;
		this.category = category;
		this.levelCode = levelCode;
		this.rankCode = rankCode;
		this.issuedDept = issuedDept;
		this.teamworkDept = teamworkDept;
		this.startTime = startTime;
		this.endTime = endTime;
		this.fund = fund;
		this.setupYear = setupYear;
		this.projectId = projectId;
		this.remark = remark;
		this.stateCode = stateCode;
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

	@Column(name = "NAME_", length = 200)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "COMPERE", length = 60)
	public String getCompere() {
		return this.compere;
	}

	public void setCompere(String compere) {
		this.compere = compere;
	}

	@Column(name = "DEPT_ID", length = 60)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "PRO_NO", length = 60)
	public String getProNo() {
		return this.proNo;
	}

	public void setProNo(String proNo) {
		this.proNo = proNo;
	}

	@Column(name = "CATEGORY", length = 200)
	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "LEVEL_CODE", length = 20)
	public String getLevelCode() {
		return this.levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	@Column(name = "RANK_CODE", length = 50)
	public String getRankCode() {
		return this.rankCode;
	}

	public void setRankCode(String rankCode) {
		this.rankCode = rankCode;
	}

	@Column(name = "ISSUED_DEPT", length = 100)
	public String getIssuedDept() {
		return this.issuedDept;
	}

	public void setIssuedDept(String issuedDept) {
		this.issuedDept = issuedDept;
	}

	@Column(name = "TEAMWORK_DEPT", length = 100)
	public String getTeamworkDept() {
		return this.teamworkDept;
	}

	public void setTeamworkDept(String teamworkDept) {
		this.teamworkDept = teamworkDept;
	}

	@Column(name = "START_TIME", length = 10)
	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@Column(name = "END_TIME", length = 10)
	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Column(name = "FUND", precision = 8, scale = 3)
	public Double getFund() {
		return this.fund;
	}

	public void setFund(Double fund) {
		this.fund = fund;
	}

	@Column(name = "SETUP_YEAR", length = 4)
	public String getSetupYear() {
		return this.setupYear;
	}

	public void setSetupYear(String setupYear) {
		this.setupYear = setupYear;
	}

	@Column(name = "PROJECT_ID", length = 20)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "REMARK", length = 100)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "STATE_CODE", length = 10)
	public String getStateCode() {
		return this.stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

}