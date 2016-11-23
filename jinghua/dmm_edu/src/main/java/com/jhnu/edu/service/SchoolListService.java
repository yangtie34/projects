package com.jhnu.edu.service;

import java.util.List;
import java.util.Map;

import com.jhnu.framework.page.Page;



public interface SchoolListService {
	/*
	 * 获取高校数据筛选条件
	 */
	public List<Map<String, Object>> getFilter();
	/*
	 * 获取高校数据筛选条件
	 */
	public Page getSchools(Map<String, Object> mapFilter,int currentPage, int numPerPage);
}
