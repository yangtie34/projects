package com.jhkj.mosdc.output.po;

import java.util.List;

/***
 * 模板数据对象定义。
 * 
 * @Comments: 
 * Company: 河南精华科技有限公司
 * Created by zhangzg(vbxnt521_edu@163.com)
 * @DATE: 2012-11-9
 * @TIME: 上午11:19:31
 */
public class TemplateData {
	private Long templateId; // 模板id
	private String templateTitle; // 模板标题
	private String templateType; // 模板类型
	// 是不是要将组件列表再在这里设置一遍。方便转换成json格式数据
	private List<FunComponent> components;
    private List<FunctionArea> positionData;//组件位置数据集合
    
	public List<FunComponent> getComponents() {
		return components;
	}
	public void setComponents(List<FunComponent> components) {
		this.components = components;
	}
	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	public String getTemplateTitle() {
		return templateTitle;
	}
	public void setTemplateTitle(String templateTitle) {
		this.templateTitle = templateTitle;
	}
	public String getTemplateType() {
		return templateType;
	}
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	public List<FunctionArea> getPositionData() {
		return positionData;
	}
	public void setPositionData(List<FunctionArea> positionData) {
		this.positionData = positionData;
	}
	/**
	 * 构造函数。
	 * @param templateId
	 * @param templateTitle
	 * @param templateType
	 */
	public TemplateData(Long templateId, String templateTitle,
			String templateType) {
		super();
		this.templateId = templateId;
		this.templateTitle = templateTitle;
		this.templateType = templateType;
	}
	public TemplateData(Long templateId, String templateTitle,
			String templateType, List<FunctionArea> positionData) {
		super();
		this.templateId = templateId;
		this.templateTitle = templateTitle;
		this.templateType = templateType;
		this.positionData = positionData;
	}
	
}
