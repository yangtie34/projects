package com.jhkj.mosdc.output.po;

import java.util.List;

/***
 * 功能区数据类型定义。
 * 
 * @Comments: 
 * Company: 河南精华科技有限公司
 * Created by zhangzg(vbxnt521_edu@163.com)
 * @DATE: 2012-11-9
 * @TIME: 上午11:27:14
 */
public class FunctionArea {
	private Long funAreaId;
	private String funAreaTitle;
	private String helpInfo;// 功能区帮助信息。
	
	public String getHelpInfo() {
		return helpInfo;
	}
	public void setHelpInfo(String helpInfo) {
		this.helpInfo = helpInfo;
	}
	private List<FunComponent> comps;
	public Long getFunAreaId() {
		return funAreaId;
	}
	public void setFunAreaId(Long funAreaId) {
		this.funAreaId = funAreaId;
	}
	public String getFunAreaTitle() {
		return funAreaTitle;
	}
	public void setFunAreaTitle(String funAreaTitle) {
		this.funAreaTitle = funAreaTitle;
	}
	public List<FunComponent> getComps() {
		return comps;
	}
	public void setComps(List<FunComponent> comps) {
		this.comps = comps;
	}
//	/**
//	 *  构造函数。
//	 * @param funAreaId
//	 * @param funAreaTitle
//	 * @param comps
//	 */
//	public FunctionArea(Long funAreaId, String funAreaTitle,
//			List<FunComponent> comps) {
//		super();
//		this.funAreaId = funAreaId;
//		this.funAreaTitle = funAreaTitle;
//		this.comps = comps;
//	}
	public FunctionArea(Long funAreaId, String funAreaTitle) {
		super();
		this.funAreaId = funAreaId;
		this.funAreaTitle = funAreaTitle;
	}
	public FunctionArea() {
		super();
	}
	public FunctionArea(Long funAreaId, String funAreaTitle, String helpInfo,
			List<FunComponent> comps) {
		super();
		this.funAreaId = funAreaId;
		this.funAreaTitle = funAreaTitle;
		this.helpInfo = helpInfo;
		this.comps = comps;
	}
}
