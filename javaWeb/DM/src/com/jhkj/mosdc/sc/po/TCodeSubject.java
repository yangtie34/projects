package com.jhkj.mosdc.sc.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TCodeSubject entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CODE_SUBJECT")
public class TCodeSubject implements java.io.Serializable {
	// Fields

	private String id;
	private String code;
	private String name;
	private String pid;
	private String path;
	private Byte level;
	private String levelType;
	private Boolean istrue;
	private Short order;

	// Constructors

	/** default constructor */
	public TCodeSubject() {
	}

	/** minimal constructor */
	public TCodeSubject(String id, String code, String name, String pid) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.pid = pid;
	}

	/** full constructor */
	public TCodeSubject(String id, String code, String name, String pid,
			String path, Byte level, String levelType, Boolean istrue,
			Short order) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.pid = pid;
		this.path = path;
		this.level = level;
		this.levelType = levelType;
		this.istrue = istrue;
		this.order = order;
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

	@Column(name = "CODE_", nullable = false, length = 60)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "NAME_", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PID", nullable = false, length = 20)
	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Column(name = "PATH_", length = 200)
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "LEVEL_", precision = 2, scale = 0)
	public Byte getLevel() {
		return this.level;
	}

	public void setLevel(Byte level) {
		this.level = level;
	}

	@Column(name = "LEVEL_TYPE", length = 20)
	public String getLevelType() {
		return this.levelType;
	}

	public void setLevelType(String levelType) {
		this.levelType = levelType;
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