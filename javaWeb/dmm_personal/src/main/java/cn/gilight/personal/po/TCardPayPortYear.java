package cn.gilight.personal.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_CARD_PAY_PORT_YEAR")
public class TCardPayPortYear implements java.io.Serializable {

	private static final long serialVersionUID = 8041144055410726436L;

	private String id;
	private String name;
	private String cardid;
	private String sno;
	private String datetime;
	private long payTimes;
	private double sumVal;
	private double maxVal;
	private String cardPortId;
	private String cardPort;

	// Constructors

	/** default constructor */
	public TCardPayPortYear() {
	}

	/** minimal constructor */
	public TCardPayPortYear(String id) {
		this.id = id;
	}

	/** full constructor */
	public TCardPayPortYear(String id, String name, String cardid, String sno,
			String datetime, long payTimes, double sumVal, double maxVal,
			String cardPortId, String cardPort) {
		this.id = id;
		this.name = name;
		this.cardid = cardid;
		this.sno = sno;
		this.datetime = datetime;
		this.payTimes = payTimes;
		this.sumVal = sumVal;
		this.maxVal = maxVal;
		this.cardPortId = cardPortId;
		this.cardPort = cardPort;
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

	@Column(name = "CARDID", length = 20)
	public String getCardid() {
		return this.cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}

	@Column(name = "SNO", length = 20)
	public String getSno() {
		return this.sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	@Column(name = "DATETIME", length = 20)
	public String getDatetime() {
		return this.datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	@Column(name = "PAY_TIMES", precision = 10, scale = 0)
	public long getPayTimes() {
		return this.payTimes;
	}

	public void setPayTimes(long payTimes) {
		this.payTimes = payTimes;
	}

	@Column(name = "SUM_VAL", precision = 10)
	public double getSumVal() {
		return this.sumVal;
	}

	public void setSumVal(double sumVal) {
		this.sumVal = sumVal;
	}

	@Column(name = "MAX_VAL", precision = 10)
	public double getMaxVal() {
		return this.maxVal;
	}

	public void setMaxVal(double maxVal) {
		this.maxVal = maxVal;
	}

	@Column(name = "CARD_PORT_ID", length = 20)
	public String getCardPortId() {
		return this.cardPortId;
	}

	public void setCardPortId(String cardPortId) {
		this.cardPortId = cardPortId;
	}

	@Column(name = "CARD_PORT", length = 60)
	public String getCardPort() {
		return this.cardPort;
	}

	public void setCardPort(String cardPort) {
		this.cardPort = cardPort;
	}
}