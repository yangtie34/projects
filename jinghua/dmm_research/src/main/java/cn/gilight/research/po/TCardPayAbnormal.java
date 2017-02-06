package cn.gilight.research.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_CARD_PAY_ABNORMAL")
public class TCardPayAbnormal implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String yxId;
	private String yxName;
	private String sno;
	private String sname;
	private String cardNo;
	private String datetime;
	private double sumVal;
	private boolean ishigh;

	// Constructors

	/** default constructor */
	public TCardPayAbnormal() {
	}

	/** minimal constructor */
	public TCardPayAbnormal(String id) {
		this.id = id;
	}

	/** full constructor */
	public TCardPayAbnormal(String id, String yxId, String yxName, String sno,
			String sname, String cardNo, String datetime, double sumVal,
			boolean ishigh) {
		this.id = id;
		this.yxId = yxId;
		this.yxName = yxName;
		this.sno = sno;
		this.sname = sname;
		this.cardNo = cardNo;
		this.datetime = datetime;
		this.sumVal = sumVal;
		this.ishigh = ishigh;
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

	@Column(name = "YX_ID", length = 20)
	public String getYxId() {
		return this.yxId;
	}

	public void setYxId(String yxId) {
		this.yxId = yxId;
	}

	@Column(name = "YX_NAME", length = 60)
	public String getYxName() {
		return this.yxName;
	}

	public void setYxName(String yxName) {
		this.yxName = yxName;
	}

	@Column(name = "SNO", length = 20)
	public String getSno() {
		return this.sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	@Column(name = "SNAME", length = 60)
	public String getSname() {
		return this.sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	@Column(name = "CARD_NO", length = 20)
	public String getCardNo() {
		return this.cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	@Column(name = "DATETIME", length = 200)
	public String getDatetime() {
		return this.datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	@Column(name = "SUM_VAL", precision = 10)
	public double getSumVal() {
		return this.sumVal;
	}

	public void setSumVal(double sumVal) {
		this.sumVal = sumVal;
	}

	@Column(name = "ISHIGH", precision = 1, scale = 0)
	public boolean getIshigh() {
		return this.ishigh;
	}

	public void setIshigh(boolean ishigh) {
		this.ishigh = ishigh;
	}

}