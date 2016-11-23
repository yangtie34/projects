package com.jhnu.person.stu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.person.stu.dao.StuSchLifeDao;
import com.jhnu.person.stu.service.StuSchLifeService;
import com.jhnu.system.common.page.Page;
@Repository("stuSchLifeService")
public class StuSchLifeServiceImpl implements StuSchLifeService {
	@Autowired
	private StuSchLifeDao stuSchLifeDao;
	@Override
	public List yktxf(String id, String startTime, String endTime) {
		List list=new ArrayList();
		list.add(stuSchLifeDao.yktye(id));
		list.add(stuSchLifeDao.yktxf(id,startTime,endTime));
		return list;
	}

	@Override
	public List yktcz(String id, String startTime, String endTime) {
		return stuSchLifeDao.yktcz(id, startTime, endTime);
	}

	@Override
	public List yktxffx(String id, String startTime, String endTime) {
		return stuSchLifeDao.yktxffx(id, startTime, endTime);
	}

	@Override
	public Page yktxfmx(String id, String startTime, String endTime,
			int currentPage, int numPerPage) {
		return stuSchLifeDao.yktxfmx(id, startTime, endTime, currentPage, numPerPage);
	}

	@Override
	public List ieAvgTime(String id, String startTime, String endTime) {
		return stuSchLifeDao.ieAvgTime(id, startTime, endTime);
	}

	@Override
	public List ieAllTime(String id, String startTime, String endTime) {
		return stuSchLifeDao.ieAllTime(id, startTime, endTime);
	}

	@Override
	public List schFirst(String id) {
		return stuSchLifeDao.schFirst(id);
	}

}
