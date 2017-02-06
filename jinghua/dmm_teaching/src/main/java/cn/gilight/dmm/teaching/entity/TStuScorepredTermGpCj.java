package cn.gilight.dmm.teaching.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author lijun
 *
 */
@Entity
@Table(name="T_STU_SCOREPRED_TERM_GP_CJ")
public class TStuScorepredTermGpCj {

	
	private String id;
	private String schoolYear;
	private String termCode;
	private Integer gradeId;
	private String stuId;
	private String courseId;
	private Double predictScore;
	private String groupId;
	private String moldId;
	private String date;
	
	public TStuScorepredTermGpCj() {
		super();
	}
	public TStuScorepredTermGpCj(String id, String schoolYear, String termCode,
			Integer gradeId, String courseId, Double predictScore,
			String groupId, String moldId, String date) {
		super();
		this.id = id;
		this.schoolYear = schoolYear;
		this.termCode = termCode;
		this.gradeId = gradeId;
		this.courseId = courseId;
		this.predictScore = predictScore;
		this.groupId = groupId;
		this.moldId = moldId;
		this.date = date;
	}
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 20, scale = 0)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "SCHOOL_YEAR", length = 9)
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
	@Column(name = "GRADE", length = 4)
	public Integer getGradeId() {
		return gradeId;
	}
	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}
	@Column(name = "STU_ID", length = 20)
	public String getStuId() {
		return stuId;
	}
	public void setStuId(String stuId) {
		this.stuId = stuId;
	}
	@Column(name = "COURSE_ID", length = 20)
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	@Column(name = "PREDICT_SCORE", precision = 8, scale = 2)
	public Double getPredictScore() {
		return predictScore;
	}
	public void setPredictScore(Double predictScore) {
		this.predictScore = predictScore;
	}
	@Column(name = "GROUP_ID", length = 20)
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	@Column(name = "MOLD_ID", length = 20)
	public String getMoldId() {
		return moldId;
	}
	public void setMoldId(String moldId) {
		this.moldId = moldId;
	}
	@Column(name = "DATE_", length = 30)
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
