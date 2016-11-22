package com.jhkj.mosdc.framework.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbJzgxx entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_JZGXX")
public class TbJzgxx implements java.io.Serializable {
  /**
	*创建时间
    */ 
	 private String cjsj;  
	/**
	*修改时间
	*/ 
	 private String xgsj;  
	/**
	*院系ID
	*/ 
	 private Long yxId;  
	/**
	*从教日期
	*/ 
	 private String cjrq;  
	 
	/**
	*姓名
	*修改属性类型
	*/ 
	 private String xm;  
	/**
	*姓名拼音
	*/ 
	 private String xmpy;  
	/**
	*出生日期
	*/ 
	 private String csrq;  
	/**
	*见国标 人的性别代码
	*参考GB/T 2261.1-1980
	*/ 
	 private Long xbId;  
	/**
	*宗教信仰
	*/ 
	 private String zjxx;  
	/**
	*婚姻状况ID
	*/ 
	 private Long hyzkId;  
	/**
	*籍贯ID
	*/ 
	 private Long jgId;  
	/**
	*国家标准GB/T 4765—1984  《家庭出身代码》
	*/ 
	 private Long jtcsId;  
	/**
	*现住址
	*/ 
	 private String xzz;  
	/**
	*户口所在地
	*/ 
	 private String hkszd;  
	/**
	*入党日期
	*/ 
	 private String rdrq;  
	/**
	*国家标准GB/T 4762—1984  《政治面貌代码》
	*/ 
	 private Long zzmmId;  
	/**
	*国家标准GB/T 2659—2000  《世界各国和地区名称代码》
	*/ 
	 private Long gjdqId;  
	/**
	*国家标准GB/T 6864—1986  《中华人民共和国学位代码》
	*/ 
	 private Long xwdId;  
	/**
	*校区ID
	*/ 
	 private Long xqdId;  
	/**
	*参加工作年月
	*/ 
	 private String cjgzny;  
	/**
	*教职工类别ID
	*/ 
	 private Long jzglbId;  
	/**
	*任课状况ID
	*/ 
	 private Long rkzkId;  
	/**
	*档案文本
	*/ 
	 private String dawb;  
	/**
	*联系电话
	*/ 
	 private String lxdh;  
	/**
	*照片
	*/ 
	 private Long zpId;  
	/**
	*职务类别ID
	*/ 
	 private Long zwlbId;  
	/**
	*行政级别ID
	*/ 
	 private Long xzjbId;  
	/**
	*电子信箱
	*/ 
	 private String dzxx;  
	/**
	*应聘研究学科ID
	*/ 
	 private Long ypyjxkId;  
	/**
	*现研究学科ID
	*/ 
	 private Long xyjxkId;  
	/**
	*国家标准GB/T 13745—1992  《学科分类与代码》
	*/ 
	 private Long xkId;  
	/**
	*国家标准GB/T 13745—1992  《学科分类与代码》
	*/ 
	 private Long xkId3;  
	/**
	*岗位类别ID
	*/ 
	 private Long gwlId;  
	/**
	*用人形式
	*/ 
	 private String yrxs;  
	/**
	*国家标准GB/T 4880—1991  《语种名称代码》
	*/ 
	 private Long dewyyzId;  
	/**
	*国家标准GB/T 6865—1986  《语种熟练程度代码》
	*/ 
	 private Long dewyspId;  
	/**
	*ID
	*/ 
	 private Long id;  
	/**
	*创建人
	*/ 
	 private String cjr;  
	/**
	*国家标准GB/T 8561—2001  《专业技术职务代码》
	*/ 
	 private Long zyId;  
	/**
	*国家标准GB/T 4767—1984  《健康状况》
	*/ 
	 private Long jkzkId;  
	/**
	*国家标准GB/T 3304—1991  《中国各民族名称的罗马字母拼写法和代码》
	*/ 
	 private Long mzId;  
	/**
	*邮政编码
	*/ 
	 private String yzbm;  
	/**
	*曾用名
	*/ 
	 private String cym;  
	/**
	*户口性质ID
	*/ 
	 private Long hkxzId;  
	/**
	*学校代码
	*/ 
	 private String xxdm;  
	/**
	*修改人
	*/ 
	 private String xgr;  
	/**
	*科室ID
	*/ 
	 private Long ksId;  
	/**
	*血型ID
	*/ 
	 private Long xxId;  
	/**
	*港澳台侨ID
	*/ 
	 private Long gatqId;  
	/**
	*出生地ID
	*/ 
	 private Long csdId;  
	/**
	*家庭住址
	*/ 
	 private String jtzz;  
	/**
	*国家标准GB/T 4658—1984  《文化程度代码》
	*/ 
	 private Long whcId;  
	/**
	*归属单位代ID
	*/ 
	 private Long dwdId;  
	/**
	*来校日期
	*/ 
	 private String lxrq;  
	/**
	*档案编号
	*/ 
	 private String dabh;  
	/**
	*通信地址
	*/ 
	 private String txdz;  
	/**
	*特长
	*/ 
	 private String tc;  
	/**
	*国家标准GB/T 8561—2001  《专业技术职务代码》
	*/ 
	 private Long zyjszwId;  
	/**
	*国家标准GB/T 13745—1992  《学科分类与代码》
	*/ 
	 private Long xkId2;  
	/**
	*国家标准GB/T 6865—1986  《语种熟练程度代码》
	*/ 
	 private Long dywyspId;  
	/**
	*身份证号
	*/ 
	 private String sfzh;  
	/**
	*编制类别ID
	*/ 
//	 private Long bzlbId;  
	/**
	*工人技术等级ID
	*参考：GB/T 14946.1-2009，“工人技术等级”
	*/ 
	 private Long grjsdjId;  
	/**
	*当前状态ID
	*/ 
	 private Long dqztId;  
	/**
	*职工号
	*/ 
	 private String zgh;  
	/**
	*本人成分代ID
	*/ 
	 private Long brcfId;  
	/**
	*国家标准GB/T 4762—1984  《政治面貌代码》
	*/ 
	 private Long dezzmmId;  
	/**
	*主页地址
	*/ 
	 private String zydz;  
	/**
	*应聘从事专业号
	*/ 
	 private String ypcszyh;  
	/**
	*国家标准GB/T 4880—1991  《语种名称代码》
	*/ 
	 private Long dywyyzId;  
	 /**
	  * 专业技术职称ID，参考：GB/T 8561-2001 “专业技术职称”
	  * @author think
	  */
	 private Long zyjszcId;
	 
