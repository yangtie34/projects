package com.jhnu.system.log.entity;

import java.io.Serializable;

public class ChangePwdLog implements Serializable{
	
	private static final long serialVersionUID = 4552644241050443856L;
	
	private String id;
	private String username;
	private String exc_time;
	private String exc_username;
	private String exc_ip;
	private String exc_type_code;
	
	//相当于PageBean
	private String exc_type_name;
	
	//相当于FromBean
	private String exc_time_start;
	private String exc_time_end;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getExc_time() {
		return exc_time;
	}
	public void setExc_time(String exc_time) {
		this.exc_time = exc_time;
	}
	public String getExc_username() {
		return exc_username;
	}
	public void setExc_username(String exc_username) {
		this.exc_username = exc_username;
	}
	public String getExc_ip() {
		return exc_ip;
	}
	public void setExc_ip(String exc_ip) {
		this.exc_ip = exc_ip;
	}
	public String getExc_type_code() {
		return exc_type_code;
	}
	public void setExc_type_code(String exc_type_code) {
		this.exc_type_code = exc_type_code;
	}
	public String getExc_type_name() {
		return exc_type_name;
	}
	public void setExc_type_name(String exc_type_name) {
		this.exc_type_name = exc_type_name;
	}
	public String getExc_time_start() {
		return exc_time_start;
	}
	public void setExc_time_start(String exc_time_start) {
		this.exc_time_start = exc_time_start;
	}
	public String getExc_time_end() {
		return exc_time_end;
	}
	public void setExc_time_end(String exc_time_end) {
		this.exc_time_end = exc_time_end;
	}
	
}

