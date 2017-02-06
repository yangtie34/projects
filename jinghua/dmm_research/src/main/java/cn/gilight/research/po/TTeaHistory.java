package cn.gilight.research.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TTeaHistory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_TEA_HISTORY")
public class TTeaHistory implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String teaNo;
	private String year;
	private String datetime;
	private String contents;
	private String infoSource;

	// Constructors

	/** default constructor */
	public TTeaHistory() {
	}

	/** minimal constructor */
	public TTeaHistory(String id) {
		this.id = id;
	}

	/** full constructor */
	public TTeaHistory(String id, String teaNo, String year, String datetime,
			String contents, String infoSource) {
		this.id = id;
		this.teaNo = teaNo;
		this.year = year;
		this.datetime = datetime;
		this.contents = contents;
		this.infoSource = infoSource;
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

	@Column(name = "TEA_NO", length = 60)
	public String getTeaNo() {
		return this.teaNo;
	}

	public void setTeaNo(String teaNo) {
		this.teaNo = teaNo;
	}

	@Column(name = "YEAR", length = 10)
	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Column(name = "DATETIME", length = 60)
	public String getDatetime() {
		return this.datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	@Column(name = "CONTENTS", length = 3000)
	public String getContents() {
		return this.contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	@Column(name = "INFO_SOURCE", length = 40)
	public String getInfoSource() {
		return this.infoSource;
	}

	public void setInfoSource(String infoSource) {
		this.infoSource = infoSource;
	}

}