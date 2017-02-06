package cn.gilight.research.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_STU_SMART")
public class TStuSmart implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String sno;
	private String bjid;
	private String zyid;
	private String rxnj;
	private String gender;
	private double score;

	public TStuSmart() {
	}

	/** minimal constructor */
	public TStuSmart(String id) {
		this.id = id;
	}

	/** full constructor */
	public TStuSmart(String id, String name, String sno, String bjid,
			String zyid, String rxnj, String gender, double score) {
		this.id = id;
		this.name = name;
		this.sno = sno;
		this.bjid = bjid;
		this.zyid = zyid;
		this.rxnj = rxnj;
		this.gender = gender;
		this.score = score;
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

	@Column(name = "ZYID", length = 20)
	public String getZyid() {
		return this.zyid;
	}

	public void setZyid(String zyid) {
		this.zyid = zyid;
	}

	@Column(name = "RXNJ", length = 10)
	public String getRxnj() {
		return this.rxnj;
	}

	public void setRxnj(String rxnj) {
		this.rxnj = rxnj;
	}

	@Column(name = "GENDER", length = 10)
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "SCORE", precision = 10)
	public double getScore() {
		return this.score;
	}

	public void setScore(double score) {
		this.score = score;
	}
}