package com.jhnu.person.tea.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.person.tea.dao.TeaKyDao;
import com.jhnu.person.tea.service.TeaKyService;
import com.jhnu.system.common.page.Page;

@Service("teaKyService")
public class TeaKyServiceImpl implements TeaKyService {
	@Autowired
	private TeaKyDao teaKyDao;

	@Override
	public List pushBooks(String id, String startTime, String endTime) {
		
		return teaKyDao.pushBooks(id, startTime, endTime);
	}

	@Override
	public List jyfl(String id, String startTime, String endTime) {
		
		return teaKyDao.jyfl(id,startTime,endTime);
	}

	@Override
	public List jysl(String id, String startTime, String endTime) {
		
		return teaKyDao.jysl(id,startTime,endTime);
	}

	@Override
	public Page jymx(String id, String startTime, String endTime,
			int currentPage, int numPerPage) {
		
		return teaKyDao.jymx(id,startTime,endTime, currentPage, numPerPage);
	}

	@Override
	public List kyxx(String id, String startTime, String endTime) {
		
		return teaKyDao.kyxx(id,startTime,endTime);
	}

}
