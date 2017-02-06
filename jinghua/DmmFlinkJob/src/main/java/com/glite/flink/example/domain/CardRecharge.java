package com.glite.flink.example.domain;

import java.util.Date;

/**
 * @作者:baihuayang
 *
 * @创建时间:2016年12月30日 上午11:19:04
 *
 * @描述:一卡通充值结果实体类。
 * 
 */
public class CardRecharge {

	private Date yearMonth;
	private String hour;
	private String upTime;
	private String upMoney;
	private String oldMoney;
	private String cardDealId;
	private String cardDealName;
	private String stuId;
	private String stuName;
	private String sexCode;
	private String sexName;
	private String deptId;
	private String deptName;
	private String majorId;
	private String majorName;
	private String eduId;
	private String eduName;

	public CardRecharge(Date yearMonth, String hour, String upTime, String upMoney, String oldMoney, String cardDealId, String cardDealName, String stuId, String stuName, String sexCode, String sexName, String deptId, String deptName, String majorId, String majorName, String eduId, String eduName) {
		super();
		this.yearMonth = yearMonth;
		this.hour = hour;
		this.upTime = upTime;
		this.upMoney = upMoney;
		this.oldMoney = oldMoney;
		this.cardDealId = cardDealId;
		this.cardDealName = cardDealName;
		this.stuId = stuId;
		this.stuName = stuName;
		this.sexCode = sexCode;
		this.sexName = sexName;
		this.deptId = deptId;
		this.deptName = deptName;
		this.majorId = majorId;
		this.majorName = majorName;
		this.eduId = eduId;
		this.eduName = eduName;
	}

	public Date getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(Date yearMonth) {
		this.yearMonth = yearMonth;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getUpTime() {
		return upTime;
	}

	public void setUpTime(String upTime) {
		this.upTime = upTime;
	}

	public String getUpMoney() {
		return upMoney;
	}

	public void setUpMoney(String upMoney) {
		this.upMoney = upMoney;
	}

	public String getOldMoney() {
		return oldMoney;
	}

	public void setOldMoney(String oldMoney) {
		this.oldMoney = oldMoney;
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

	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
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

	public void setDeptName(String deptName) {
		this.deptName = deptName;
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

	@Override
	public String toString() {
		return yearMonth + "," + hour + "," + upTime + "," + upMoney + "," + oldMoney + "," + cardDealId + "," + cardDealName + "," + stuId + "," + stuName + "," + sexCode + "," + sexName + "," + deptId + "," + deptName + "," + majorId + "," + majorName + "," + eduId + "," + eduName;
	}

}
