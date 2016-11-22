package com.jhkj.mosdc.xxzy.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_XXZY_SXJD")
public class TbXxzySxjd implements java.io.Serializable {

	// Fields

	private Long id;
	private String jdmc;
	private Long jdlxId;
	private Long ssyyId;
	private String dz;
	private String tp;
	private Boolean sfky;
	private String cgsj;
	private String cjsj;
	private String cjr;
	private String xgsj;
	private String xgr;
	private String yb;
	private String jdjs;
	private Long rs;
	private Short rnbjs;

	// Constructors

	/** default constructor */
	public TbXxzySxjd() {
	}

	/** minimal constructor */
	public TbXxzySxjd(Long id) {
		this.id = id;
	}

	public TbXxzySxjd(Long id, String jdmc, Long jdlxId, Long ssyyId,
			String dz, String tp, Boolean sfky, String cgsj, String cjsj,
			String cjr, String xgsj, String xgr, String yb, String jdjs,
			Long rs, Short rnbjs) {
		super();
		this.id = id;
		this.jdmc = jdmc;
		this.jdlxId = jdlxId;
		this.ssyyId = ssyyId;
		this.dz = dz;
		this.tp = tp;
		this.sfky = sfky;
		this.cgsj = cgsj;
		this.cjsj = cjsj;
		this.cjr = cjr;
		this.xgsj = xgsj;
		this.xgr = xgr;
		this.yb = yb;
		this.jdjs = jdjs;
		this.rs = rs;
		this.rnbjs = rnbjs;
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

	@Column(name = "JDMC", length = 20)
	public String getJdmc() {
		return this.jdmc;
	}

	public void setJdmc(String jdmc) {
		this.jdmc = jdmc;
	}

	@Column(name = "JDLX_ID", precision = 16, scale = 0)
	public Long getJdlxId() {
		return this.jdlxId;
	}

	public void setJdlxId(Long jdlxId) {
		this.jdlxId = jdlxId;
	}

	@Column(name = "SSYY_ID", precision = 16, scale = 0)
	public Long getSsyyId() {
		return this.ssyyId;
	}

	public void setSsyyId(Long ssyyId) {
		this.ssyyId = ssyyId;
	}

	@Column(name = "DZ", length = 20)
	public String getDz() {
		return this.dz;
	}

	public void setDz(String dz) {
		this.dz = dz;
	}

	@Column(name = "TP", length = 60)
	public String getTp() {
		return this.tp;
	}

	public void setTp(String tp) {
		this.tp = tp;
	}

	@Column(name = "SFKY", precision = 1, scale = 0)
	public Boolean getSfky() {
		return this.sfky;
	}

	public void setSfky(Boolean sfky) {
		this.sfky = sfky;
	}

	@Column(name = "CGSJ", length = 20)
	public String getCgsj() {
		return this.cgsj;
	}

	public void setCgsj(String cgsj) {
		this.cgsj = cgsj;
	}

	@Column(name = "CJSJ", length = 60)
	public String getCjsj() {
		return this.cjsj;
	}

	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
	}

	@Column(name = "CJR", length = 60)
	public String getCjr() {
		return this.cjr;
	}

	public void setCjr(String cjr) {
		this.cjr = cjr;
	}

	@Column(name = "XGSJ", length = 60)
	public String getXgsj() {
		return this.xgsj;
	}

	public void setXgsj(String xgsj) {
		this.xgsj = xgsj;
	}

	@Column(name = "XGR", length = 60)
	public String getXgr() {
		return this.xgr;
	}

	public void setXgr(String xgr) {
		this.xgr = xgr;
	}

	@Column(name = "YB", length = 60)
	public String getYb() {
		return this.yb;
	}

	public void setYb(String yb) {
		this.yb = yb;
	}

	@Column(name = "JDJS", length = 500)
	public String getJdjs() {
		return this.jdjs;
	}

	public void setJdjs(String jdjs) {
		this.jdjs = jdjs;
	}

	@Column(name = "RS", precision = 4, scale = 0)
	public Long getRs() {
		return this.rs;
	}

	public void setRs(Long rs) {
		this.rs = rs;
	}
	@Column(name = "RNBJS", precision = 4, scale = 0)
	public Short getRnbjs() {
		return rnbjs;
	}

	public void setRnbjs(Short rnbjs) {
		this.rnbjs = rnbjs;
	}
}