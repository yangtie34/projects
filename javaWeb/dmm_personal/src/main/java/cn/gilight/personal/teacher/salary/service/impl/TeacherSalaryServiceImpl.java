package cn.gilight.personal.teacher.salary.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import test.test1.util.MapUtils;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.personal.teacher.salary.dao.TeacherSalaryDao;
import cn.gilight.personal.teacher.salary.service.TeacherSalaryService;

@Service("teacherSalaryService")
public class TeacherSalaryServiceImpl implements TeacherSalaryService{

	@Autowired
	private TeacherSalaryDao teacherSalaryDao;

	@Override
	public Map<String, Object> getLastSalary(String tea_id) {
		String lastMonth = DateUtils.getLastMonth();
		String year = lastMonth.substring(0, 4);
		String month = lastMonth.substring(5, 7);
				
		List<Map<String,Object>> list = teacherSalaryDao.getLastSalary(tea_id,year,month);
		Map<String,Object> map = new HashMap<String,Object>();
		if(list!=null && list.size()>0){
			map = list.get(0);
		}else{
			map.put("LASTSALARY", "");
		}
		return map;
	}

	@Override
	public Map<String, Object> getTotalSalary(String tea_id) {
		List<Map<String,Object>> list = teacherSalaryDao.getTotalSalary(tea_id);
		Map<String,Object> map = new HashMap<String,Object>();
		if(list!=null && list.size()>0){
			map = list.get(0);
		}else{
			map.put("TOTALSALARY", "");
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> getSalaryCompose(String tea_id) {
		return teacherSalaryDao.getSalaryCompose(tea_id);
	}

	@Override
	public List<Map<String,Object>> getHistorySalary(String tea_id) {
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> years = teacherSalaryDao.getYears(tea_id);
		if(years != null && years.size()>0){
			for(Map<String,Object> map : years){
				int year_ = MapUtils.getIntValue(map, "year_");
				Map<String,Object> resultMap = new HashMap<String,Object>();
				List<Map<String,Object>> list = teacherSalaryDao.getHistorySalary(tea_id, year_);
				resultMap.put("year", year_);
				resultMap.put("list", list);
				resultList.add(resultMap);
			}
		}
		return resultList;
	}
	
	@Override
	public List<Map<String, Object>> getFiveYearSalary(String tea_id) {
		List<Map<String,Object>> list = teacherSalaryDao.getFiveYearSalary(tea_id);
		if(list != null && list.size()>0){
			for(Map<String,Object> map : list){
				map.put("field", MapUtils.getString(map, "YEAR_"));
				map.put("name", "年均工资");
				map.put("value", MapUtils.getString(map, "AVG_SALARY"));
				map.remove("YEAR_");
				map.remove("AVG_SALARY");
			}
		}
		return list;
	}

	@Override
	public Map<String, Object> getRetireTotalSalary(String tea_id) {
		List<Map<String,Object>> list = teacherSalaryDao.getRetireYear(tea_id);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String years = "";
		if(list!=null && list.size()>0){
			years = MapUtils.getString(list.get(0), "YEARS");
		}
		Map<String,Object> map = getLastSalary(tea_id);
		String lastSalary = MapUtils.getString(map, "LASTSALARY");
		if(StringUtils.hasText(lastSalary)){
			Double totalSalary = Double.parseDouble(lastSalary)*Integer.parseInt(years)*12;
			resultMap.put("futureSalary", MathUtils.get2Point(totalSalary));
		}else{
			resultMap.put("futureSalary", "未维护");
		}
		
		return resultMap;
	}

	@Override
	public Map<String, Object> getLastSalaryCom(String tea_id,String year_,String month_) {
		
		List<Map<String,Object>> list = teacherSalaryDao.getLastSalaryCom(tea_id, year_, month_);
		Map<String,Object> map = new HashMap<String,Object>();
		if(list!=null && list.size()>0){
			map = list.get(0);
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> getLastSalaryPayable(String tea_id,String year_,String month_) {
		return teacherSalaryDao.getLastSalaryPayable(tea_id, year_, month_);
	}
	
	@Override
	public Map<String, Object> getLastSalaryTotal(String tea_id,String year_,String month_) {
		List<Map<String,Object>> list = teacherSalaryDao.getLastSalaryTotal(tea_id,year_,month_);
		Map<String,Object> map = new HashMap<String,Object>();
		if(list!=null && list.size()>0){
			map = list.get(0);
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> getLastSalarySubtract(String tea_id,String year_,String month_) {
		return teacherSalaryDao.getLastSalarySubtract(tea_id, year_, month_);
	}

	@Override
	public Map<String, Object> getLastSalarySubtractTotal(String tea_id,String year_,String month_) {
		List<Map<String,Object>> list = teacherSalaryDao.getLastSalarySubtractTotal(tea_id,year_,month_);
		Map<String,Object> map = new HashMap<String,Object>();
		if(list!=null && list.size()>0){
			map = list.get(0);
		}
		return map;
	}
	
	
	

}
