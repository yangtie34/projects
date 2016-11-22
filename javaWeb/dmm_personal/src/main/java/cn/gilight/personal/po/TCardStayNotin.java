package cn.gilight.personal.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_CARD_STAY_NOTIN")
public class TCardStayNotin implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String sno;
	private String bjid;
	private String bjmc;
	private String datetime;
	private String lasttime;
	// Constructors

	/** default constructor */
	public TCardStayNotin() {
	}

	/** minimal constructor */
	public TCardStayNotin(String id) {
		this.id = id;
	}

	/** full constructor */
	public TCardStayNotin(String id, String name, String sno, String bjid,
			String bjmc, String datetime, String lasttime ) {
		this.id = id;
		this.name = name;
		this.sno = sno;
		this.bjid = bjid;
		this.bjmc = bjmc;
		this.datetime = datetime;
		this.lasttime = lasttime;
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

	@Column(name = "LASTTIME", length = 20)
	public String getLasttime() {
		return this.lasttime;
	}

	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}
}