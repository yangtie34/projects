package cn.gilight.product.equipment.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.product.equipment.dao.EquipOutTimeDao;
import cn.gilight.product.equipment.service.EquipOutTimeService;

@Service("equipOutTimeService")
public class EquipOutTimeServiceImpl implements EquipOutTimeService{
	
	@Autowired
	private EquipOutTimeDao equipOutTimeDao;

	@Override
	public Map<String, Object> getCount(String emType) {
		switch (emType) {
		case "all":
			return equipOutTimeDao.getCount();
		case "val":
			return equipOutTimeDao.getValuableCount();
		default:
			break;
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getContrastByType(String type,
			String emType) {
		return equipOutTimeDao.getContrastByType(type, emType);
	}

	@Override
	public List<Map<String, Object>> getContrastTrendByType(String type,
			String emType) {
		return equipOutTimeDao.getContrastTrendByType(type, emType);
	}

	@Override
	public List<Map<String, Object>> getCountByUseDept(String emType) {
		return equipOutTimeDao.getCountByUseDept(emType);
	}

	@Override
	public List<Map<String, Object>> getCountByUseDept(String deptId,
			String deptGroup,String emType) {
		return equipOutTimeDao.getCountByUseDept(deptId, deptGroup, emType);
	}

	@Override
	public List<Map<String, Object>> getWillOutTime(String emType) {
		return equipOutTimeDao.getWillOutTime(emType);
	}
	
}
