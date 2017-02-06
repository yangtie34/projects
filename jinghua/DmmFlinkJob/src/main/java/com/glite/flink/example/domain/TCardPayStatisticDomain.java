package com.glite.flink.example.domain;

public class TCardPayStatisticDomain {
	private String yearMonth;
	private String hour;
	private Float payMoney;
	private Integer payCount;
	private String cardPortId;
	private String cardPortName;
	private String cardPortPid;
	private String cardPortPname;
	private String cardDealId;
	private String cardDealName;
	private String sexCode;
	private String sexName;
	private String deptId;
	private String deptName;
	private String majorId;
	private String majorName;
	private String eduId;
	private String eduName;

	public String toString() {
		return this.getYearMonth() + "," + this.getHour() + ","
				+ this.getDeptId() + "," + this.getDeptName() + ","
				+ this.getMajorId() + "," + this.getMajorName() + ","
				+ this.getEduId() + "," + this.getEduName() + ","
				+ this.getSexCode() + "," + this.getSexName() + ","
				+ this.getCardPortId() + "," + this.getCardPortName() + ","
				+ this.getCardPortPid() + "," + this.getCardPortPname() + ","
				+ this.getCardDealId() + "," + this.getCardDealName() + ","
				+ this.getPayCount() + "," + this.getPayMoney();
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public Float getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Float payMoney) {
		this.payMoney = payMoney;
	}

	public Integer getPayCount() {
		return payCount;
	}

	public void setPayCount(Integer payCount) {
		this.payCount = payCount;
	}

	public String getCardPortId() {
		return cardPortId;
	}

	public void setCardPortId(String cardPortId) {
		this.cardPortId = cardPortId;
	}

	public String getCardPortName() {
		return cardPortName;
	}

	public void setCardPortName(String cardPortName) {
		this.cardPortName = cardPortName;
	}

	public String getCardPortPid() {
		return cardPortPid;
	}

	public void setCardPortPid(String cardPortPid) {
		this.cardPortPid = cardPortPid;
	}

	public String getCardPortPname() {
		return cardPortPname;
	}

	public void setCardPortPname(String cardPortPname) {
		this.cardPortPname = cardPortPname;
	}

	public String getCardDealId() {
		return cardDealId;
	}

	public void setCardDealId(String cardDealId) {
		this.cardDealId = cardDealId;
	}

	public String getCardDealName() {
		return cardDealName;
	}

	public void setCardDealName(String cardDealName) {
		this.cardDealName = cardDealName;
	}

	public String getSexCode() {
		return sexCode;
	}

	public void setSexCode(String sexCode) {
		this.sexCode = sexCode;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public String getMajorId() {
		return majorId;
	}

	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getEduId() {
		return eduId;
	}

	public void setEduId(String eduId) {
		this.eduId = eduId;
	}

	public String getEduName() {
		return eduName;
	}

	public void setEduName(String eduName) {
		this.eduName = eduName;
	}

}
