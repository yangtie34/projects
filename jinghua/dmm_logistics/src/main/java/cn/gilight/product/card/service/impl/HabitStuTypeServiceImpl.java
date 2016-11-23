package cn.gilight.product.card.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.product.card.dao.HabitStuTypeDao;
import cn.gilight.product.card.service.HabitStuTypeService;

@Service("habitStuTypeService")
public class HabitStuTypeServiceImpl implements HabitStuTypeService {
	
	@Autowired
	private HabitStuTypeDao habitStuTypeDao;

	@Override
	public List<Map<String, Object>> getHabitZao(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return habitStuTypeDao.getHabitZao(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getHabitZaoBySex(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return habitStuTypeDao.getHabitZaoBySex(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getHabitZaoByEdu(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return habitStuTypeDao.getHabitZaoByEdu(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getHabitHour(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return habitStuTypeDao.getHabitHour(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getHabitHourBySex(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return habitStuTypeDao.getHabitHourBySex(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getHabitHourByEdu(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return habitStuTypeDao.getHabitHourByEdu(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getHabitEat(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return habitStuTypeDao.getHabitEat(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getHabitEatBySex(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return habitStuTypeDao.getHabitEatBySex(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getHabitEatByEdu(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return habitStuTypeDao.getHabitEatByEdu(startDate, endDate, deptTeach);
	}
	
}
