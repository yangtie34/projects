package com.jhnu.product.manager.scientific.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TResThesisReship entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_RES_THESIS_RESHIP", schema = "DM_MOSDC_HTU")
public class TResThesisReship implements java.io.Serializable {

	// Fields
	@CloumnAs(name = "ID")
	private String id;
	@CloumnAs(name = "论文编号")
	private String thesisId;
	@CloumnAs(name = "转载期刊名称")
	private String periodical;
	@CloumnAs(name = "转载年卷期页")
	private String njqy;
	@CloumnAs(name = "转载年份")
	private String year;
	@CloumnAs(name = "转载期刊类别")
	private String thesisReshipCode;
	@CloumnAs(name = "备注")
	private String remark;

	// Constructors

	/** default constructor */
	public TResThesisReship() {
	}

	/** minimal constructor */
	public TResThesisReship(String id, String thesisId) {
		this.id = id;
		this.thesisId = thesisId;
	}

	/** full constructor */
	public TResThesisReship(String id, String thesisId, String periodical,
			String njqy, String year, String thesisReshipCode, String remark) {
		this.id = id;
		this.thesisId = thesisId;
		this.periodical = periodical;
		this.njqy = njqy;
		this.year = year;
		this.thesisReshipCode = thesisReshipCode;
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

	@Column(name = "PERIODICAL", length = 100)
	public String getPeriodical() {
		return this.periodical;
	}

	public void setPeriodical(String periodical) {
		this.periodical = periodical;
	}

	@Column(name = "NJQY", length = 30)
	public String getNjqy() {
		return this.njqy;
	}

	public void setNjqy(String njqy) {
		this.njqy = njqy;
	}

	@Column(name = "YEAR_", length = 4)
	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Column(name = "THESIS_RESHIP_CODE", length = 10)
	public String getThesisReshipCode() {
		return this.thesisReshipCode;
	}

	public void setThesisReshipCode(String thesisReshipCode) {
		this.thesisReshipCode = thesisReshipCode;
	}

	@Column(name = "REMARK", length = 100)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}