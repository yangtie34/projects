package cn.gilight.product.common.stu.entity;

import java.io.Serializable;


public class LateDormStudent implements Serializable{
	
	private static final long serialVersionUID = 6566361119459072408L;
	
	private String stu_id;			// 学号
	private String stu_name;		// 姓名
	private String sex_code;	// 性别
	private String dept_id;		// 院系ID
	private String dept_name;	// 院系名称
	private String major_id;	// 专业ID
	private String major_name; 	// 专业名称
	private String class_id;	// 班级ID
	private String class_name;	// 班级名称
	private String late_time;	// 迟到时间
	private String address;		// 刷卡地点
	private String action_date;	// 发生日期
	private String exe_time;	// 同步时间
	
	public String getStu_id() {
		return stu_id;
	}
	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}
	public String getStu_name() {
		return stu_name;
	}
	public void setStu_name(String stu_name) {
		this.stu_name = stu_name;
	}
	public String getMajor_id() {
		return major_id;
	}
	public void setMajor_id(String major_id) {
		this.major_id = major_id;
	}
	public String getMajor_name() {
		return major_name;
	}
	public void setMajor_name(String major_name) {
		this.major_name = major_name;
	}
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getClass_id() {
		return class_id;
	}
	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public String getLate_time() {
		return late_time;
	}
	public void setLate_time(String late_time) {
		this.late_time = late_time;
	}
	public String getAction_date() {
		return action_date;
	}
	public void setAction_date(String action_date) {
		this.action_date = action_date;
	}
	public String getExe_time() {
		return exe_time;
	}
	public void setExe_time(String exe_time) {
		this.exe_time = exe_time;
	}
	public String getSex_code() {
		return sex_code;
	}
	public void setSex_code(String sex_code) {
		this.sex_code = sex_code;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLateTimeFormat(){
		String outS=late_time.substring(11, 19);
		int hour=Integer.valueOf(late_time.substring(11, 13));
		if(hour<12){
			outS="次日凌晨 "+outS;
		}
		return outS;
	}
	
}
