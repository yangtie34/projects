package com.eyun.eyunstorage.product.entity;


public class StorageOutRecDetail implements java.io.Serializable {

	// Fields

	private String recNumber;
	private Long proId;
	private Long detailId;
	private String proBarCode;
	private String proName;
	private Long proCategory;
	private String proCategoryName;
	private Long proSpec;
	private String proSpecName;
	private String proMeasureUnitName;
	private Integer proNumber;
	private Double proPrice;
	private Double proMoney;
	private String remark;
	private Boolean isUpload;
	private String uploadTime;
	private Boolean isBalanceStock;
	private String balanceStockTime;
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
	public Long getDetailId() {
		return detailId;
	}
	public void setDetailId(Long detailId) {
		this.detailId = detailId;
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
	public String getProCategoryName() {
		return proCategoryName;
	}
	public void setProCategoryName(String proCategoryName) {
		this.proCategoryName = proCategoryName;
	}
	public Long getProSpec() {
		return proSpec;
	}
	public void setProSpec(Long proSpec) {
		this.proSpec = proSpec;
	}
	public String getProSpecName() {
		return proSpecName;
	}
	public void setProSpecName(String proSpecName) {
		this.proSpecName = proSpecName;
	}
	public String getProMeasureUnitName() {
		return proMeasureUnitName;
	}
	public void setProMeasureUnitName(String proMeasureUnitName) {
		this.proMeasureUnitName = proMeasureUnitName;
	}
	public Integer getProNumber() {
		return proNumber;
	}
	public void setProNumber(Integer proNumber) {
		this.proNumber = proNumber;
	}
	public Double getProPrice() {
		return proPrice;
	}
	public void setProPrice(Double proPrice) {
		this.proPrice = proPrice;
	}
	public Double getProMoney() {
		return proMoney;
	}
	public void setProMoney(Double proMoney) {
		this.proMoney = proMoney;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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


}