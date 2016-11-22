package com.jhnu.product.wechat.parent.score.entity;
/**
 * 学生成绩对象
 * @author Administrator
 *
 */
public class CourseScore {
	private String code;
	private String name;
	private double score;
	private String stuId;
	private String schoolYear;
	private String termCode;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
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
	public CourseScore(String code, String name, double score, String stuId,
			String schoolYear, String termCode) {
		super();
		this.code = code;
		this.name = name;
		this.score = score;
		this.stuId = stuId;
		this.schoolYear = schoolYear;
		this.termCode = termCode;
	}
	public CourseScore() {
		super();
	}
	
}
