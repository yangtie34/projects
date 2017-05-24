package com.eyun.eyunstorage.product.entity;


public class StorageLocationChange {

	// Fields
	private Long changeId;
	private Integer changeType;
	private Long storLocaCode;
	private Long storLocaComBrId;
	private Long proId;
	private Integer proNumber;
	private Long createUserId;
	private Long createComBrId;
	private String createIp;
	private String createTime;
	public Long getChangeId() {
		return changeId;
	}
	public void setChangeId(Long changeId) {
		this.changeId = changeId;
	}
	public Integer getChangeType() {
		return changeType;
	}
	public void setChangeType(Integer changeType) {
		this.changeType = changeType;
	}
	public Long getStorLocaCode() {
		return storLocaCode;
	}
	public void setStorLocaCode(Long storLocaCode) {
		this.storLocaCode = storLocaCode;
	}
	public Long getStorLocaComBrId() {
		return storLocaComBrId;
	}
	public void setStorLocaComBrId(Long storLocaComBrId) {
		this.storLocaComBrId = storLocaComBrId;
	}
	public Long getProId() {
		return proId;
	}
	public void setProId(Long proId) {
		this.proId = proId;
	}
	public Integer getProNumber() {
		return proNumber;
	}
	public void setProNumber(Integer proNumber) {
		this.proNumber = proNumber;
	}
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	public Long getCreateComBrId() {
		return createComBrId;
	}
	public void setCreateComBrId(Long createComBrId) {
		this.createComBrId = createComBrId;
	}
	public String getCreateIp() {
		return createIp;
	}
	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}


}