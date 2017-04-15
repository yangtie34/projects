package com.eyun.jybfreightscan.product.entity;



/**
 * StorageInRec entity. @author MyEclipse Persistence Tools
 */

public class StorageInRec implements java.io.Serializable {

	// Fields

	private String recNumber;
	private String recTime;
	private Integer recSource;
	private Integer recState;
	private Double recMoney;
	private Double recActualMoney;
	private Long comCusID;

	private Long comSuppId;
	private String contactName;
	private String contactPhone;
	private Long contactArea;
	private String contactAddress;
	private Long handleUserId;
	private Long toComId;
	private Long toComBrId;
	private String remark;
	private Boolean isBlindReceipt;
	private Boolean isUpload;
	private String uploadTime;
	private Boolean isBalanceStock;
	private String balanceStockTime;
	private String settlementDate;
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
	public String getRecTime() {
		return recTime;
	}
	public void setRecTime(String recTime) {
		this.recTime = recTime;
	}
	public Integer getRecSource() {
		return recSource;
	}
	public void setRecSource(Integer recSource) {
		this.recSource = recSource;
	}
	public Integer getRecState() {
		return recState;
	}
	public void setRecState(Integer recState) {
		this.recState = recState;
	}
	public Double getRecMoney() {
		return recMoney;
	}
	public void setRecMoney(Double recMoney) {
		this.recMoney = recMoney;
	}
	public Double getRecActualMoney() {
		return recActualMoney;
	}
	public void setRecActualMoney(Double recActualMoney) {
		this.recActualMoney = recActualMoney;
	}
	public Long getComSuppId() {
		return comSuppId;
	}
	public void setComSuppId(Long comSuppId) {
		this.comSuppId = comSuppId;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public Long getContactArea() {
		return contactArea;
	}
	public void setContactArea(Long contactArea) {
		this.contactArea = contactArea;
	}
	public String getContactAddress() {
		return contactAddress;
	}
	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}
	public Long getHandleUserId() {
		return handleUserId;
	}
	public void setHandleUserId(Long handleUserId) {
		this.handleUserId = handleUserId;
	}
	public Long getToComId() {
		return toComId;
	}
	public void setToComId(Long toComId) {
		this.toComId = toComId;
	}
	public Long getToComBrId() {
		return toComBrId;
	}
	public void setToComBrId(Long toComBrId) {
		this.toComBrId = toComBrId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Boolean getIsBlindReceipt() {
		return isBlindReceipt;
	}
	public void setIsBlindReceipt(Boolean isBlindReceipt) {
		this.isBlindReceipt = isBlindReceipt;
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
	public Boolean getIsBalanceStock() {
		return isBalanceStock;
	}
	public void setIsBalanceStock(Boolean isBalanceStock) {
		this.isBalanceStock = isBalanceStock;
	}
	public String getBalanceStockTime() {
		return balanceStockTime;
	}
	public void setBalanceStockTime(String balanceStockTime) {
		this.balanceStockTime = balanceStockTime;
	}
	public String getSettlementDate() {
		return settlementDate;
	}
	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
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


	public Long getComCusID() {
		return comCusID;
	}

	public void setComCusID(Long comCusID) {
		this.comCusID = comCusID;
	}
}