package com.jhnu.product.manager.scientific.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TResWork entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_RES_WORK", schema = "DM_MOSDC_HTU")
public class TResWork implements java.io.Serializable {

	// Fields
	@CloumnAs(name = "ID")
	private String id;
	@CloumnAs(name = "作者单位")
	private String deptId;
	@CloumnAs(name = "作者姓名")
	private String authors;
	@CloumnAs(name = "著作题目")
	private String title;
	@CloumnAs(name = "著作书号")
	private String workNo;
	@CloumnAs(name = "出版单位")
	private String pressName;
	@CloumnAs(name = "出版时间")
	private String pressTime;
	@CloumnAs(name = "著作字数")
	private String number;
	@CloumnAs(name = "学科门类")
	private String projectId;
	@CloumnAs(name = "备注")
	private String remark;

	// Constructors

	/** default constructor */
	public TResWork() {
	}

	/** minimal constructor */
	public TResWork(String id, String deptId, String authors, String title,
			String workNo) {
		this.id = id;
		this.deptId = deptId;
		this.authors = authors;
		this.title = title;
		this.workNo = workNo;
	}

	/** full constructor */
	public TResWork(String id, String deptId, String authors, String title,
			String workNo, String pressName, String pressTime, String number,
			String projectId, String remark) {
		this.id = id;
		this.deptId = deptId;
		this.authors = authors;
		this.title = title;
		this.workNo = workNo;
		this.pressName = pressName;
		this.pressTime = pressTime;
		this.number = number;
		this.projectId = projectId;
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

	@Column(name = "DEPT_ID", nullable = false, length = 100)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "AUTHORS", nullable = false, length = 200)
	public String getAuthors() {
		return this.authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	@Column(name = "TITLE_", nullable = false, length = 100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "WORK_NO", nullable = false, length = 60)
	public String getWorkNo() {
		return this.workNo;
	}

	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}

	@Column(name = "PRESS_NAME", length = 100)
	public String getPressName() {
		return this.pressName;
	}

	public void setPressName(String pressName) {
		this.pressName = pressName;
	}

	@Column(name = "PRESS_TIME", length = 10)
	public String getPressTime() {
		return this.pressTime;
	}

	public void setPressTime(String pressTime) {
		this.pressTime = pressTime;
	}

	@Column(name = "NUMBER_", length = 20)
	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name = "PROJECT_ID", length = 20)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}