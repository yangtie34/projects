package com.jhnu.product.manager.student.service.impl;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.manager.student.dao.IStusSourceStatisticsDao;
import com.jhnu.product.manager.student.service.IStusSourceStatisticsService;
import com.jhnu.system.common.page.Page;
import com.jhnu.util.common.MapUtils;

/**
 * @title 学生生源地统计Service实现类
 * @description 学生生源地统计
 * @author Administrator
 * @date 2015/10/14 16:58
 */
@Service("iStusSourceStatisticsService")
public class StusSourceStatisticsServiceImpl implements
		IStusSourceStatisticsService {

	// 自动注入IStusSourceStatisticsDao
	@Autowired
	private IStusSourceStatisticsDao stusSourceStatisticsDao;

	/**
	 * @description 百分比转换
	 * @param currentNum
	 *            被除数
	 * @param allNum
	 *            除数
	 * @return String
	 */
	private String proportionExchange(int currentNum, int allNum) {
		NumberFormat rateFormat = NumberFormat.getInstance();
		rateFormat.setMaximumFractionDigits(2);
		String exchangeResult = rateFormat.format((float) currentNum
				/ (float) allNum * 100);
		return exchangeResult + "%";
	}

	@Override
	public Map<String, Object> stusStatisticalInterval(
			String entranceStartTime, String entranceEndTime,
			String departmentMajorId,boolean isLeaf) {
		int schoolBoyCounts = 0; // 男学生数量
		String schoolBoyCountsRate = ""; // 男学生人数占比
		int schoolGirlCounts = 0; // 女学生数量
		String schoolGirlCountsRate = ""; // 女学生人数占比
		int urbanHouseholdCounts = 0; // 城市户口数量
		String urbanHouseholdCountsRate = ""; // 城市户口占比
		int ruralHouseholdCounts = 0; // 农村户口数量
		String ruralHouseholdCountsRate = ""; // 农村户口占比
		int countyTownHouseholdCounts = 0; // 县镇户口数量
		String countyTownHouseholdCountsRate = ""; // 县镇户口占比
		int allStusCounts = 0; // 来校学生总人数
		int noMiantainedNums = 0; // 未维护的人数
		String noMiantainedNumsRate = ""; // 未维护的人数占比
		Map<String, Object> maps = new HashMap<String, Object>();

		if ((entranceStartTime.equals("") || entranceStartTime == null)
				|| (entranceEndTime.equals("") || entranceEndTime == null)) {
			return null;
		}
		List<Map<String, Object>> resultList = stusSourceStatisticsDao
				.stusStatisticalInterval(entranceStartTime, entranceEndTime,
						departmentMajorId,isLeaf);
		
			for(Map<String,Object> results : resultList){
			String sexCode = MapUtils.getString(results, "SEX_CODE");
			if (sexCode.equals("1")) {
				schoolBoyCounts += Integer.parseInt(MapUtils.getString(results, "ALLCOUNTS"));
			} else if (sexCode.equals("2")) {
				schoolGirlCounts += Integer.parseInt(MapUtils.getString(results, "ALLCOUNTS"));
			}

			String household = MapUtils.getString(results, "ANMELDEN_CODE");
			if (household.equals("3")) {
				urbanHouseholdCounts += Integer.parseInt(MapUtils.getString(results,
						"ALLCOUNTS"));
			} else if (household.equals("2")) {
				countyTownHouseholdCounts += Integer.parseInt(MapUtils.getString(results,
						"ALLCOUNTS"));
			} else if (household.equals("1")) {
				ruralHouseholdCounts += Integer.parseInt(MapUtils.getString(results,
						"ALLCOUNTS"));
			}
			allStusCounts += Integer.parseInt(MapUtils.getString(results, "ALLCOUNTS"));
		}
		noMiantainedNums = stusSourceStatisticsDao.stusNoMaintainedSource(
				entranceStartTime, entranceEndTime, departmentMajorId,isLeaf);

		noMiantainedNumsRate = proportionExchange(noMiantainedNums,
				allStusCounts);
		countyTownHouseholdCountsRate = proportionExchange(
				countyTownHouseholdCounts, allStusCounts);
		ruralHouseholdCountsRate = proportionExchange(ruralHouseholdCounts,
				allStusCounts);
		urbanHouseholdCountsRate = proportionExchange(urbanHouseholdCounts,
				allStusCounts);
		schoolGirlCountsRate = proportionExchange(schoolGirlCounts,
				allStusCounts);
		schoolBoyCountsRate = proportionExchange(schoolBoyCounts, allStusCounts);

		maps.put("allStusCountsRate", "100%"); // 来校总人数占比
		maps.put("countyTownHouseholdCountsRate", countyTownHouseholdCountsRate);
		maps.put("noMiantainedNumsRate", noMiantainedNumsRate);
		maps.put("ruralHouseholdCountsRate", ruralHouseholdCountsRate);
		maps.put("urbanHouseholdCountsRate", urbanHouseholdCountsRate);
		maps.put("schoolGirlCountsRate", schoolGirlCountsRate);
		maps.put("schoolBoyCountsRate", schoolBoyCountsRate);
		maps.put("schoolBoyCounts", schoolBoyCounts);
		maps.put("schoolGirlCounts", schoolGirlCounts);
		maps.put("urbanHouseholdCounts", urbanHouseholdCounts);
		maps.put("ruralHouseholdCounts", ruralHouseholdCounts);
		maps.put("countyTownHouseholdCounts", countyTownHouseholdCounts);
		maps.put("allStusCounts", allStusCounts);
		maps.put("noMiantainedNums", noMiantainedNums);
		return maps;
	}

	@Override
	public Page stusNumsDistribution(String entranceStartTime, String entranceEndTime,
			String departmentMajorId,boolean isLeaf,
			 int level,String sexthIdCards, String quota,int currentPage,int numPerPage) {
		Page page =  stusSourceStatisticsDao.stusNumsDistribution(entranceStartTime,
				entranceEndTime, departmentMajorId,isLeaf, quota, level, sexthIdCards,
				currentPage, numPerPage);
		return page;
	}

	@Override
	public List<Map<String, Object>> stusNumsMap(String entranceStartTime, String entranceEndTime,
			String departmentMajorId,boolean isLeaf,
			 int level,String sexthIdCards, String quota) {
		return stusSourceStatisticsDao.stusNumsMap(departmentMajorId,isLeaf,
				entranceStartTime, entranceEndTime, level, sexthIdCards, quota);
	}

	@Override
	public Page stusSchlloOfGraduation(String entranceStartTime, String entranceEndTime,
			String departmentMajorId,boolean isLeaf,
			int level,String sexthIdCards, String quota,
			int currentPage,int numPerPage) {
		return stusSourceStatisticsDao.stusSchlloOfGraduation(
				entranceStartTime, entranceEndTime, level, departmentMajorId,isLeaf,
				quota, sexthIdCards, currentPage, numPerPage);
	}
}
