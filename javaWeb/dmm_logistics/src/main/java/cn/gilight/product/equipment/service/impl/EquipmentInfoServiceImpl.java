package cn.gilight.product.equipment.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.product.equipment.dao.EquipmentInfoDao;
import cn.gilight.product.equipment.service.EquipmentInfoService;

@Service("equipmentInfoService")
public class EquipmentInfoServiceImpl implements EquipmentInfoService{

	@Autowired
	private EquipmentInfoDao equipmentInfoDao;
	
	@Override
	public Map<String, Object> getCount(String emType) {
		switch (emType) {
		case "all":
			return equipmentInfoDao.getCount();
		case "val":
			return equipmentInfoDao.getValuableCount();
		case "now":
			return equipmentInfoDao.getNowCount();
		case "nowVal":
			return equipmentInfoDao.getNowValuableCount();
		default:
			break;
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getContrastByType(String type,
			String emType) {
		return equipmentInfoDao.getContrastByType(type, emType);
	}

	@Override
	public List<Map<String, Object>> getContrastTrendByType(String type,
			String emType) {
		return equipmentInfoDao.getContrastTrendByType(type, emType);
	}

	@Override
	public List<Map<String, Object>> getEquipmentTrend() {
		return equipmentInfoDao.getEquipmentTrend();
	}
	
}
