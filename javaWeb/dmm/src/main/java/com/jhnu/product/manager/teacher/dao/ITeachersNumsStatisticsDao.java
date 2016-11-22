package com.jhnu.product.manager.teacher.dao;

import java.util.List;
import java.util.Map;

import com.jhnu.system.common.page.Page;

/**
 * @title 教职工人数变化统计Dao
 * @description 教职工人数变化统计
 * @author Administrator
 * @date 2015/10/15 17:01
 */
public interface ITeachersNumsStatisticsDao {

	/**
	 * @description 统计教职工人数变化趋势(年份)
	 * @param startYear
	 *            开始年份
	 * @param endYear
	 *            结束年份
	 * @param departmentId
	 *            部门Id
	 * @param coditions
	 *            选择统计维度
	 * @return List<Map>
	 */
	public List<Map<String, Object>> teachersClassificationNums(int startYear,
			int endYear, String departmentId, String conditions);

	/**
	 * @description 统计教职工人数变化趋势(月份)
	 * @param year
	 *            查询的年份
	 * @param departmentId
	 *            部门Id
	 * @param conditions
	 *            选择统计维度
	 * @return List<Map>
	 */
	public List<Map<String, Object>> teachersClassificationNumsByMonth(
			int year, String departmentId, String conditions);

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
