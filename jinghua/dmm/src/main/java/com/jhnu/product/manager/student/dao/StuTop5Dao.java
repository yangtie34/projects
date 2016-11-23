package com.jhnu.product.manager.student.dao;

import java.util.List;
import java.util.Map;

public interface StuTop5Dao {
	/**
	 * 获取按平均成绩TOP5
	 * @param year
	 * @param dept_id
	 * @return
	 */
	public List<Map<String,Object>> getStuTop5ByAvg(boolean isLeaf,String year,String dept_id);
	
	
	/**
	 * 获取按gpa的TOP5
	 * @param year
	 * @param dept_id
	 * @return
	 */
	public List<Map<String,Object>> getStuTop5ByGpa(boolean isLeaf,String year,String dept_id);
}
