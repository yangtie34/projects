package cn.gilight.personal.teacher.dailylife.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.test1.util.MapUtils;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.personal.teacher.dailylife.dao.DailyLifeDao;
import cn.gilight.personal.teacher.dailylife.service.DailyLifeService;

@Service("dailyLifeService")
public class DailyLifeServiceImpl implements DailyLifeService{
	
	@Autowired
	private DailyLifeDao dailyLifeDao;
	
	@Resource
	private BaseDao baseDao;

	@Override
	public Map<String, Object> getMonthConsume(String tea_id) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String year_ = DateUtils.getNowYear();
		String month_ = DateUtils.getNowMonth();
		if(month_.length()<2){
			month_ = "0"+month_;
		}
		String monthStart = year_+"-"+month_;
		resultMap.put("year_", year_);
		resultMap.put("month_", month_);
		List<Map<String,Object>> list = dailyLifeDao.getMonthConsume(monthStart,tea_id);
		String monthConsume = "0";
		if(list != null && list.size() > 0){
			monthConsume = MapUtils.getString(list.get(0), "MONTH_CONSUME");
		}
		
		String sql= "select t.surplus_money from t_card_pay t left join t_card c on c.no_ = t.card_id where c.people_id = '"+tea_id+"' order by t.time_ desc";
		List<Map<String,Object>> list2 = baseDao.queryListInLowerKey(sql);
		float money = 0f;
		if(list2 != null && list2.size()>0){
			money = MapUtils.getFloatValue(list2.get(0), "surplus_money");
		}
		
		resultMap.put("monthConsume", monthConsume);
		resultMap.put("money", money);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> getTotalConsume(String tea_id) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String year_ = DateUtils.getNowYear();
		String month_ = DateUtils.getNowMonth();
		if(month_.length()<2){
			month_ = "0"+month_;
		}
		String monthStart = year_+"-"+month_;
		List<Map<String,Object>> list2 = dailyLifeDao.getTotalConsume(tea_id,monthStart);
		String totalConsume = "0";
		if(list2 != null && list2.size() > 0){
			totalConsume = MapUtils.getString(list2.get(0), "TOTAL_CONSUME");
		}
		String tc = MapUtils.getString(getMonthConsume(tea_id),"monthConsume");
		totalConsume = String.valueOf(Double.parseDouble(totalConsume) + Double.parseDouble(tc)); 
		resultMap.put("totalConsume", totalConsume);
		return resultMap;
	}
	
	
	@Override
	public Map<String, Object> getBorrow(String tea_id) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<Map<String,Object>> list = dailyLifeDao.getInBorrow(tea_id);
		String inBorrow = "0";
		if(list != null && list.size() > 0){
			inBorrow = MapUtils.getString(list.get(0), "IN_BORROW");
		}
		resultMap.put("inBorrow", inBorrow);
		List<Map<String,Object>> list2 = dailyLifeDao.getTotalBorrow(tea_id);
		String totalBorrow = "0";
		if(list2 != null && list2.size() > 0){
			totalBorrow = MapUtils.getString(list2.get(0), "TOTAL_BORROW");
		}
		resultMap.put("totalBorrow", totalBorrow);
		return resultMap;
	}


	@Override
	public List<Map<String,Object>> getInBorrow(String tea_id) {
		return dailyLifeDao.getInBorrowList(tea_id);
	}


	@Override
	public List<Map<String, Object>> getRecommendedBook(String tea_id) {
		return dailyLifeDao.getRecommendedBook(tea_id);
	}


	@Override
	public List<Map<String,Object>> getPayHistory(String tea_id,String currYear) {
		return dailyLifeDao.getPayHistory(tea_id, Integer.parseInt(currYear));
	}


	@Override
	public Page getMonthConsumeList(String tea_id,String monthStart,Page page,String flag) {
		String nextMonth = DateUtils.getNextMonth(monthStart);
		return dailyLifeDao.getMonthConsumeList(monthStart,nextMonth, tea_id,page,flag);
	}


	@Override
	public Map<String, Object> getMonthPayType(String tea_id, String monthStart) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String nextMonth = DateUtils.getNextMonth(monthStart);
		List<Map<String,Object>> monthMealsList = dailyLifeDao.getMonthMeals(monthStart,nextMonth, tea_id);
		String pay_eat = "0";
		if(monthMealsList != null && monthMealsList.size() > 0){
			pay_eat = MapUtils.getString(monthMealsList.get(0), "PAY_EAT");
		}
		resultMap.put("pay_eat", pay_eat);
		List<Map<String,Object>> list2 = dailyLifeDao.getMonthOther(monthStart,nextMonth, tea_id);
		String pay_other = "0";
		if(list2 != null && list2.size() > 0){
			pay_other = MapUtils.getString(list2.get(0), "PAY_OTHER");
		}
		resultMap.put("pay_other", pay_other);
		
		return resultMap;
	}

	@Override
	public List<Map<String, Object>> getOutOfDateBorrow(String tea_id) {
		return dailyLifeDao.getOutOfDateBorrow(tea_id);
	}

	@Override
	public List<Map<String, Object>> getReturnBorrow(String tea_id) {
		return dailyLifeDao.getReturnBorrow(tea_id);
	}

	@Override
	public Map<String, Object> getStopCounts(String tea_id) {
		return dailyLifeDao.getStopCounts(tea_id);
	}

	@Override
	public Map<String, Object> getStopTimeAvg(String tea_id) {
		return dailyLifeDao.getStopTimeAvg(tea_id);
	}

	@Override
	public List<Map<String, Object>> getCarStop(String tea_id) {
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> years = dailyLifeDao.getYearsMonth(tea_id);
		if(years != null && years.size()>0){
			for(Map<String,Object> map : years){
				Map<String,Object> resultMap = new HashMap<String,Object>();
				int year = MapUtils.getIntValue(map, "year_");
				String month = MapUtils.getString(map, "month_");
				List<Map<String,Object>> list = dailyLifeDao.getCarStop(tea_id,year,month);
				if(month.startsWith("0")){
					month = month.substring(1);
				}
				resultMap.put("y", year);
				resultMap.put("m", Integer.parseInt(month));
				resultMap.put("list", list);
				resultList.add(resultMap);
			}
		}
		return resultList;
	}
	
	
	
	
	
}
