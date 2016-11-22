package com.jhnu.product.visit.parent.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.visit.parent.dao.ParentVisitDao;
import com.jhnu.product.visit.parent.service.ParentVisitService;

@Service("parentVisitService")
public class ParentVisitServiceImpl implements ParentVisitService{

	@Autowired
	private ParentVisitDao parentVisitDao;

	@Override
	public Map<String, Object> getVisitTotalCounts(String startDate,
			String endDate) {
		Map<String,Object> map = null;
		List<Map<String,Object>> list = parentVisitDao.getVisitTotalCounts(startDate, endDate);
		if(list != null && list.size()>0){
			map = list.get(0);
		}
		return map;
	}

	@Override
	public Map<String, Object> getVisitPcCounts(String startDate, String endDate) {
		Map<String,Object> map = null;
		List<Map<String,Object>> list = parentVisitDao.getVisitPcCounts(startDate, endDate);
		if(list != null && list.size()>0){
			map = list.get(0);
		}
		return map;
	}

	@Override
	public Map<String, Object> getVisitMobelCounts(String startDate,String endDate) {
		Map<String,Object> map = null;
		List<Map<String,Object>> list = parentVisitDao.getVisitMobelCounts(startDate, endDate);
		if(list != null && list.size()>0){
			map = list.get(0);
		}
		return map;
	}

	@Override
	public Map<String, Object> getUserCounts(String startDate, String endDate) {
		Map<String,Object> map = null;
		List<Map<String,Object>> list = parentVisitDao.getUserCounts(startDate, endDate);
		if(list != null && list.size()>0){
			map = list.get(0);
		}
		return map;
	}
	
	@Override
	public List<Map<String, Object>> getParentVisitCounts(String startDate,String endDate){
		return parentVisitDao.getParentVisitCounts(startDate,endDate);
	}

	@Override
	public Map<String, Object> getParentNums(String startDate, String endDate) {
		Map<String,Object> map = null;
		List<Map<String,Object>> list = parentVisitDao.getParentNums(startDate, endDate);
		if(list != null && list.size()>0){
			map = list.get(0);
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> getOtherVisitCounts(String startDate,String endDate) {
		return parentVisitDao.getOtherVisitCounts(startDate,endDate);
	}
	
	
	

}
