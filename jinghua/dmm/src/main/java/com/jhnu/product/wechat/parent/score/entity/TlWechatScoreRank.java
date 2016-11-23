package com.jhnu.product.wechat.parent.score.entity;
/**
 * 学生排名存储对象
 * @author Administrator
 *
 */
public class TlWechatScoreRank {
	private String stuId;
	private String classId;
	private String majorId;
	private String schoolYear;
	private String termCode;
	private int classRank=0;
	private int majorRank=0;
	private Double scoreCount;
	private Double scoreAvg;
	
	public TlWechatScoreRank(){
		super();
	}
	
	public TlWechatScoreRank(String stuId,String classId,String majorId, String schoolYear, String termCode,
			Double count, Double avg) {
		super();
		this.stuId = stuId;
		this.classId = classId;
		this.majorId = majorId;
		this.schoolYear = schoolYear;
		this.termCode = termCode;
		this.scoreCount = count;
		this.scoreAvg = avg;
	}
	
	
	public String getClassId() {
		return classId;
	}


	public void setClassId(String classId) {
		this.classId = classId;
	}


	public String getMajorId() {
		return majorId;
	}


	public void setMajorId(String majorId) {
		this.majorId = majorId;
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
	public int getClassRank() {
		return classRank;
	}
	public void setClassRank(int classRank) {
		this.classRank = classRank;
	}
	public int getMajorRank() {
		return majorRank;
	}
	public void setMajorRank(int majorRank) {
		this.majorRank = majorRank;
	}

	public Double getScoreCount() {
		return scoreCount;
	}

	public void setScoreCount(Double scoreCount) {
		this.scoreCount = scoreCount;
	}

	public Double getScoreAvg() {
		return scoreAvg;
	}

	public void setScoreAvg(Double scoreAvg) {
		this.scoreAvg = scoreAvg;
	}
	
}
