package cn.gilight.personal.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_CARD_PAY_MONTH")
public class TCardPayMonth implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String cardid;
	private String sno;
	private String datetime;
	private double sumVal;
	private String cardDealId;
	private String cardDeal;

	// Constructors

	/** default constructor */
	public TCardPayMonth() {
	}

	/** minimal constructor */
	public TCardPayMonth(String id) {
		this.id = id;
	}

	/** full constructor */
	public TCardPayMonth(String id, String name, String cardid, String sno,
			String datetime, double sumVal, String cardDealId,String cardDeal) {
		this.id = id;
		this.name = name;
		this.cardid = cardid;
		this.sno = sno;
		this.datetime = datetime;
		this.sumVal = sumVal;
		this.cardDealId = cardDealId;
		this.cardDeal = cardDeal;
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

	@Column(name = "SUM_VAL", precision = 10)
	public double getSumVal() {
		return this.sumVal;
	}

	public void setSumVal(double sumVal) {
		this.sumVal = sumVal;
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
}