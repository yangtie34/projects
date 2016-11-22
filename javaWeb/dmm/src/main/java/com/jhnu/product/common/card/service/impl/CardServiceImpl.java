package com.jhnu.product.common.card.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.common.card.dao.CardDao;
import com.jhnu.product.common.card.service.CardService;
import com.jhnu.system.common.TreeCode;
import com.jhnu.util.common.MapUtils;

@Service("cardService")
public class CardServiceImpl implements CardService{
	@Autowired
	private CardDao cardDao;
	
	@Override
	public TreeCode getTreeCode(String id) {
		return cardDao.getTreeCode(id);
	}
	
	@Override
	public List<String> getCardDealGroup(String groupCode) {
		List<Map<String,Object>> temps =cardDao.getCardDealGroup(groupCode);
		List<String> strs = new ArrayList<String>();
		for(Map<String,Object> temp : temps){
			strs.add(MapUtils.getString(temp, "ID"));
		}
		return strs;
	}
	
	@Override
	public List<Map<String,Object>> getGroupCodes() {
		return cardDao.getGroupCodes();
	}
	
	@Override
	public double getCardBlanceById(String stuId) {
		List<Map<String,Object>> result = cardDao.getCardBlanceByStudentId(stuId);
		return MapUtils.getDoubleValue(result.get(0),"SURPLUS_MONEY");
	}
}
