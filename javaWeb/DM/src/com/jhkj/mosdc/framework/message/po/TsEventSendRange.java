package com.jhkj.mosdc.framework.message.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="TS_EVENT_SEND_RANGE")
public class TsEventSendRange {

	private Long id;
	private Long eventId;
	private Boolean readYet;
	private String readTime;
	private String rangeType;
	private Long rangeId;
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "EVENT_ID", precision = 16, scale = 0)
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	@Column(name = "READ_YET", precision = 1, scale = 0)
	public Boolean getReadYet() {
		return readYet;
	}
	public void setReadYet(Boolean readYet) {
		this.readYet = readYet;
	}
	@Column(name = "READ_TIME",  length = 60)
	public String getReadTime() {
		return readTime;
	}
	public void setReadTime(String readTime) {
		this.readTime = readTime;
	}
	@Column(name = "RANGE_TYPE", length = 60)
	public String getRangeType() {
		return rangeType;
	}
	public void setRangeType(String rangeType) {
		this.rangeType = rangeType;
	}
	@Column(name = "RANGE_ID", precision = 16, scale = 0)
	public Long getRangeId() {
		return rangeId;
	}
	public void setRangeId(Long rangeId) {
		this.rangeId = rangeId;
	}
	
}
