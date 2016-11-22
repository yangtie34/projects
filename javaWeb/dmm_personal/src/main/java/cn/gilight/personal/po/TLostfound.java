package cn.gilight.personal.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_LOSTFOUND" )
public class TLostfound implements java.io.Serializable {

	private static final long serialVersionUID = -8018170130741636564L;
	private String id;
	private String username;
	private String lostfoundTypeCode;
	private String message;
	private String tel;
	private String imageUrl;
	private String creatTime;
	private boolean isSolve;
	private String solveTime;

	// Constructors

	/** default constructor */
	public TLostfound() {
	}

	/** minimal constructor */
	public TLostfound(String id) {
		this.id = id;
	}

	/** full constructor */
	public TLostfound(String id, String username,  
			String lostfoundTypeCode, String message, String tel,
			String imageUrl, String creatTime, boolean isSolve, String solveTime) {
		this.id = id;
		this.username = username;
		this.lostfoundTypeCode = lostfoundTypeCode;
		this.message = message;
		this.tel = tel;
		this.imageUrl = imageUrl;
		this.creatTime = creatTime;
		this.isSolve = isSolve;
		this.solveTime = solveTime;
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

	@Column(name = "USERNAME", length = 60)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	@Column(name = "LOSTFOUND_TYPE_CODE", length = 10)
	public String getLostfoundTypeCode() {
		return this.lostfoundTypeCode;
	}

	public void setLostfoundTypeCode(String lostfoundTypeCode) {
		this.lostfoundTypeCode = lostfoundTypeCode;
	}

	@Column(name = "MESSAGE", length = 2000)
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name = "TEL_", length = 20)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "IMAGE_URL", length = 200)
	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Column(name = "CREAT_TIME", length = 20)
	public String getCreatTime() {
		return this.creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	@Column(name = "IS_SOLVE", precision = 1, scale = 0)
	public boolean getIsSolve() {
		return this.isSolve;
	}

	public void setIsSolve(boolean isSolve) {
		this.isSolve = isSolve;
	}

	@Column(name = "SOLVE_TIME", length = 20)
	public String getSolveTime() {
		return this.solveTime;
	}

	public void setSolveTime(String solveTime) {
		this.solveTime = solveTime;
	}

}