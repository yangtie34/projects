package com.jhnu.person.tea.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.person.tea.dao.TeaTeachDao;
import com.jhnu.person.tea.service.TeaTeachService;
import com.jhnu.system.common.page.Page;

@Service("teaTeachService")
public class TeaTeachServiceImpl implements TeaTeachService {
	@Autowired
	private TeaTeachDao teaTeachDao;

	@Override
	public List jrkc(String id) {
		
		return teaTeachDao.jrkc(id);
	}

	@Override
	public List grkb(String id) {
		
		return teaTeachDao.grkb(id);
	}

	@Override
	public List skjd(String id) {
		
		return teaTeachDao.skjd(id);
	}

	@Override
	public Page skcj(String id, int currentPage, int numPerPage) {
		
		return teaTeachDao.skcj(id, currentPage, numPerPage);
	}

}
