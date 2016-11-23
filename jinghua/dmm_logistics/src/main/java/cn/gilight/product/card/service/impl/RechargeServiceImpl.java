package cn.gilight.product.card.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.product.card.dao.RechargeDao;
import cn.gilight.product.card.service.RechargeService;

@Service("rechargeService")
public class RechargeServiceImpl implements RechargeService {
	
	@Autowired
	private RechargeDao rechargeDao;

	@Override
	public Map<String, Object> getRecharge(String startDate, String endDate,
			Map<String, String> deptTeach) {
		return rechargeDao.getRecharge(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getRechargeByDept(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return rechargeDao.getRechargeByDept(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getRechargeRegion(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return rechargeDao.getRechargeRegion(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getLastMoneyRegion(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return rechargeDao.getLastMoneyRegion(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getRechargeByType(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return rechargeDao.getRechargeByType(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getRechargeByHour(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return rechargeDao.getRechargeByHour(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getRechargeTrend(
			Map<String, String> deptTeach) {
		
		return rechargeDao.getRechargeTrend(deptTeach);
	}

	@Override
	public List<Map<String, Object>> getRechargeTrendByType(
			Map<String, String> deptTeach) {
		
		return rechargeDao.getRechargeTrendByType(deptTeach);
	}
	
}
