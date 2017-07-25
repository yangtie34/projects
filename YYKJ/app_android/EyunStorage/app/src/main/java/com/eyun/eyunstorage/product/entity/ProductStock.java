package com.eyun.eyunstorage.product.entity;


public class ProductStock {

	// Fields

	private Long comId;
	private Long comBrId;
	private Long proId;
	private Long stockId;
	private Integer proNumber;
	private String proNumberTime;
	public Long getComId() {
		return comId;
	}
	public void setComId(Long comId) {
		this.comId = comId;
	}
	public Long getComBrId() {
		return comBrId;
	}
	public void setComBrId(Long comBrId) {
		this.comBrId = comBrId;
	}
	public Long getProId() {
		return proId;
	}
	public void setProId(Long proId) {
		this.proId = proId;
	}
	public Long getStockId() {
		return stockId;
	}
	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}
	public Integer getProNumber() {
		return proNumber;
	}
	public void setProNumber(Integer proNumber) {
		this.proNumber = proNumber;
	}
	public String getProNumberTime() {
		return proNumberTime;
	}
	public void setProNumberTime(String proNumberTime) {
		this.proNumberTime = proNumberTime;
	}

}