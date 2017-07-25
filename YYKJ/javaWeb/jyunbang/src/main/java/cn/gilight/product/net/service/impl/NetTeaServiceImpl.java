package cn.gilight.product.net.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.product.net.dao.NetTeaDao;
import cn.gilight.product.net.service.NetTeaService;

/**
 * 学生上网习惯
 *
 */
@Service("netTeaService")
public class NetTeaServiceImpl implements NetTeaService{
	
	@Autowired
	private NetTeaDao netTeaDao;

	@Override
	public List<Map<String, Object>> getCounts(String startDate,
			String endDate, Map<String, String> deptTeach, String type) {
		return netTeaDao.getCounts(startDate, endDate, deptTeach, type);
	}

	@Override
	public List<Map<String, Object>> getNetType(String startDate,
			String endDate, Map<String, String> deptTeach, String type) {
		return netTeaDao.getNetType(startDate, endDate, deptTeach, type);
	}

	@Override
	public List<Map<String, Object>> getNetHourTea(String startDate,
			String endDate, Map<String, String> deptTeach, String type) {
		return netTeaDao.getNetHourTea(startDate, endDate, deptTeach, type);
	}

	@Override
	public List<Map<String, Object>> getCountsTrend(
			Map<String, String> deptTeach, String type) {
		return netTeaDao.getCountsTrend(deptTeach, type);
	}
	
}
