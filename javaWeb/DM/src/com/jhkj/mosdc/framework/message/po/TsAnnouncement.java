package com.jhkj.mosdc.framework.message.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 公告表
 * @author Administrator
 *
 */
@Entity
@Table(name = "TS_ANNOUNCEMENT")
public class TsAnnouncement {

	private Long id;
	private String title;//标题
	private String content;
	private Long jzgId;//创建人ID
	private String jzgXm;//创建人姓名
	private String createTime;
	private String sendTime;
	private Integer readTimes;//阅读次数
	private Long zzjgId;//发送范围,即组织结构ID
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
	@Column(name = "JZG_ID", length = 60)
	public Long getJzgId() {
		return jzgId;
	}
	public void setJzgId(Long jzgId) {
		this.jzgId = jzgId;
	}
	@Column(name = "JZG_XM", length = 60)
	public String getJzgXm() {
		return jzgXm;
	}
	public void setJzgXm(String jzgXm) {
		this.jzgXm = jzgXm;
	}
	@Column(name = "CREATE_TIME", length = 60)
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Column(name = "SEND_TIME", length = 60)
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	@Column(name = "READ_TIMES", precision = 16, scale = 0)
	public Integer getReadTimes() {
		return readTimes;
	}
	public void setReadTimes(Integer readTimes) {
		this.readTimes = readTimes;
	}
	@Column(name = "ZZJG_ID", precision = 16, scale = 0)
	public Long getZzjgId() {
		return zzjgId;
	}
	public void setZzjgId(Long zzjgId) {
		this.zzjgId = zzjgId;
	}

	
}
