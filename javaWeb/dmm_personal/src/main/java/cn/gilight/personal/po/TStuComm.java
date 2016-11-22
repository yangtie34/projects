package cn.gilight.personal.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_STU_COMM")
public class TStuComm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String stuId;
	private String id;
	private String homeTel;
	private String tel;
	private String homeAddr;
	private String postcode;
	private String qq;
	private String email;
	private String homePage;

	/** default constructor */
	public TStuComm() {
	}

	/** minimal constructor */
	public TStuComm(String stuId) {
		this.stuId = stuId;
	}

	/** full constructor */
	public TStuComm(String stuId, String id, String homeTel, String tel,
			String homeAddr, String postcode, String qq, String email,
			String homePage) {
		this.stuId = stuId;
		this.id = id;
		this.homeTel = homeTel;
		this.tel = tel;
		this.homeAddr = homeAddr;
		this.postcode = postcode;
		this.qq = qq;
		this.email = email;
		this.homePage = homePage;
	}

	// Property accessors
	@Id
	@Column(name = "STU_ID", unique = true, nullable = false, length = 20)
	public String getStuId() {
		return this.stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}

	@Column(name = "ID", length = 20)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "HOME_TEL", length = 120)
	public String getHomeTel() {
		return this.homeTel;
	}

	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}

	@Column(name = "TEL", length = 120)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "HOME_ADDR", length = 300)
	public String getHomeAddr() {
		return this.homeAddr;
	}

	public void setHomeAddr(String homeAddr) {
		this.homeAddr = homeAddr;
	}

	@Column(name = "POSTCODE", length = 10)
	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	@Column(name = "QQ", length = 20)
	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Column(name = "EMAIL", length = 60)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "HOME_PAGE", length = 100)
	public String getHomePage() {
		return this.homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

}