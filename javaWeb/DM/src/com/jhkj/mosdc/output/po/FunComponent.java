package com.jhkj.mosdc.output.po;

import java.util.Map;

/***
 * 组件数据类型定义
 * 
 * @Comments: 
 * Company: 河南精华科技有限公司
 * Created by zhangzg(vbxnt521_edu@163.com)
 * @DATE: 2012-11-9
 * @TIME: 上午11:24:01
 */
public class FunComponent {
	private Long componentId; //功能组件id
	private String componentType; // 功能组件前端显示的类型。
	private String style;// 功能组件样式。
	private Map displayData; // 显示数据
	private String name;// 功能组件名
	private String componentDetail;// 功能组件详述
	private String bz;//备注
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComponentDetail() {
		return componentDetail;
	}
	public void setComponentDetail(String componentDetail) {
		this.componentDetail = componentDetail;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	
	public Long getComponentId() {
		return componentId;
	}
	public void setComponentId(Long componentId) {
		this.componentId = componentId;
	}
	public String getComponentType() {
		return componentType;
	}
	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public Map getDisplayData() {
		return displayData;
	}
	public void setDisplayData(Map displayData) {
		this.displayData = displayData;
	}
	/***
	 * 构造函数。
	 * @param compoId
	 * @param compoType
	 * @param displayData
	 */
	public FunComponent(Long compoId, String compoType, Map displayData) {
		super();
		this.componentId = compoId;
		this.componentType = compoType;
		this.displayData = displayData;
	}
	public FunComponent() {
		super();
	}
	public FunComponent(Long compoId) {
		super();
		this.componentId = compoId;
	}
	public FunComponent(Long compoId, String compoType, String style,
			Map displayData) {
		super();
		this.componentId = compoId;
		this.componentType = compoType;
		this.style = style;
		this.displayData = displayData;
	}
	public FunComponent(Long compoId, String compoType, String style,
			Map displayData, String name, String componentDetail, String bz) {
		super();
		this.componentId = compoId;
		this.componentType = compoType;
		this.style = style;
		this.displayData = displayData;
		this.name = name;
		this.componentDetail = componentDetail;
		this.bz = bz;
	}
	
}
