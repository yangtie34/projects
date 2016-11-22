package com.jhnu.product.wechat.parent.card.entity;

public class TlWechatConsumAnalyze {
	private String stuId;// 学生id
	private String sex; // 学生性别
	private String timeType;// 时间类型
	private String analyzePaydealId;// 统计用消费类型
	private String sum_;// 累计消费
	private String xflxZb;//消费类型消费占比
	private String xflxAvg;//消费类型平均消费
	private String moreThan;// 超越人数
	public String getStuId() {
		return stuId;
	}
	public void setStuId(String stuId) {
		this.stuId = stuId;
	}
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getTimeType() {
		return timeType;
	}
	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
	public String getAnalyzePaydealId() {
		return analyzePaydealId;
	}
	public void setAnalyzePaydealId(String analyzePaydealId) {
		this.analyzePaydealId = analyzePaydealId;
	}
	public String getSum_() {
		return sum_;
	}
	public void setSum_(String sum_) {
		this.sum_ = sum_;
	}
	public String getXflxZb() {
		return xflxZb;
	}
	public void setXflxZb(String xflxZb) {
		this.xflxZb = xflxZb;
	}
	public String getXflxAvg() {
		return xflxAvg;
	}
	public void setXflxAvg(String xflxAvg) {
		this.xflxAvg = xflxAvg;
	}
	public String getMoreThan() {
		return moreThan;
	}
	public void setMoreThan(String moreThan) {
		this.moreThan = moreThan;
	}
	public TlWechatConsumAnalyze() {
		super();
	}
	
	
}
