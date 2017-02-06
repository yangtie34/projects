package cn.gilight.dmm.teaching.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 成绩预测-历次预测成绩表
 * 
 * @author xuebl
 * @date 2017年1月8日 下午3:07:55
 */
@Entity
@Table(name="T_STU_SCOREPRED_TERM_HIS")
public class TStuScorepredTermHis {
	
	private String id;
	private String stuId;
	private String schoolYear;
	private String termCode;
	private Integer grade;
	private String courseId;
	private String predictScore;
	private String date_;
	private Integer istrue;
	private Integer isexact;
	
	
	public TStuScorepredTermHis() {
	}
	
	public TStuScorepredTermHis(String id, String stuId, String schoolYear, String termCode, Integer grade,
			String courseId, String predictScore, String date_, Integer istrue, Integer isexact) {
		this.id = id;
		this.stuId = stuId;
		this.schoolYear = schoolYear;
		this.termCode = termCode;
		this.grade = grade;
		this.courseId = courseId;
		this.predictScore = predictScore;
		this.date_ = date_;
		this.istrue = istrue;
		this.isexact = isexact;
	}


	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 20)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "COURSE_ID", length = 20)
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	@Column(name = "SCHOOL_YEAR", length = 10)
	public String getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}

	@Column(name = "TERM_CODE", length = 10)
	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	@Column(name = "GRADE",  precision = 4)
	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	@Column(name = "STU_ID", length = 20)
	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}

	@Column(name = "PREDICT_SCORE",  precision = 8, scale = 2)
	public String getPredictScore() {
		return predictScore;
	}

	public void setPredictScore(String predictScore) {
		this.predictScore = predictScore;
	}

	@Column(name = "DATE_", length = 10)
	public String getDate_() {
		return date_;
	}

	public void setDate_(String date_) {
		this.date_ = date_;
	}

	@Column(name = "ISTRUE",  precision = 1)
	public Integer getIstrue() {
		return istrue;
	}

	public void setIstrue(Integer istrue) {
		this.istrue = istrue;
	}

	@Column(name = "ISEXACT",  precision = 1)
	public Integer getIsexact() {
		return isexact;
	}

	public void setIsexact(Integer isexact) {
		this.isexact = isexact;
	}
	
}
