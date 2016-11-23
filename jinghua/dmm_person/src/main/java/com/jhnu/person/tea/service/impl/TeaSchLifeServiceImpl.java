package com.jhnu.person.tea.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.person.tea.dao.TeaSchLifeDao;
import com.jhnu.person.tea.service.TeaSchLifeService;
import com.jhnu.system.common.page.Page;

@Service("teaSchLifeService")
public class TeaSchLifeServiceImpl implements TeaSchLifeService {
	@Autowired
	private TeaSchLifeDao teaSchLifeDao;

	@Override
	public List gzComb(String id, String startTime, String endTime) {
		
		return teaSchLifeDao.gzComb(id,startTime,endTime);
	}

	@Override
	public List gzChange(String id, String startTime, String endTime) {
		
		return teaSchLifeDao.gzChange(id,startTime,endTime);
	}

	@Override
	public Page gzxq(String id, String startTime, String endTime, int currentPage, int numPerPage) {
		
		return teaSchLifeDao.gzxq(id,startTime,endTime, currentPage,numPerPage);
	}

	@Override
	public List yktxf(String id, String startTime, String endTime) {
		List list=new ArrayList();
		List ye=teaSchLifeDao.yktye(id);
		if(ye.size()==0){
			Map map=new HashMap();
			map.put("VALUE", 0);
			ye.add(map);
		}
		list.add(ye.get(0));
		list.add(teaSchLifeDao.yktxf(id,startTime,endTime));
		return list;
	}

	@Override
	public List yktcz(String id, String startTime, String endTime) {
		
		return teaSchLifeDao.yktcz(id,startTime,endTime);
	}

	@Override
	public List yktxffx(String id, String startTime, String endTime) {
		
		return teaSchLifeDao.yktxffx(id,startTime,endTime);
	}

	@Override
	public Page yktxfmx(String id, String startTime, String endTime,
			int currentPage, int numPerPage) {
		
		return teaSchLifeDao.yktxfmx(id,startTime,endTime, currentPage, numPerPage);
	}

	@Override
	public List ieAvgTime(String id, String startTime, String endTime) {
		
		return teaSchLifeDao.ieAvgTime(id,startTime,endTime);
	}

	@Override
	public List ieAllTime(String id, String startTime, String endTime) {
		
		return teaSchLifeDao.ieAllTime(id,startTime,endTime);
	}

	@Override
	public List schFirst(String id) {
		
		
		return teaSchLifeDao.schFirst(id);
	}
	

}
