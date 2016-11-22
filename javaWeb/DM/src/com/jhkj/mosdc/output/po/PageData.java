package com.jhkj.mosdc.output.po;

import java.util.List;

/***
 * 页面数据实体类
 * 
 * @Comments: Company: 河南精华科技有限公司 
 * Created by wangyongtai(490091105@.com)
 * @DATE:2012-11-2
 * @TIME: 下午3:57:43
 */
public class PageData {
	private Long id;// id
	private String title;// 页面标题
	private List<TemplateData> templates; // 模板列表。

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<TemplateData> getTemplates() {
		return templates;
	}

	public void setTemplates(List<TemplateData> templates) {
		this.templates = templates;
	}

	/**
	 * 构造函数。
	 * 
	 * @param id
	 *            页面id
	 */
	public PageData(Long id) {
		super();
		this.id = id;
	}
	
	public PageData(Long id, String title, List<TemplateData> templates) {
		super();
		this.id = id;
		this.title = title;
		this.templates = templates;
	}

	/**
	 * 空构造。
	 */
	public PageData() {

	}
}
