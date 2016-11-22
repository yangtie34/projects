package com.jhnu.system.task.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "T_SYS_SCHEDULE_GROUP")
public class Group {
	
	private String id;
	
	private String url_;
	@Column(name = "ID")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "URL_")
	public String getUrl_() {
		return url_;
	}

	public void setUrl_(String url_) {
		this.url_ = url_;
	}
}
