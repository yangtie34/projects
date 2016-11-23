package cn.gilight.product.dorm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.page.Page;
import cn.gilight.product.dorm.dao.DormRkeUsedDao;
import cn.gilight.product.dorm.service.DormRkeUsedService;

@Service("dormRkeUsedService")
public class DormRkeUsedServiceImpl implements DormRkeUsedService{
	
	@Autowired
	private DormRkeUsedDao dormRkeUsedDao;

	@Override
	public Map<String, Object> getDormRkeUsed(String startDate, String endDate,
			Map<String, String> deptTeach) {
		return dormRkeUsedDao.getDormRkeUsed(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getDormRkeUsedBySex(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return dormRkeUsedDao.getDormRkeUsedBySex(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getDormRkeUsedByEdu(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return dormRkeUsedDao.getDormRkeUsedByEdu(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getDormRkeUsedByMz(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return dormRkeUsedDao.getDormRkeUsedByMz(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getDormRkeUsedByLY(String startDate,
			String endDate, Map<String, String> deptTeach) {
		return dormRkeUsedDao.getDormRkeUsedByLY(startDate, endDate, deptTeach);
	}
	
	@Override
	public List<Map<String, Object>> getDormRkeUsedByDept(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return dormRkeUsedDao.getDormRkeUsedByDept(startDate, endDate, deptTeach);
	}

	@Override
	public Page getDormRkeUsedPage(int currentPage, int numPerPage,
			int totalRow, String startDate, String endDate,
			Map<String, String> deptTeach, String type, String type_code) {
		
		return dormRkeUsedDao.getDormRkeUsedPage(currentPage, numPerPage, totalRow, startDate, endDate, deptTeach, type, type_code);
	}

	@Override
	public Page getNoDormRkeUsed(int currentPage, int numPerPage, int totalRow,
			String startDate, String endDate, Map<String, String> deptTeach) {
		
		return dormRkeUsedDao.getNoDormRkeUsed(currentPage, numPerPage, totalRow, startDate, endDate, deptTeach);
	}

}
