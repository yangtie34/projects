package com.jhnu.product.manager.card.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.manager.card.dao.PayTypeDao;
import com.jhnu.product.manager.card.service.PayTypeService;
@Service("payTypeService")
public class PayTypeServiceImpl implements PayTypeService {
	
	@Autowired
	private PayTypeDao payTypeDao;
	@Override
	public void savePay(){
		String startDate = "2015-03-31" ;//DateUtils.getYesterday();
		String endDate = "2015-04-01";//DateUtils.date2String(new Date());
		List<Map<String,Object>> list = payTypeDao.getPay(startDate, endDate);
		payTypeDao.savePay(list);
	}

	@Override
	public List<Map<String, Object>> getPayLog(String dept_id,boolean isLeaf,String type_code, String startDate,String endDate) {
		return payTypeDao.getPayLog(dept_id,isLeaf,type_code, startDate, endDate);
	}

	@Override
	public List<Map<String, Object>> getPayDetailLog(String dept_id,boolean isLeaf,String type_code,String startDate, String endDate) {
		return payTypeDao.getPayDetailLog(dept_id,isLeaf,type_code, startDate, endDate);
	}

	@Override
	public void savePayDetail() {
		String startDate = "2015-03-01" ;//DateUtils.getYesterday();
		String endDate = "2015-03-02";//DateUtils.date2String(new Date());
		List<Map<String,Object>> list = payTypeDao.getPayDetail(startDate, endDate);
		payTypeDao.savePayDetail(list);
	}
	
	

}
