package com.jhnu.product.manager.teacher.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.manager.teacher.dao.ITeachersNumsStatisticsDao;
import com.jhnu.product.manager.teacher.service.ITeachersNumsStatisticsService;
import com.jhnu.system.common.page.Page;
import com.jhnu.util.common.MapUtils;

/**
 * @title 教职工人数变化统计Service实现类
 * @description 教职工人数变化统计
 * @author Administrator
 * @date 2015/10/15 17:04
 */
@Service("teachersNumsStatisticsService")
public class TeachersNumsStatisticsServiceImpl implements
		ITeachersNumsStatisticsService {

	// 自动注入ITeachersNumsStatisticsDao
	@Autowired
	private ITeachersNumsStatisticsDao teachersNumsStatisticsDao;

	@Override
	public List<Map<String, Object>> teachersClassificationNums(int startYear,
			int endYear, String departmentId, String conditions) {
		List<Map<String, Object>> saveResults;
		if (endYear == startYear) {
			List<Map<String, Object>> results = teachersNumsStatisticsDao
					.teachersClassificationNumsByMonth(startYear, departmentId,
							conditions);
			saveResults=getListByAll(1,12,results);
		} else {
			List<Map<String, Object>> results = teachersNumsStatisticsDao
					.teachersClassificationNums(startYear, endYear,
							departmentId, conditions);
			
			saveResults=getListByAll(startYear,endYear,results);
		}
		return saveResults;
	}

	@Override
	public Page teachersClassificationNumsTable(int startYear, int endYear, String departmentId,int currentPage,int numPerPage) {
		return teachersNumsStatisticsDao.teachersClassificationNumsTable(startYear, endYear, departmentId,currentPage,numPerPage);
	}
	/**
	 * 通过label自动补全name值，有值的用counts填充，反之用0
	 * @param start
	 * @param end
	 * @param results
	 * @return
	 */
	private List<Map<String, Object>> getListByAll(int start,int end,List<Map<String, Object>> results){
		List<Map<String, Object>> saveResults = new ArrayList<Map<String, Object>>();
		Map<String, Object> getResult  = new HashMap<String, Object>();
		boolean isEnd = false;
		if (results==null || results.size() == 0) {
			isEnd = true;
		}
		if (!isEnd) {
			String lableTemp="-1";
			for (int i = 0; i < results.size(); i++) {
				if(!lableTemp.equals(MapUtils.getString(results.get(i), "LABLE"))){
					lableTemp=MapUtils.getString(results.get(i), "LABLE");
					i--;
				}else{
					int k=-1;int thisIndex=0;
					boolean flag=false;
					for (int j = start; j <= end; j++) {
						k++;
						getResult = new HashMap<String, Object>();
						thisIndex=i+k;
						if(thisIndex<results.size()){
							int getYear = MapUtils.getIntValue(results.get(thisIndex), "NAME");
							int getCounts = MapUtils.getIntValue(results.get(thisIndex), "COUNTS"); // 得到该年下员工入职人数
							String thisLable=MapUtils.getString(results.get(thisIndex), "LABLE");
							
							if (j == getYear&& lableTemp.equals(thisLable)) {
								getResult.put("value", getCounts);
							} else {
								k--;
								getResult.put("value", 0);
							}
							if(!lableTemp.equals(thisLable)){
								flag=true;
							}
						}else{
							getResult.put("value", 0);
						}
						getResult.put("lable", lableTemp);
						getResult.put("name", j+"");
						saveResults.add(getResult);
					}
					if(flag){
						thisIndex--;
					}
					i=thisIndex;
				}
			}
		}else{
			for (int i = start; i <= end; i++) {
				getResult = new HashMap<String, Object>();
				getResult.put("name", i+"");
				getResult.put("value", 0);
				getResult.put("lable", "空数据");
				saveResults.add(getResult);
			}
		}
		
		return saveResults;
	}
}
