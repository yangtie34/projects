
package cn.gilight.personal.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TStu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_STU")
public class TStu implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String no;
	private String id;
	private String examineeNo;
	private String name;
	private String fomerName;
	private String englishName;
	private String birthday;
	private String idno;
	private String sexCode;
	private String nationCode;
	private String politicsCode;
	private String anmeldenCode;
	private Boolean married;
	private String deptId;
	private String majorId;
	private String classId;
	private Boolean lengthSchooling;
	private String beforeEduId;
	private String eduId;
	private String degreeId;
	private String trainingId;
	private String trainingLevelCode;
	private String recruitCode;
	private String learningStyleCode;
	private String stuFromCode;
	private String stuCategoryId;
	private String enrollDate;
	private String enrollGrade;
	private String stuStateCode;
	private String stuRollCode;
	private String stuOriginId;
	private String placeDomicile;
	private String schooltag;
	private String gatqCode;
	/** default constructor */
	public TStu() {
	}

	/** minimal constructor */
	public TStu(String no, Boolean lengthSchooling, String enrollGrade) {
		this.no = no;
		this.lengthSchooling = lengthSchooling;
		this.enrollGrade = enrollGrade;
	}

	/** full constructor */
	public TStu(String no, String id, String examineeNo, String name,
			String fomerName, String englishName, String birthday, String idno,
			String sexCode, String nationCode, String politicsCode,
			String anmeldenCode, Boolean married, String deptId,
			String majorId, String classId, Boolean lengthSchooling,
			String beforeEduId, String eduId, String degreeId,
			String trainingId, String trainingLevelCode, String recruitCode,
			String learningStyleCode, String stuFromCode, String stuCategoryId,
			String enrollDate, String enrollGrade, String stuStateCode,
			String stuRollCode, String stuOriginId, String placeDomicile,
			String schooltag, String gatqCode) {
		this.no = no;
		this.id = id;
		this.examineeNo = examineeNo;
		this.name = name;
		this.fomerName = fomerName;
		this.englishName = englishName;
		this.birthday = birthday;
		this.idno = idno;
		this.sexCode = sexCode;
		this.nationCode = nationCode;
		this.politicsCode = politicsCode;
		this.anmeldenCode = anmeldenCode;
		this.married = married;
		this.deptId = deptId;
		this.majorId = majorId;
		this.classId = classId;
		this.lengthSchooling = lengthSchooling;
		this.beforeEduId = beforeEduId;
		this.eduId = eduId;
		this.degreeId = degreeId;
		this.trainingId = trainingId;
		this.trainingLevelCode = trainingLevelCode;
		this.recruitCode = recruitCode;
		this.learningStyleCode = learningStyleCode;
		this.stuFromCode = stuFromCode;
		this.stuCategoryId = stuCategoryId;
		this.enrollDate = enrollDate;
		this.enrollGrade = enrollGrade;
		this.stuStateCode = stuStateCode;
		this.stuRollCode = stuRollCode;
		this.stuOriginId = stuOriginId;
		this.placeDomicile = placeDomicile;
		this.schooltag = schooltag;
		this.gatqCode = gatqCode;
	}

	// Property accessors
	@Id
	@Column(name = "NO_", unique = true, nullable = false, length = 20)
	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Column(name = "ID", length = 20)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "EXAMINEE_NO", length = 30)
	public String getExamineeNo() {
		return this.examineeNo;
	}

	public void setExamineeNo(String examineeNo) {
		this.examineeNo = examineeNo;
	}

	@Column(name = "NAME_", length = 60)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "FOMER_NAME", length = 60)
	public String getFomerName() {
		return this.fomerName;
	}

	public void setFomerName(String fomerName) {
		this.fomerName = fomerName;
	}

	@Column(name = "ENGLISH_NAME", length = 60)
	public String getEnglishName() {
		return this.englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	@Column(name = "BIRTHDAY", length = 10)
	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@Column(name = "IDNO", length = 18)
	public String getIdno() {
		return this.idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	@Column(name = "SEX_CODE", length = 10)
	public String getSexCode() {
		return this.sexCode;
	}

	public void setSexCode(String sexCode) {
		this.sexCode = sexCode;
	}

	@Column(name = "NATION_CODE", length = 10)
	public String getNationCode() {
		return this.nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	@Column(name = "POLITICS_CODE", length = 10)
	public String getPoliticsCode() {
		return this.politicsCode;
	}

	public void setPoliticsCode(String politicsCode) {
		this.politicsCode = politicsCode;
	}

	@Column(name = "ANMELDEN_CODE", length = 10)
	public String getAnmeldenCode() {
		return this.anmeldenCode;
	}

	public void setAnmeldenCode(String anmeldenCode) {
		this.anmeldenCode = anmeldenCode;
	}

	@Column(name = "MARRIED", precision = 1, scale = 0)
	public Boolean getMarried() {
		return this.married;
	}

	public void setMarried(Boolean married) {
		this.married = married;
	}

	@Column(name = "DEPT_ID", length = 20)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "MAJOR_ID", length = 20)
	public String getMajorId() {
		return this.majorId;
	}

	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}

	@Column(name = "CLASS_ID", length = 20)
	public String getClassId() {
		return this.classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	@Column(name = "LENGTH_SCHOOLING", nullable = false, precision = 1, scale = 0)
	public Boolean getLengthSchooling() {
		return this.lengthSchooling;
	}

	public void setLengthSchooling(Boolean lengthSchooling) {
		this.lengthSchooling = lengthSchooling;
	}

	@Column(name = "BEFORE_EDU_ID", length = 20)
	public String getBeforeEduId() {
		return this.beforeEduId;
	}

	public void setBeforeEduId(String beforeEduId) {
		this.beforeEduId = beforeEduId;
	}

	@Column(name = "EDU_ID", length = 20)
	public String getEduId() {
		return this.eduId;
	}

	public void setEduId(String eduId) {
		this.eduId = eduId;
	}

	@Column(name = "DEGREE_ID", length = 20)
	public String getDegreeId() {
		return this.degreeId;
	}

	public void setDegreeId(String degreeId) {
		this.degreeId = degreeId;
	}

	@Column(name = "TRAINING_ID", length = 20)
	public String getTrainingId() {
		return this.trainingId;
	}

	public void setTrainingId(String trainingId) {
		this.trainingId = trainingId;
	}

	@Column(name = "TRAINING_LEVEL_CODE", length = 10)
	public String getTrainingLevelCode() {
		return this.trainingLevelCode;
	}

	public void setTrainingLevelCode(String trainingLevelCode) {
		this.trainingLevelCode = trainingLevelCode;
	}

	@Column(name = "RECRUIT_CODE", length = 10)
	public String getRecruitCode() {
		return this.recruitCode;
	}

	public void setRecruitCode(String recruitCode) {
		this.recruitCode = recruitCode;
	}

	@Column(name = "LEARNING_STYLE_CODE", length = 10)
	public String getLearningStyleCode() {
		return this.learningStyleCode;
	}

	public void setLearningStyleCode(String learningStyleCode) {
		this.learningStyleCode = learningStyleCode;
	}

	@Column(name = "STU_FROM_CODE", length = 10)
	public String getStuFromCode() {
		return this.stuFromCode;
	}

	public void setStuFromCode(String stuFromCode) {
		this.stuFromCode = stuFromCode;
	}

	@Column(name = "STU_CATEGORY_ID", length = 20)
	public String getStuCategoryId() {
		return this.stuCategoryId;
	}

	public void setStuCategoryId(String stuCategoryId) {
		this.stuCategoryId = stuCategoryId;
	}

	@Column(name = "ENROLL_DATE", length = 10)
	public String getEnrollDate() {
		return this.enrollDate;
	}

	public void setEnrollDate(String enrollDate) {
		this.enrollDate = enrollDate;
	}

	@Column(name = "ENROLL_GRADE", nullable = false, length = 4)
	public String getEnrollGrade() {
		return this.enrollGrade;
	}

	public void setEnrollGrade(String enrollGrade) {
		this.enrollGrade = enrollGrade;
	}

	@Column(name = "STU_STATE_CODE", length = 10)
	public String getStuStateCode() {
		return this.stuStateCode;
	}

	public void setStuStateCode(String stuStateCode) {
		this.stuStateCode = stuStateCode;
	}

	@Column(name = "STU_ROLL_CODE", length = 10)
	public String getStuRollCode() {
		return this.stuRollCode;
	}

	public void setStuRollCode(String stuRollCode) {
		this.stuRollCode = stuRollCode;
	}

	@Column(name = "STU_ORIGIN_ID", length = 20)
	public String getStuOriginId() {
		return this.stuOriginId;
	}

	public void setStuOriginId(String stuOriginId) {
		this.stuOriginId = stuOriginId;
	}

	@Column(name = "PLACE_DOMICILE", length = 300)
	public String getPlaceDomicile() {
		return this.placeDomicile;
	}

	public void setPlaceDomicile(String placeDomicile) {
		this.placeDomicile = placeDomicile;
	}

	@Column(name = "SCHOOLTAG", length = 300)
	public String getSchooltag() {
		return this.schooltag;
	}

	public void setSchooltag(String schooltag) {
		this.schooltag = schooltag;
	}

	@Column(name = "GATQ_CODE", length = 10)
	public String getGatqCode() {
		return this.gatqCode;
	}

	public void setGatqCode(String gatqCode) {
		this.gatqCode = gatqCode;
	}

}