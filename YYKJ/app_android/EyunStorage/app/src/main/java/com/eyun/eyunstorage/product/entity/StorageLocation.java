package com.eyun.eyunstorage.product.entity;


public class StorageLocation {

	// Fields

	private String name;
	private Long parentCode;
	private Long comBrId;
	private Long code;
	private Integer orderNumber;
	private Boolean disabled;
	private String locaBarCode;
	private Double locaLength;
	private Double locaWidth;
	private Double locaHeight;
	private Double locaArea;
	private Double locaVolume;
	public StorageLocation(){}
	public StorageLocation(Long code,String name){
		this.code=code;
		this.name=name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Long getParentCode() {
		return parentCode;
	}
	public void setParentCode(Long parentCode) {
		this.parentCode = parentCode;
	}
	public Long getComBrId() {
		return comBrId;
	}
	public void setComBrId(Long comBrId) {
		this.comBrId = comBrId;
	}
	public Long getCode() {
		return code;
	}
	public void setCode(Long code) {
		this.code = code;
	}
	public Integer getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}
	public Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	public String getLocaBarCode() {
		return locaBarCode;
	}
	public void setLocaBarCode(String locaBarCode) {
		this.locaBarCode = locaBarCode;
	}
	public Double getLocaLength() {
		return locaLength;
	}
	public void setLocaLength(Double locaLength) {
		this.locaLength = locaLength;
	}
	public Double getLocaWidth() {
		return locaWidth;
	}
	public void setLocaWidth(Double locaWidth) {
		this.locaWidth = locaWidth;
	}
	public Double getLocaHeight() {
		return locaHeight;
	}
	public void setLocaHeight(Double locaHeight) {
		this.locaHeight = locaHeight;
	}
	public Double getLocaArea() {
		return locaArea;
	}
	public void setLocaArea(Double locaArea) {
		this.locaArea = locaArea;
	}
	public Double getLocaVolume() {
		return locaVolume;
	}
	public void setLocaVolume(Double locaVolume) {
		this.locaVolume = locaVolume;
	}

	
}