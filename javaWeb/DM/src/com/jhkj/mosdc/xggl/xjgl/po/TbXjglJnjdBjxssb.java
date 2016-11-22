package com.jhkj.mosdc.xggl.xjgl.po;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbXjdaJnjdBjxssb entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_XJGL_JNJD_BJXSSB")
public class TbXjglJnjdBjxssb implements java.io.Serializable {
	private static final long serialVersionUID = -9144815340410561752L;

	/**
	 * 无状态
	 */
	public static final int STATUS_NULL = -1;
	/**
	 * 已申报
	 */
	public static final int STATUS_REPORT = 0;
	/**
	 * 退回
	 */
	public static final int STATUS_RETURN = 1;

	// Fields

	private Long id;
	private Long bjsbId;
	private Long xsId;
	private Integer status;
	private String sbzy;
	private String sbjb;
	private String sbtj;
	private String jdfl;

	// Constructors

	@Column(name = "status", precision = 1, scale = 0)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/** default constructor */
	public TbXjglJnjdBjxssb() {
	}

	/** minimal constructor */
	public TbXjglJnjdBjxssb(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TbXjglJnjdBjxssb(Long id, Long bjsbId, Long xsId, String sbzy,
			String sbjb, String sbtj, String jdfl) {
		this.id = id;
		this.bjsbId = bjsbId;
		this.xsId = xsId;
		this.sbzy = sbzy;
		this.sbjb = sbjb;
		this.sbtj = sbtj;
		this.jdfl = jdfl;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "BJSB_ID", precision = 16, scale = 0)
	public Long getBjsbId() {
		return this.bjsbId;
	}

	public void setBjsbId(Long bjsbId) {
		this.bjsbId = bjsbId;
	}

	@Column(name = "XS_ID", precision = 16, scale = 0)
	public Long getXsId() {
		return this.xsId;
	}

	public void setXsId(Long xsId) {
		this.xsId = xsId;
	}

	@Column(name = "SBZY", length = 100)
	public String getSbzy() {
		return this.sbzy;
	}

	public void setSbzy(String sbzy) {
		this.sbzy = sbzy;
	}

	@Column(name = "SBJB", length = 100)
	public String getSbjb() {
		return this.sbjb;
	}

	public void setSbjb(String sbjb) {
		this.sbjb = sbjb;
	}

	@Column(name = "SBTJ", length = 100)
	public String getSbtj() {
		return this.sbtj;
	}

	public void setSbtj(String sbtj) {
		this.sbtj = sbtj;
	}

	@Column(name = "JDFL", length = 100)
	public String getJdfl() {
		return this.jdfl;
	}

	public void setJdfl(String jdfl) {
		this.jdfl = jdfl;
	}

}