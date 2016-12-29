package com.glite.flink.example.domain;

public class TCardPayDayHour {
	private String cardId;
	private String cardPortId;
	private String cardDealId;
	private String year;
	private String month;
	private String day;
	private String begin;
	private String end;
	private Long counts;
	private Float payMoney;

	public TCardPayDayHour() {
		super();
	}

	public TCardPayDayHour(String cardId, String cardPortId, String cardDealId,
			String year, String month, String day, String begin, String end,
			Long counts, Float payMoney) {
		super();
		this.cardId = cardId;
		this.cardPortId = cardPortId;
		this.cardDealId = cardDealId;
		this.year = year;
		this.month = month;
		this.day = day;
		this.begin = begin;
		this.end = end;
		this.counts = counts;
		this.payMoney = payMoney;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCardPortId() {
		return cardPortId;
	}

	public void setCardPortId(String cardPortId) {
		this.cardPortId = cardPortId;
	}

	public String getCardDealId() {
		return cardDealId;
	}

	public void setCardDealId(String cardDealId) {
		this.cardDealId = cardDealId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public Long getCounts() {
		return counts;
	}

	public void setCounts(Long counts) {
		this.counts = counts;
	}

	public Float getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Float payMoney) {
		this.payMoney = payMoney;
	}

	public String toString() {
		return this.cardId + "," + this.cardPortId + "," + this.cardDealId
				+ "," + this.year + "," + this.month + "," + this.day + ","
				+ this.begin + "," + this.end + "," + this.counts + ","
				+ this.payMoney;
	}

}
