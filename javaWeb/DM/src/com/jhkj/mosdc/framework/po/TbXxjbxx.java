package com.jhkj.mosdc.framework.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbXxjbxx entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_XXJBXX")
public class TbXxjbxx implements java.io.Serializable {

	// Fields

	private Long id;
	private String xxdm;
	private String xxmc;
	private String xxywmc;
	private String xxdz;
	private String xxqhm;
	private String xxxz;
	private String dwfzr;
	private String jxny;
	private String xqr;
	private Long xxxzId;
	private Long bxlxId;
	private Long jbzId;
	private String xxzgbmmc;
	private String jbwyxzk;
	private String gcyxzk;
	private String zdyxzk;
	private String yjsyzk;
	private String wlxyzk;
	private String cjxyzk;
	private String yzbm;
	private String lxdh;
	private String czdh;
	private String dzxx;
	private String zydz;
	private String lsyg;
	private String cjsj;
	private String cjr;
	private String xgsj;
	private String xgr;
	private Long cjrId;
	private Long xgrId;
	private String zzjgdm;
	private String xxzgbmm;
	private Long xkmls;

	// Constructors

	/** default constructor */
	public TbXxjbxx() {
	}

	/** minimal constructor */
	public TbXxjbxx(Long id, String xxdm, String xxmc, String xxdz) {
		this.id = id;
		this.xxdm = xxdm;
		this.xxmc = xxmc;
		this.xxdz = xxdz;
	}

