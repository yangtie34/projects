package com.jhnu.person.tea.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.person.tea.dao.TeaInfoDao;
import com.jhnu.person.tea.service.TeaInfoService;

@Service("teaInfoService")
public class TeaInfoServiceImpl implements TeaInfoService {
	@Autowired
	private TeaInfoDao teaInfoDao;

	@Override
	public Map getInfo(String id) {
		return teaInfoDao.getInfo(id);
	}

	@Override
	public Map getHisInfo(String id) {
		return teaInfoDao.getHisInfo(id);
	}

	@Override
	public List OtherUser(String id) {
		return teaInfoDao.OtherUser(id);
	}
	@Override
	public String getUserSex(String id) {
		return teaInfoDao.getUserSex(id);
	}

	@Override
	public boolean isTeacher(String id) {
		return teaInfoDao.isTeacher(id);
	}
}
