package cn.gilight.product.net.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.product.net.dao.NetCountDao;
import cn.gilight.product.net.service.NetTypeService;

/**
 * 上网类型分析
 *
 */
@Service("netTypeService")
public class NetTypeServiceImpl implements NetTypeService{
	
	@Autowired
	private NetCountDao netCountDao;

	@Override
	public List<Map<String, Object>> getCounts(String startDate,
			String endDate, Map<String, String> deptTeach, String type) {
		return netCountDao.getCounts(startDate, endDate, deptTeach, type);
	}

	@Override
	public List<Map<String, Object>> getNetStuRatio(String startDate,
			String endDate, Map<String, String> deptTeach, String type) {
		return netCountDao.getNetStuRatio(startDate, endDate, deptTeach, type);
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

	@Override
	public List<Map<String, Object>> getNetStuRatioTrend(
			Map<String, String> deptTeach, String type) {
		return netCountDao.getNetStuRatioTrend(deptTeach, type);
	}
	
}
