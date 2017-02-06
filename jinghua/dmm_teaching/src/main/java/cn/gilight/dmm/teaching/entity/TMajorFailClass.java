package cn.gilight.dmm.teaching.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 
 * @author xuebl
 * @date 2016年6月22日 下午7:11:11
 */
@Entity
@Table(name="T_MAJOR_FAIL_CLASS")
public class TMajorFailClass implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String majorId;
	private String school_year;
	private String course_attr_code;
	private Double fail_class_rate;
	private Integer ranking;
	
	public TMajorFailClass() {
		super();
	}
	
	public TMajorFailClass(String id, String majorId, String school_year, String course_attr_code, Double fail_class_rate, Integer ranking) {
		super();
		this.id = id;
		this.majorId = majorId;
		this.school_year = school_year;
		this.course_attr_code = course_attr_code;
		this.fail_class_rate = fail_class_rate;
		this.ranking = ranking;
	}


	// Property accessors
	@Id
	@Column(name = "ID", unique = false, nullable = true, length = 20)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "MAJOR_ID", length = 20)
	public String getMajorId() {
		return majorId;
	}
	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}

	@Column(name = "SCHOOL_YEAR", length = 9)
	public String getSchool_year() {
		return school_year;
	}
	public void setSchool_year(String school_year) {
		this.school_year = school_year;
	}

	@Column(name = "COURSE_ATTR_CODE", length = 10)
	public String getCourse_attr_code() {
		return course_attr_code;
	}
	public void setCourse_attr_code(String course_attr_code) {
		this.course_attr_code = course_attr_code;
	}

	@Column(name = "FAIL_CLASS_RATE", precision = 10, scale = 2)
	public Double getFail_class_rate() {
		return fail_class_rate;
	}
	public void setFail_class_rate(Double fail_class_rate) {
		this.fail_class_rate = fail_class_rate;
	}

	@Column(name = "RANKING", precision = 4)
	public Integer getRanking() {
		return ranking;
	}
	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

}
