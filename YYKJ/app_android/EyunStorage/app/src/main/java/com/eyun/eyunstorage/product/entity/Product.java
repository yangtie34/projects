package com.eyun.eyunstorage.product.entity;


public class Product implements java.io.Serializable {

	// Fields

	private Long proId;
	private String proCode;
	private String proBarCode;
	private String proName;
	private Long proCategory;
	private Long proSpec;
	private Integer proStockNumber;
	private Integer proStockMaxNumber;
	private Integer proStockMinNumber;
	private String proMeasureUnitName;
	private Double proWeight;
	private Double purchasePrice;
	private Double salesPrice;
	private Double promotionPrice;
	private String remark;
	private Integer auditState;
	private Boolean isStorageRack;
	private String storageRackTime;
	private Boolean isOnlineShopping;
	private Boolean isReturnPromise;
	private Long vestingComId;
	private Long vestingComBrId;
	private Long vestingComCusID;
	private Long createUserId;
	private Long createComBrId;
	private Long createComId;
	private String createIp;
	private String createTime;
	public Long getProId() {
		return proId;
	}
	public void setProId(Long proId) {
		this.proId = proId;
	}
	public String getProCode() {
		return proCode;
	}
	public void setProCode(String proCode) {
		this.proCode = proCode;
	}
	public String getProBarCode() {
		return proBarCode;
	}
	public void setProBarCode(String proBarCode) {
		this.proBarCode = proBarCode;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public Long getProCategory() {
		return proCategory;
	}
	public void setProCategory(Long proCategory) {
		this.proCategory = proCategory;
	}
	public Long getProSpec() {
		return proSpec;
	}
	public void setProSpec(Long proSpec) {
		this.proSpec = proSpec;
	}
	public Integer getProStockNumber() {
		return proStockNumber;
	}
	public void setProStockNumber(Integer proStockNumber) {
		this.proStockNumber = proStockNumber;
	}
	public Integer getProStockMaxNumber() {
		return proStockMaxNumber;
	}
	public void setProStockMaxNumber(Integer proStockMaxNumber) {
		this.proStockMaxNumber = proStockMaxNumber;
	}
	public Integer getProStockMinNumber() {
		return proStockMinNumber;
	}
	public void setProStockMinNumber(Integer proStockMinNumber) {
		this.proStockMinNumber = proStockMinNumber;
	}
	public String getProMeasureUnitName() {
		return proMeasureUnitName;
	}
	public void setProMeasureUnitName(String proMeasureUnitName) {
		this.proMeasureUnitName = proMeasureUnitName;
	}
	public Double getProWeight() {
		return proWeight;
	}
	public void setProWeight(Double proWeight) {
		this.proWeight = proWeight;
	}
	public Double getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public Double getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(Double salesPrice) {
		this.salesPrice = salesPrice;
	}
	public Double getPromotionPrice() {
		return promotionPrice;
	}
	public void setPromotionPrice(Double promotionPrice) {
		this.promotionPrice = promotionPrice;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getAuditState() {
		return auditState;
	}
	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}
	public Boolean getIsStorageRack() {
		return isStorageRack;
	}
	public void setIsStorageRack(Boolean isStorageRack) {
		this.isStorageRack = isStorageRack;
	}
	public String getStorageRackTime() {
		return storageRackTime;
	}
	public void setStorageRackTime(String storageRackTime) {
		this.storageRackTime = storageRackTime;
	}
	public Boolean getIsOnlineShopping() {
		return isOnlineShopping;
	}
	public void setIsOnlineShopping(Boolean isOnlineShopping) {
		this.isOnlineShopping = isOnlineShopping;
	}
	public Boolean getIsReturnPromise() {
		return isReturnPromise;
	}
	public void setIsReturnPromise(Boolean isReturnPromise) {
		this.isReturnPromise = isReturnPromise;
	}
	public Long getVestingComId() {
		return vestingComId;
	}
	public void setVestingComId(Long vestingComId) {
		this.vestingComId = vestingComId;
	}
	public Long getVestingComBrId() {
		return vestingComBrId;
	}
	public void setVestingComBrId(Long vestingComBrId) {
		this.vestingComBrId = vestingComBrId;
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

	public Long getVestingComCusID() {
		return vestingComCusID;
	}

	public void setVestingComCusID(Long vestingComCusID) {
		this.vestingComCusID = vestingComCusID;
	}
}