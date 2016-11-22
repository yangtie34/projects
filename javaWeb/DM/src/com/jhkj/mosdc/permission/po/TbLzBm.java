package com.jhkj.mosdc.permission.po;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TbLzBm entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_LZ_BM")
public class TbLzBm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 标识
	 */
	private Long id;

//	/**
//	 * 原部门ID
//	 */
//	private String wid;

	/**
	 * 单位代码
	 */
	private String dwdm;

	/**
	 * 单位名称
	 */
	private String dwmc;

	/**
	 * 单位英文名称
	 */
	private String dwywmc;

	/**
	 * 单位简称
	 */
	private String dwjc;

	/**
	 * 单位地址
	 */
	private String dwdz;

	/**
	 * 办别码
	 */
	private String bbm;

	/**
	 * 类别码
	 */
	private String lbm;

	/**
	 * 建立年月
	 */
	private Date jlny;

	/**
	 * 行政负责人
	 */
	private String xzfzrdm;

	/**
	 * 党务负责人
	 */
	private String dwfzrdm;

	/**
	 * 电话号码
	 */
	private String dhhm;

	/**
	 * 传真号码
	 */
	private String czhm;

	/**
	 * 电子信箱
	 */
	private String dzxx;

	/**
	 * 主页地址
	 */
	private String zydz;

	/**
	 * 上级部门标识
	 */
	private Long sjBmId;

	/**
	 * 描述
	 */
	private String ms;

	@Id
	@Column(name="ID", precision=16, scale=0)
	public Long getId() {
		 return id;
	}

	public void setId(Long id) {
		 this.id = id;
	}

//	@Column(name="WID", length=40)
//	public String getWid() {
//		 return wid;
//	}
//
//	public void setWid(String wid) {
//		 this.wid = wid;
//	}

	@Column(name="DWDM", length=20)
	public String getDwdm() {
		 return dwdm;
	}

	public void setDwdm(String dwdm) {
		 this.dwdm = dwdm;
	}

	@Column(name="DWMC", length=20)
	public String getDwmc() {
		 return dwmc;
	}

	public void setDwmc(String dwmc) {
		 this.dwmc = dwmc;
	}

	@Column(name="DWYWMC", length=60)
	public String getDwywmc() {
		 return dwywmc;
	}

	public void setDwywmc(String dwywmc) {
		 this.dwywmc = dwywmc;
	}

	@Column(name="DWJC", length=20)
	public String getDwjc() {
		 return dwjc;
	}

	public void setDwjc(String dwjc) {
		 this.dwjc = dwjc;
	}

	@Column(name="DWDZ", length=50)
	public String getDwdz() {
		 return dwdz;
	}

	public void setDwdz(String dwdz) {
		 this.dwdz = dwdz;
	}

	@Column(name="BBM", length=1)
	public String getBbm() {
		 return bbm;
	}

	public void setBbm(String bbm) {
		 this.bbm = bbm;
	}

	@Column(name="LBM", length=2)
	public String getLbm() {
		 return lbm;
	}

	public void setLbm(String lbm) {
		 this.lbm = lbm;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="JLNY")
	public Date getJlny() {
		 return jlny==null ? null : (Date)jlny.clone();
	}

	public void setJlny(Date jlny) {
		 this.jlny = jlny==null ? null : (Date)jlny.clone();
	}

	@Column(name="XZFZRDM", length=30)
	public String getXzfzrdm() {
		 return xzfzrdm;
	}

	public void setXzfzrdm(String xzfzrdm) {
		 this.xzfzrdm = xzfzrdm;
	}
	
	@Column(name="DWFZRDM", length=10)
	public String getDwfzrdm() {
		 return dwfzrdm;
	}

	
	public void setDwfzrdm(String dwfzrdm) {
		 this.dwfzrdm = dwfzrdm;
	}
	
	
	@Column(name="DHHM", length=20)
	public String getDhhm() {
		 return dhhm;
	}

	public void setDhhm(String dhhm) {
		 this.dhhm = dhhm;
	}

	@Column(name="CZHM", length=20)
	public String getCzhm() {
		 return czhm;
	}
	
	public void setCzhm(String czhm) {
		 this.czhm = czhm;
	}

	@Column(name="DZXX", length=60)
	public String getDzxx() {
		 return dzxx;
	}

	public void setDzxx(String dzxx) {
		 this.dzxx = dzxx;
	}

	@Column(name="ZYDZ", length=20)
	public String getZydz() {
		 return zydz;
	}

	public void setZydz(String zydz) {
		 this.zydz = zydz;
	}

	@Column(name="SJ_BM_ID", precision=18, scale=0)
	public Long getSjBmId() {
		 return sjBmId;
	}
	
	
	public void setSjBmId(Long sjBmId) {
		 this.sjBmId = sjBmId;
	}

	@Column(name="MS", length=256)
	public String getMs() {
		 return ms;
	}

	public void setMs(String ms) {
		 this.ms = ms;
	}
	
}