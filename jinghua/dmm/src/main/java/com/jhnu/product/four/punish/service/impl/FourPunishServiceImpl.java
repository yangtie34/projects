package com.jhnu.product.four.punish.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.product.four.common.entity.ResultBean;
import com.jhnu.product.four.punish.dao.FourPunishDao;
import com.jhnu.product.four.punish.service.FourPunishService;

@Service("fourPunishService")
public class FourPunishServiceImpl implements FourPunishService {
	
	@Autowired
	private FourPunishDao fourPunishDao;
	
	@Override
	@Transactional
	public void savePunishLog()
	{
		List<Map<String,Object>> list=fourPunishDao.getPunishMsg();
		fourPunishDao.savePunishLog(list);
	}
	
	

	public ResultBean getStuPunishMsgByID(String Id)
	{
		ResultBean res = new ResultBean();
		res.setName("getPunishMsg");
		res.getData().put("getPunishMsg",fourPunishDao.getPunishMsg(Id));
		return res;
	}
	
	
	
	
	
}



