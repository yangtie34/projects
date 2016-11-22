package cn.gilight.personal.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "T_TEA" )
public class TTea implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String teaNo;
	private String id;
	private String name;
	private String idno;
	private String fomerName;
	private String birthday;
	private String deptId;
	private String sexCode;
	private String nationCode;
	private Boolean married;
	private String eduId;
	private String degreeId;
	private String subjectId;
	private String domicileId;
	private String placeDomicile;
	private String anmeldenCode;
	private String joinPartyDate;
	private String politicsCode;
	private String inDate;
	private String authorizedStrengthId;
	private String teaSourceId;
	private String bzlbCode;
	private String zyjszwId;
	private String skillMovesCode;
	private String teachingDate;
	private String gatqCode;
	private String teaStatusCode;
	private Boolean sfssjs;

	// Constructors

	/** default constructor */
	public TTea() {
	}

	/** minimal constructor */
	public TTea(String teaNo, String name) {
		this.teaNo = teaNo;
		this.name = name;
	}

	/** full constructor */
	public TTea(String teaNo, String id, String name, String idno,
			String fomerName, String birthday, String deptId, String sexCode,
			String nationCode, Boolean married, String eduId, String degreeId,
			String subjectId, String domicileId, String placeDomicile,
			String anmeldenCode, String joinPartyDate, String politicsCode,
			String inDate, String authorizedStrengthId, String teaSourceId,
			String bzlbCode, String zyjszwId, String skillMovesCode,
			String teachingDate, String gatqCode, String teaStatusCode,
			Boolean sfssjs) {
		this.teaNo = teaNo;
		this.id = id;
		this.name = name;
		this.idno = idno;
		this.fomerName = fomerName;
		this.birthday = birthday;
		this.deptId = deptId;
		this.sexCode = sexCode;
		this.nationCode = nationCode;
		this.married = married;
		this.eduId = eduId;
		this.degreeId = degreeId;
		this.subjectId = subjectId;
		this.domicileId = domicileId;
		this.placeDomicile = placeDomicile;
		this.anmeldenCode = anmeldenCode;
		this.joinPartyDate = joinPartyDate;
		this.politicsCode = politicsCode;
		this.inDate = inDate;
		this.authorizedStrengthId = authorizedStrengthId;
		this.teaSourceId = teaSourceId;
		this.bzlbCode = bzlbCode;
		this.zyjszwId = zyjszwId;
		this.skillMovesCode = skillMovesCode;
		this.teachingDate = teachingDate;
		this.gatqCode = gatqCode;
		this.teaStatusCode = teaStatusCode;
		this.sfssjs = sfssjs;
	}

	// Property accessors
	@Id
	@Column(name = "TEA_NO", unique = true, nullable = false, length = 20)
	public String getTeaNo() {
		return this.teaNo;
	}

	public void setTeaNo(String teaNo) {
		this.teaNo = teaNo;
	}

	@Column(name = "ID", length = 20)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "NAME_", nullable = false, length = 60)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "IDNO", length = 18)
	public String getIdno() {
		return this.idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	@Column(name = "FOMER_NAME", length = 60)
	public String getFomerName() {
		return this.fomerName;
	}

	public void setFomerName(String fomerName) {
		this.fomerName = fomerName;
	}

	@Column(name = "BIRTHDAY", length = 10)
	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@Column(name = "DEPT_ID", length = 20)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
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

	@Column(name = "MARRIED", precision = 1, scale = 0)
	public Boolean getMarried() {
		return this.married;
	}

	public void setMarried(Boolean married) {
		this.married = married;
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

	@Column(name = "SUBJECT_ID", length = 20)
	public String getSubjectId() {
		return this.subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	@Column(name = "DOMICILE_ID", length = 20)
	public String getDomicileId() {
		return this.domicileId;
	}

	public void setDomicileId(String domicileId) {
		this.domicileId = domicileId;
	}

	@Column(name = "PLACE_DOMICILE", length = 200)
	public String getPlaceDomicile() {
		return this.placeDomicile;
	}

	public void setPlaceDomicile(String placeDomicile) {
		this.placeDomicile = placeDomicile;
	}

	@Column(name = "ANMELDEN_CODE", length = 10)
	public String getAnmeldenCode() {
		return this.anmeldenCode;
	}

	public void setAnmeldenCode(String anmeldenCode) {
		this.anmeldenCode = anmeldenCode;
	}

	@Column(name = "JOIN_PARTY_DATE", length = 10)
	public String getJoinPartyDate() {
		return this.joinPartyDate;
	}

	public void setJoinPartyDate(String joinPartyDate) {
		this.joinPartyDate = joinPartyDate;
	}

	@Column(name = "POLITICS_CODE", length = 10)
	public String getPoliticsCode() {
		return this.politicsCode;
	}

	public void setPoliticsCode(String politicsCode) {
		this.politicsCode = politicsCode;
	}

	@Column(name = "IN_DATE", length = 10)
	public String getInDate() {
		return this.inDate;
	}

	public void setInDate(String inDate) {
		this.inDate = inDate;
	}

	@Column(name = "AUTHORIZED_STRENGTH_ID", length = 10)
	public String getAuthorizedStrengthId() {
		return this.authorizedStrengthId;
	}

	public void setAuthorizedStrengthId(String authorizedStrengthId) {
		this.authorizedStrengthId = authorizedStrengthId;
	}

	@Column(name = "TEA_SOURCE_ID", length = 20)
	public String getTeaSourceId() {
		return this.teaSourceId;
	}

	public void setTeaSourceId(String teaSourceId) {
		this.teaSourceId = teaSourceId;
	}

	@Column(name = "BZLB_CODE", length = 10)
	public String getBzlbCode() {
		return this.bzlbCode;
	}

	public void setBzlbCode(String bzlbCode) {
		this.bzlbCode = bzlbCode;
	}

	@Column(name = "ZYJSZW_ID", length = 20)
	public String getZyjszwId() {
		return this.zyjszwId;
	}

	public void setZyjszwId(String zyjszwId) {
		this.zyjszwId = zyjszwId;
	}

	@Column(name = "SKILL_MOVES_CODE", length = 10)
	public String getSkillMovesCode() {
		return this.skillMovesCode;
	}

	public void setSkillMovesCode(String skillMovesCode) {
		this.skillMovesCode = skillMovesCode;
	}

	@Column(name = "TEACHING_DATE", length = 10)
	public String getTeachingDate() {
		return this.teachingDate;
	}

	public void setTeachingDate(String teachingDate) {
		this.teachingDate = teachingDate;
	}

	@Column(name = "GATQ_CODE", length = 10)
	public String getGatqCode() {
		return this.gatqCode;
	}

	public void setGatqCode(String gatqCode) {
		this.gatqCode = gatqCode;
	}

	@Column(name = "TEA_STATUS_CODE", length = 10)
	public String getTeaStatusCode() {
		return this.teaStatusCode;
	}

	public void setTeaStatusCode(String teaStatusCode) {
		this.teaStatusCode = teaStatusCode;
	}

	@Column(name = "SFSSJS", precision = 1, scale = 0)
	public Boolean getSfssjs() {
		return this.sfssjs;
	}

	public void setSfssjs(Boolean sfssjs) {
		this.sfssjs = sfssjs;
	}
}