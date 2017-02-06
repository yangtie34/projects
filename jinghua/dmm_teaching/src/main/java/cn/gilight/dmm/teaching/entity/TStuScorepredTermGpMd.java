package cn.gilight.dmm.teaching.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 成绩预测-分组模型表
 * 
 * @author xuebl
 * @date 2017年1月8日 下午3:07:55
 */
@Entity
@Table(name="T_STU_SCOREPRED_TERM_GP_MD")
public class TStuScorepredTermGpMd {
	
	private String id;
	private String groupId;
	private String courseId;
	private String courseAttrCode;
	private String courseNatureCode;
	private String courseCategoryCode;
	private String schoolYear;
	private String termCode;
	private Integer order_;
	private Integer ispredict;
	private Integer issave;
	private String moldId;
	
	
	public TStuScorepredTermGpMd() {
	}

	public TStuScorepredTermGpMd(String id, String groupId, String courseId, String courseAttrCode,
			String courseNatureCode, String courseCategoryCode, String schoolYear, String termCode, Integer order_,
			Integer ispredict, Integer issave, String moldId) {
		this.id = id;
		this.groupId = groupId;
		this.courseId = courseId;
		this.courseAttrCode = courseAttrCode;
		this.courseNatureCode = courseNatureCode;
		this.courseCategoryCode = courseCategoryCode;
		this.schoolYear = schoolYear;
		this.termCode = termCode;
		this.order_ = order_;
		this.ispredict = ispredict;
		this.issave = issave;
		this.moldId = moldId;
	}

	
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 20)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "GROUP_ID", length = 20)
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Column(name = "COURSE_ID", length = 20)
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	@Column(name = "COURSE_ATTR_CODE", length = 10)
	public String getCourseAttrCode() {
		return courseAttrCode;
	}

	public void setCourseAttrCode(String courseAttrCode) {
		this.courseAttrCode = courseAttrCode;
	}

	@Column(name = "COURSE_NATURE_CODE", length = 10)
	public String getCourseNatureCode() {
		return courseNatureCode;
	}

	public void setCourseNatureCode(String courseNatureCode) {
		this.courseNatureCode = courseNatureCode;
	}

	@Column(name = "COURSE_CATEGORY_CODE", length = 10)
	public String getCourseCategoryCode() {
		return courseCategoryCode;
	}

	public void setCourseCategoryCode(String courseCategoryCode) {
		this.courseCategoryCode = courseCategoryCode;
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

	@Column(name = "ORDER_",  precision = 2)
	public Integer getOrder_() {
		return order_;
	}

	public void setOrder_(Integer order_) {
		this.order_ = order_;
	}

	@Column(name = "ISPREDICT",  precision = 1)
	public Integer getIspredict() {
		return ispredict;
	}

	public void setIspredict(Integer ispredict) {
		this.ispredict = ispredict;
	}

	@Column(name = "ISSAVE",  precision = 1)
	public Integer getIssave() {
		return issave;
	}

	public void setIssave(Integer issave) {
		this.issave = issave;
	}

	@Column(name = "MOLD_ID", length = 20)
	public String getMoldId() {
		return moldId;
	}

	public void setMoldId(String moldId) {
		this.moldId = moldId;
	}
}
