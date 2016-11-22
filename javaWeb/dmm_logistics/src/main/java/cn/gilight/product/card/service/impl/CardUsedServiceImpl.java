package cn.gilight.product.card.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.page.Page;
import cn.gilight.product.card.dao.CardUsedDao;
import cn.gilight.product.card.service.CardUsedService;

@Service("cardUsedService")
public class CardUsedServiceImpl implements CardUsedService{
	
	@Autowired
	private CardUsedDao cardUsedDao;

	@Override
	public Map<String, Object> getCardUsed(String startDate, String endDate,
			Map<String, String> deptTeach) {
		
		return cardUsedDao.getCardUsed(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getCardUsedBySex(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return cardUsedDao.getCardUsedBySex(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getCardUsedByEdu(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return cardUsedDao.getCardUsedByEdu(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getCardUsedByDept(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return cardUsedDao.getCardUsedByDept(startDate, endDate, deptTeach);
	}

	@Override
	public Page getNoCardUsed(int currentPage, int numPerPage, int totalRow,
			String startDate, String endDate, Map<String, String> deptTeach) {
		
		return cardUsedDao.getNoCardUsed(currentPage, numPerPage, totalRow, startDate, endDate, deptTeach);
	}

	@Override
	public Page getCardUsedPage(int currentPage, int numPerPage, int totalRow,
			String startDate, String endDate, Map<String, String> deptTeach,
			String type, String type_code) {
		return cardUsedDao.getCardUsedPage(currentPage, numPerPage, totalRow, startDate, endDate, deptTeach, type, type_code);
	}
	
}
