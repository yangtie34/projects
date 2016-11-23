package cn.gilight.product.net.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.product.net.dao.NetStuDeptDao;
import cn.gilight.product.net.service.NetStuDeptService;

/**
 * 学生分学院上网分析
 *
 */
@Service("netStuDeptService")
public class NetStuDeptServiceImpl  implements NetStuDeptService{
	
	@Autowired
	private NetStuDeptDao netStuDeptDao;

	@Override
	public List<Map<String, Object>> getNetStus(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return netStuDeptDao.getNetStus(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getNetCounts(String startDate,
			String endDate, Map<String, String> deptTeach, String type) {
		
		return netStuDeptDao.getNetCounts(startDate, endDate, deptTeach, type);
	}

	@Override
	public List<Map<String, Object>> getNetTimes(String startDate,
			String endDate, Map<String, String> deptTeach, String type) {
		
		return netStuDeptDao.getNetTimes(startDate, endDate, deptTeach, type);
	}

	@Override
	public List<Map<String, Object>> getNetWarnStus(String startDate,
			String endDate, Map<String, String> deptTeach, String type,String value) {
		
		return netStuDeptDao.getNetWarnStus(startDate, endDate, deptTeach, type,value);
	}
	
}
