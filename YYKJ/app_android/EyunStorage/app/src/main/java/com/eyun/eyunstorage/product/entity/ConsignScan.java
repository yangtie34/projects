package com.eyun.eyunstorage.product.entity;


public class ConsignScan {

	// Fields

	private Long scanId;
	private Integer scanType;
	private Integer scanErrorType;
	private Integer scanNumber;
	private String vehDispNumber;
	private Long vehNumber;
	private String recNumber;
	private Integer proNumber;
	private Boolean isUpload;
	private String uploadTime;
	private Long createUserId;
	private Long createComBrId;
	private Long createComId;
	private String createIp;
	private String createTime;
	public Long getScanId() {
		return scanId;
	}
	public void setScanId(Long scanId) {
		this.scanId = scanId;
	}
	public Integer getScanType() {
		return scanType;
	}
	public void setScanType(Integer scanType) {
		this.scanType = scanType;
	}
	public Integer getScanErrorType() {
		return scanErrorType;
	}
	public void setScanErrorType(Integer scanErrorType) {
		this.scanErrorType = scanErrorType;
	}
	public Integer getScanNumber() {
		return scanNumber;
	}
	public void setScanNumber(Integer scanNumber) {
		this.scanNumber = scanNumber;
	}
	public String getVehDispNumber() {
		return vehDispNumber;
	}
	public void setVehDispNumber(String vehDispNumber) {
		this.vehDispNumber = vehDispNumber;
	}
	public Long getVehNumber() {
		return vehNumber;
	}
	public void setVehNumber(Long vehNumber) {
		this.vehNumber = vehNumber;
	}
	public String getRecNumber() {
		return recNumber;
	}
	public void setRecNumber(String recNumber) {
		this.recNumber = recNumber;
	}
	public Integer getProNumber() {
		return proNumber;
	}
	public void setProNumber(Integer proNumber) {
		this.proNumber = proNumber;
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