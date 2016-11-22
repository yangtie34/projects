package com.jhnu.product.manager.scientific.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TResOutcomeAppraisal entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_RES_OUTCOME_APPRAISAL", schema = "DM_MOSDC_HTU")
public class TResOutcomeAppraisal implements java.io.Serializable {

	// Fields
	@CloumnAs(name = "ID")
	private String id;
	@CloumnAs(name = "完成人单位")
	private String deptId;
	@CloumnAs(name = "完成人")
	private String authors;
	@CloumnAs(name = "成果名称")
	private String name;
	@CloumnAs(name = "鉴定单位")
	private String appraisalDept;
	@CloumnAs(name = "鉴定形式")
	private String identifymodeCode;
	@CloumnAs(name = "鉴定水平")
	private String identifylevelCode;
	@CloumnAs(name = "鉴定时间")
	private String time;
	@CloumnAs(name = "鉴定证号")
	private String identifyNo;
	@CloumnAs(name = "成果登记号")
	private String identifyRegistNo;
	@CloumnAs(name = "学科门类")
	private String projectId;
	@CloumnAs(name = "我校名次")
	private Short order;
	@CloumnAs(name = "备注")
	private String remark;

	// Constructors

	/** default constructor */
	public TResOutcomeAppraisal() {
	}

	/** minimal constructor */
	public TResOutcomeAppraisal(String id) {
		this.id = id;
	}

	/** full constructor */
	public TResOutcomeAppraisal(String id, String deptId, String authors,
			String name, String appraisalDept, String identifymodeCode,
			String identifylevelCode, String time, String identifyNo,
			String identifyRegistNo, String projectId, Short order,
			String remark) {
		this.id = id;
		this.deptId = deptId;
		this.authors = authors;
		this.name = name;
		this.appraisalDept = appraisalDept;
		this.identifymodeCode = identifymodeCode;
		this.identifylevelCode = identifylevelCode;
		this.time = time;
		this.identifyNo = identifyNo;
		this.identifyRegistNo = identifyRegistNo;
		this.projectId = projectId;
		this.order = order;
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

	@Column(name = "DEPT_ID", length = 100)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "AUTHORS", length = 200)
	public String getAuthors() {
		return this.authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	@Column(name = "NAME_", length = 200)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "APPRAISAL_DEPT", length = 200)
	public String getAppraisalDept() {
		return this.appraisalDept;
	}

	public void setAppraisalDept(String appraisalDept) {
		this.appraisalDept = appraisalDept;
	}

	@Column(name = "IDENTIFYMODE_CODE", length = 10)
	public String getIdentifymodeCode() {
		return this.identifymodeCode;
	}

	public void setIdentifymodeCode(String identifymodeCode) {
		this.identifymodeCode = identifymodeCode;
	}

	@Column(name = "IDENTIFYLEVEL_CODE", length = 10)
	public String getIdentifylevelCode() {
		return this.identifylevelCode;
	}

	public void setIdentifylevelCode(String identifylevelCode) {
		this.identifylevelCode = identifylevelCode;
	}

	@Column(name = "TIME_", length = 10)
	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Column(name = "IDENTIFY_NO", length = 30)
	public String getIdentifyNo() {
		return this.identifyNo;
	}

	public void setIdentifyNo(String identifyNo) {
		this.identifyNo = identifyNo;
	}

	@Column(name = "IDENTIFY_REGIST_NO", length = 30)
	public String getIdentifyRegistNo() {
		return this.identifyRegistNo;
	}

	public void setIdentifyRegistNo(String identifyRegistNo) {
		this.identifyRegistNo = identifyRegistNo;
	}

	@Column(name = "PROJECT_ID", length = 20)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "ORDER_", precision = 4, scale = 0)
	public Short getOrder() {
		return this.order;
	}

	public void setOrder(Short order) {
		this.order = order;
	}

	@Column(name = "REMARK", length = 100)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}