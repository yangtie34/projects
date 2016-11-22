package cn.gilight.product.card.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.code.Code;
import cn.gilight.product.card.dao.PayPowerDao;
import cn.gilight.product.card.service.PayPowerService;

@Service("payPowerService")
public class PayPowerServiceImpl implements PayPowerService{
	
	@Autowired
	private PayPowerDao payPowerDao;

	@Override
	public List<Map<String, Object>> getPower(String startDate, String endDate,
			Map<String, String> deptTeach) {
		
		return payPowerDao.getPower(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getPowerBySex(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return payPowerDao.getPowerBySex(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getPowerByEdu(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return payPowerDao.getPowerByEdu(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getPowerByDept(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return payPowerDao.getPowerByDept(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getPayType(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return payPowerDao.getPayType(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getPayTypeBySex(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return payPowerDao.getPayTypeBySex(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getPayTypeByEdu(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return payPowerDao.getPayTypeByEdu(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getPayRegion(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return payPowerDao.getPayRegion(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getPayRegionBySex(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return payPowerDao.getPayRegionBySex(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getPayRegionByEdu(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return payPowerDao.getPayRegionByEdu(startDate, endDate, deptTeach);
	}

	@Override
	public Map<String, String> getxldm() {
		Map<String,String> map=new HashMap<String,String>();
		map.put("yjs", Code.getKey("xl.yjs"));
		map.put("dz", Code.getKey("xl.dz"));
		map.put("bk", Code.getKey("xl.bk"));
		map.put("ct", Code.getKey("card.ct"));
		map.put("xy", Code.getKey("card.xy"));
		map.put("cs", Code.getKey("card.cs"));
		map.put("gzbz",Code.getKey("em.val"));
		map.put("hz", Code.getKey("mz.hz"));
		map.put("zzcode", Code.getKey("mz.zz.code"));
		map.put("zzname", Code.getKey("mz.zz.name"));
		return map;
	}
	
}
