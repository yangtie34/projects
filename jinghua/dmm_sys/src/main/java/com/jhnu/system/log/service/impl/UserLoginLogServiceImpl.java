package com.jhnu.system.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jhnu.system.common.page.Page;
import com.jhnu.system.log.dao.UserLoginLogDao;
import com.jhnu.system.log.entity.UserLoginLog;
import com.jhnu.system.log.service.UserLoginLogService;

@Service("userLoginLogService")
public class UserLoginLogServiceImpl implements UserLoginLogService{
	@Autowired
	private UserLoginLogDao userLoginLogDao;

	@Override
	public UserLoginLog addUserLoginLog(UserLoginLog userLoginLog) {
		return userLoginLogDao.addUserLoginLog(userLoginLog);
	}

	@Override
	public Page getUserLoginLog(int currentPage, int numPerPage, int totalRow,
			String sort, boolean isAsc, UserLoginLog userLoginLog) {
		if(!StringUtils.hasLength(sort)){
			sort="LOGIN_TIME";
			isAsc=false;
		}
		return userLoginLogDao.getUserLoginLog(currentPage, numPerPage, totalRow, sort, isAsc, userLoginLog);
	}

	@Override
	public int clearUserLoginLog(UserLoginLog userLoginLog) {
		return userLoginLogDao.clearUserLoginLog(userLoginLog);
	}
	
}
