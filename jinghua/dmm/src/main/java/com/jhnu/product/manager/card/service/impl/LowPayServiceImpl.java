package com.jhnu.product.manager.card.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.manager.card.dao.LowPayDao;
import com.jhnu.product.manager.card.service.LowPayService;
import com.jhnu.system.common.page.Page;
import com.jhnu.util.common.MapUtils;

@Service("lowPayService")
public class LowPayServiceImpl implements LowPayService {

	@Autowired
	private LowPayDao lowPayDao;

	@Override
	public List<Map<String, Object>> getAvgEatMoneyBySex(String startDate, String endDate) {
		return lowPayDao.getAvgEatMoneyBySex(startDate, endDate);
	}

	@Override
	public void saveStuEatDetailLog() {
		lowPayDao.saveStuEatDetail("1");
		lowPayDao.saveStuEatDetail("2");
		List<Map<String, Object>> fzResult = lowPayDao.getFaZhi();
		String fz = "";
		if (fzResult != null) {
			fz = MapUtils.getString(fzResult.get(0), "YZ");
		}
		lowPayDao.saveStuEatResult(fz);
	}

	@Override
	public Page getLowPayStu(String dept_id, boolean isLeaf, int currentPage, int pageSize) {
		return lowPayDao.getLowPayStu(dept_id, isLeaf, currentPage, pageSize);
	}

	@Override
	public Page getLowPayDetailStu(String stuId, String sexCode, String startDate, String endDate, int currentPage, int pageSize) {
		return lowPayDao.getLowPayDetailStu(stuId, sexCode,startDate, endDate, currentPage, pageSize);
	}

}
