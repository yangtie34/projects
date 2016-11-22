package com.jhkj.mosdc.permission.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * 功能说明:菜单资源
 * @author: 党冬(mrdangdong@163.com)
 * @DATE:2012-5-31 @TIME: 下午10:08:21
 */
@Entity
@Table(name = "TS_CDZY")
public class TsCdzy implements java.io.Serializable {

	private static final long serialVersionUID = 3164074492019251908L;
	
	private Long id;
	
	/**
	 * 菜单资源名称
	 */
	
	private String mc;
	
	/**
	 * 菜单所属分类标识
	 */
	
	private Long cdssflId;
	
	/**
	 * 排序号
	 */
	
	private Long pxh;
	
	/**
	 * 上级资源标识
	 */

	private Long fjdId;
	
	/**
	 * 是否可见 0 不可见 1 可见
	 */
	
	private String sfky;
	
	/**
	 * 菜单路径
	 */
	
	private String cdlj;
	
	/**
	 * 是否叶子 0 非叶子 1 叶子
	 */
	private String sfyzjd;
	/**
	 * 按钮类型标识
	 */
	private Long anlxId;
	
	/**
	*创建人
	*/ 
	 private String cjr;  
	/**
	*创建时间
	*/ 
	 private String cjsj;  
	 
	 /**
		*修改人
		*/ 
	private String xgr;  
	
	/**
	*修改时间
	*/ 
	 private String xgsj;
	 
	 private Boolean checked = false;
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "MC", nullable = false, length = 100)
	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}
	@Column(name = "CDSSFL_ID", precision = 16, scale = 0)
	public Long getCdssflId() {
		return cdssflId;
	}

	public void setCdssflId(Long cdssflId) {
		this.cdssflId = cdssflId;
	}
	@Column(name = "PXH", precision = 5, scale = 0)
	public Long getPxh() {
		return pxh;
	}

	public void setPxh(Long pxh) {
		this.pxh = pxh;
	}
	@Column(name = "FJD_ID", precision = 16, scale = 0)
	public Long getFjdId() {
		return fjdId;
	}

	public void setFjdId(Long fjdId) {
		this.fjdId = fjdId;
	}
	@Column(name = "SFKY", precision = 1, scale = 0)
	public String getSfky() {
		return sfky;
	}
	
	public void setSfky(String sfky) {
		this.sfky = sfky;
	}
	
	
	@Column(name = "CDLJ", length = 500)
	public String getCdlj() {
		return cdlj;
	}


	public void setCdlj(String cdlj) {
		this.cdlj = cdlj;
	}
	@Column(name = "SFYZJD", precision = 22, scale = 0)
	public String getSfyzjd() {
		return sfyzjd;
	}

	public void setSfyzjd(String sfyzjd) {
		this.sfyzjd = sfyzjd;
	}
	@Column(name = "ANLX_ID", precision = 16, scale = 0)
	public Long getAnlxId() {
		return anlxId;
	}

	public void setAnlxId(Long anlxId) {
		this.anlxId = anlxId;
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
	@Transient //不对应数据库字段
	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
	
}