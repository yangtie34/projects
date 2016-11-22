package com.jhnu.product.manager.student.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.manager.student.dao.StuTop5Dao;
import com.jhnu.product.manager.student.service.StuTop5Service;
import com.jhnu.util.common.DateUtils;

@Service("stuTop5Service")
public class StuTop5ServiceImpl implements StuTop5Service {
 
	@Autowired
	private StuTop5Dao stuTop5Dao;
	
	@Override
	public List<Map<String, Object>> getStuTop5ByAvg(String dept_id,boolean isLeaf) {
		Integer year = Integer.parseInt(DateUtils.getNowYear())-3;
		return stuTop5Dao.getStuTop5ByAvg(isLeaf,year.toString(), dept_id);
	}

	@Override
	public List<Map<String, Object>> getStuTop5ByGpa(String dept_id,boolean isLeaf) {
		Integer year = Integer.parseInt(DateUtils.getNowYear())-3;
		return stuTop5Dao.getStuTop5ByGpa(isLeaf, year.toString(), dept_id);
	}

}
