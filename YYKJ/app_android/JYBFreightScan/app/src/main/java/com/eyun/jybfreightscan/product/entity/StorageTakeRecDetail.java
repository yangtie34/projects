package com.eyun.jybfreightscan.product.entity;


public class StorageTakeRecDetail {

	// Fields

	private String recNumber;
	private Long proId;
	private Long detailId;
	private String proName;
	private Long proCategory;
	private String proCategoryName;
	private Long proSpec;
	private String proSpecName;
	private String proMeasureUnitName;
	private Integer proNumber;
	private Integer proBookNumber;
	private Integer proDifferNumber;
	private String remark;
	private Boolean isUpload;
	private String uploadTime;
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
	public Integer getProBookNumber() {
		return proBookNumber;
	}
	public void setProBookNumber(Integer proBookNumber) {
		this.proBookNumber = proBookNumber;
	}
	public Integer getProDifferNumber() {
		return proDifferNumber;
	}
	public void setProDifferNumber(Integer proDifferNumber) {
		this.proDifferNumber = proDifferNumber;
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

}