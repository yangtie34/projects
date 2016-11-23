package com.jhkj.mosdc.sc.po;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CODE", schema = "DM_MOSDC")
public class TCode implements java.io.Serializable {

	// Fields
	private String id;
	private String code;
	private String name;
	private String codeType;
	private String codeCategory;
	private String codetypeName;
	private Boolean istrue;
	private Short order;

	// Constructors

	

	public TCode() {
		super();
	}

	public TCode(String id, String code, String name, String codeType,
			String codeCategory, String codetypeName, Boolean istrue,
			Short order) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.codeType = codeType;
		this.codeCategory = codeCategory;
		this.codetypeName = codetypeName;
		this.istrue = istrue;
		this.order = order;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 60)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "CODE_", length = 2)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "NAME_", length = 2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CODE_TYPE", length = 2)
	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
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
	public Boolean getIstrue() {
		return this.istrue;
	}

	public void setIstrue(Boolean istrue) {
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