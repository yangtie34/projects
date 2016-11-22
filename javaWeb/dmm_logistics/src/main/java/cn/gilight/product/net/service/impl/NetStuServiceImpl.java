package cn.gilight.product.net.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.product.net.dao.NetCountDao;
import cn.gilight.product.net.service.NetStuService;

/**
 * 学生上网习惯
 *
 */
@Service("netStuService")
public class NetStuServiceImpl implements NetStuService{
	
	@Autowired
	private NetCountDao netCountDao;

	@Override
	public List<Map<String, Object>> getCounts(String startDate,
			String endDate, Map<String, String> deptTeach, String type) {
		return netCountDao.getCounts(startDate, endDate, deptTeach, type);
	}

	@Override
	public List<Map<String, Object>> getNetType(String startDate,
			String endDate, Map<String, String> deptTeach, String type) {
		return netCountDao.getNetType(startDate, endDate, deptTeach, type);
	}

	@Override
	public List<Map<String, Object>> getNetHourStu(String startDate,
			String endDate, Map<String, String> deptTeach, String type) {
		return netCountDao.getNetHourStu(startDate, endDate, deptTeach, type);
	}

	@Override
	public List<Map<String, Object>> getCountsTrend(
			Map<String, String> deptTeach, String type) {
		return netCountDao.getCountsTrend(deptTeach, type);
	}
	
}
