package com.jhnu.syspermiss.permiss.entity;

import java.io.Serializable;

public class UserExpandInfo  implements Serializable {
	private static final long serialVersionUID = 8274021243114059341L;
	
	private String userName;// 用户名
	private String realName;	// 真实名
	private String deptName;// 部门名称（教职工的是部门，学生的是院系/专业）
	private String lastLoginTime;// 上次登录时间
	private String sex;// 性别
	private String enrollDate;//入校时间
	
	public String getEnrollDate() {
		return enrollDate;
	}
	public void setEnrollDate(String enrollDate) {
		this.enrollDate = enrollDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public UserExpandInfo(String userName, String realName, String deptName,
			String lastLoginTime,String sex,String enrollDate) {
		super();
		this.userName = userName;
		this.realName = realName;
		this.deptName = deptName;
		this.lastLoginTime = lastLoginTime;
		this.sex  =sex;
		this.enrollDate = enrollDate;
	}
	public UserExpandInfo() {
		super();
	}
	
}
