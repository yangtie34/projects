package com.jhnu.product.four.award.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.common.stu.service.StuService;
import com.jhnu.product.four.award.dao.FourAwardDao;
import com.jhnu.product.four.award.service.FourAwardService;
import com.jhnu.product.four.common.entity.ResultBean;
import com.jhnu.util.common.MapUtils;
import com.jhnu.util.common.MathUtils;

@Service("fourAwardService")
public class FourAwardServiceImpl implements FourAwardService {

	@Autowired
	private FourAwardDao fourAwardDao;

	@Autowired
	private StuService stuService;

	private static final Logger logger = Logger
			.getLogger(FourAwardServiceImpl.class);

	@SuppressWarnings("unused")
	public void SaveAllAwardTimesToLog() {
		logger.info("开始保存获奖次数");
		
		List<Map<String, Object>> all = fourAwardDao.getAwardTimes();

		int sum = -1, byn = -1, bynCon = -1;
		String moreThanS = "";
		double mc = 1, qmc = 0;
		for (int i = 0; i < all.size(); i++) {

			
			if (byn != MapUtils.getIntValue(all.get(i),"byn")) {
				byn = MapUtils.getIntValue(all.get(i),"byn");
			
				bynCon++;
				qmc = MapUtils.getIntValue(all.get(i),"r") - 1;
				sum = -1;
			}
		
			if (sum != MapUtils.getIntValue(all.get(i),"times")) {

				int num = stuService.getStuCByEndYear(MapUtils.getIntValue(all.get(i),"byn"));
				sum = MapUtils.getIntValue(all.get(i),"times");
				mc = MapUtils.getIntValue(all.get(i),"r") - qmc;
				double moreThan = MathUtils.get2Point((mc - 1) / num * 100);
				moreThanS = moreThan + "%";

			}
			
			all.get(i).put("more_than", moreThanS);
			
		}

		fourAwardDao.SaveAwardLog(all);

	}

	public void SaveAllSubsidy() {
		List<Map<String, Object>> all = fourAwardDao.getSubsidyTimesAndMoney();

		fourAwardDao.SaveSubsidyLog(all);
	}

	public void SaveAllFirstAward() {
		List<Map<String, Object>> all = fourAwardDao.getFirstAwardMsg();
		fourAwardDao.SaveFirstAwardMsgLog(all);
	}

	public ResultBean getAwardTimes(String Id) {
		ResultBean res = new ResultBean();
		res.setName("getAwardTimes");
		res.getData().put("getAwardTimes", fourAwardDao.getAwardTimes(Id));
		return res;

	}

	public ResultBean getSubsidyTimesAndMoney(String Id) {
		ResultBean res = new ResultBean();
		res.setName("getSubsidyTimesAndMoney");
		res.getData().put("getSubsidyTimesAndMoney",
				fourAwardDao.getSubsidyTimesAndMoney(Id));
		return res;
	}

	public ResultBean getFirstAwardMsg(String Id) {
		ResultBean res = new ResultBean();
		res.setName("getFirstAwardMsg");
		res.getData()
				.put("getFirstAwardMsg", fourAwardDao.getFirstAwardMsg(Id));
		return res;
	}

}
