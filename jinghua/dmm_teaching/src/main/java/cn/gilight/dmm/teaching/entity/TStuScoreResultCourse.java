package cn.gilight.dmm.teaching.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_STU_SCORE_RESULT_COURSE")
public class TStuScoreResultCourse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String course_id;
	private String school_year;
	private String term_code;
	private Double gpa_avg;
	private Double gpa_middle;
	private Double gpa_mode;
	private Double gpa_fc;
	private Double gpa_bzc;
	private Double better;
	private Double fail;
	private Double rebuild;
	private Double under;
	private Double score_avg;
	private Double score_middle;
	private Double score_mode;
	private Double score_fc;
	private Double score_bzc;
	private Double gpa_before;
	private int    smart_count;
	private Double smart_scale;
	
	public TStuScoreResultCourse() {
		super();
	}
	
	public TStuScoreResultCourse(String id, String course_id, String school_year, String term_code, Double gpa_avg, Double gpa_middle,
			Double gpa_mode,Double gpa_fc,Double gpa_bzc,Double better,Double fail,Double rebuild,Double under,Double score_avg,
			Double score_middle,Double score_mode,Double score_fc,Double score_bzc,Double gpa_before,int smart_count,Double smart_scale) {
		super();
		this.id = id;
		this.course_id = course_id;
		this.school_year = school_year;
		this.term_code = term_code;
		this.gpa_avg = gpa_avg;
		this.gpa_middle = gpa_middle;
		this.gpa_mode = gpa_mode;
		this.gpa_fc = gpa_fc;
		this.gpa_bzc = gpa_bzc;
		this.better = better;
		this.fail = fail;
		this.rebuild = rebuild;
		this.under = under;
		this.score_avg = score_avg;
		this.score_middle = score_middle;
		this.score_mode = score_mode;
		this.score_fc = score_fc;
		this.score_bzc = score_bzc;
		this.gpa_before = gpa_before;
		this.smart_count = smart_count;
		this.smart_scale = smart_scale;
	}
	
	
	public TStuScoreResultCourse( String course_id, String school_year, String term_code, Double gpa_avg, Double gpa_middle,
			Double gpa_mode,Double gpa_fc,Double gpa_bzc,Double better,Double fail,Double rebuild,Double under,Double score_avg,
			Double score_middle,Double score_mode,Double score_fc,Double score_bzc,Double gpa_before,int smart_count,Double smart_scale) {
		super();
		this.course_id = course_id;
		this.school_year = school_year;
		this.term_code = term_code;
		this.gpa_avg = gpa_avg;
		this.gpa_middle = gpa_middle;
		this.gpa_mode = gpa_mode;
		this.gpa_fc = gpa_fc;
		this.gpa_bzc = gpa_bzc;
		this.better = better;
		this.fail = fail;
		this.rebuild = rebuild;
		this.under = under;
		this.score_avg = score_avg;
		this.score_middle = score_middle;
		this.score_mode = score_mode;
		this.score_fc = score_fc;
		this.score_bzc = score_bzc;
		this.gpa_before = gpa_before;
		this.smart_count = smart_count;
		this.smart_scale = smart_scale;
	}
	
	
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 20, scale = 0)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "COURSE_ID", length = 20)
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	@Column(name = "SCHOOL_YEAR", length = 20)
	public String getSchool_year() {
		return school_year;
	}
	public void setSchool_year(String school_year) {
		this.school_year = school_year;
	}
	@Column(name = "TERM_CODE", length = 20)
	public String getTerm_code() {
		return term_code;
	}
	public void setTerm_code(String term_code) {
		this.term_code = term_code;
	}
	@Column(name = "GPA_AVG",  precision = 8, scale = 2)
	public Double getGpa_avg() {
		return gpa_avg;
	}
	public void setGpa_avg(Double gpa_avg) {
		this.gpa_avg = gpa_avg;
	}
	@Column(name = "GPA_MIDDLE",  precision = 8, scale = 2)
	public Double getGpa_middle() {
		return gpa_middle;
	}
	public void setGpa_middle(Double gpa_middle) {
		this.gpa_middle = gpa_middle;
	}
	@Column(name = "GPA_MODE",  precision = 8, scale = 2)
	public Double getGpa_mode() {
		return gpa_mode;
	}
	public void setGpa_mode(Double gpa_mode) {
		this.gpa_mode = gpa_mode;
	}
	@Column(name = "GPA_FC",  precision = 8, scale = 2)
	public Double getGpa_fc() {
		return gpa_fc;
	}
	public void setGpa_fc(Double gpa_fc) {
		this.gpa_fc = gpa_fc;
	}
	
	@Column(name = "GPA_BZC",  precision = 8, scale = 2)
	public Double getGpa_bzc() {
		return gpa_bzc;
	}
	public void setGpa_bzc(Double gpa_bzc) {
		this.gpa_bzc = gpa_bzc;
	}
	@Column(name = "BETTER",  precision = 8, scale = 2)
	public Double getBetter() {
		return better;
	}
	public void setBetter(Double better) {
		this.better = better;
	}
	@Column(name = "FAIL",  precision = 8, scale = 2)
	public Double getFail() {
		return fail;
	}
	public void setFail(Double fail) {
		this.fail = fail;
	}
	@Column(name = "REBUILD",  precision = 8, scale = 2)
	public Double getRebuild() {
		return rebuild;
	}
	public void setRebuild(Double rebuild) {
		this.rebuild = rebuild;
	}
	@Column(name = "UNDER",  precision = 8, scale = 2)
	public Double getUnder() {
		return under;
	}
	public void setUnder(Double under) {
		this.under = under;
	}
	@Column(name = "SCORE_AVG",  precision = 8, scale = 2)
	public Double getScore_avg() {
		return score_avg;
	}
	public void setScore_avg(Double score_avg) {
		this.score_avg = score_avg;
	}
	@Column(name = "SCORE_MIDDLE",  precision = 8, scale = 2)
	public Double getScore_middle() {
		return score_middle;
	}
	public void setScore_middle(Double score_middle) {
		this.score_middle = score_middle;
	}
	@Column(name = "SCORE_MODE",  precision = 8, scale = 2)
	public Double getScore_mode() {
		return score_mode;
	}
	public void setScore_mode(Double score_mode) {
		this.score_mode = score_mode;
	}
	@Column(name = "SCORE_FC",  precision = 8, scale = 2)
	public Double getScore_fc() {
		return score_fc;
	}
	public void setScore_fc(Double score_fc) {
		this.score_fc = score_fc;
	}
	@Column(name = "SCORE_BZC",  precision = 8, scale = 2)
	public Double getScore_bzc() {
		return score_bzc;
	}
	public void setScore_bzc(Double score_bzc) {
		this.score_bzc = score_bzc;
	}
	
	@Column(name = "GPA_BEFORE",  precision = 8, scale = 2)
	public Double getGpa_before() {
		return gpa_before;
	}

	public void setGpa_before(Double gpa_before) {
		this.gpa_before = gpa_before;
	}
	
	@Column(name = "SMART_COUNT",  precision = 8, scale = 2)
	public int getSmart_count() {
		return smart_count;
	}
	public void setSmart_count(int smart_count) {
		this.smart_count = smart_count;
	}
	
	@Column(name = "SMART_SCALE",  precision = 8, scale = 2)
	public Double getSmart_scale() {
		return smart_scale;
	}

	public void setSmart_scale(Double smart_scale) {
		this.smart_scale = smart_scale;
	}
}
