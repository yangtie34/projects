package com.jhnu.product.manager.scientific.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TResThesisIn entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_RES_THESIS_IN", schema = "DM_MOSDC_HTU")
public class TResThesisIn implements java.io.Serializable {

	// Fields
	@CloumnAs(name = "ID")
	private String id;
	@CloumnAs(name = "论文ID")
	private String thesisId;
	@CloumnAs(name = "发表期刊名称")
	private String periodical;
	@CloumnAs(name = "收录年份")
	private String year;
	@CloumnAs(name = "年卷期页")
	private String njqy;
	@CloumnAs(name = "ISSN")
	private String issn;
	@CloumnAs(name = "影响因子")
	private Double impactFactor;
	@CloumnAs(name = "SCI分区")
	private String sciZone;
	@CloumnAs(name = "收录类别")
	private String periodicalTypeCode;
	@CloumnAs(name = "备注")
	private String remark;

	// Constructors

	/** default constructor */
	public TResThesisIn() {
	}

	/** minimal constructor */
	public TResThesisIn(String id, String thesisId) {
		this.id = id;
		this.thesisId = thesisId;
	}

	/** full constructor */
	public TResThesisIn(String id, String thesisId, String periodical,
			String year, String njqy, String issn, Double impactFactor,
			String sciZone, String periodicalTypeCode, String remark) {
		this.id = id;
		this.thesisId = thesisId;
		this.periodical = periodical;
		this.year = year;
		this.njqy = njqy;
		this.issn = issn;
		this.impactFactor = impactFactor;
		this.sciZone = sciZone;
		this.periodicalTypeCode = periodicalTypeCode;
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

	@Column(name = "YEAR_", length = 4)
	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Column(name = "NJQY", length = 60)
	public String getNjqy() {
		return this.njqy;
	}

	public void setNjqy(String njqy) {
		this.njqy = njqy;
	}

	@Column(name = "ISSN", length = 60)
	public String getIssn() {
		return this.issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	@Column(name = "IMPACT_FACTOR", precision = 4, scale = 3)
	public Double getImpactFactor() {
		return this.impactFactor;
	}

	public void setImpactFactor(Double impactFactor) {
		this.impactFactor = impactFactor;
	}

	@Column(name = "SCI_ZONE", length = 20)
	public String getSciZone() {
		return this.sciZone;
	}

	public void setSciZone(String sciZone) {
		this.sciZone = sciZone;
	}

	@Column(name = "PERIODICAL_TYPE_CODE", length = 10)
	public String getPeriodicalTypeCode() {
		return this.periodicalTypeCode;
	}

	public void setPeriodicalTypeCode(String periodicalTypeCode) {
		this.periodicalTypeCode = periodicalTypeCode;
	}

	@Column(name = "REMARK", length = 100)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}