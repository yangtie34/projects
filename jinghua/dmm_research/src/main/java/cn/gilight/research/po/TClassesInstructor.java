package cn.gilight.research.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_CLASSES_INSTRUCTOR")
public class TClassesInstructor implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String teaId;
	private String classId;
	private String schoolYear;
	private String termCode;

	// Constructors

	/** default constructor */
	public TClassesInstructor() {
	}

	/** full constructor */
	public TClassesInstructor(String id, String teaId, String classId,
			String schoolYear, String termCode) {
		this.id = id;
		this.teaId = teaId;
		this.classId = classId;
		this.schoolYear = schoolYear;
		this.termCode = termCode;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 20)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "TEA_ID", nullable = false, length = 20)
	public String getTeaId() {
		return this.teaId;
	}

	public void setTeaId(String teaId) {
		this.teaId = teaId;
	}

	@Column(name = "CLASS_ID", nullable = false, length = 20)
	public String getClassId() {
		return this.classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	@Column(name = "SCHOOL_YEAR", nullable = false, length = 9)
	public String getSchoolYear() {
		return this.schoolYear;
	}

	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}

	@Column(name = "TERM_CODE", nullable = false, length = 10)
	public String getTermCode() {
		return this.termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

}