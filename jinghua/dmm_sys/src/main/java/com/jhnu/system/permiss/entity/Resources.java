package com.jhnu.system.permiss.entity;

import java.io.Serializable;

public class Resources implements Serializable {
	private static final long serialVersionUID = 1332235345232013662L;
	private Long id;
    private String name_; //角色标识 程序中判断使用,如"admin"
    private String url_;
    private String description; //角色描述,UI界面显示使用
    private Long pid;
    private Integer level_;
    private String path_;
    private Integer istrue; //是否可用,如果不可用将不会添加给用户
    private Integer order_;
    private String keyWord;
    private String resource_type_code;//资源类型
    private String shiro_tag;//shiro类型资源标识符
    private String sysGroup_;//系统类型
    private Integer isShow;//系统类型
    public Resources() {
    }

    /**
	 * 构造可用的菜单
	 * @param istrue
	 */
	public Resources(Integer istrue) {
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
		if(url_==null){
			return "";
		}
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

	public String getPath_() {
		return path_;
	}

	public void setPath_(String path_) {
		this.path_ = path_;
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

	public String getResource_type_code() {
		return resource_type_code;
	}

	public void setResource_type_code(String resource_type_code) {
		this.resource_type_code = resource_type_code;
	}

	public String getShiro_tag() {
		return shiro_tag;
	}

	public void setShiro_tag(String shiro_tag) {
		this.shiro_tag = shiro_tag;
	}

	public String getSysGroup_() {
		return sysGroup_;
	}

	public void setSysGroup_(String sysGroup_) {
		this.sysGroup_ = sysGroup_;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	
	

    

}
