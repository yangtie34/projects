package com.jhnu.product.manager.student.dao;

import java.util.List;
import java.util.Map;

import com.jhnu.system.common.page.Page;

/**
 * @title 学生生源地统计Dao
 * @description 学生生源地统计
 * @author Administrator
 * @date 2015/10/14 12:20
 */
public interface IStusSourceStatisticsDao {

	/**
	 * @description 学生的来校，性别，来源地人数以及占比的统计
	 * @param departmentMajorId
	 *            院系/专业Id
	 * @param entranceTime
	 *            入学时间区间起始时间
	 * @param entranceEndTime
	 *            入学时间区间结束时间
	 * @return Map
	 */
	public List<Map<String, Object>> stusStatisticalInterval(String entranceStartTime, String entranceEndTime,
			String departmentMajorId,boolean isLeaf);

	/**
	 * @description 未维护学生的人数以及占比的统计
	 * @param entranceStartTime
	 *            入学时间区间起始时间
	 * @param entranceEndTime
	 *            入学时间区间结束时间
	 * @param departmentMajorId
	 *            院系/专业Id
	 * @return int
	 */
	public int stusNoMaintainedSource(String entranceStartTime,
			String entranceEndTime, String departmentMajorId,boolean isLeaf);

	/**
	 * @description 学生人数按省，市、区，县、镇地区分布(列表展示)
	 * @param entranceStartTime
	 *            入学时间区间起始时间
	 * @param entranceEndTime
	 *            入学时间区间结束时间
	 * @param departmentMajorId
	 *            院系/专业Id
	 * @param quota
	 *            指标(gsn:来校人数，csn:农村户口人数，tn:县镇户口人数，cn:城市户口人数，sbn:男生人数，sgn:女生人数)
	 * @param level
	 *            根据level查询全国/全省/全市的学生生源地(0:全国，1:全省，2:全市)
	 * @param sexthIdCards
	 *            生源地的Id
	 * @param currentPage
	 *            当前页
	 * @param numPerPage
	 *            每页几行数据  
	 *            
	 * @return List<Map>
	 */
	public Page stusNumsDistribution(
			String entranceStartTime, String entranceEndTime,
			String departmentMajorId,boolean isLeaf,String quota, int level,
			String sexthIdCards,int currentPage,int numPerPage);

	/**
	 * @description 学生人数按省，市、区，县、镇地区分布(图形展示)
	 * @param departmentMajorId
	 *            院系/专业Id
	 * @param entranceStartTime
	 *            入学时间区间起始时间
	 * @param entranceEndTime
	 *            入学时间区间结束时间
	 * @param level
	 *            根据level查询全国/全省/全市的学生生源地(0:全国，1:全省，2:全市)
	 * @param sexthIdCards
	 *            生源地的Id
	 * @param quota
	 *            指标(gsn:来校人数，csn:农村户口人数，tn:县镇户口人数，cn:城市户口人数，sbn:男生人数，sgn:女生人数)
	 * @return Map
	 */
	public List<Map<String, Object>> stusNumsMap(String departmentMajorId,boolean isLeaf,
			String entranceStartTime, String entranceEndTime, int level,
			String sexthIdCards, String quota);

	/**
	 * @description 学生毕业学校
	 * @param entranceStartTime
	 *            入学时间区间起始时间
	 * @param entranceEndTime
	 *            入学时间区间结束时间
	 * @param level
	 *            根据level查询全国/全省/全市的学生生源地(0:全国，1:全省，2:全市)
	 * @param departmentMajorId
	 *            院系/专业Id
	 * @param quota
	 *            指标(gsn:来校人数，csn:农村户口人数，tn:县镇户口人数，cn:城市户口人数，sbn:男生人数，sgn:女生人数)
	 * @param sexthIdCards
	 *            生源地的Id
	 * @param currentPage
	 *            当前页
	 * @param numPerPage
	 *            每页几行数据 
	 *            
	 * @return List<Map>
	 */
	public Page stusSchlloOfGraduation(
			String entranceStartTime, String entranceEndTime, int level,
			String departmentMajorId,boolean isLeaf, String quota, String sexthIdCards,
			int currentPage,int numPerPage);

}
