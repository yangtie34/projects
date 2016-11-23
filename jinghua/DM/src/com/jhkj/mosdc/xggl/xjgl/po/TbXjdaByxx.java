package com.jhkj.mosdc.xggl.xjgl.po;

// Generated 2013-11-5 13:49:26 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 毕业学校
 */
@Entity
@Table(name = "TB_XJDA_BYXX")
public class TbXjdaByxx implements java.io.Serializable {

	private Long id;
	private String mc;
	private Long dq1;
	private Long dq2;
	private Long dq3;
	private String lxdh;
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "MC", length = 60)
	public String getMc() {
		return mc;
	}
	public void setMc(String mc) {
		this.mc = mc;
	}
	@Column(name = "DQ1", precision = 16, scale = 0)
	public Long getDq1() {
		return dq1;
	}
	public void setDq1(Long dq1) {
		this.dq1 = dq1;
	}
	@Column(name = "DQ2", precision = 16, scale = 0)
	public Long getDq2() {
		return dq2;
	}
	public void setDq2(Long dq2) {
		this.dq2 = dq2;
	}
	@Column(name = "DQ3", precision = 16, scale = 0)
	public Long getDq3() {
		return dq3;
	}
	public void setDq3(Long dq3) {
		this.dq3 = dq3;
	}
	@Column(name = "RXNF", length = 20)
	public String getLxdh() {
		return lxdh;
	}
	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}
	
	
}
