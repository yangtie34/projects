package com.jhnu.product.manager.scientific.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TResSoftCopyright entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_RES_SOFT_COPYRIGHT", schema = "DM_MOSDC_HTU")
public class TResSoftCopyright implements java.io.Serializable {

	// Fields
	@CloumnAs(name = "ID")
	private String id;
	@CloumnAs(name = "软件名称")
	private String softName;
	@CloumnAs(name = "完成人单位")
	private String deptId;
	@CloumnAs(name = "著作权人")
	private String owner;
	@CloumnAs(name = "完成人")
	private String completeMan;
	@CloumnAs(name = "学科门类")
	private String projectId;
	@CloumnAs(name = "版权类型")
	private String copyrightCode;
	@CloumnAs(name = "权利取得方式")
	private String getCode;
	@CloumnAs(name = "登记号")
	private String registNo;
	@CloumnAs(name = "证书号")
	private String certificateNo;
	@CloumnAs(name = "开发完成日期")
	private String completeTime;
	@CloumnAs(name = "首次发表日期")
	private String dispatchTime;
	@CloumnAs(name = "软件著作登记日期")
	private String registTime;

	// Constructors

	/** default constructor */
	public TResSoftCopyright() {
	}

	/** minimal constructor */
	public TResSoftCopyright(String id) {
		this.id = id;
	}

	/** full constructor */
	public TResSoftCopyright(String id, String softName, String deptId,
			String owner, String completeMan, String projectId,
			String copyrightCode, String getCode, String registNo,
			String certificateNo, String completeTime, String dispatchTime,
			String registTime) {
		this.id = id;
		this.softName = softName;
		this.deptId = deptId;
		this.owner = owner;
		this.completeMan = completeMan;
		this.projectId = projectId;
		this.copyrightCode = copyrightCode;
		this.getCode = getCode;
		this.registNo = registNo;
		this.certificateNo = certificateNo;
		this.completeTime = completeTime;
		this.dispatchTime = dispatchTime;
		this.registTime = registTime;
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

	@Column(name = "SOFT_NAME", length = 200)
	public String getSoftName() {
		return this.softName;
	}

	public void setSoftName(String softName) {
		this.softName = softName;
	}

	@Column(name = "DEPT_ID", length = 200)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "OWNER", length = 200)
	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Column(name = "COMPLETE_MAN", length = 200)
	public String getCompleteMan() {
		return this.completeMan;
	}

	public void setCompleteMan(String completeMan) {
		this.completeMan = completeMan;
	}

	@Column(name = "PROJECT_ID", length = 20)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "COPYRIGHT_CODE", length = 20)
	public String getCopyrightCode() {
		return this.copyrightCode;
	}

	public void setCopyrightCode(String copyrightCode) {
		this.copyrightCode = copyrightCode;
	}

	@Column(name = "GET_CODE", length = 10)
	public String getGetCode() {
		return this.getCode;
	}

	public void setGetCode(String getCode) {
		this.getCode = getCode;
	}

	@Column(name = "REGIST_NO", length = 60)
	public String getRegistNo() {
		return this.registNo;
	}

	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	@Column(name = "CERTIFICATE_NO", length = 60)
	public String getCertificateNo() {
		return this.certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	@Column(name = "COMPLETE_TIME", length = 10)
	public String getCompleteTime() {
		return this.completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}

	@Column(name = "DISPATCH_TIME", length = 10)
	public String getDispatchTime() {
		return this.dispatchTime;
	}

	public void setDispatchTime(String dispatchTime) {
		this.dispatchTime = dispatchTime;
	}

	@Column(name = "REGIST_TIME", length = 10)
	public String getRegistTime() {
		return this.registTime;
	}

	public void setRegistTime(String registTime) {
		this.registTime = registTime;
	}

}