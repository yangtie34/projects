package com.jhnu.product.manager.scientific.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CODE", schema = "DM_MOSDC_HTU")
public class TCode implements java.io.Serializable {

	@CloumnAs(name = "代码编号")
	private String code;
	@CloumnAs(name = "代码类型code")
	private String codeType;
	@CloumnAs(name = "名称")
	private String name;
	@CloumnAs(name = "部门代码")
	private String codeCategory;
	@CloumnAs(name = "代码类型名称")
	private String codetypeName;
	@CloumnAs(name = "部门代码")
	private String istrue;
	@CloumnAs(name = "部门代码")
	private Short order;

	// Constructors

	/** default constructor */
	public TCode() {
	}

	/** minimal constructor */
	public TCode(String code) {
		this.code = code;
	}

	/** full constructor */
	public TCode(String code,String codeType, String name, String codeCategory,
			String codetypeName, String istrue, Short order) {
		this.code = code;
		this.codeType=codeType;
		this.name = name;
		this.codeCategory = codeCategory;
		this.codetypeName = codetypeName;
		this.istrue = istrue;
		this.order = order;
	}
	@Column(name = "CODE_", nullable = false, length = 10)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "CODE_TYPE", nullable = false, length = 30)
	public String getCodeType() {
		return this.codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	
	@Column(name = "NAME_", length = 60)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CODE_CATEGORY", length = 2)
	public String getCodeCategory() {
		return this.codeCategory;
	}

	public void setCodeCategory(String codeCategory) {
		this.codeCategory = codeCategory;
	}

	@Column(name = "CODETYPE_NAME", length = 100)
	public String getCodetypeName() {
		return this.codetypeName;
	}

	public void setCodetypeName(String codetypeName) {
		this.codetypeName = codetypeName;
	}

	@Column(name = "ISTRUE", precision = 1, scale = 0)
	public String getIstrue() {
		return this.istrue;
	}

	public void setIstrue(String istrue) {
		this.istrue = istrue;
	}

	@Column(name = "ORDER_", precision = 4, scale = 0)
	public Short getOrder() {
		return this.order;
	}

	public void setOrder(Short order) {
		this.order = order;
	}

}