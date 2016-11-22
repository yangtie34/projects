package com.jhnu.product.manager.card.service;

import java.util.List;
import java.util.Map;

public interface StuPayHabitService {


	/**
	 * 保存生均消费笔数及金额到log表
	 * @param school_year
	 * @param term_code
	 * @return
	 */
	public void saveStuPays();
	
	/**
	 * 从临时表获取某段时间内生均消费笔数及金额
	 * @param school_year
	 * @param term_code
	 * @return
	 */
	public List<Map<String,Object>> getStuPaysLog(String dept_id,boolean isLeaf,String startDate,String endDate);
	
	/**
	 * 获取某段时间内日生均消费笔数及金额
	 * @param school_year
	 * @param term_code
	 * @return
	 */
	public List<Map<String,Object>> getStuPaysByDay(String dept_id,boolean isLeaf,String startDate,String endDate);
	
	/**
	 * 保存生均消费笔数及金额到log表
	 * @param school_year
	 * @param term_code
	 * @return
	 */
	public void saveStuEatNumbers();
	
	/**
	 * 从临时表获取某段时间内学生就餐习惯
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String,Object>> getStuEatNumbersLog(String dept_id,boolean isLeaf,String startDate,String endDate);
	
	/**
	 * 保存学生消费能力区间分布到log表
	 * @param school_year
	 * @param term_code
	 * @return
	 */
	public void saveStuPayMoney();
	
	/**
	 * 获取某段时间内学生消费能力区间分布
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String,Object>> getStuPayMoneyLog(String dept_id,boolean isLeaf,String startDate,String endDate);
}
