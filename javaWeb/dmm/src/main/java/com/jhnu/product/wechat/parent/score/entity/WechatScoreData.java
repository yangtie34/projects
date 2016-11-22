package com.jhnu.product.wechat.parent.score.entity;
/**
 * pageMode
 * @author Administrator
 *
 */
public class WechatScoreData {
	private String stuId;// 学生id
	private String schoolYear;// 学年
	private String termCode;// 学期
	private int classRanking; // 班级排名
	private int majorRanking;// 专业排名
	private int classStuNum;// 班级学生总数
	private int majorStuNum;// 专业学生总数
	private double scoreCount;// 总成绩
	private double scoreAvg;// 平均成绩
	private String[] gfkms;// 高分科目列表
	private String[] gfkmfs;// 高分科目分数
	private String[] gfkmzgf;// 高分科目专业最高分
	
	private String[] dfkms;// 低分科目列表
	private String[] dfkmfs;// 低分科目分数
	private String[] dfkmzgf;// 低分科目专业最高分
	
	private String[] bjgkms;// 不及格科目数

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

	public int getClassRanking() {
		return classRanking;
	}

	public void setClassRanking(int classRanking) {
		this.classRanking = classRanking;
	}

	public int getMajorRanking() {
		return majorRanking;
	}

	public String[] getGfkmfs() {
		return gfkmfs;
	}

	public void setGfkmfs(String[] gfkmfs) {
		this.gfkmfs = gfkmfs;
	}

	public String[] getGfkmzgf() {
		return gfkmzgf;
	}

	public void setGfkmzgf(String[] gfkmzgf) {
		this.gfkmzgf = gfkmzgf;
	}

	public String[] getDfkmfs() {
		return dfkmfs;
	}

	public void setDfkmfs(String[] dfkmfs) {
		this.dfkmfs = dfkmfs;
	}

	public String[] getDfkmzgf() {
		return dfkmzgf;
	}

	public void setDfkmzgf(String[] dfkmzgf) {
		this.dfkmzgf = dfkmzgf;
	}

	public void setMajorRanking(int majorRanking) {
		this.majorRanking = majorRanking;
	}

	public int getClassStuNum() {
		return classStuNum;
	}

	public void setClassStuNum(int classStuNum) {
		this.classStuNum = classStuNum;
	}

	public int getMajorStuNum() {
		return majorStuNum;
	}

	public void setMajorStuNum(int majorStuNum) {
		this.majorStuNum = majorStuNum;
	}

	public double getScoreCount() {
		return scoreCount;
	}

	public void setScoreCount(double scoreCount) {
		this.scoreCount = scoreCount;
	}

	public double getScoreAvg() {
		return scoreAvg;
	}

	public void setScoreAvg(double scoreAvg) {
		this.scoreAvg = scoreAvg;
	}

	public String[] getGfkms() {
		return gfkms;
	}

	public void setGfkms(String[] gfkms) {
		this.gfkms = gfkms;
	}

	public String[] getDfkms() {
		return dfkms;
	}

	public void setDfkms(String[] dfkms) {
		this.dfkms = dfkms;
	}

	public String[] getBjgkms() {
		return bjgkms;
	}

	public void setBjgkms(String[] bjgkms) {
		this.bjgkms = bjgkms;
	}
	
	
}
