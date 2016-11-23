package com.jhkj.mosdc.framework.dto;

import java.util.HashMap;
import java.util.Map;

public class Stsx implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3471629693814467810L;
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
	 * 是否列表显示字段
	 */
	private Long sflbxszd;
	
	/**
	 * 是否列表字段
	 */
	private Long sflbzd;
	/**
	 * 是否下钻
	 */
	private Long sfxz;
	/**
	 * 列表显示属性，显示关联表名称字段
	 */
	private String lbxssx;
	/**
	 * 所属实体名
	 */
	private String ssstm;
	
	private Map data;
	
	@SuppressWarnings("unchecked")
	public Stsx(Long id, String sx, String sxzwm, 
			String sxxslx,String sxzjlx,Long sflbxszd,Long sflbzd,Long sfxz, String bmbstm,String bmbbzdm,String lbxssx,String ssstm) {
		this.id = id;
		this.sx = sx;
		this.sxzwm = sxzwm;
		this.sxxslx = sxxslx;
		this.sflbxszd = sflbxszd;
		this.sflbzd = sflbzd;
		this.sfxz = sfxz;
		this.lbxssx = lbxssx;
		this.ssstm = ssstm;
		if(null==sxzjlx){
			sxzjlx="textfield";
		}
		if(null==bmbstm){
			bmbstm="";
		}
		if(null==bmbbzdm){
			bmbbzdm="";
		}
		String url = "";
		if("combobox".equals(sxzjlx)){
			url="baseAction!queryBM.action?entityName="+bmbstm+"&bzdm="+bmbbzdm;
		}
		Map map = new HashMap();
		map.put("componentType", sxzjlx);
		map.put("fields", "['id','mc']");
		map.put("url", url);
		map.put("displayValue", "mc");
		map.put("displayField", "id");
		this.data = map;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSflbxszd() {
		return sflbxszd;
	}

	public void setSflbxszd(Long sflbxszd) {
		this.sflbxszd = sflbxszd;
	}

	public Long getSfxz() {
		return sfxz;
	}

	public void setSfxz(Long sfxz) {
		this.sfxz = sfxz;
	}

	public String getSx() {
		return sx;
	}

	public void setSx(String sx) {
		this.sx = sx;
	}

	public String getSxxslx() {
		return sxxslx;
	}

	public void setSxxslx(String sxxslx) {
		this.sxxslx = sxxslx;
	}

	public String getSxzwm() {
		return sxzwm;
	}

	public void setSxzwm(String sxzwm) {
		this.sxzwm = sxzwm;
	}

	public Map getData() {
		return data;
	}

	public void setData(Map data) {
		this.data = data;
	}

	public String getLbxssx() {
		if(null!=lbxssx&&!"".equals(lbxssx.trim())){
			return ssstm+"."+lbxssx;
		}else{
			return lbxssx;
		}
	}

	public void setLbxssx(String lbxssx) {
		this.lbxssx = lbxssx;
	}

	public Long getSflbzd() {
		return sflbzd;
	}

	public void setSflbzd(Long sflbzd) {
		this.sflbzd = sflbzd;
	}

}