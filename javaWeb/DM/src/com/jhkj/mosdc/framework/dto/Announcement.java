package com.jhkj.mosdc.framework.dto;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * 公告类
 * @author Administrator
 *
 */
public class Announcement {
	private Long id;
	private String title;
	private String content;
	private String fbsj;
	private String fbr;
	private String cjsj;
	private String cjr;
	private String xgsj;
	private String xgr;
	private Boolean sfky;
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "TITLE", length = 100)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name = "CONTENT", length = 2000)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(name = "FBSJ", length = 30)
	public String getFbsj() {
		return fbsj;
	}
	public void setFbsj(String fbsj) {
		this.fbsj = fbsj;
	}

	@Column(name = "FBR", length = 50)
	public String getFbr() {
		return fbr;
	}
	public void setFbr(String fbr) {
		this.fbr = fbr;
	}

	@Column(name = "CJSJ", length = 20)
	public String getCjsj() {
		return cjsj;
	}
	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
	}

	@Column(name = "CJR", length = 20)
	public String getCjr() {
		return cjr;
	}
	public void setCjr(String cjr) {
		this.cjr = cjr;
	}

	@Column(name = "XGSJ", length = 50)
	public String getXgsj() {
		return xgsj;
	}
	public void setXgsj(String xgsj) {
		this.xgsj = xgsj;
	}

	@Column(name = "XGR", length = 50)
	public String getXgr() {
		return xgr;
	}
	public void setXgr(String xgr) {
		this.xgr = xgr;
	}

	@Column(name = "SFKY", precision = 1, scale = 0)
	public Boolean getSfky() {
		return sfky;
	}
	public void setSfky(Boolean sfky) {
		this.sfky = sfky;
	}
	
}
