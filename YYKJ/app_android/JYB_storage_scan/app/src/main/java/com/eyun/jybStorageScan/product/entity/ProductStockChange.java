package com.eyun.jybStorageScan.product.entity;


public class ProductStockChange {

	// Fields

	private Integer changeType;
	private String recNumber;
	private Long proId;
	private Long changeId;
	private String recTime;
	private Integer proNumber;
	private Long fromComId;
	private Long fromComBrId;
	private Long toComId;
	private Long toComBrId;
	private Boolean isBalanceStock;
	private String balanceStockTime;
	private Long createUserId;
	private Long createComBrId;
	private Long createComId;
	private String createIp;
	private String createTime;
	public Integer getChangeType() {
		return changeType;
	}
	public void setChangeType(Integer changeType) {
		this.changeType = changeType;
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
	public Long getChangeId() {
		return changeId;
	}
	public void setChangeId(Long changeId) {
		this.changeId = changeId;
	}
	public String getRecTime() {
		return recTime;
	}
	public void setRecTime(String recTime) {
		this.recTime = recTime;
	}
	public Integer getProNumber() {
		return proNumber;
	}
	public void setProNumber(Integer proNumber) {
		this.proNumber = proNumber;
	}
	public Long getFromComId() {
		return fromComId;
	}
	public void setFromComId(Long fromComId) {
		this.fromComId = fromComId;
	}
	public Long getFromComBrId() {
		return fromComBrId;
	}
	public void setFromComBrId(Long fromComBrId) {
		this.fromComBrId = fromComBrId;
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