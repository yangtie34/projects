package com.jhnu.product.manager.score.dao;

import java.util.List;
import java.util.Map;

public interface ScoreAndBreakfastDao {
	
	/**
	 * 获取某学年学期的成绩与早餐分布
	 * @param school_year
	 * @param term_code
	 * @return
	 */
	public List<Map<String,Object>> getScoreAndBreakfast(String school_year,String term_code);
	
	/**
	 * 保存成绩与早餐分布临时表
	 * @param results
	 */
	public void saveScoreAndBreakfast(List<Map<String,Object>> results);
	
	/**
	 * 获取男生成绩与早餐分布
	 * @param school_year
	 * @param term_code
	 * @param dept_id
	 * @return
	 */
	public List<Map<String,Object>> getBoyScoreAndBreakfastLog(String school_year,String term_code,String dept_id,boolean isLeaf);
	
	/**
	 * 获取女生成绩与早餐分布
	 * @param school_year
	 * @param term_code
	 * @param dept_id
	 * @return
	 */
	public List<Map<String,Object>> getGrilScoreAndBreakfastLog(String school_year,String term_code,String dept_id,boolean isLeaf);
}
