package com.jhnu.system.log.entity;

import java.io.Serializable;

public class UserLoginLog implements Serializable{
	
	private static final long serialVersionUID = 7117641648495853910L;
	
	private String id;
	private String username;
	private String login_time;
	private String login_way_code;
	private String login_type_code;
	private String login_ip;
	
	//相当于PageBean
	private String login_way_name;
	private String login_type_name;
	
	//相当于FromBean
	private String login_time_start;
	private String login_time_end;
	
	
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
	public String getLogin_time() {
		return login_time;
	}
	public void setLogin_time(String login_time) {
		this.login_time = login_time;
	}
	public String getLogin_way_code() {
		return login_way_code;
	}
	public void setLogin_way_code(String login_way_code) {
		this.login_way_code = login_way_code;
	}
	public String getLogin_type_code() {
		return login_type_code;
	}
	public void setLogin_type_code(String login_type_code) {
		this.login_type_code = login_type_code;
	}
	public String getLogin_ip() {
		return login_ip;
	}
	public void setLogin_ip(String login_ip) {
		this.login_ip = login_ip;
	}
	public String getLogin_way_name() {
		return login_way_name;
	}
	public void setLogin_way_name(String login_way_name) {
		this.login_way_name = login_way_name;
	}
	public String getLogin_type_name() {
		return login_type_name;
	}
	public void setLogin_type_name(String login_type_name) {
		this.login_type_name = login_type_name;
	}
	public String getLogin_time_start() {
		return login_time_start;
	}
	public void setLogin_time_start(String login_time_start) {
		this.login_time_start = login_time_start;
	}
	public String getLogin_time_end() {
		return login_time_end;
	}
	public void setLogin_time_end(String login_time_end) {
		this.login_time_end = login_time_end;
	}
	
}

