package com.jhnu.framework.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * TsStsx entity. @author MyEclipse Persistence Tools
 */
public class TsStsx implements java.io.Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -307581197374961110L;
	/**
	 * ID
	 */
	private Long id;
	/**
	 * 属性
	 */
	private String sx;
	/**
	 * 属性中文名
	 */
	private String sxzwm;
	/**
	 * 属性显示类型
	 */
	private String sxxslx;
	/**
	 * 是否表单显示字段
	 */
	private Long sfxszd;
	/**
	 * 是否查询字段
	 */
	private Long sfcxzd;
	/**
	 * 长度
	 */
	private Long cd;	
	/**
	 * 校验正则表达式
	 */
	private String jyzzbds; 
	/**
	 * 正则校验错误信息
	 */
	private String zzjycwxx;
	/**
	 * 校验类型
	 */
	private String jylx;
	/**
	 * 类型校验错误信息
	 */
	private String lxjycwxx;
	/**
	 * 排序序号
	 */
	private Long pxxh;
	/**
	 * 是否排序
	 */
	
	private Long sfpx;
	/**
	 * 升降序
	 */
	private Long sjx;
	/**
	 * 所属实体名
	 */
	private String ssstm;
	/**
	 * 编码表实体名
	 */
	private String bmbstm;
	/**
	 * 编码表标准代码
	 */
	private String bmbbzdm;
	/**
	 * 编码表显示字段  显示名称
	 */
	private String bmbxszd;
	/**
	 * 编码表关联字段 最为其他表的外键
	 */
	private String bmbglzd;
	
	/**
	 * 编码表类别字段
	 */
	private String bmblbzd;
	
	/**
	 * 编码表代码字段
	 */
	private String bmbdmzd;
	
	/**
	 * 是否可用
	 */ 
	private Long sfky;
	/**
	 * 创建时间
	 */
	private String cjsj;
	/**
	 * 创建人
	 */
	private String cjr;
	/**
	 * 修改时间
	 */
	private String xgsj;
	/**
	 * 修改人
	 */
	private String xgr;
	/**
	 * 属性组件类型
	 */
	private String sxzjlx;
	/**
	 * 是否下钻
	 */
	private String sfxz;
	/**
	 * 是否列表显示字段
	 */
	private Long sflbxszd;
	/**
	 * 是否列表字段
	 */
	private Long sflbzd;
	/**
	 * 所属分类(字段所属分类，例如联系方式，个人基本信息) 对应Ext.form.FieldSet
	 */
    private String fl;
    /**
	 * 编码数据
	 */
    private Object bmsj;
    
    /**
     * 列表宽度
     */
    private Long lbkd;
    
    /**
     * 列表显示属性（列表数据显示编码表的名称或者子表的需要显示的字段）
     */
    private String lbxssx;
    
    /**
     * 新增表单字段属性
     */
    private String xzzdsx;
    /**
     * 新增表单字段属性
     */
    private String xgzdsx;
    /**
     * 实体对应表名
     */
    private String bm;
    /**
     * 实体属性对应字段名
     */
    private String zdm;
    
    /**
     * 实体属性对应是否非空
     */
    private Long sffk;
    /**
     * 是否可编辑
     */
    private Long sfkbj;
    
    /**
     * tree对应的层次
     */
    private Long cc;
	// Constructors
    /**
     * 编码表服务名。
     */
    private String bmbfwm;
	/** default constructor */
	public TsStsx() {
	}

	/** minimal constructor */
	public TsStsx(Long id) {
		this.id = id;
	}

	
	public TsStsx(Long id, String sx, String sxzwm, String sxxslx, Long sfxszd,
			Long sfcxzd, Long cd, String jyzzbds, String zzjycwxx, String jylx,
			String lxjycwxx, Long pxxh, Long sfpx, Long sjx, String ssstm,
			String bmbstm, String bmbbzdm, String bmbxszd, String bmbglzd,
			String bmblbzd, String bmbdmzd, Long sfky, String cjsj, String cjr,
			String xgsj, String xgr, String sxzjlx, String sfxz, Long sflbxszd,
			Long sflbzd, String fl, Object bmsj, Long lbkd, String lbxssx,
			String xzzdsx, String xgzdsx, String bm, String zdm, Long sffk,
			Long sfkbj, Long cc, String bmbfwm) {
		super();
		this.id = id;
		this.sx = sx;
		this.sxzwm = sxzwm;
		this.sxxslx = sxxslx;
		this.sfxszd = sfxszd;
		this.sfcxzd = sfcxzd;
		this.cd = cd;
		this.jyzzbds = jyzzbds;
		this.zzjycwxx = zzjycwxx;
		this.jylx = jylx;
		this.lxjycwxx = lxjycwxx;
		this.pxxh = pxxh;
		this.sfpx = sfpx;
		this.sjx = sjx;
		this.ssstm = ssstm;
		this.bmbstm = bmbstm;
		this.bmbbzdm = bmbbzdm;
		this.bmbxszd = bmbxszd;
		this.bmbglzd = bmbglzd;
		this.bmblbzd = bmblbzd;
		this.bmbdmzd = bmbdmzd;
		this.sfky = sfky;
		this.cjsj = cjsj;
		this.cjr = cjr;
		this.xgsj = xgsj;
		this.xgr = xgr;
		this.sxzjlx = sxzjlx;
		this.sfxz = sfxz;
		this.sflbxszd = sflbxszd;
		this.sflbzd = sflbzd;
		this.fl = fl;
		this.bmsj = bmsj;
		this.lbkd = lbkd;
		this.lbxssx = lbxssx;
		this.xzzdsx = xzzdsx;
		this.xgzdsx = xgzdsx;
		this.bm = bm;
		this.zdm = zdm;
		this.sffk = sffk;
		this.sfkbj = sfkbj;
		this.cc = cc;
		this.bmbfwm = bmbfwm;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "SX", length = 60)
	public String getSx() {
		return this.sx;
	}

	public void setSx(String sx) {
		this.sx = sx;
	}
	@Column(name = "PXXH")
	public Long getPxxh() {
		return pxxh;
	}

	public void setPxxh(Long pxxh) {
		this.pxxh = pxxh;
	}

	@Column(name = "SXZWM", length = 200)
	public String getSxzwm() {
		return this.sxzwm;
	}

	public void setSxzwm(String sxzwm) {
		this.sxzwm = sxzwm;
	}

	@Column(name = "SXXSLX", length = 60)
	public String getSxxslx() {
		return this.sxxslx;
	}

	public void setSxxslx(String sxxslx) {
		this.sxxslx = sxxslx;
	}

	@Column(name = "SFXSZD", precision = 22, scale = 0)
	public Long getSfxszd() {
		return this.sfxszd;
	}

	public void setSfxszd(Long sfxszd) {
		this.sfxszd = sfxszd;
	}

	@Column(name = "SFCXZD", precision = 22, scale = 0)
	public Long getSfcxzd() {
		return this.sfcxzd;
	}

	public void setSfcxzd(Long sfcxzd) {
		this.sfcxzd = sfcxzd;
	}

	@Column(name = "CD", precision = 22, scale = 0)
	public Long getCd() {
		return this.cd;
	}

	public void setCd(Long cd) {
		this.cd = cd;
	}

	@Column(name = "JYZZBDS", length = 500)
	public String getJyzzbds() {
		return this.jyzzbds;
	}

	public void setJyzzbds(String jyzzbds) {
		this.jyzzbds = jyzzbds;
	}

	@Column(name = "SFPX", precision = 22, scale = 0)
	public Long getSfpx() {
		return this.sfpx;
	}

	public void setSfpx(Long sfpx) {
		this.sfpx = sfpx;
	}

	@Column(name = "SJX", precision = 22, scale = 0)
	public Long getSjx() {
		return this.sjx;
	}

	public void setSjx(Long sjx) {
		this.sjx = sjx;
	}

	@Column(name = "SSSTM", length = 60)
	public String getSsstm() {
		return this.ssstm;
	}

	public void setSsstm(String ssstm) {
		this.ssstm = ssstm;
	}

	@Column(name = "BMBSTM", length = 60)
	public String getBmbstm() {
		return this.bmbstm;
	}

	public void setBmbstm(String bmbstm) {
		this.bmbstm = bmbstm;
	}

	@Column(name = "BMBBZDM", length = 60)
	public String getBmbbzdm() {
		return this.bmbbzdm;
	}

	public void setBmbbzdm(String bmbbzdm) {
		this.bmbbzdm = bmbbzdm;
	}
	
	@Column(name = "BMBLBZD", length = 60)
	public String getBmblbzd() {
		return bmblbzd;
	}

	public void setBmblbzd(String bmglbzd) {
		this.bmblbzd = bmglbzd;
	}

	@Column(name = "BMBXSZD", length = 60)
	public String getBmbxszd() {
		return bmbxszd;
	}

	public void setBmbxszd(String bmbxszd) {
		this.bmbxszd = bmbxszd;
	}
	
	@Column(name = "BMBGLZD", length = 60)
	public String getBmbglzd() {
		return bmbglzd;
	}

	public void setBmbglzd(String bmbglzd) {
		this.bmbglzd = bmbglzd;
	}
	
	@Column(name = "BMBDMZD", length = 60)
	public String getBmbdmzd() {
		return bmbdmzd;
	}

	public void setBmbdmzd(String bmbdmzd) {
		this.bmbdmzd = bmbdmzd;
	}

	
	@Column(name = "SFKY", precision = 22, scale = 0)
	public Long getSfky() {
		return this.sfky;
	}

	
	public void setSfky(Long sfky) {
		this.sfky = sfky;
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

	@Column(name = "SXZJLX", length = 60)
	public String getSxzjlx() {
		return this.sxzjlx;
	}

	public void setSxzjlx(String sxzjlx) {
		this.sxzjlx = sxzjlx;
	}

	@Column(name = "SFXZ", length = 20)
	public String getSfxz() {
		return this.sfxz;
	}

	public void setSfxz(String sfxz) {
		this.sfxz = sfxz;
	}

	@Column(name = "SFLBXSZD", precision = 22, scale = 0)
	public Long getSflbxszd() {
		return this.sflbxszd;
	}

	public void setSflbxszd(Long sflbxszd) {
		this.sflbxszd = sflbxszd;
	}
	@Column(name = "SFLBZD", precision = 22, scale = 0)
	public Long getSflbzd() {
		return this.sflbzd;
	}
	
	public void setSflbzd(Long sflbzd) {
		this.sflbzd = sflbzd;
	}
    
	@Column(name = "ZZJYCWXX")
	public String getZzjycwxx() {
		return zzjycwxx;
	}

	public void setZzjycwxx(String zzjycwxx) {
		this.zzjycwxx = zzjycwxx;
	}
	
	@Column(name = "JYLX")
	public String getJylx() {
		return jylx;
	}

	public void setJylx(String jylx) {
		this.jylx = jylx;
	}
	
	@Column(name = "LXJYCWXX")
	public String getLxjycwxx() {
		return lxjycwxx;
	}

	public void setLxjycwxx(String lxjycwxx) {
		this.lxjycwxx = lxjycwxx;
	}
	@Column(name = "FL")
	public String getFl() {
		return fl;
	}

	public void setFl(String fl) {
		this.fl = fl;
	}
	 @Transient
	public Object getBmsj() {
		return bmsj;
	}

	public void setBmsj(Object bmsj) {
		this.bmsj = bmsj;
	}
	@Column(name = "LBKD")
	public Long getLbkd() {
		return lbkd;
	}

	public void setLbkd(Long lbkd) {
		this.lbkd = lbkd;
	}
	@Column(name = "LBXSSX", length = 60)
	public String getLbxssx() {
		return lbxssx;
	}

	public void setLbxssx(String lbxssx) {
		this.lbxssx = lbxssx;
	}
	@Column(name = "BM", length = 60)
	public String getBm() {
		return bm;
	}

	public void setBm(String bm) {
		this.bm = bm;
	}
	@Column(name = "XGZDSX", length = 60)
	public String getXgzdsx() {
		return xgzdsx;
	}

	public void setXgzdsx(String xgzdsx) {
		this.xgzdsx = xgzdsx;
	}
	@Column(name = "XZZDSX", length = 60)
	public String getXzzdsx() {
		return xzzdsx;
	}

	public void setXzzdsx(String xzzdsx) {
		this.xzzdsx = xzzdsx;
	}
	@Column(name = "ZDM", length = 60)
	public String getZdm() {
		return zdm;
	}

	public void setZdm(String zdm) {
		this.zdm = zdm;
	}
	
	@Column(name = "SFFK")
	public Long getSffk() {
		return sffk;
	}

	public void setSffk(Long sffk) {
		this.sffk = sffk;
	}
	@Column(name = "SFKBJ")
	public Long getSfkbj() {
		return sfkbj;
	}

	public void setSfkbj(Long sfkbj) {
		this.sfkbj = sfkbj;
	}
	
	@Column(name = "CC")
	public Long getCc() {
		return cc;
	}

	public void setCc(Long cc) {
		this.cc = cc;
	}
	
	@Column(name="BMBFWM", length = 200)
	public String getBmbfwm() {
		return bmbfwm;
	}

	public void setBmbfwm(String bmbfwm) {
		this.bmbfwm = bmbfwm;
	}
	
	
}