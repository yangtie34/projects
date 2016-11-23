package com.jhnu.syspermiss.permiss.entity;

import java.io.Serializable;

/**
 * 角色授权表
 */
public class RolePermssion implements Serializable {

	private static final long serialVersionUID = 4007185241593700143L;
	private Long id;
    private Role role;
    private Resources resource;
    private Operate operate;
    private DataServe dataServe;
    private String wirldcard;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Resources getResource() {
		return resource;
	}

	public void setResource(Resources resource) {
		this.resource = resource;
	}

	public Operate getOperate() {
		return operate;
	}

	public void setOperate(Operate operate) {
		this.operate = operate;
	}

	public DataServe getDataServe() {
		return dataServe;
	}

	public void setDataServe(DataServe dataServe) {
		this.dataServe = dataServe;
	}

	public String getWirldcard() {
		return wirldcard;
	}

	public void setWirldcard(String wirldcard) {
		this.wirldcard = wirldcard;
	}
    
}
