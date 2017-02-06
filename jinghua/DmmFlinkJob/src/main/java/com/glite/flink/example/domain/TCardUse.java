package com.glite.flink.example.domain;

import java.util.Date;

/**
 * @作者:baihuayang
 *
 * @创建时间:2016年12月30日 上午11:18:42
 *
 * @描述:一卡通使用情况实体类
 * 
 */
public class TCardUse {

	private Date yearMonth;
	private String payCount;
	private String payMoney;
	private String payDays;
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
	private String classId;
	private String className;

	public TCardUse(Date yearMonth, String payCount, String payMoney, String payDays, String stuId, String stuName, String sexCode, String sexName, String deptId, String deptName, String majorId, String majorName, String eduId, String eduName, String classId, String className) {
		super();
		this.yearMonth = yearMonth;
		this.payCount = payCount;
		this.payMoney = payMoney;
		this.payDays = payDays;
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
		this.classId = classId;
		this.className = className;
	}

	public Date getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(Date yearMonth) {
		this.yearMonth = yearMonth;
	}

	public String getPayCount() {
		return payCount;
	}

	public void setPayCount(String payCount) {
		this.payCount = payCount;
	}

	public String getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(String payMoney) {
		this.payMoney = payMoney;
	}

	public String getPayDays() {
		return payDays;
	}

	public void setPayDays(String payDays) {
		this.payDays = payDays;
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

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public String toString() {
		return yearMonth + "," + payCount + "," + payMoney + "," + payDays + "," + stuId + "," + stuName + "," + sexCode + "," + sexName + "," + deptId + "," + deptName + "," + majorId + "," + majorName + "," + eduId + "," + eduName + "," + classId + "," + className;
	}

}
