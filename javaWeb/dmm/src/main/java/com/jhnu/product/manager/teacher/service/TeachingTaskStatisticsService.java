package com.jhnu.product.manager.teacher.service;

import java.util.List;
import java.util.Map;

public interface TeachingTaskStatisticsService {

	
	/**
	 * 获取各职称教学任务
	 * @param school_year
	 * @param term_code
	 * @param dept_id
	 * @return
	 */
	public List<Map<String,Object>> getZcJxrw(String school_year,String term_code,String dept_id);
	
	/**
	 * 获取各类别教学任务
	 * @param school_year
	 * @param term_code
	 * @param dept_id
	 * @return
	 */
	public List<Map<String,Object>> getBzlbJxrw(String school_year,String term_code,String dept_id);
	
	/**
	 * 获取各学院各职称的教学任务
	 * @param school_year
	 * @param term_code
	 * @return
	 */
	public List<Map<String,Object>> getXyZcJxrw(String dept_id,String school_year,String term_code);
	
	/**
	 * 获取各学院各编制类别的教学任务
	 * @param school_year
	 * @param term_code
	 * @return
	 */
	public List<Map<String,Object>> getXyBzlbJxrw(String dept_id,String school_year,String term_code);
}
