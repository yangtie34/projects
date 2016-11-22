package com.jhkj.mosdc.framework.message.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ts_msg_suggest")
public class TsMsgSuggest {
	private Long id;
	private String title;
	private String content;
	private String createrName;
	private Long createrId;
	private Boolean isReply;
	private String replyContent;
	private String replyDate;
	private String createDate;
	private Boolean sfky;
	private Boolean sfmy;
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "TITLE", length = 200)
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
	@Column(name = "CREATER_NAME", length = 60)
	public String getCreaterName() {
		return createrName;
	}
	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}
	@Column(name = "CREATER_ID", precision = 16, scale = 0)
	public Long getCreaterId() {
		return createrId;
	}
	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}
	@Column(name = "IS_REPLY", precision = 1, scale = 0)
	public Boolean getIsReply() {
		return isReply;
	}
	public void setIsReply(Boolean isReply) {
		this.isReply = isReply;
	}
	@Column(name = "REPLY_CONTENT", length = 2000)
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	@Column(name = "REPLY_DATE", length = 60)
	public String getReplyDate() {
		return replyDate;
	}
	public void setReplyDate(String replyDate) {
		this.replyDate = replyDate;
	}
	@Column(name = "CREATE_DATE", length = 60)
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	@Column(name = "SFKY", precision = 1, scale = 0)
	public Boolean getSfky() {
		return sfky;
	}
	public void setSfky(Boolean sfky) {
		this.sfky = sfky;
	}
	@Column(name = "SFMY", precision = 1, scale = 0)
	public Boolean getSfmy() {
		return sfmy;
	}
	public void setSfmy(Boolean sfmy) {
		this.sfmy = sfmy;
	}
	
}
