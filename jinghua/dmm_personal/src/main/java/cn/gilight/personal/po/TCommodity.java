package cn.gilight.personal.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_COMMODITY")
public class TCommodity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String stuId;
	private String name;
	private String keyword;
	private String desc;
	private String commodityTypeCode;
	private String useTime;
	private double price;
	private String stuName;
	private String tel;
	private boolean isSold;
	private String createTime;
	private String soldTime;

	// Constructors

	/** default constructor */
	public TCommodity() {
	}

	/** minimal constructor */
	public TCommodity(String id) {
		this.id = id;
	}

	/** full constructor */
	public TCommodity(String id, String stuId, String name, String keyword,
			String desc, String commodityTypeCode, String useTime,
			double price, String stuName, String tel, boolean isSold,
			String createTime, String soldTime) {
		this.id = id;
		this.stuId = stuId;
		this.name = name;
		this.keyword = keyword;
		this.desc = desc;
		this.commodityTypeCode = commodityTypeCode;
		this.useTime = useTime;
		this.price = price;
		this.stuName = stuName;
		this.tel = tel;
		this.isSold = isSold;
		this.createTime = createTime;
		this.soldTime = soldTime;
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

	@Column(name = "STU_ID", length = 20)
	public String getStuId() {
		return this.stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}

	@Column(name = "NAME_", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "KEYWORD", length = 20)
	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Column(name = "DESC_", length = 2000)
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Column(name = "COMMODITY_TYPE_CODE", length = 10)
	public String getCommodityTypeCode() {
		return this.commodityTypeCode;
	}

	public void setCommodityTypeCode(String commodityTypeCode) {
		this.commodityTypeCode = commodityTypeCode;
	}

	@Column(name = "USE_TIME", length = 20)
	public String getUseTime() {
		return this.useTime;
	}

	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}

	@Column(name = "PRICE", precision = 8)
	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Column(name = "STU_NAME", length = 20)
	public String getStuName() {
		return this.stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	@Column(name = "TEL", length = 20)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "IS_SOLD", precision = 1, scale = 0)
	public boolean getIsSold() {
		return this.isSold;
	}

	public void setIsSold(boolean isSold) {
		this.isSold = isSold;
	}

	@Column(name = "CREATE_TIME", length = 20)
	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Column(name = "SOLD_TIME", length = 20)
	public String getSoldTime() {
		return this.soldTime;
	}

	public void setSoldTime(String soldTime) {
		this.soldTime = soldTime;
	}

}