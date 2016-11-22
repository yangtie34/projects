package com.jhnu.product.manager.score.service;

import java.util.List;
import java.util.Map;

public interface ScoreAndLibraryService {

	/**
	 * 保存成绩与图书馆进出次数分布
	 * 
	 * @return
	 */
	public void saveScoreAndLibraryLog();

	/**
	 * 获取某学年学期男生成绩与进入图书馆次数分布
	 * 
	 * @param school_year
	 * @param term_code
	 * @param dept_id
	 * @return
	 */
	public List<Map<String, Object>> getBoyScoreAndLibraryLog(
			String school_year, String term_code, String dept_id, boolean isLeaf);

	/**
	 * 获取某学年学期女生成绩与进入图书馆次数分布
	 * 
	 * @param school_year
	 * @param term_code
	 * @param dept_id
	 * @return
	 */
	public List<Map<String, Object>> getGrilScoreAndLibraryLog(
			String school_year, String term_code, String dept_id, boolean isLeaf);

	/**
	 * 获取某学年学期学生成绩与进入图书馆次数分布
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
	public List<Map<String, Object>> getStusScoreAndLibraryLog(
			String school_year, String term_code, String dept_id, boolean isLeaf);
}