	 private String htqx; //合同期限
	/*
	 * 教学组织机构ID 
	 */
	private Long jxzzjgId;
	
	private Long rylbId;
	private Long sfky;
	// Constructors

	/** default constructor */
	public TbJzgxx(String cjsj, String xgsj, Long yxId, String cjrq,
			String xm, String xmpy, String csrq, Long xbId, String zjxx,
			Long hyzkId, Long jgId, Long jtcsId, String xzz, String hkszd,
			String rdrq, Long zzmmId, Long gjdqId, Long xwdId, Long xqdId,
			String cjgzny, Long jzglbId, Long rkzkId, String dawb, String lxdh,
			Long zpId, Long zwlbId, Long xzjbId, String dzxx, Long ypyjxkId,
			Long xyjxkId, Long xkId, Long xkId3, Long gwlId, String yrxs,
			Long dewyyzId, Long dewyspId, Long id, String cjr, Long zyId,
			Long jkzkId, Long mzId, String yzbm, String cym, Long hkxzId,
			String xxdm, String xgr, Long ksId, Long xxId, Long gatqId,
			Long csdId, String jtzz, Long whcId, Long dwdId, String lxrq,
			String dabh, String txdz, String tc, Long zyjszwId, Long xkId2,
			Long dywyspId, String sfzh, Long grjsdjId, Long dqztId, String zgh,
			Long brcfId, Long dezzmmId, String zydz, String ypcszyh,
			Long dywyyzId, Long zyjszcId, String htqx, Long jxzzjgId,Long rylbId,Long sfky) {
		super();
		this.cjsj = cjsj;
		this.xgsj = xgsj;
		this.yxId = yxId;
		this.cjrq = cjrq;
		this.xm = xm;
		this.xmpy = xmpy;
		this.csrq = csrq;
		this.xbId = xbId;
		this.zjxx = zjxx;
		this.hyzkId = hyzkId;
		this.jgId = jgId;
		this.jtcsId = jtcsId;
		this.xzz = xzz;
		this.hkszd = hkszd;
		this.rdrq = rdrq;
		this.zzmmId = zzmmId;
		this.gjdqId = gjdqId;
		this.xwdId = xwdId;
		this.xqdId = xqdId;
		this.cjgzny = cjgzny;
		this.jzglbId = jzglbId;
		this.rkzkId = rkzkId;
		this.dawb = dawb;
		this.lxdh = lxdh;
		this.zpId = zpId;
		this.zwlbId = zwlbId;
		this.xzjbId = xzjbId;
		this.dzxx = dzxx;
		this.ypyjxkId = ypyjxkId;
		this.xyjxkId = xyjxkId;
		this.xkId = xkId;
		this.xkId3 = xkId3;
		this.gwlId = gwlId;
		this.yrxs = yrxs;
		this.dewyyzId = dewyyzId;
		this.dewyspId = dewyspId;
		this.id = id;
		this.cjr = cjr;
		this.zyId = zyId;
		this.jkzkId = jkzkId;
		this.mzId = mzId;
		this.yzbm = yzbm;
		this.cym = cym;
		this.hkxzId = hkxzId;
		this.xxdm = xxdm;
		this.xgr = xgr;
		this.ksId = ksId;
		this.xxId = xxId;
		this.gatqId = gatqId;
		this.csdId = csdId;
		this.jtzz = jtzz;
		this.whcId = whcId;
		this.dwdId = dwdId;
		this.lxrq = lxrq;
		this.dabh = dabh;
		this.txdz = txdz;
		this.tc = tc;
		this.zyjszwId = zyjszwId;
		this.xkId2 = xkId2;
		this.dywyspId = dywyspId;
		this.sfzh = sfzh;
		this.grjsdjId = grjsdjId;
		this.dqztId = dqztId;
		this.zgh = zgh;
		this.brcfId = brcfId;
		this.dezzmmId = dezzmmId;
		this.zydz = zydz;
		this.ypcszyh = ypcszyh;
		this.dywyyzId = dywyyzId;
		this.zyjszcId = zyjszcId;
		this.htqx = htqx;
		this.jxzzjgId = jxzzjgId;
		this.rylbId=rylbId;
	}



