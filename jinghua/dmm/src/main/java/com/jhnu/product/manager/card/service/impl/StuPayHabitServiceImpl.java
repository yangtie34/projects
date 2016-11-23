package com.jhnu.product.manager.card.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.manager.card.dao.StuPayHabitDao;
import com.jhnu.product.manager.card.service.StuPayHabitService;
import com.jhnu.util.common.DateUtils;
import com.jhnu.util.common.StringUtils;

@Service("stuPayHabitService")
public class StuPayHabitServiceImpl implements StuPayHabitService{

	@Autowired
	private StuPayHabitDao stuPayHabitDao;
	
	@Override
	public void saveStuPays(){
		String startDate = "2015-03-31" ;//DateUtils.getYesterday();
		String endDate = "2015-04-01";//DateUtils.date2String(new Date());
		List<Map<String,Object>> list = stuPayHabitDao.getStuPays(startDate, endDate);
		stuPayHabitDao.saveStuPays(list);
	}

	@Override
	public List<Map<String, Object>> getStuPaysLog(String dept_id,boolean isLeaf,String startDate, String endDate) {
		if(!StringUtils.hasText(startDate)){
			startDate = DateUtils.getLastMonth();
		}
		if(!StringUtils.hasText(endDate)){
			endDate = DateUtils.getNowDate();
		}
		return stuPayHabitDao.getStuPaysLog(dept_id,isLeaf,startDate, endDate);
	}

	@Override
	public List<Map<String, Object>> getStuPaysByDay(String dept_id,boolean isLeaf,String startDate,String endDate) {
		if(!StringUtils.hasText(startDate)){
			startDate = DateUtils.getLastMonth();
		}
		if(!StringUtils.hasText(endDate)){
			endDate = DateUtils.getNowDate();
		}
		return stuPayHabitDao.getStuPaysByDay(dept_id,isLeaf,startDate, endDate);
	}
	
	@Override
	public void saveStuEatNumbers(){
		String startDate = "2015-03-31" ;//DateUtils.getYesterday();
		String endDate = "2015-04-01";//DateUtils.date2String(new Date());
		List<Map<String,Object>> list = stuPayHabitDao.getStuEatNumbers(startDate, endDate);
		stuPayHabitDao.saveStuEatNumbers(list);
	}

	@Override
	public List<Map<String, Object>> getStuEatNumbersLog(String dept_id,boolean isLeaf,String startDate,String endDate) {
		if(!StringUtils.hasText(startDate)){
			startDate = DateUtils.getLastMonth();
		}
		if(!StringUtils.hasText(endDate)){
			endDate = DateUtils.getNowDate();
		}
		return stuPayHabitDao.getStuEatNumbersLog(dept_id,isLeaf,startDate, endDate);
	}
	
	@Override
	public List<Map<String, Object>> getStuPayMoneyLog(String dept_id,boolean isLeaf,String startDate,String endDate) {
		if(!StringUtils.hasText(startDate)){
			startDate = DateUtils.getLastMonth();
		}
		if(!StringUtils.hasText(endDate)){
			endDate = DateUtils.getNowDate();
		}
		return stuPayHabitDao.getStuPayMoneyLog(dept_id,isLeaf,startDate,endDate);
	}

	@Override
	public void saveStuPayMoney() {
		String startDate = "2015-03-31" ;//DateUtils.getYesterday();
		String endDate = "2015-04-01";//DateUtils.date2String(new Date());
		List<Map<String,Object>> list = stuPayHabitDao.getStuPayMoney(startDate, endDate);
		stuPayHabitDao.saveStuPayMoney(list);
	}
	
	
}
