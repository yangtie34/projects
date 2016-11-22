package com.jhnu.product.manager.student.service;

import java.util.List;
import java.util.Map;

public interface StuTop5Service {
	/**
	 * 获取按平均成绩TOP5
	 * @param year
	 * @param dept_id
	 * @return
	 */
	public List<Map<String,Object>> getStuTop5ByAvg(String dept_id,boolean isLeaf);
	
	
	/**
	 * 获取按gpa的TOP5
	 * @param year
	 * @param dept_id
	 * @return
	 */
	public List<Map<String,Object>> getStuTop5ByGpa(String dept_id,boolean isLeaf);
}
