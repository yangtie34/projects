package cn.gilight.research.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_RES_XKJS_MX")
public class TResXkjsMx implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String xkId;
	private String zbId;
	private String year;
	private String ywczbs;
	private String wczbs;

	/** default constructor */
	public TResXkjsMx() {
	}

	/** minimal constructor */
	public TResXkjsMx(String id) {
		this.id = id;
	}

	/** full constructor */
	public TResXkjsMx(String id, String xkId, String zbId, String year,
			String ywczbs, String wczbs) {
		this.id = id;
		this.xkId = xkId;
		this.zbId = zbId;
		this.year = year;
		this.ywczbs = ywczbs;
		this.wczbs = wczbs;
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

	@Column(name = "ZB_ID", length = 20)
	public String getZbId() {
		return this.zbId;
	}

	public void setZbId(String zbId) {
		this.zbId = zbId;
	}

	@Column(name = "YEAR_", length = 20)
	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Column(name = "YWCZBS", length = 20)
	public String getYwczbs() {
		return this.ywczbs;
	}

	public void setYwczbs(String ywczbs) {
		this.ywczbs = ywczbs;
	}

	@Column(name = "WCZBS", length = 20)
	public String getWczbs() {
		return this.wczbs;
	}

	public void setWczbs(String wczbs) {
		this.wczbs = wczbs;
	}

}