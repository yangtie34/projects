package cn.gilight.personal.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_WECHAT_BIND")
public class TWechatBind implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String openid;
	private String wechatName;
	private Integer wechatSex;
	private String city;
	private String wechatHeadImg;
	private String province;
	private String username;
	private String xsId;
	private String jzgId;
	private Integer sf;
	
	// Constructors

	/** default constructor */
	public TWechatBind() {
	}

	/** minimal constructor */
	public TWechatBind(String id) {
		this.id = id;
	}

	/** full constructor */
	public TWechatBind(String id, String openid, String wechatName,
			Integer wechatSex, String city, String wechatHeadImg,
			String province, Integer sf, String username, String xsId,
			String jzgId) {
		this.id = id;
		this.openid = openid;
		this.wechatName = wechatName;
		this.wechatSex = wechatSex;
		this.city = city;
		this.wechatHeadImg = wechatHeadImg;
		this.province = province;
		this.sf = sf;
		this.username = username;
		this.xsId = xsId;
		this.jzgId = jzgId;
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

	@Column(name = "OPENID", length = 60)
	public String getOpenid() {
		return this.openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	@Column(name = "WECHAT_NAME", length = 40)
	public String getWechatName() {
		return this.wechatName;
	}

	public void setWechatName(String wechatName) {
		this.wechatName = wechatName;
	}

	@Column(name = "WECHAT_SEX", length = 4)
	public Integer getWechatSex() {
		return this.wechatSex;
	}

	public void setWechatSex(Integer wechatSex) {
		this.wechatSex = wechatSex;
	}

	@Column(name = "CITY", length = 20)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "WECHAT_HEAD_IMG", length = 200)
	public String getWechatHeadImg() {
		return this.wechatHeadImg;
	}

	public void setWechatHeadImg(String wechatHeadImg) {
		this.wechatHeadImg = wechatHeadImg;
	}

	@Column(name = "PROVINCE", length = 20)
	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "SF", precision = 2, scale = 0)
	public Integer getSf() {
		return this.sf;
	}

	public void setSf(Integer sf) {
		this.sf = sf;
	}
	@Column(name = "USERNAME", length = 20)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name = "XS_ID", length = 20)
	public String getXsId() {
		return this.xsId;
	}

	public void setXsId(String xsId) {
		this.xsId = xsId;
	}

	@Column(name = "JZG_ID", length = 20)
	public String getJzgId() {
		return this.jzgId;
	}

	public void setJzgId(String jzgId) {
		this.jzgId = jzgId;
	}

}