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
	public List<Map<String, Object>> getPowerByArea(String startDate, String endDate, Map<String, String> deptTeach) {
		return payPowerDao.getPowerByArea(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getPayTypeByArea(String startDate, String endDate, Map<String, String> deptTeach) {
		return payPowerDao.getPayTypeByArea(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getPayRegionByArea(String startDate, String endDate,
			Map<String, String> deptTeach) {
		return payPowerDao.getPayRegionByArea(startDate, endDate, deptTeach);
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
		map.put("dqcode", Code.getKey("area.xz.code"));
		map.put("dqname", Code.getKey("area.xz.name"));
		map.put("zzcode", Code.getKey("mz.zz.code"));
		map.put("zzname", Code.getKey("mz.zz.name"));
		map.put("tea.status.code", Code.getKey("tea.status.code").replaceAll("'", ""));
		map.put("tea.status.name", Code.getKey("tea.status.name").replaceAll("'", ""));
		map.put("tea.bzlb.code", Code.getKey("tea.bzlb.code").replaceAll("'", ""));
		map.put("tea.bzlb.name", Code.getKey("tea.bzlb.name").replaceAll("'", ""));
		map.put("tea.zwjb.code", Code.getKey("tea.zwjb.code").replaceAll("'", ""));
		map.put("tea.zwjb.name", Code.getKey("tea.zwjb.name").replaceAll("'", ""));
		return map;
	}

	@Override
	public List<Map<String, Object>> getPowerByMZ(String startDate,
			String endDate, Map<String, String> deptTeach) {
		return payPowerDao.getPowerByMZ(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getPayTypeByMZ(String startDate,
			String endDate, Map<String, String> deptTeach) {
		return payPowerDao.getPayTypeByMZ(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getPayRegionByMZ(String startDate,
			String endDate, Map<String, String> deptTeach) {
		return payPowerDao.getPayRegionByMZ(startDate, endDate, deptTeach);
	}

	
}
