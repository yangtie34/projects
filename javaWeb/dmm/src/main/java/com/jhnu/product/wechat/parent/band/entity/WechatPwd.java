package com.jhnu.product.wechat.parent.band.entity;

import java.io.Serializable;

public class WechatPwd implements Serializable{

	private static final long serialVersionUID = -8485288755910816845L;
	
	private String stu_id;
	
	private String pwd;
	
	private String phone_no;
	
	private Integer is_change=0;
	
	
	
	public WechatPwd() {
		super();
	}

	public WechatPwd(String stu_id, String pwd, String phone_no) {
		super();
		this.stu_id = stu_id;
		this.pwd = pwd;
		this.phone_no = phone_no;
	}

	public String getStu_id() {
		return stu_id;
	}

	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPhone_no() {
		return phone_no;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

	public Integer getIs_change() {
		return is_change;
	}

	public void setIs_change(Integer is_change) {
		this.is_change = is_change;
	}

}
