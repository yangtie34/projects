package cn.gilight.personal.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_STU_SMART_PAY")
public class TStuSmartPay implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String sno;
	private double sumVal;
	private Integer payTimes;
	private Integer payDays;
	private double avgDay;
	private Integer zaoTimes;
	private double zaoAvg;
	private Integer wuTimes;
	private double wuAvg;
	private Integer wanTimes;
	private double wanAvg;

	// Constructors

	/** default constructor */
	public TStuSmartPay() {
	}

	/** minimal constructor */
	public TStuSmartPay(String id) {
		this.id = id;
	}

	/** full constructor */
	public TStuSmartPay(String id, String sno, double sumVal, Integer payTimes,
			Integer payDays, double avgDay, Integer zaoTimes, double zaoAvg,
			Integer wuTimes, double wuAvg, Integer wanTimes, double wanAvg) {
		this.id = id;
		this.sno = sno;
		this.sumVal = sumVal;
		this.payTimes = payTimes;
		this.payDays = payDays;
		this.avgDay = avgDay;
		this.zaoTimes = zaoTimes;
		this.zaoAvg = zaoAvg;
		this.wuTimes = wuTimes;
		this.wuAvg = wuAvg;
		this.wanTimes = wanTimes;
		this.wanAvg = wanAvg;
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

	@Column(name = "SNO", length = 20)
	public String getSno() {
		return this.sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	@Column(name = "SUM_VAL", precision = 10)
	public double getSumVal() {
		return this.sumVal;
	}

	public void setSumVal(double sumVal) {
		this.sumVal = sumVal;
	}

	@Column(name = "PAY_TIMES", precision = 6, scale = 0)
	public Integer getPayTimes() {
		return this.payTimes;
	}

	public void setPayTimes(Integer payTimes) {
		this.payTimes = payTimes;
	}

	@Column(name = "PAY_DAYS", precision = 6, scale = 0)
	public Integer getPayDays() {
		return this.payDays;
	}

	public void setPayDays(Integer payDays) {
		this.payDays = payDays;
	}

	@Column(name = "AVG_DAY", precision = 10)
	public double getAvgDay() {
		return this.avgDay;
	}

	public void setAvgDay(double avgDay) {
		this.avgDay = avgDay;
	}

	@Column(name = "ZAO_TIMES", precision = 6, scale = 0)
	public Integer getZaoTimes() {
		return this.zaoTimes;
	}

	public void setZaoTimes(Integer zaoTimes) {
		this.zaoTimes = zaoTimes;
	}

	@Column(name = "ZAO_AVG", precision = 10)
	public double getZaoAvg() {
		return this.zaoAvg;
	}

	public void setZaoAvg(double zaoAvg) {
		this.zaoAvg = zaoAvg;
	}

	@Column(name = "WU_TIMES", precision = 6, scale = 0)
	public Integer getWuTimes() {
		return this.wuTimes;
	}

	public void setWuTimes(Integer wuTimes) {
		this.wuTimes = wuTimes;
	}

	@Column(name = "WU_AVG", precision = 10)
	public double getWuAvg() {
		return this.wuAvg;
	}

	public void setWuAvg(double wuAvg) {
		this.wuAvg = wuAvg;
	}

	@Column(name = "WAN_TIMES", precision = 6, scale = 0)
	public Integer getWanTimes() {
		return this.wanTimes;
	}

	public void setWanTimes(Integer wanTimes) {
		this.wanTimes = wanTimes;
	}

	@Column(name = "WAN_AVG", precision = 10)
	public double getWanAvg() {
		return this.wanAvg;
	}

	public void setWanAvg(double wanAvg) {
		this.wanAvg = wanAvg;
	}
}