package com.jhnu.product.manager.teacher.service;

import java.util.List;
import java.util.Map;

/**
 * @title 教职工概况Service
 * @description 教职工基本概况统计
 * @author Administrator
 * @date 2015/10/14 11:30
 */
public interface ITeacherEmpInfoService {

	/**
	 * @description 根据教职工类别获取教职工的性别分布
	 * @param departmentId
	 *            部门Id
	 * @return List<Map>
	 */
	public List<Map<String, Object>> teachersSexComposition(String departmentId);

	/**
	 * @description 根据教职工类别获取教职工的年龄段分布
	 * @param departmentId
	 *            部门Id
	 * @return List<Map>
	 */
	public List<Map<String, Object>> teachersAgesComposition(String departmentId);

	/**
	 * @description 根据教职工类别获取教职工的民族组成
	 * @param departmentId
	 *            部门Id
	 * @return List<Map>
	 */
	public List<Map<String, Object>> teachersNationsComposition(
			String departmentId);

	/**
	 * @description 根据教职工类别获取教职工的政治面貌
	 * @param departmentId
	 *            部门Id
	 * @return List<Map>
	 */
	public List<Map<String, Object>> teachersPoliticalStatus(String departmentId);

	/**
	 * @description 根据教职工类别获取教职工的生源地
	 * @param level
	 *            根据level查询全国/全省/全市的教职工生源地(0:全国，1:全省，2:全市)
	 * @param departmentId
	 *            部门Id
	 * @param sexthIdCard
	 *            生源地的Id
	 * @return List<Map>
	 */
	public List<Map<String, Object>> teachersSourceLand(int level,
			String departmentId, String sexthIdCard);

	/**
	 * @description 根据教职工类别获取教职工的学科分布
	 * @param departmentId
	 *            部门Id
	 * @return List<Map>
	 */
	public List<Map<String, Object>> teachersSubjectsComposition(
			String departmentId);

	/**
	 * @description 根据教职工类别获取教职工的学历组成
	 * @param departmentId
	 *            部门Id
	 * @return List<Map>
	 */
	public List<Map<String, Object>> teachersEducationalBackground(
			String departmentId);

	/**
	 * @description 根据教职工类别获取教职工的学位组成
	 * @param departmentId
	 *            部门Id
	 * @return List<Map>
	 */
	public List<Map<String, Object>> teachersDegreeComposition(
			String departmentId);

}
