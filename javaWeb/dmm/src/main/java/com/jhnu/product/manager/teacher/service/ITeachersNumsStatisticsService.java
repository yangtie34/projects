package com.jhnu.product.manager.teacher.service;

import java.util.List;
import java.util.Map;

import com.jhnu.system.common.page.Page;

/**
 * @title 教职工人数变化统计Service
 * @description 教职工人数变化统计
 * @author Administrator
 * @date 2015/10/15 17:04
 */
public interface ITeachersNumsStatisticsService {
	/**
	 * @description 统计教职工人数变化趋势
	 * @param startYear
	 *            开始年份
	 * @param endYear
	 *            结束年份
	 * @param departmentId
	 *            部门Id
	 * @param coditions
	 *            选择统计维度
	 * 
	 * @alias YEARS:年份
	 * @alias COUNTS:数量
	 * @alias getCounts:从数据库取出的年份
	 * @alias getCounts:从数据库取出的该年份对应的人数
	 * @alias MONTHES:月份
	 * @return List<Map>
	 */
	public List<Map<String, Object>> teachersClassificationNums(int startYear,
			int endYear, String departmentId, String conditions);

	/**
	 * @description 统计教职工人数变化趋势(图表)
	 * @param startYear
	 *            开始年份
	 * @param endYear
	 *            结束年份
	 * @param departmentId
	 *            部门Id
	 * @param currentPage
	 *            当前页
	 * @param numPerPage
	 *            每页几行数据 
	 * @return Page
	 */
	public Page teachersClassificationNumsTable(int startYear, int endYear, String departmentId,int currentPage,int numPerPage);

}
