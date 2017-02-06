package com.glite.flink.example.domain;

public class TCardPay {
	private Long id;
	private String cardId;
	private Float payMoney;
	private Float surplusMoney;
	private String time_;
	private String cardPortId;
	private String cardDealId;
	private String walletCode;	
	
	public TCardPay() {
		super();
	}


	public TCardPay(String items){
		String[] fields=items.replace("'", "").split(",");
		this.setId(Long.valueOf(fields[0]));
		this.setCardId(fields[1]);
		this.setPayMoney(Float.valueOf(fields[2]));
		this.setSurplusMoney(Float.valueOf(fields[3]));
		this.setTime_(fields[4]);
		this.setCardPortId(fields[5]);
		this.setCardDealId(fields[6]);
		this.setWalletCode(fields[7]);		
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public Float getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(Float payMoney) {
		this.payMoney = payMoney;
	}
	public Float getSurplusMoney() {
		return surplusMoney;
	}
	public void setSurplusMoney(Float surplusMoney) {
		this.surplusMoney = surplusMoney;
	}
	public String getTime_() {
		return time_;
	}
	public void setTime_(String time_) {
		this.time_ = time_;
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
	public String getWalletCode() {
		return walletCode;
	}
	public void setWalletCode(String walletCode) {
		this.walletCode = walletCode;
	}
	
	
}
