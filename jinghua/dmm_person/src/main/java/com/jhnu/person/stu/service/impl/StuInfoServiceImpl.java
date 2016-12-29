package com.jhnu.person.stu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.person.stu.dao.StuInfoDao;
import com.jhnu.person.stu.service.StuInfoService;
import com.jhnu.system.common.page.Page;

@Repository("stuInfoService")
public class StuInfoServiceImpl implements StuInfoService {
	@Autowired
	private StuInfoDao stuInfoDao;

	@Override
	public Map getInfo(String id) {
		return stuInfoDao.getInfo(id);
	}

	@Override
	public List getGlory(String id) {
		return stuInfoDao.getGlory(id);
	}

	@Override
	public List OtherUser(String id) {
		return stuInfoDao.OtherUser(id);
	}

	@Override
	public List getXqzc(String id) {
		return stuInfoDao.getXqzc(id);
	}

	@Override
	public List getXjyd(String id) {
		return stuInfoDao.getXjyd(id);
	}

	@Override
	public Page getSsZsxx(String id, int currentPage, int numPerPage) {
		return stuInfoDao.getSsZsxx(id,currentPage,numPerPage);
	}

	@Override
	public List getSsTzxx(String id) {
		return stuInfoDao.getSsTzxx(id);
	}

	@Override
	public String getUserSex(String id) {
		return stuInfoDao.getUserSex(id);
	}
}
