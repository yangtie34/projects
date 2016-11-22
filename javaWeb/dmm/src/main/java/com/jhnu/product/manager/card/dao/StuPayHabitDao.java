package com.jhnu.product.manager.card.dao;

import java.util.List;
import java.util.Map;

public interface StuPayHabitDao {
	
	/**
	 * 获取生均消费笔数及金额
	 * @param school_year
	 * @param term_code
	 * @return
	 */
	public List<Map<String,Object>> getStuPays(String startDate,String endDate);
	/**
	 * 保存生均消费笔数及金额到log表
	 * @param school_year
	 * @param term_code
	 * @return
	 */
	public void saveStuPays(List<Map<String,Object>> list);
	
	/**
	 * 从临时表获取某段时间某部门的生均消费笔数及金额
	 * @param school_year
	 * @param term_code
	 * @return
	 */
	public List<Map<String,Object>> getStuPaysLog(String dept_id,boolean flag,String startDate,String endDate);
	
	
	/**
	 * 获取某段时间内日生均消费笔数及金额
	 * @param school_year
	 * @param term_code
	 * @return
	 */
	public List<Map<String,Object>> getStuPaysByDay(String dept_id,boolean isLeaf,String startDate,String endDate);
	
	/**
	 * 获取某段时间内学生就餐习惯
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String,Object>> getStuEatNumbers(String startDate,String endDate);
	
	/**
	 * 保存某段时间内学生就餐习惯到log表
	 * @param school_year
	 * @param term_code
	 * @return
	 */
	public void saveStuEatNumbers(List<Map<String,Object>> list);
	
	/**
	 * 从临时表获取某段时间内学生就餐习惯
	 * @param school_year
	 * @param term_code
	 * @return
	 */
	public List<Map<String,Object>> getStuEatNumbersLog(String dept_id,boolean flag,String startDate,String endDate);
	
	/**
	 * 获取学生消费能力区间分布
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String,Object>> getStuPayMoney(String startDate,String endDate);
	
	/**
	 * 保存消费能力区间分布到临时表
	 * @param list
	 */
	public void saveStuPayMoney(List<Map<String,Object>> list);
	
	/**
	 * 从临时表获取学生消费能力区间分布
	 * @param school_year
	 * @param term_code
	 * @return
	 */
	public List<Map<String,Object>> getStuPayMoneyLog(String dept_id,boolean flag,String startDate,String endDate);
	
	
}
