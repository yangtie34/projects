package cn.gilight.personal.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_CARD_PAY_DAY")
public class TCardPayDay implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String gender;
	private String datetime;
	private long total;
	private String cardDealId;
	private String cardDeal;
	private long paytimes;
	private double sumVal;
	private double avgVal;
	private double maxVal;
	private double minVal;

	// Constructors

	/** default constructor */
	public TCardPayDay() {
	}

	/** minimal constructor */
	public TCardPayDay(String id) {
		this.id = id;
	}

	/** full constructor */
	public TCardPayDay(String id, String gender, String datetime, long total,
			String cardDealId, String cardDeal, long paytimes, double sumVal,
			double avgVal, double maxVal, double minVal) {
		this.id = id;
		this.gender = gender;
		this.datetime = datetime;
		this.total = total;
		this.cardDealId = cardDealId;
		this.cardDeal = cardDeal;
		this.paytimes = paytimes;
		this.sumVal = sumVal;
		this.avgVal = avgVal;
		this.maxVal = maxVal;
		this.minVal = minVal;
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

	@Column(name = "GENDER", length = 60)
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "DATETIME", length = 20)
	public String getDatetime() {
		return this.datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	@Column(name = "TOTAL", precision = 10, scale = 0)
	public long getTotal() {
		return this.total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	@Column(name = "CARD_DEAL_ID", length = 20)
	public String getCardDealId() {
		return this.cardDealId;
	}

	public void setCardDealId(String cardDealId) {
		this.cardDealId = cardDealId;
	}

	@Column(name = "CARD_DEAL", length = 60)
	public String getCardDeal() {
		return this.cardDeal;
	}

	public void setCardDeal(String cardDeal) {
		this.cardDeal = cardDeal;
	}

	@Column(name = "PAYTIMES", precision = 10, scale = 0)
	public long getPaytimes() {
		return this.paytimes;
	}

	public void setPaytimes(long paytimes) {
		this.paytimes = paytimes;
	}

	@Column(name = "SUM_VAL", precision = 10)
	public double getSumVal() {
		return this.sumVal;
	}

	public void setSumVal(double sumVal) {
		this.sumVal = sumVal;
	}

	@Column(name = "AVG_VAL", precision = 10)
	public double getAvgVal() {
		return this.avgVal;
	}

	public void setAvgVal(double avgVal) {
		this.avgVal = avgVal;
	}

	@Column(name = "MAX_VAL", precision = 10)
	public double getMaxVal() {
		return this.maxVal;
	}

	public void setMaxVal(double maxVal) {
		this.maxVal = maxVal;
	}

	@Column(name = "MIN_VAL", precision = 10)
	public double getMinVal() {
		return this.minVal;
	}

	public void setMinVal(double minVal) {
		this.minVal = minVal;
	}
}