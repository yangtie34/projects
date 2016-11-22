package com.jhnu.product.manager.scientific.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TResPatent entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_RES_PATENT", schema = "DM_MOSDC_HTU")
public class TResPatent implements java.io.Serializable {

	// Fields
	@CloumnAs(name = "ID")
	private String id;
	@CloumnAs(name = "专利名称")
	private String name;
	@CloumnAs(name = "发明人单位")
	private String inventorDeptId;
	@CloumnAs(name = "专利权单位")
	private String patentDept;
	@CloumnAs(name = "发明人")
	private String inventors;
	@CloumnAs(name = "专利类型")
	private String patentTypeCode;
	@CloumnAs(name = "专利实施状态")
	private String patentStateCode;
	@CloumnAs(name = "学科门类")
	private String projectId;
	@CloumnAs(name = "发文日")
	private String dispatchTime;
	@CloumnAs(name = "受理号")
	private String acceptNo;
	@CloumnAs(name = "受理日")
	private String acceptTime;
	@CloumnAs(name = "授权日")
	private String accreditTime;
	@CloumnAs(name = "专利号")
	private String patentNo;
	@CloumnAs(name = "证书编号")
	private String certificateNo;
	@CloumnAs(name = "备注")
	private String remark;

	// Constructors

	/** default constructor */
	public TResPatent() {
	}

	/** minimal constructor */
	public TResPatent(String id) {
		this.id = id;
	}

	/** full constructor */
	public TResPatent(String id, String name, String inventorDeptId,
			String patentDept, String inventors, String patentTypeCode,
			String patentStateCode, String projectId, String dispatchTime,
			String acceptNo, String acceptTime, String accreditTime,
			String patentNo, String certificateNo, String remark) {
		this.id = id;
		this.name = name;
		this.inventorDeptId = inventorDeptId;
		this.patentDept = patentDept;
		this.inventors = inventors;
		this.patentTypeCode = patentTypeCode;
		this.patentStateCode = patentStateCode;
		this.projectId = projectId;
		this.dispatchTime = dispatchTime;
		this.acceptNo = acceptNo;
		this.acceptTime = acceptTime;
		this.accreditTime = accreditTime;
		this.patentNo = patentNo;
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

	@Column(name = "NAME_", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "INVENTOR_DEPT_ID", length = 200)
	public String getInventorDeptId() {
		return this.inventorDeptId;
	}

	public void setInventorDeptId(String inventorDeptId) {
		this.inventorDeptId = inventorDeptId;
	}

	@Column(name = "PATENT_DEPT", length = 200)
	public String getPatentDept() {
		return this.patentDept;
	}

	public void setPatentDept(String patentDept) {
		this.patentDept = patentDept;
	}

	@Column(name = "INVENTORS", length = 200)
	public String getInventors() {
		return this.inventors;
	}

	public void setInventors(String inventors) {
		this.inventors = inventors;
	}

	@Column(name = "PATENT_TYPE_CODE", length = 20)
	public String getPatentTypeCode() {
		return this.patentTypeCode;
	}

	public void setPatentTypeCode(String patentTypeCode) {
		this.patentTypeCode = patentTypeCode;
	}

	@Column(name = "PATENT_STATE_CODE", length = 10)
	public String getPatentStateCode() {
		return this.patentStateCode;
	}

	public void setPatentStateCode(String patentStateCode) {
		this.patentStateCode = patentStateCode;
	}

	@Column(name = "PROJECT_ID", length = 20)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "DISPATCH_TIME", length = 10)
	public String getDispatchTime() {
		return this.dispatchTime;
	}

	public void setDispatchTime(String dispatchTime) {
		this.dispatchTime = dispatchTime;
	}

	@Column(name = "ACCEPT_NO", length = 60)
	public String getAcceptNo() {
		return this.acceptNo;
	}

	public void setAcceptNo(String acceptNo) {
		this.acceptNo = acceptNo;
	}

	@Column(name = "ACCEPT_TIME", length = 10)
	public String getAcceptTime() {
		return this.acceptTime;
	}

	public void setAcceptTime(String acceptTime) {
		this.acceptTime = acceptTime;
	}

	@Column(name = "ACCREDIT_TIME", length = 10)
	public String getAccreditTime() {
		return this.accreditTime;
	}

	public void setAccreditTime(String accreditTime) {
		this.accreditTime = accreditTime;
	}

	@Column(name = "PATENT_NO", length = 60)
	public String getPatentNo() {
		return this.patentNo;
	}

	public void setPatentNo(String patentNo) {
		this.patentNo = patentNo;
	}

	@Column(name = "CERTIFICATE_NO", length = 60)
	public String getCertificateNo() {
		return this.certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}