	/** full constructor */
	public TbXxjbxx(Long id, String xxdm, String xxmc, String xxywmc,
			String xxdz, String xxqhm, String xxxz, String dwfzr, String jxny,
			String xqr, Long xxxzId, Long bxlxId, Long jbzId, String xxzgbmmc,
			String jbwyxzk, String gcyxzk, String zdyxzk, String yjsyzk,
			String wlxyzk, String cjxyzk, String yzbm, String lxdh,
			String czdh, String dzxx, String zydz, String lsyg, String cjsj,
			String cjr, String xgsj, String xgr, Long cjrId, Long xgrId,
			String zzjgdm, String xxzgbmm, Long xkmls) {
		this.id = id;
		this.xxdm = xxdm;
		this.xxmc = xxmc;
		this.xxywmc = xxywmc;
		this.xxdz = xxdz;
		this.xxqhm = xxqhm;
		this.xxxz = xxxz;
		this.dwfzr = dwfzr;
		this.jxny = jxny;
		this.xqr = xqr;
		this.xxxzId = xxxzId;
		this.bxlxId = bxlxId;
		this.jbzId = jbzId;
		this.xxzgbmmc = xxzgbmmc;
		this.jbwyxzk = jbwyxzk;
		this.gcyxzk = gcyxzk;
		this.zdyxzk = zdyxzk;
		this.yjsyzk = yjsyzk;
		this.wlxyzk = wlxyzk;
		this.cjxyzk = cjxyzk;
		this.yzbm = yzbm;
		this.lxdh = lxdh;
		this.czdh = czdh;
		this.dzxx = dzxx;
		this.zydz = zydz;
		this.lsyg = lsyg;
		this.cjsj = cjsj;
		this.cjr = cjr;
		this.xgsj = xgsj;
		this.xgr = xgr;
		this.cjrId = cjrId;
		this.xgrId = xgrId;
		this.zzjgdm = zzjgdm;
		this.xxzgbmm = xxzgbmm;
		this.xkmls = xkmls;
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

	@Column(name = "XXDM", nullable = false, length = 12)
	public String getXxdm() {
		return this.xxdm;
	}

	public void setXxdm(String xxdm) {
		this.xxdm = xxdm;
	}

	@Column(name = "XXMC", nullable = false, length = 60)
	public String getXxmc() {
		return this.xxmc;
	}

	public void setXxmc(String xxmc) {
		this.xxmc = xxmc;
	}

	@Column(name = "XXYWMC", length = 180)
	public String getXxywmc() {
		return this.xxywmc;
	}

	public void setXxywmc(String xxywmc) {
		this.xxywmc = xxywmc;
	}

	@Column(name = "XXDZ", nullable = false, length = 60)
	public String getXxdz() {
		return this.xxdz;
	}

	public void setXxdz(String xxdz) {
		this.xxdz = xxdz;
	}

	@Column(name = "XXQHM", length = 6)
	public String getXxqhm() {
		return this.xxqhm;
	}

	public void setXxqhm(String xxqhm) {
		this.xxqhm = xxqhm;
	}

	@Column(name = "XXXZ", length = 60)
	public String getXxxz() {
		return this.xxxz;
	}

	public void setXxxz(String xxxz) {
		this.xxxz = xxxz;
	}

	@Column(name = "DWFZR", length = 60)
	public String getDwfzr() {
		return this.dwfzr;
	}

	public void setDwfzr(String dwfzr) {
		this.dwfzr = dwfzr;
	}

	@Column(name = "JXNY", length = 10)
	public String getJxny() {
		return this.jxny;
	}

	public void setJxny(String jxny) {
		this.jxny = jxny;
	}

	@Column(name = "XQR", length = 20)
	public String getXqr() {
		return this.xqr;
	}

	public void setXqr(String xqr) {
		this.xqr = xqr;
	}

	@Column(name = "XXXZ_ID", precision = 16, scale = 0)
	public Long getXxxzId() {
		return this.xxxzId;
	}

	public void setXxxzId(Long xxxzId) {
		this.xxxzId = xxxzId;
	}

	@Column(name = "BXLX_ID", precision = 16, scale = 0)
	public Long getBxlxId() {
		return this.bxlxId;
	}

	public void setBxlxId(Long bxlxId) {
		this.bxlxId = bxlxId;
	}

	@Column(name = "JBZ_ID", precision = 16, scale = 0)
	public Long getJbzId() {
		return this.jbzId;
	}

	public void setJbzId(Long jbzId) {
		this.jbzId = jbzId;
	}

	@Column(name = "XXZGBMMC", length = 60)
	public String getXxzgbmmc() {
		return this.xxzgbmmc;
	}

	public void setXxzgbmmc(String xxzgbmmc) {
		this.xxzgbmmc = xxzgbmmc;
	}

	@Column(name = "JBWYXZK", length = 5)
	public String getJbwyxzk() {
		return this.jbwyxzk;
	}

	public void setJbwyxzk(String jbwyxzk) {
		this.jbwyxzk = jbwyxzk;
	}

	@Column(name = "GCYXZK", length = 5)
	public String getGcyxzk() {
		return this.gcyxzk;
	}

	public void setGcyxzk(String gcyxzk) {
		this.gcyxzk = gcyxzk;
	}

	@Column(name = "ZDYXZK", length = 5)
	public String getZdyxzk() {
		return this.zdyxzk;
	}

	public void setZdyxzk(String zdyxzk) {
		this.zdyxzk = zdyxzk;
	}

	@Column(name = "YJSYZK", length = 5)
	public String getYjsyzk() {
		return this.yjsyzk;
	}

	public void setYjsyzk(String yjsyzk) {
		this.yjsyzk = yjsyzk;
	}

	@Column(name = "WLXYZK", length = 5)
	public String getWlxyzk() {
		return this.wlxyzk;
	}

	public void setWlxyzk(String wlxyzk) {
		this.wlxyzk = wlxyzk;
	}

	@Column(name = "CJXYZK", length = 5)
	public String getCjxyzk() {
		return this.cjxyzk;
	}

	public void setCjxyzk(String cjxyzk) {
		this.cjxyzk = cjxyzk;
	}

	@Column(name = "YZBM", length = 6)
	public String getYzbm() {
		return this.yzbm;
	}

	public void setYzbm(String yzbm) {
		this.yzbm = yzbm;
	}

	@Column(name = "LXDH", length = 30)
	public String getLxdh() {
		return this.lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	@Column(name = "CZDH", length = 30)
	public String getCzdh() {
		return this.czdh;
	}

	public void setCzdh(String czdh) {
		this.czdh = czdh;
	}

	@Column(name = "DZXX", length = 30)
	public String getDzxx() {
		return this.dzxx;
	}

	public void setDzxx(String dzxx) {
		this.dzxx = dzxx;
	}

	@Column(name = "ZYDZ", length = 60)
	public String getZydz() {
		return this.zydz;
	}

	public void setZydz(String zydz) {
		this.zydz = zydz;
	}

	@Column(name = "LSYG")
	public String getLsyg() {
		return this.lsyg;
	}

	public void setLsyg(String lsyg) {
		this.lsyg = lsyg;
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

	@Column(name = "CJR_ID", precision = 16, scale = 0)
	public Long getCjrId() {
		return this.cjrId;
	}

	public void setCjrId(Long cjrId) {
		this.cjrId = cjrId;
	}

	@Column(name = "XGR_ID", precision = 16, scale = 0)
	public Long getXgrId() {
		return this.xgrId;
	}

	public void setXgrId(Long xgrId) {
		this.xgrId = xgrId;
	}

	@Column(name = "ZZJGDM", length = 9)
	public String getZzjgdm() {
		return this.zzjgdm;
	}

	public void setZzjgdm(String zzjgdm) {
		this.zzjgdm = zzjgdm;
	}

	@Column(name = "XXZGBMM", length = 10)
	public String getXxzgbmm() {
		return this.xxzgbmm;
	}

	public void setXxzgbmm(String xxzgbmm) {
		this.xxzgbmm = xxzgbmm;
	}

	@Column(name = "XKMLS", precision = 3, scale = 0)
	public Long getXkmls() {
		return this.xkmls;
	}

	public void setXkmls(Long xkmls) {
		this.xkmls = xkmls;
	}

}