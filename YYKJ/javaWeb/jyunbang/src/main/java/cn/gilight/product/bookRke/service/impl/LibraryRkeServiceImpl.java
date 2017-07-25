package cn.gilight.product.bookRke.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.product.bookRke.dao.LibraryRkeDao;
import cn.gilight.product.bookRke.service.LibraryRkeService;

@Service("libraryRkeService")
public class LibraryRkeServiceImpl implements LibraryRkeService {
	@Autowired
	private LibraryRkeDao libraryRkeDao;

	@Override
	public Map<String, List<Map<String, Object>>> getRkeInfo(String startDate,
			String endDate, Map<String, String> deptTeach, String lb) {
		Map<String, List<Map<String, Object>>> map=new HashMap<String, List<Map<String, Object>>>();
		map.put("ALL_COUNT", libraryRkeDao.getCounts(startDate, endDate, deptTeach, lb));
		map.put("AVG_COUNT", libraryRkeDao.getAvgCounts(startDate, endDate, deptTeach, lb));
		map.put("INRATE", libraryRkeDao.getInRate(startDate, endDate, deptTeach, lb));
		map.put("week", libraryRkeDao.getCountsByGroup(startDate, endDate, deptTeach, lb,"week"));
		map.put("hour", libraryRkeDao.getCountsByGroup(startDate, endDate, deptTeach, lb,"hour"));
		map.put("csqj", libraryRkeDao.getQjByGroup(startDate, endDate, deptTeach, lb));
		return map;
	}

	@Override
	public List<Map<String, Object>> getCountsByDept(String startDate,
			String endDate, Map<String, String> deptTeach) {
		return libraryRkeDao.getCountsByDept(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getCountsForYears(
			Map<String, String> deptTeach) {
		return libraryRkeDao.getCountsForYears(deptTeach);
	}

	@Override
	public List<Map<String, Object>> getStuRkeTrend(
			Map<String, String> deptTeach, String flag, String flagCode) {
		return libraryRkeDao.getStuRkeTrend(deptTeach,flag,flagCode);
	}
}
