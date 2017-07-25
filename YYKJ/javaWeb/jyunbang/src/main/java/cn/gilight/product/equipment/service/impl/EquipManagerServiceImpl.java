package cn.gilight.product.equipment.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.page.Page;
import cn.gilight.product.equipment.dao.EquipManagerDao;
import cn.gilight.product.equipment.service.EquipManagerService;

@Service("equipManagerService")
public class EquipManagerServiceImpl implements EquipManagerService{

	@Autowired
	private EquipManagerDao equipManagerDao;
	
	@Override
	public int getManagers() {
		return equipManagerDao.getManagers();
	}

	@Override
	public List<Map<String, Object>> getManagersByDept() {
		return equipManagerDao.getManagersByDept();
	}

	@Override
	public List<Map<String, Object>> getContrastByType(String type,String DeptGroup) {
		if("edu".equals(type)){
			return equipManagerDao.getContrastByEdu(DeptGroup);
		}else if("zc".equals(type)){
			return equipManagerDao.getContrastByZC(DeptGroup);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getContrastTrendByType(String type,
			String DeptGroup) {
		if("edu".equals(type)){
			return equipManagerDao.getContrastTrendByEdu(DeptGroup);
		}else if("zc".equals(type)){
			return equipManagerDao.getContrastTrendByZC(DeptGroup);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getManagersByDept(String deptId,
			String deptGroup) {
		return equipManagerDao.getManagersByDept(deptId, deptGroup);
	}

	@Override
	public List<Map<String, Object>> getCountByUseDept(String deptId,
			String deptGroup) {
		return equipManagerDao.getCountByUseDept(deptId, deptGroup);
	}

	@Override
	public Page getEmTop(int currentPage, int numPerPage,int totalRow, String rankType,
			String queryType, String deptGroup, int rank) {
		return equipManagerDao.getEmTop(currentPage, numPerPage,totalRow, rankType, queryType, deptGroup, rank);
	}

	
}
