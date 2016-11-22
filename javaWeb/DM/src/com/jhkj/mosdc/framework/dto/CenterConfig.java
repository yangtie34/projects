package com.jhkj.mosdc.framework.dto;

import java.util.ArrayList;
import java.util.List;

public class CenterConfig {
	// id
	private Long id;
	// 父节点id
	private Long fjdid;
	// 层次
	private int cc;
	// 层次类型
	private String cclx;
	// 是否叶子节点
	private String sfyzjd;
	// 备注
	private String bz;
	// 列序号
	private int lxh;
	// 列中序号
	private int lzxh;
	// 栏目id
	private Long lmid;
	// 模板id
	private Long mbid;
	// 子孙节点
	private List<CenterConfig> childs = new ArrayList<CenterConfig>();
	
	/*追加属性,方便前台解析*/
	private ICenterTemplate ict = null;// 该节点关联的模板数据对象。没有则为空。
	private ICenterPortlet icp=null;// 该节点关联的组件数据对象。没有则为空。
	
	public Long getLmid() {
		return lmid;
	}
	public void setLmid(Long lmid) {
		this.lmid = lmid;
	}
	public Long getMbid() {
		return mbid;
	}
	public void setMbid(Long mbid) {
		this.mbid = mbid;
	}
	public List<CenterConfig> getChilds() {
		return childs;
	}
	public void setChilds(List<CenterConfig> childs) {
		this.childs = childs;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFjdid() {
		return fjdid;
	}
	public void setFjdid(Long fjdid) {
		this.fjdid = fjdid;
	}
	public int getCc() {
		return cc;
	}
	public void setCc(int cc) {
		this.cc = cc;
	}
	public String getCclx() {
		return cclx;
	}
	public void setCclx(String cclx) {
		this.cclx = cclx;
	}
	public String getSfyzjd() {
		return sfyzjd;
	}
	public void setSfyzjd(String sfyzjd) {
		this.sfyzjd = sfyzjd;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public int getLxh() {
		return lxh;
	}
	public void setLxh(int lxh) {
		this.lxh = lxh;
	}
	public int getLzxh() {
		return lzxh;
	}
	public void setLzxh(int lzxh) {
		this.lzxh = lzxh;
	}
	public ICenterTemplate getIct() {
		return ict;
	}
	public void setIct(ICenterTemplate ict) {
		this.ict = ict;
	}
	public ICenterPortlet getIcp() {
		return icp;
	}
	public void setIcp(ICenterPortlet icp) {
		this.icp = icp;
	}
	public CenterConfig(Long id, Long fjdid, int cc, String cclx,
			String sfyzjd, String bz, int lxh, int lzxh, Long lmid, Long mbid) {
		super();
		this.id = id;
		this.fjdid = fjdid;
		this.cc = cc;
		this.cclx = cclx;
		this.sfyzjd = sfyzjd;
		this.bz = bz;
		this.lxh = lxh;
		this.lzxh = lzxh;
		this.lmid = lmid;
		this.mbid = mbid;
	}
	public CenterConfig() {
		super();
	}
	
}
