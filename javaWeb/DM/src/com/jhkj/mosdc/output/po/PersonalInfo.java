package com.jhkj.mosdc.output.po;
/**
 * 个人信息统计。
 * 
 * @Comments: 
 * Company: 河南精华科技有限公司
 * Created by zhangzg(vbxnt521_edu@163.com)
 * @DATE: 2012-12-20
 * @TIME: 上午11:55:22
 */
public class PersonalInfo {
	private String imageFileName; // 头像路径
	private String userName;// 用户名
	private String greeting;// 问候语
	private String schoolName;//学校名称
	private String xgh;// 学工号
	private String role;// 角色
	private String lastLoginTime;// 上次登录时间
	private String lastLoginIP;// 上次登录ip
	private String bm;// 部门
	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getGreeting() {
		return greeting;
	}
	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getXgh() {
		return xgh;
	}
	public void setXgh(String xgh) {
		this.xgh = xgh;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getLastLoginIP() {
		return lastLoginIP;
	}
	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}
	public String getBm() {
		return bm;
	}
	public void setBm(String bm) {
		this.bm = bm;
	}
	
	
}
