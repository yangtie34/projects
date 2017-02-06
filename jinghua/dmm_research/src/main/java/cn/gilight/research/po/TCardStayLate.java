package cn.gilight.research.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TCardStayLate entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CARD_STAY_LATE" )
public class TCardStayLate implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String sno;
	private String bjid;
	private String bjmc;
	private String datetime;
	private String backtime;

	// Constructors

	/** default constructor */
	public TCardStayLate() {
	}

	/** minimal constructor */
	public TCardStayLate(String id) {
		this.id = id;
	}

	/** full constructor */
	public TCardStayLate(String id, String name, String sno, String bjid,
			String bjmc, String datetime, String backtime) {
		this.id = id;
		this.name = name;
		this.sno = sno;
		this.bjid = bjid;
		this.bjmc = bjmc;
		this.datetime = datetime;
		this.backtime = backtime;
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

	@Column(name = "NAME", length = 60)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "SNO", length = 20)
	public String getSno() {
		return this.sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	@Column(name = "BJID", length = 20)
	public String getBjid() {
		return this.bjid;
	}

	public void setBjid(String bjid) {
		this.bjid = bjid;
	}

	@Column(name = "BJMC", length = 60)
	public String getBjmc() {
		return this.bjmc;
	}

	public void setBjmc(String bjmc) {
		this.bjmc = bjmc;
	}

	@Column(name = "DATETIME", length = 20)
	public String getDatetime() {
		return this.datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	@Column(name = "BACKTIME", length = 10)
	public String getBacktime() {
		return this.backtime;
	}

	public void setBacktime(String backtime) {
		this.backtime = backtime;
	}
}