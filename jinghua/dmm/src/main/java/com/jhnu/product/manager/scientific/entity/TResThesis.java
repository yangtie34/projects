package com.jhnu.product.manager.scientific.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TResThesis entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_RES_THESIS", schema = "DM_MOSDC_HTU")
public class TResThesis implements java.io.Serializable {

	// Fields
	@CloumnAs(name = "ID")
	private String id;
	@CloumnAs(name = "论文题目")
	private String title;
	@CloumnAs(name = "作者姓名")
	private String authors;
	@CloumnAs(name = "论文所属单位")
	private String deptId;
	@CloumnAs(name = "发表期刊")
	private String periodical;
	@CloumnAs(name = "发表年份")
	private String year;
	@CloumnAs(name = "年卷期页")
	private String njqy;
	@CloumnAs(name = "期刊类别")
	private String periodicalTypeCode;
	@CloumnAs(name = "所属学科门类")
	private String projectId;
	@CloumnAs(name = "我校排名")
	private String order;
	@CloumnAs(name = "备注")
	private String remark;

	// Constructors

	/** default constructor */
	public TResThesis() {
	}

	/** minimal constructor */
	public TResThesis(String id, String title, String authors, String deptId,
			String periodical) {
		this.id = id;
		this.title = title;
		this.authors = authors;
		this.deptId = deptId;
		this.periodical = periodical;
	}

	/** full constructor */
	public TResThesis(String id, String title, String authors, String deptId,
			String periodical, String year, String njqy,
			String periodicalTypeCode, String projectId, String order,
			String remark) {
		this.id = id;
		this.title = title;
		this.authors = authors;
		this.deptId = deptId;
		this.periodical = periodical;
		this.year = year;
		this.njqy = njqy;
		this.periodicalTypeCode = periodicalTypeCode;
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

	@Column(name = "TITLE_", nullable = false, length = 500)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "AUTHORS", nullable = false, length = 200)
	public String getAuthors() {
		return this.authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	@Column(name = "DEPT_ID", nullable = false, length = 100)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "PERIODICAL", nullable = false, length = 300)
	public String getPeriodical() {
		return this.periodical;
	}

	public void setPeriodical(String periodical) {
		this.periodical = periodical;
	}

	@Column(name = "YEAR_", length = 4)
	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Column(name = "NJQY", length = 100)
	public String getNjqy() {
		return this.njqy;
	}

	public void setNjqy(String njqy) {
		this.njqy = njqy;
	}

	@Column(name = "PERIODICAL_TYPE_CODE", length = 10)
	public String getPeriodicalTypeCode() {
		return this.periodicalTypeCode;
	}

	public void setPeriodicalTypeCode(String periodicalTypeCode) {
		this.periodicalTypeCode = periodicalTypeCode;
	}

	@Column(name = "PROJECT_ID", length = 20)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "ORDER_", precision = 3, scale = 0)
	public String getOrder() {
		return this.order;
	}

	public void setOrder(String order) {
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