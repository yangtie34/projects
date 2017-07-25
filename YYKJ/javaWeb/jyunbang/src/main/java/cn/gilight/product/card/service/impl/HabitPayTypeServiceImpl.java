package cn.gilight.product.card.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.product.card.dao.HabitPayTypeDao;
import cn.gilight.product.card.service.HabitPayTypeService;

@Service("habitPayTypeService")
public class HabitPayTypeServiceImpl implements HabitPayTypeService {

	@Autowired
	private HabitPayTypeDao habitPayTypeDao;

	@Override
	public Map<String, Object> getHabitCount(String startDate, String endDate,
			Map<String, String> deptTeach) {
		
		return habitPayTypeDao.getHabitCount(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getHabitCountByType(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return habitPayTypeDao.getHabitCountByType(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getHabitZao(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return habitPayTypeDao.getHabitZao(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getHabitZaoByType(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return habitPayTypeDao.getHabitZaoByType(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getHabitHour(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return habitPayTypeDao.getHabitHour(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getHabitHourByType(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return habitPayTypeDao.getHabitHourByType(startDate, endDate, deptTeach);
	}
	
}
