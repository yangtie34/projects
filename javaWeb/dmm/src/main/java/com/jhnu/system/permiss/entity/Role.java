package com.jhnu.system.permiss.entity;

import java.io.Serializable;

public class Role implements Serializable {
	private static final long serialVersionUID = 1332235345232013662L;
	private Long id;
    private String name_; //角色标识 程序中判断使用,如"admin"
    private String description; //角色描述,UI界面显示使用
    private Integer istrue ; //是否可用,如果不可用将不会添加给用户
    private String role_type;
    private Integer ismain ; //是否基本教师
    private Long resourceid ; //基本角色主页
    
    private String name_OrDescription;
    public Role() {
    }

    public Role(String name, String description, Integer istrue) {
        this.name_ = name;
        this.description = description;
        this.istrue = istrue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public String getName_() {
		return name_;
	}

	public void setName_(String name) {
		this.name_ = name;
	}

	public Integer getIstrue() {
		return istrue;
	}

	public void setIstrue(Integer istrue) {
		this.istrue = istrue;
	}

	public String getRole_type() {
		return role_type;
	}

	public void setRole_type(String role_type) {
		this.role_type = role_type;
	}

	public Integer getIsmain() {
		return ismain;
	}

	public void setIsmain(Integer ismain) {
		this.ismain = ismain;
	}

	public Long getResourceid() {
		return resourceid;
	}

	public void setResourceid(Long resourceid) {
		this.resourceid = resourceid;
	}

	public String getName_OrDescription() {
		return name_OrDescription;
	}

	public void setName_OrDescription(String name_OrDescription) {
		this.name_OrDescription = name_OrDescription;
	}

}
