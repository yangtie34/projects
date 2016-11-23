package com.jhnu.product.manager.student.dao;

import java.util.List;
import java.util.Map;

public interface StuScoreDao {
	
	/**
	 * 获取各院系成绩
	 * @param school_year
	 * @param term_code
	 * @return
	 */
	public List<Map<String,Object>> getDeptScore(String school_year,String term_code);
	
	/**
	 * 获取各专业成绩
	 * @param school_year
	 * @param term_code
	 * @return
	 */
	public List<Map<String,Object>> getMajorScore(String school_year,String term_code);
	/**
	 * 查询院系
	 * @return
	 */
	public List<Map<String,Object>> getDept();
	
	/**
	 * 查询专业
	 * @param dept_id
	 * @return
	 */
	public List<Map<String,Object>> getMajor(String dept_id);

	/**
	 * 保存成绩JOB
	 * @param results
	 */
	public void saveStuScoreLog(List<Map<String, Object>> results);
	
	/**
	 * 通过临时表获取各院系某学期的成绩统计
	 * @param dept_id
	 * @param school_year
	 * @param term_code
	 * @return
	 */
	public List<Map<String,Object>> getScoreLog(String dept_id,boolean isLeaf,String school_year,String term_code);
	
	/**
	 * 从临时表获取某院系或某专业的历年成绩
	 * @param dept_id
	 * @return
	 */
	public List<Map<String,Object>> getTermScoreByDept(String dept_id);
	
	/**
	 * 获取成绩分布
	 * @param dept_id
	 * @param school_year
	 * @param term_code
	 * @return
	 */
	public List<Map<String,Object>> getScoreFb(String dept_id,String school_year,String term_code);
	
}
