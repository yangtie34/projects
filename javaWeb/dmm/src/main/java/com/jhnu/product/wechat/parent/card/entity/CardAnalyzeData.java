package com.jhnu.product.wechat.parent.card.entity;

import java.util.Map;
import java.util.TreeMap;

import com.jhnu.product.common.stu.entity.Student;

public class CardAnalyzeData {
	private Student stu;
	private double balance = 0.0d;// 卡余额
	private double total = 0.0d;// 累计消费
	private double avg_my = 0.0d;// 我的日均消费值
	private Map<String,Double> type_avg_my = new TreeMap<String,Double>();// 学生的 分类日均消费水平
	private double avg_all = 0.0d;// 全部学生的日均消费值
	private double avg_morethan = 0.0d;// 我的日均消费值超越多少人
	private double val1;// 普遍学生的超市消费占比值
	private double val2;// 我的超市消费占比值
	public CardAnalyzeData(double balance, double total, double avg_my,
			double avg_all) {
		super();
		this.balance = balance;
		this.total = total;
		this.avg_my = avg_my;
		this.avg_all = avg_all;
	}
	
	public CardAnalyzeData() {}
	
	
	public Student getStu() {
		return stu;
	}

	public void setStu(Student stu) {
		this.stu = stu;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public double getVal1() {
		return val1;
	}

	public void setVal1(double val1) {
		this.val1 = val1;
	}

	public double getVal2() {
		return val2;
	}

	public void setVal2(double val2) {
		this.val2 = val2;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getAvg_my() {
		return avg_my;
	}

	public void setAvg_my(double avg_my) {
		this.avg_my = avg_my;
	}

	public Map<String, Double> getType_avg_my() {
		return type_avg_my;
	}

	public void setType_avg_my(Map<String, Double> type_avg_my) {
		this.type_avg_my = type_avg_my;
	}

	public double getAvg_all() {
		return avg_all;
	}

	public void setAvg_all(double avg_all) {
		this.avg_all = avg_all;
	}

	public double getAvg_morethan() {
		return avg_morethan;
	}

	public void setAvg_morethan(double avg_morethan) {
		this.avg_morethan = avg_morethan;
	}

	
}
