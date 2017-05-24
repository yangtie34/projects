package com.eyun.eyunstorage.product.entity;


public class StorageOutRecScan implements java.io.Serializable {

	// Fields

	private Long scanId;
	private Integer scanType;
	private Integer scanErrorType;
	private Integer scanNumber;
	private Long StorLocaCode;
	private Long StorLocaComBrID;

	private String recNumber;
	private Long proId;
	private String proName;
	private Integer proNumber;
	private Boolean isUpload;
	private String uploadTime;
	private Long createUserId;
	private Long createComBrId;
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
	public String getRecNumber() {
		return recNumber;
	}
	public void setRecNumber(String recNumber) {
		this.recNumber = recNumber;
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

	public Long getStorLocaCode() {
		return StorLocaCode;
	}

	public void setStorLocaCode(Long storLocaCode) {
		StorLocaCode = storLocaCode;
	}

	public Long getStorLocaComBrID() {
		return StorLocaComBrID;
	}

	public void setStorLocaComBrID(Long storLocaComBrID) {
		StorLocaComBrID = storLocaComBrID;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}
}