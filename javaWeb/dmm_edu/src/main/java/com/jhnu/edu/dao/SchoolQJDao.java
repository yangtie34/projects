package com.jhnu.edu.dao;

import java.util.Map;

public interface SchoolQJDao {
	/**
	 * 获取某高校基本信息
	 */
	public Map<String, Object> getSchoolInfo(String schoolId);
	/**
	 * 获取某高校详细信息
	 */
	public Map<String, Object> getSchoolInfoDetails(String schoolId);
	
	/**
	 * 根据字段 ids及限定年份获取高校详细信息
	 */
	public Map<String, Object> getSchoolInfoDetails(String schoolId,String[] titleIds,String start,String end);
	/**
	 * 根据字段 ids获取高校详细信息，默认上一年
	 */
	public Map<String, Object> getSchoolInfoDetails(String schoolId,String[] titleIds);
}
