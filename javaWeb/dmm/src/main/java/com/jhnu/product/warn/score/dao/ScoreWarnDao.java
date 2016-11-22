package com.jhnu.product.warn.score.dao;

import java.util.List;
import java.util.Map;

public interface ScoreWarnDao {
	
	/**
	 * 根据学生ID 获取挂科科目
	 * @param stuId
	 * @return
	 */
	public List<Map<String,Object>> getScores(String stuId);
	
}
