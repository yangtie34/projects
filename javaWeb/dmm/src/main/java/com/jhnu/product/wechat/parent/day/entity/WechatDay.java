package com.jhnu.product.wechat.parent.day.entity;

import java.io.Serializable;

public class WechatDay implements Serializable{

	private static final long serialVersionUID = 7810693701430777071L;

	private Long id;

	private String stu_id;
	private String class_id;
	private String action_code;
	private String warn_level_code;
	private String address;
	private String action;
	private String start_time;
	private String end_time;
	private String action_date;
	private String exe_time;
	private String exe_from;
	
	
	public WechatDay() {
		super();
	}
	public WechatDay(String stu_id, String class_id,String action_date) {
		super();
		this.stu_id = stu_id;
		this.class_id = class_id;
		this.action_date = action_date;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStu_id() {
		return stu_id;
	}
	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}
	public String getAction_code() {
		return action_code;
	}
	public void setAction_code(String action_code) {
		this.action_code = action_code;
	}
	public String getWarn_level_code() {
		return warn_level_code;
	}
	public void setWarn_level_code(String warn_level_code) {
		this.warn_level_code = warn_level_code;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getAction_date() {
		return action_date;
	}
	public void setAction_date(String action_date) {
		this.action_date = action_date;
	}
	public String getExe_time() {
		return exe_time;
	}
	public void setExe_time(String exe_time) {
		this.exe_time = exe_time;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getExe_from() {
		return exe_from;
	}
	public void setExe_from(String exe_from) {
		this.exe_from = exe_from;
	}
	public String getClass_id() {
		return class_id;
	}
	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}
	
}
