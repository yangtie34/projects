package cn.gilight.research.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_RES_XKJS_XKCY")
public class TResXkjsXkcy implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String xkId;
	private String teaNo;

	// Constructors

	/** default constructor */
	public TResXkjsXkcy() {
	}

	/** minimal constructor */
	public TResXkjsXkcy(String id) {
		this.id = id;
	}

	/** full constructor */
	public TResXkjsXkcy(String id, String xkId, String teaNo) {
		this.id = id;
		this.xkId = xkId;
		this.teaNo = teaNo;
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

	@Column(name = "XK_ID", length = 20)
	public String getXkId() {
		return this.xkId;
	}

	public void setXkId(String xkId) {
		this.xkId = xkId;
	}

	@Column(name = "TEA_NO", length = 20)
	public String getTeaNo() {
		return this.teaNo;
	}

	public void setTeaNo(String teaNo) {
		this.teaNo = teaNo;
	}

}