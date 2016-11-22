package com.jhnu.system.task.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.dao.VerifyDao;
import com.jhnu.system.task.entity.PlanLogDetails;
import com.jhnu.system.task.entity.Verify;
import com.jhnu.system.task.service.VerifyService;
@Service("taskVerifyService")
public class VerifyServiceImpl implements VerifyService {
	@Autowired
	private VerifyDao verifyDao;
	private static final Logger logger = Logger.getLogger(VerifyServiceImpl.class);
	@Override
	public Page getVerifys(int currentPage, int numPerPage) {
		return verifyDao.getVerifys(currentPage,numPerPage,new Verify());
	}
	public Page getVerifys(int currentPage, int numPerPage,Verify verify) {
		return verifyDao.getVerifys(currentPage,numPerPage,verify);
	}
	@Override
	public Verify getVerifyById(String id) {
		return verifyDao.getVerifyById(id);
	}

	@Override
	public Page getVerifyByName(int currentPage, int numPerPage,String name) {
		return verifyDao.getVerifysByName(currentPage,numPerPage,name);
	}

	@Override
	public boolean updateOrInsert(Verify plan)  throws ParamException{
		return verifyDao.updateOrInsert(plan);
	}

	@Override
	public boolean del(Verify plan) {
		return verifyDao.del(plan);
	}
	@Override
	public String insertReturnId(Verify plan) {
		return verifyDao.insertReturnId(plan);
	}
}
