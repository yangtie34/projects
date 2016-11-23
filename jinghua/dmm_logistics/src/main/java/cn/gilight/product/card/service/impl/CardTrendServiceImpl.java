package cn.gilight.product.card.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.product.card.dao.CardTrendDao;
import cn.gilight.product.card.service.CardTrendService;

@Service("cardTrendService")
public class CardTrendServiceImpl implements CardTrendService{
	
	@Autowired
	private CardTrendDao cardTrendDao;

	@Override
	public List<Map<String, Object>> getRechargeTrend(
			Map<String, String> deptTeach) {
		return cardTrendDao.getRechargeTrend(deptTeach);
	}

	@Override
	public List<Map<String, Object>> getCardUsedTrend(
			Map<String, String> deptTeach, String type_code, String flag) {
		return cardTrendDao.getCardUsedTrend(deptTeach, type_code, flag);
	}

	@Override
	public List<Map<String, Object>> getPayTypeTrend(
			Map<String, String> deptTeach, String type_code) {
		return cardTrendDao.getPayTypeTrend(deptTeach, type_code);
	}

	@Override
	public List<Map<String, Object>> getStuPayTrend(
			Map<String, String> deptTeach, String type_code, String flag) {
		return cardTrendDao.getStuPayTrend(deptTeach, type_code, flag);
	}

	@Override
	public List<Map<String, Object>> getEatTrend(String type_code, String flag,
			String queryType) {
		return cardTrendDao.getEatTrend(type_code, flag, queryType);
	}

	@Override
	public List<Map<String, Object>> getPayYearTrend(
			Map<String, String> deptTeach) {
		return cardTrendDao.getPayYearTrend(deptTeach);
	}
	
}
