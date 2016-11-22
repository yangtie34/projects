package com.jhnu.product.wechat.parent.score.entity;

public class TlWechatScoreCourse {
	private String stuId;
	private String schoolYear;
	private String termCode;
	private String topCourse;
	private String topCourseS;
	private String topCourseMs;
	private String lowCourse;
	private String lowCourseS;
	private String lowCourseMs;
	private String failCourse;
	public String getStuId() {
		return stuId;
	}
	public void setStuId(String stuId) {
		this.stuId = stuId;
	}
	public String getSchoolYear() {
		return schoolYear;
	}
	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}
	public String getTermCode() {
		return termCode;
	}
	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}
	public String getTopCourse() {
		return topCourse;
	}
	public void setTopCourse(String topCourse) {
		this.topCourse = topCourse;
	}
	public String getTopCourseS() {
		return topCourseS;
	}
	public void setTopCourseS(String topCourseS) {
		this.topCourseS = topCourseS;
	}
	public String getTopCourseMs() {
		return topCourseMs;
	}
	public void setTopCourseMs(String topCourseMs) {
		this.topCourseMs = topCourseMs;
	}
	public String getLowCourse() {
		return lowCourse;
	}
	public void setLowCourse(String lowCourse) {
		this.lowCourse = lowCourse;
	}
	public String getLowCourseS() {
		return lowCourseS;
	}
	public void setLowCourseS(String lowCourseS) {
		this.lowCourseS = lowCourseS;
	}
	public String getLowCourseMs() {
		return lowCourseMs;
	}
	public void setLowCourseMs(String lowCourseMs) {
		this.lowCourseMs = lowCourseMs;
	}
	public String getFailCourse() {
		return failCourse;
	}
	public void setFailCourse(String failCourse) {
		this.failCourse = failCourse;
	}
	public TlWechatScoreCourse(String stuId, String schoolYear,
			String termCode, String topCourse, String topCourseS,
			String lowCourse, String lowCourseS, String failCourse) {
		super();
		this.stuId = stuId;
		this.schoolYear = schoolYear;
		this.termCode = termCode;
		this.topCourse = topCourse;
		this.topCourseS = topCourseS;
		this.lowCourse = lowCourse;
		this.lowCourseS = lowCourseS;
		this.failCourse = failCourse;
	}
	public TlWechatScoreCourse() {
		super();
	}
}
