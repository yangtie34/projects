package com.jhnu.product.manager.student.service;

import java.util.List;
import java.util.Map;

import com.jhnu.system.common.page.Page;

/**
 * @title 学生生源地统计Service
 * @description 学生生源地统计
 * @author Administrator
 * @date 2015/10/14 16:57
 */
public interface IStusSourceStatisticsService {

	/**
	 * @description 学生的来校，性别，来源地，未维护人数以及占比的统计
	 * @param departmentMajorId
	 *            院系/专业Id
	 * @param entranceTime
	 *            入学时间区间起始时间
	 * @param entranceEndTime
	 *            入学时间区间结束时间
	 * @return Map
	 * 
	 * @alias 
	 *        NOMAINTENANCE:未维护人员数量，SEXCODE(1:男，2:女):性别code，ANMELDENCODE(1:农村,2:镇
	 *        、县、区,3:城市):户口类别code，ALLCOUNTS:总数
	 */
	public Map<String, Object> stusStatisticalInterval(
			String entranceStartTime, String entranceEndTime,
			String departmentMajorId,boolean isLeaf);

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
	 * @return List<Map>
	 * 
	 * @alias 
	 *        CITIESNAME:省，市，县，区，镇名称，ALLCOUNTS:总数，SCHOOLBOY:男生人数，SCHOOLGIRL:女生人数，
	 *        COUNTRYSIDES:农村户口人数，TOWNS:镇、区户口人数，CITIES:城市户口人数
	 *        
	 * @param currentPage
	 *            当前页
	 * @param numPerPage
	 *            每页几行数据 
	 *            
	 */
	public Page stusNumsDistribution(String entranceStartTime, String entranceEndTime,
			String departmentMajorId,boolean isLeaf,
			 int level,String sexthIdCards, String quota,int currentPage,int numPerPage);

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
	 * 
	 * @alias ACOUNTS:数量，PROVINCES:省名称
	 */
	public List<Map<String, Object>> stusNumsMap(String entranceStartTime, String entranceEndTime,
			String departmentMajorId,boolean isLeaf,
			 int level,String sexthIdCards, String quota);

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
	 * @return List<Map>
	 * 
	 * @param currentPage
	 *            当前页
	 * @param numPerPage
	 *            每页几行数据 
	 *            
	 * @alias SCHOOLTAG:学校名称，COUNTS:数量
	 */
	public Page stusSchlloOfGraduation(
			String entranceStartTime, String entranceEndTime,
			String departmentMajorId,boolean isLeaf,
			int level,String sexthIdCards, String quota,
			int currentPage,int numPerPage);

}
