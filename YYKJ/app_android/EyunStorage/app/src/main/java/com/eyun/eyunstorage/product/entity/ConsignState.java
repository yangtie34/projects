package com.eyun.eyunstorage.product.entity;


public class ConsignState {

	// Fields

	private String recNumber;
	private Integer recType;
	private Integer recState;
	private String remark;
	private Double locationLng;
	private Double locationLat;
	private String scanTime;
	private Boolean isUpload;
	private String uploadTime;
	private Long createUserId;
	private Long createComBrId;
	private Long createComId;
	private String createIp;
	private String createTime;
	public String getRecNumber() {
		return recNumber;
	}
	public void setRecNumber(String recNumber) {
		this.recNumber = recNumber;
	}
	public Integer getRecType() {
		return recType;
	}
	public void setRecType(Integer recType) {
		this.recType = recType;
	}
	public Integer getRecState() {
		return recState;
	}
	public void setRecState(Integer recState) {
		this.recState = recState;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Double getLocationLng() {
		return locationLng;
	}
	public void setLocationLng(Double locationLng) {
		this.locationLng = locationLng;
	}
	public Double getLocationLat() {
		return locationLat;
	}
	public void setLocationLat(Double locationLat) {
		this.locationLat = locationLat;
	}
	public String getScanTime() {
		return scanTime;
	}
	public void setScanTime(String scanTime) {
		this.scanTime = scanTime;
	}
	public Boolean getIsUpload() {
		return isUpload;
	}
	public void setIsUpload(Boolean isUpload) {
		this.isUpload = isUpload;
	}
	public String getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
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
	public Long getCreateComId() {
		return createComId;
	}
	public void setCreateComId(Long createComId) {
		this.createComId = createComId;
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