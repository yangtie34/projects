package com.jhnu.product.wechat.parent.menu.entity;

import java.io.Serializable;

public class WechatMenu implements Serializable{

	private static final long serialVersionUID = -732603931192555065L;

	private Long id;
	
	private String name_;
	
	private String url_;
	
	private String description;
	
	private Integer istrue = 0; 
	
	private Integer order_;
	
	private String keyWord;
	
	private Long pid;
	
	private Integer level_;
	
	private String code;
	
	private String path;
	
	
	public WechatMenu(){
		
	}
	
	/**
	 * 构造可用的菜单
	 * @param istrue
	 */
	public WechatMenu(Integer istrue) {
		super();
		this.istrue = istrue;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName_() {
		return name_;
	}

	public void setName_(String name_) {
		this.name_ = name_;
	}

	public String getUrl_() {
		return url_;
	}

	public void setUrl_(String url_) {
		this.url_ = url_;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIstrue() {
		return istrue;
	}

	public void setIstrue(Integer istrue) {
		this.istrue = istrue;
	}

	public Integer getOrder_() {
		return order_;
	}

	public void setOrder_(Integer order_) {
		this.order_ = order_;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Integer getLevel_() {
		return level_;
	}

	public void setLevel_(Integer level_) {
		this.level_ = level_;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
