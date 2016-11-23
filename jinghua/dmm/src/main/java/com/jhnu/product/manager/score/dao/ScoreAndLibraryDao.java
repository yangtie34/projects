package com.jhnu.product.manager.score.dao;

import java.util.List;
import java.util.Map;

public interface ScoreAndLibraryDao {
	/**
	 * 获取某学年学期成绩与进出图书馆次数分布
	 * @param school_year
	 * @param term_code
	 * @param dept_id
	 * @return
	 */
	public List<Map<String,Object>> getScoreAndLibrary(String school_year,String term_code);
	
	/**
	 * 保存某学年学期成绩与进出图书馆次数分布LOG
	 * @param list
	 */
	public void saveScoreAndLibraryLog(List<Map<String,Object>> results);
	
	/**
	 * 获取某学年学期男生成绩与进入图书馆次数分布
	 * @param school_year
	 * @param term_code
	 * @param dept_id
	 * @return
	 */
	public List<Map<String,Object>> getBoyScoreAndLibraryLog(String school_year,String term_code,String dept_id,boolean isLeaf);
	
	/**
	 * 获取某学年学期女生成绩与进入图书馆次数分布
	 * @param school_year
	 * @param term_code
	 * @param dept_id
	 * @return
	 */
	public List<Map<String,Object>> getGrilScoreAndLibraryLog(String school_year,String term_code,String dept_id,boolean isLeaf);
	
}
