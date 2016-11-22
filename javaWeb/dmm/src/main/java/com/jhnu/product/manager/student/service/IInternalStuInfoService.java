package com.jhnu.product.manager.student.service;

import java.util.List;
import java.util.Map;

/**
 * @title 学生概况Service
 * @description 在校生基本组成概况统计
 * @author Administrator
 * @date 2015/10/13 17:55
 */
public interface IInternalStuInfoService {

	/**
	 * @description 根据学生学历和院系专业查询学生数
	 * @param deptMajor
	 *            专业/院系
	 * @return List<Map>
	 */
	public List<Map<String, Object>> typeNumsOfStus(String deptMajor,
			boolean isLeaf);

	/**
	 * @description 获取各学院人数对比统计
	 * @return List<Map>
	 */
	public List<Map<String, Object>> stusOfNumsContrastStatistics(
			String deptMajor, boolean isLeaf);

	/**
	 * @description 根据学生类别获取学生的性别分布
	 * @param EducationId
	 *            就读学历Id
	 * @param departmentId
	 *            院系Id
	 * @return List<Map>
	 */
	public List<Map<String, Object>> stusSexComposition(String educationId,
			String departmentId, boolean isLeaf);

	/**
	 * @description 根据学生类别获取学生的年龄段分布
	 * @param EducationId
	 *            就读学历Id
	 * @param departmentId
	 *            院系Id
	 * @return List<Map>
	 */
	public List<Map<String, Object>> stusAgesComposition(String educationId,
			String departmentId, boolean isLeaf);

	/**
	 * @description 根据学生类别获取学生的民族组成
	 * @param EducationId
	 *            就读学历Id
	 * @param departmentId
	 *            院系Id
	 * @return List<Map>
	 */
	public List<Map<String, Object>> stusNationsComposition(String educationId,
			String departmentId, boolean isLeaf);

	/**
	 * @description 根据学生类别获取学生的政治面貌
	 * @param EducationId
	 *            就读学历Id
	 * @param departmentId
	 *            院系Id
	 * @return List<Map>
	 */
	public List<Map<String, Object>> stuPoliticalStatus(String educationId,
			String departmentId, boolean isLeaf);

	/**
	 * @description 根据学生类别获取学生的生源地
	 * @param level
	 *            根据level查询全国/全省/全市的生源地信息(0:全国，1:全省，2:全市)
	 * @param EducationId
	 *            就读学历Id
	 * @param departmentId
	 *            院系Id
	 * @param sexthIdCard
	 *            生源地的Id
	 * @return List<Map>
	 */
	public List<Map<String, Object>> stuSourceLand(int level,
			String educationId, String departmentId, boolean isLeaf,
			String sexthIdCard);

	/**
	 * @description 根据学生类别获取学生的学科分布
	 * @param EducationId
	 *            就读学历Id
	 * @param departmentId
	 *            院系Id
	 * @return List<Map>
	 */
	public List<Map<String, Object>> stuSubjectsComposition(String educationId,
			String departmentId, boolean isLeaf);

	/**
	 * @description 根据学生类别获取学生的学历组成
	 * @param departmentId
	 *            院系Id
	 * @return List<Map>
	 */
	public List<Map<String, Object>> stuEducationalBackground(
			String departmentId, boolean isLeaf);

	/**
	 * @description 根据学生类别获取学生的学位组成
	 * @param departmentId
	 *            院系Id
	 * @return List<Map>
	 */
	public List<Map<String, Object>> stuDegreeComposition(String departmentId,
			boolean isLeaf);

}
