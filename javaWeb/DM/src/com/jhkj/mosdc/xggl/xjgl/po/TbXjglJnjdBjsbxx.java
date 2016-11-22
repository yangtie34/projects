package com.jhkj.mosdc.xggl.xjgl.po;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbXjdaJnjdBjsbxx entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_XJGL_JNJD_BJSBXX")
public class TbXjglJnjdBjsbxx implements java.io.Serializable {
	private static final long serialVersionUID = 3727250928637004314L;

	/**
	 * 无状态
	 */
	public static final int STATUS_NULL = -1;
	/**
	 * 已通知/异常
	 */
	public static final int STATUS_NOTIFY = 0;
	/**
	 * 已审核通过
	 */
	public static final int STATUS_PASS = 1;

	// Fields

	private Long id;
	private Long pcId;
	private Long bjId;
	private Integer status;
	private String memo;

	// Constructors

	/** default constructor */
	public TbXjglJnjdBjsbxx() {
	}

	/** minimal constructor */
	public TbXjglJnjdBjsbxx(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TbXjglJnjdBjsbxx(Long id, Long pcId, Long bjId, Integer status,
			String memo) {
		this.id = id;
		this.pcId = pcId;
		this.bjId = bjId;
		this.status = status;
		this.memo = memo;
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

	@Column(name = "PC_ID", precision = 16, scale = 0)
	public Long getPcId() {
		return this.pcId;
	}

	public void setPcId(Long pcId) {
		this.pcId = pcId;
	}

	@Column(name = "BJ_ID", precision = 16, scale = 0)
	public Long getBjId() {
		return this.bjId;
	}

	public void setBjId(Long bjId) {
		this.bjId = bjId;
	}

	@Column(name = "STATUS", precision = 1, scale = 0)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "MEMO", length = 500)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}