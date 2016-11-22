package com.jhnu.product.wechat.parent.band.entity;

import java.io.Serializable;

public class Band implements Serializable{
	
	private static final long serialVersionUID = -8898541699087115507L;

	private Long id;
	
	private String weChat_no;
	
	private String stu_id;
	
	private String band_time;
	
	private String stu_name;
	
	private String stu_idno;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWeChat_no() {
		return weChat_no;
	}

	public void setWeChat_no(String weChat_no) {
		this.weChat_no = weChat_no;
	}

	public String getStu_id() {
		return stu_id;
	}

	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}

	public String getStu_name() {
		return stu_name;
	}

	public void setStu_name(String stu_name) {
		this.stu_name = stu_name;
	}

	public String getBand_time() {
		return band_time;
	}

	public void setBand_time(String band_time) {
		this.band_time = band_time;
	}

	public String getStu_idno() {
		return stu_idno;
	}

	public void setStu_idno(String stu_idno) {
		this.stu_idno = stu_idno;
	}

}