	public TbJzgxx() {
		super();
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

	@Column(name = "RYLB_ID", length = 20)
	public Long getRylbId() {
		return rylbId;
	}

	public void setRylbId(Long rylbId) {
		this.rylbId = rylbId;
	}
	
	@Column(name = "ZGH", nullable = false, length = 20)
	public String getZgh() {
		return this.zgh;
	}

	public void setZgh(String zgh) {
		this.zgh = zgh;
	}

	@Column(name = "YX_ID", precision = 16, scale = 0)
	public Long getYxId() {
		return this.yxId;
	}

	public void setYxId(Long yxId) {
		this.yxId = yxId;
	}

	@Column(name = "KS_ID", precision = 16, scale = 0)
	public Long getKsId() {
		return this.ksId;
	}

	public void setKsId(Long ksId) {
		this.ksId = ksId;
	}

	@Column(name = "XM", nullable = false)
	public String getXm() {
		return this.xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	@Column(name = "XMPY", length = 12)
	public String getXmpy() {
		return this.xmpy;
	}

	public void setXmpy(String xmpy) {
		this.xmpy = xmpy;
	}

	@Column(name = "CYM", length = 60)
	public String getCym() {
		return this.cym;
	}

	public void setCym(String cym) {
		this.cym = cym;
	}

	@Column(name = "SFZH", nullable = false, length = 60)
	public String getSfzh() {
		return this.sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}

	@Column(name = "CSRQ", length = 10)
	public String getCsrq() {
		return this.csrq;
	}

	public void setCsrq(String csrq) {
		this.csrq = csrq;
	}

	@Column(name = "XB_ID", precision = 16, scale = 0)
	public Long getXbId() {
		return this.xbId;
	}

	public void setXbId(Long xbId) {
		this.xbId = xbId;
	}

	@Column(name = "JKZK_ID", precision = 16, scale = 0)
	public Long getJkzkId() {
		return this.jkzkId;
	}

	public void setJkzkId(Long jkzkId) {
		this.jkzkId = jkzkId;
	}

	@Column(name = "MZ_ID", precision = 16, scale = 0)
	public Long getMzId() {
		return this.mzId;
	}

	public void setMzId(Long mzId) {
		this.mzId = mzId;
	}

	@Column(name = "XX_ID", precision = 16, scale = 0)
	public Long getXxId() {
		return this.xxId;
	}

	public void setXxId(Long xxId) {
		this.xxId = xxId;
	}

	@Column(name = "ZJXX", length = 60)
	public String getZjxx() {
		return this.zjxx;
	}

	public void setZjxx(String zjxx) {
		this.zjxx = zjxx;
	}

	@Column(name = "GATQ_ID", precision = 16, scale = 0)
	public Long getGatqId() {
		return this.gatqId;
	}

	public void setGatqId(Long gatqId) {
		this.gatqId = gatqId;
	}

	@Column(name = "HYZK_ID", precision = 16, scale = 0)
	public Long getHyzkId() {
		return this.hyzkId;
	}

	public void setHyzkId(Long hyzkId) {
		this.hyzkId = hyzkId;
	}

	@Column(name = "JG_ID", precision = 16, scale = 0)
	public Long getJgId() {
		return this.jgId;
	}

	public void setJgId(Long jgId) {
		this.jgId = jgId;
	}

	@Column(name = "CSD_ID", precision = 16, scale = 0)
	public Long getCsdId() {
		return this.csdId;
	}

	public void setCsdId(Long csdId) {
		this.csdId = csdId;
	}

	@Column(name = "JTCS_ID", precision = 16, scale = 0)
	public Long getJtcsId() {
		return this.jtcsId;
	}

	public void setJtcsId(Long jtcsId) {
		this.jtcsId = jtcsId;
	}

	@Column(name = "BRCF_ID", precision = 16, scale = 0)
	public Long getBrcfId() {
		return this.brcfId;
	}

	public void setBrcfId(Long brcfId) {
		this.brcfId = brcfId;
	}

	@Column(name = "JTZZ", length = 60)
	public String getJtzz() {
		return this.jtzz;
	}

	public void setJtzz(String jtzz) {
		this.jtzz = jtzz;
	}

	@Column(name = "XZZ", length = 60)
	public String getXzz() {
		return this.xzz;
	}

	public void setXzz(String xzz) {
		this.xzz = xzz;
	}

	@Column(name = "HKSZD", length = 60)
	public String getHkszd() {
		return this.hkszd;
	}

	public void setHkszd(String hkszd) {
		this.hkszd = hkszd;
	}

	@Column(name = "HKXZ_ID", precision = 16, scale = 0)
	public Long getHkxzId() {
		return this.hkxzId;
	}

	public void setHkxzId(Long hkxzId) {
		this.hkxzId = hkxzId;
	}

	@Column(name = "WHC_ID", precision = 16, scale = 0)
	public Long getWhcId() {
		return this.whcId;
	}

	public void setWhcId(Long whcId) {
		this.whcId = whcId;
	}

	@Column(name = "RDRQ", length = 10)
	public String getRdrq() {
		return this.rdrq;
	}

	public void setRdrq(String rdrq) {
		this.rdrq = rdrq;
	}

	@Column(name = "ZZMM_ID", precision = 16, scale = 0)
	public Long getZzmmId() {
		return this.zzmmId;
	}

	public void setZzmmId(Long zzmmId) {
		this.zzmmId = zzmmId;
	}

	@Column(name = "DEZZMM_ID", precision = 16, scale = 0)
	public Long getDezzmmId() {
		return this.dezzmmId;
	}

	public void setDezzmmId(Long dezzmmId) {
		this.dezzmmId = dezzmmId;
	}

	@Column(name = "GJDQ_ID", precision = 16, scale = 0)
	public Long getGjdqId() {
		return this.gjdqId;
	}

	public void setGjdqId(Long gjdqId) {
		this.gjdqId = gjdqId;
	}

	@Column(name = "XWD_ID", precision = 16, scale = 0)
	public Long getXwdId() {
		return this.xwdId;
	}

	public void setXwdId(Long xwdId) {
		this.xwdId = xwdId;
	}

	@Column(name = "DWD_ID", precision = 16, scale = 0)
	public Long getDwdId() {
		return this.dwdId;
	}

	public void setDwdId(Long dwdId) {
		this.dwdId = dwdId;
	}

	@Column(name = "XQD_ID", precision = 16, scale = 0)
	public Long getXqdId() {
		return this.xqdId;
	}

	public void setXqdId(Long xqdId) {
		this.xqdId = xqdId;
	}

	@Column(name = "CJGZNY", length = 10)
	public String getCjgzny() {
		return this.cjgzny;
	}

	public void setCjgzny(String cjgzny) {
		this.cjgzny = cjgzny;
	}

	@Column(name = "LXRQ", length = 10)
	public String getLxrq() {
		return this.lxrq;
	}

	public void setLxrq(String lxrq) {
		this.lxrq = lxrq;
	}

	@Column(name = "CJRQ", length = 10)
	public String getCjrq() {
		return this.cjrq;
	}

	public void setCjrq(String cjrq) {
		this.cjrq = cjrq;
	}

	/*@Column(name = "BZLB_ID", precision = 16, scale = 0)
	public Long getBzlbId() {
		return this.bzlbId;
	}

	public void setBzlbId(Long bzlbId) {
		this.bzlbId = bzlbId;
	}*/

	@Column(name = "JZGLB_ID", precision = 16, scale = 0)
	public Long getJzglbId() {
		return this.jzglbId;
	}

	public void setJzglbId(Long jzglbId) {
		this.jzglbId = jzglbId;
	}

	@Column(name = "RKZK_ID", precision = 16, scale = 0)
	public Long getRkzkId() {
		return this.rkzkId;
	}

	public void setRkzkId(Long rkzkId) {
		this.rkzkId = rkzkId;
	}

	@Column(name = "DABH", length = 30)
	public String getDabh() {
		return this.dabh;
	}

	public void setDabh(String dabh) {
		this.dabh = dabh;
	}

	@Column(name = "DAWB")
	public String getDawb() {
		return this.dawb;
	}

	public void setDawb(String dawb) {
		this.dawb = dawb;
	}

	@Column(name = "LXDH", length = 30)
	public String getLxdh() {
		return this.lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	@Column(name = "TXDZ", length = 60)
	public String getTxdz() {
		return this.txdz;
	}

	public void setTxdz(String txdz) {
		this.txdz = txdz;
	}

	@Column(name = "YZBM", length = 6)
	public String getYzbm() {
		return this.yzbm;
	}

	public void setYzbm(String yzbm) {
		this.yzbm = yzbm;
	}

	@Column(name = "ZYDZ", length = 100)
	public String getZydz() {
		return this.zydz;
	}

	public void setZydz(String zydz) {
		this.zydz = zydz;
	}

	@Column(name = "ZP_ID", precision = 16, scale = 0)
	public Long getZpId() {
		return this.zpId;
	}

	public void setZpId(Long zpId) {
		this.zpId = zpId;
	}

	@Column(name = "TC", length = 200)
	public String getTc() {
		return this.tc;
	}

	public void setTc(String tc) {
		this.tc = tc;
	}

	@Column(name = "ZWLB_ID", precision = 16, scale = 0)
	public Long getZwlbId() {
		return this.zwlbId;
	}

	public void setZwlbId(Long zwlbId) {
		this.zwlbId = zwlbId;
	}

	@Column(name = "XZJB_ID", precision = 16, scale = 0)
	public Long getXzjbId() {
		return this.xzjbId;
	}

	public void setXzjbId(Long xzjbId) {
		this.xzjbId = xzjbId;
	}

	@Column(name = "ZYJSZW_ID", precision = 16, scale = 0)
	public Long getZyjszwId() {
		return this.zyjszwId;
	}

	public void setZyjszwId(Long zyjszwId) {
		this.zyjszwId = zyjszwId;
	}

	@Column(name = "DZXX", length = 30)
	public String getDzxx() {
		return this.dzxx;
	}

	public void setDzxx(String dzxx) {
		this.dzxx = dzxx;
	}

	@Column(name = "GRJSDJ_ID", precision = 16, scale = 0)
	public Long getGrjsdjId() {
		return this.grjsdjId;
	}

	public void setGrjsdjId(Long grjsdjId) {
		this.grjsdjId = grjsdjId;
	}

	@Column(name = "YPCSZYH", length = 20)
	public String getYpcszyh() {
		return this.ypcszyh;
	}

	public void setYpcszyh(String ypcszyh) {
		this.ypcszyh = ypcszyh;
	}

	@Column(name = "ZY_ID", precision = 16, scale = 0)
	public Long getZyId() {
		return this.zyId;
	}

	public void setZyId(Long zyId) {
		this.zyId = zyId;
	}

	@Column(name = "YPYJXK_ID", precision = 16, scale = 0)
	public Long getYpyjxkId() {
		return this.ypyjxkId;
	}

	public void setYpyjxkId(Long ypyjxkId) {
		this.ypyjxkId = ypyjxkId;
	}

	@Column(name = "XYJXK_ID", precision = 16, scale = 0)
	public Long getXyjxkId() {
		return this.xyjxkId;
	}

	public void setXyjxkId(Long xyjxkId) {
		this.xyjxkId = xyjxkId;
	}

	@Column(name = "XK_ID", precision = 16, scale = 0)
	public Long getXkId() {
		return this.xkId;
	}

	public void setXkId(Long xkId) {
		this.xkId = xkId;
	}

	@Column(name = "XK_ID2", precision = 16, scale = 0)
	public Long getXkId2() {
		return this.xkId2;
	}

	public void setXkId2(Long xkId2) {
		this.xkId2 = xkId2;
	}

	@Column(name = "XK_ID3", precision = 16, scale = 0)
	public Long getXkId3() {
		return this.xkId3;
	}

	public void setXkId3(Long xkId3) {
		this.xkId3 = xkId3;
	}

	@Column(name = "GWL_ID", precision = 16, scale = 0)
	public Long getGwlId() {
		return this.gwlId;
	}

	public void setGwlId(Long gwlId) {
		this.gwlId = gwlId;
	}
	@Column(name = "BZLB_ID", precision = 16, scale = 0)
	public String getYrxs() {
		return yrxs;
	}

	public void setYrxs(String yrxs) {
		this.yrxs = yrxs;
	}

	@Column(name = "DQZT_ID", precision = 16, scale = 0)
	public Long getDqztId() {
		return this.dqztId;
	}

	public void setDqztId(Long dqztId) {
		this.dqztId = dqztId;
	}

	@Column(name = "DYWYYZ_ID", precision = 16, scale = 0)
	public Long getDywyyzId() {
		return this.dywyyzId;
	}

	public void setDywyyzId(Long dywyyzId) {
		this.dywyyzId = dywyyzId;
	}

	@Column(name = "DYWYSP_ID", precision = 16, scale = 0)
	public Long getDywyspId() {
		return this.dywyspId;
	}

	public void setDywyspId(Long dywyspId) {
		this.dywyspId = dywyspId;
	}

	@Column(name = "DEWYYZ_ID", precision = 16, scale = 0)
	public Long getDewyyzId() {
		return this.dewyyzId;
	}

	public void setDewyyzId(Long dewyyzId) {
		this.dewyyzId = dewyyzId;
	}

	@Column(name = "DEWYSP_ID", precision = 16, scale = 0)
	public Long getDewyspId() {
		return this.dewyspId;
	}

	public void setDewyspId(Long dewyspId) {
		this.dewyspId = dewyspId;
	}

	@Column(name = "XXDM", length = 12)
	public String getXxdm() {
		return this.xxdm;
	}

	public void setXxdm(String xxdm) {
		this.xxdm = xxdm;
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
	
	@Column(name = "JXZZJG_ID", precision = 16, scale = 0)
	public Long getJxzzjgId() {
		return this.jxzzjgId;
	}

	public void setJxzzjgId(Long jxzzjgId) {
		this.jxzzjgId = jxzzjgId;
	}
	
	@Column(name = "ZYJSZC_ID", precision = 16, scale = 0)
	public Long getZyjszcId() {
		return zyjszcId;
	}
	
	public void setZyjszcId(Long zyjszcId) {
		this.zyjszcId = zyjszcId;
	}
	@Column(name = "HTQX", length = 10)
	public String getHtqx() {
		return htqx;
	}

	public void setHtqx(String htqx) {
		this.htqx = htqx;
	}
	
	public Long getSfky() {
		return sfky;
	}
	@Column(name = "SFKY", length = 1)
	public void setSfky(Long sfky) {
		this.sfky = sfky;
	}
}