package com.jhkj.mosdc.framework.message.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name = "ts_Letter")
public class TsLetter {

	private Long id;
	private String title;
	private String content;
	private String sendTime;
	private Boolean isReply;
	private Long replyLetterId;
	private String cjsj;
	private String xgsj;
	private Long jzgId;//创建人ID
	private String cjr;
	private String xgr;
	private Boolean sfky;
	private String sjrmc;
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
	@Column(name = "CONTENT", length = 4000)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(name = "SEND_TIME", length = 60)
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	@Column(name = "IS_REPLY", precision = 1, scale = 0)
	public Boolean getIsReply() {
		return isReply;
	}
	public void setIsReply(Boolean isReply) {
		this.isReply = isReply;
	}
	@Column(name = "REPLY_LETTER_ID", precision = 16, scale = 0)
	public Long getReplyLetterId() {
		return replyLetterId;
	}
	public void setReplyLetterId(Long replyLetterId) {
		this.replyLetterId = replyLetterId;
	}
	@Column(name = "CJSJ", length = 60)
	public String getCjsj() {
		return this.cjsj;
	}

	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
	}
	@Column(name = "XGSJ", length = 60)
	public String getXgsj() {
		return xgsj;
	}

	public void setXgsj(String xgsj) {
		this.xgsj = xgsj;
	}
	@Column(name = "CJR", length = 60)
	public String getCjr() {
		return cjr;
	}

	public void setCjr(String cjr) {
		this.cjr = cjr;
	}
	@Column(name = "XGR", length = 60)
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
	@Column(name = "JZG_ID", precision = 16, scale = 0)
	public Long getJzgId() {
		return jzgId;
	}
	public void setJzgId(Long jzgId) {
		this.jzgId = jzgId;
	}
	@Column(name = "SJRMC", length = 60)
	public String getSjrmc() {
		return sjrmc;
	}
	public void setSjrmc(String sjrmc) {
		this.sjrmc = sjrmc;
	}
	
}
