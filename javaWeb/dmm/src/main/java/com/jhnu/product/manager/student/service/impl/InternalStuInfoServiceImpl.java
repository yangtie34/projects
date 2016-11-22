package com.jhnu.product.manager.student.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.manager.student.dao.IInternalStuInfoDao;
import com.jhnu.product.manager.student.service.IInternalStuInfoService;
import com.jhnu.util.common.MapUtils;

/**
 * @title 学生概况Service实现类
 * @description 在校生基本组成概况统计
 * @author Administrator
 * @date 2015/10/13 17:55
 */
@Service("internalStuInfoService")
public class InternalStuInfoServiceImpl implements IInternalStuInfoService {

	// 自动注入IInternalStuInfoDao
	@Autowired
	private IInternalStuInfoDao internalStuInfoDao;

	@Override
	public List<Map<String, Object>> typeNumsOfStus(String deptMajor,
			boolean isLeaf) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> eduList = internalStuInfoDao.typeNumsOfStus(deptMajor, false);

		Integer sums = 0;

		for (Map<String, Object> eduMap : eduList) {
			MapUtils.getString(eduMap, "EDUNAME");
		}
		
		result.put("eduList", eduList);
		result.put("totalNum", sums);
		resultList.add(result);
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> stusOfNumsContrastStatistics(
			String deptMajor, boolean isLeaf) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> eduList = internalStuInfoDao.typeNumsOfStus(
				deptMajor, false);
		Integer sums = 0;
		for (Map<String, Object> eduMap : eduList) {
			String counts = MapUtils.getString(eduMap, "COUNTS");
			sums += Integer.valueOf(counts);
		}

		// 查询出stusOfNumsContrastStatistics的数据
		List<Map<String, Object>> contrastList = internalStuInfoDao
				.stusOfNumsContrastStatistics(deptMajor, isLeaf);
		//
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		// 只是为了建立索引
		Map<String, Object> dataMap = new HashMap<String, Object>();
		for (Map<String, Object> contrastMap : contrastList) {
			// 建立索引，把DEPT_NAME的值，作为Key
			Object deptName = MapUtils.getString(contrastMap, "DEPT_NAME");
			if (!dataMap.containsKey(deptName)) {
				Map<String, Object> deptMap = new HashMap<String, Object>();
				deptMap.put("DEPT_NAME", deptName);
				dataMap.put((String) deptName, deptMap);
				dataList.add(deptMap);
			}
			Map<String, Object> deptMap = (Map<String, Object>) dataMap
					.get(deptName);
			deptMap.put(MapUtils.getString(contrastMap, "EDU_ID"),
					MapUtils.getString(contrastMap, "NUMS"));
		}
		result.put("eduList", eduList);
		result.put("dataList", dataList);
		result.put("totalNum", sums);
		resultList.add(result);
		return resultList;
	}

	@Override
	public List<Map<String, Object>> stusSexComposition(String educationId,
			String departmentId, boolean isLeaf) {
		
		return internalStuInfoDao.stusSexComposition(educationId, departmentId,
				isLeaf);
	}

	@Override
	public List<Map<String, Object>> stusAgesComposition(String educationId,
			String departmentId, boolean isLeaf) {
		return internalStuInfoDao.stusAgesComposition(educationId,
				departmentId, isLeaf);
	}

	@Override
	public List<Map<String, Object>> stusNationsComposition(String educationId,
			String departmentId, boolean isLeaf) {
		return internalStuInfoDao.stusNationsComposition(educationId,
				departmentId, isLeaf);
	}
	
	@Override
	public List<Map<String, Object>> stuPoliticalStatus(String educationId,
			String departmentId, boolean isLeaf) {
		return internalStuInfoDao.stuPoliticalStatus(educationId, departmentId,
				isLeaf);
	}

	@Override
	public List<Map<String, Object>> stuSourceLand(int level,
			String educationId, String departmentId, boolean isLeaf,
			String sexthIdCard) {
		return internalStuInfoDao.stuSourceLand(level, educationId,
				departmentId, isLeaf, sexthIdCard);
	}

	@Override
	public List<Map<String, Object>> stuSubjectsComposition(String educationId,
			String departmentId, boolean isLeaf) {
		return internalStuInfoDao.stuSubjectsComposition(educationId,
				departmentId, isLeaf);
	}

	@Override
	public List<Map<String, Object>> stuEducationalBackground(
			String departmentId, boolean isLeaf) {
		return internalStuInfoDao
				.stuEducationalBackground(departmentId, isLeaf);
	}

	@Override
	public List<Map<String, Object>> stuDegreeComposition(String departmentId,
			boolean isLeaf) {
		return internalStuInfoDao.stuDegreeComposition(departmentId, isLeaf);
	}

}
