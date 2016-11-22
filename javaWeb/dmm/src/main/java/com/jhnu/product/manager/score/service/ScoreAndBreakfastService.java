package com.jhnu.product.manager.score.service;

import java.util.List;
import java.util.Map;

public interface ScoreAndBreakfastService {

	/**
	 * 保存成绩与早餐分布临时表
	 * 
	 * @param results
	 */
	public void saveScoreAndBreakfast();

	/**
	 * 获取男生成绩与早餐分布
	 * 
	 * @param school_year
	 * @param term_code
	 * @param dept_id
	 * @return
	 */
	public List<Map<String, Object>> getBoyScoreAndBreakfastLog(
			String school_year, String term_code, String dept_id, boolean isLeaf);

	/**
	 * 获取女生成绩与早餐分布
	 * 
	 * @param school_year
	 * @param term_code
	 * @param dept_id
	 * @return
	 */
	public List<Map<String, Object>> getGrilScoreAndBreakfastLog(
			String school_year, String term_code, String dept_id, boolean isLeaf);

	/**
	 * 获取所有学生成绩与早餐分布
	 * 
	 * @param school_year
	 *            某学年
	 * @param term_code
	 *            学历code
	 * @param dept_id
	 *            院系Id
	 * @param isLeaf
	 *            是否是子节点
	 * @return List<Map>
	 */
	public List<Map<String, Object>> getAllStusScoreAndBreakfastLog(
			String school_year, String term_code, String dept_id, boolean isLeaf);

}
