package com.jhnu.system.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jhnu.system.common.page.Page;
import com.jhnu.system.log.dao.ChangePwdLogDao;
import com.jhnu.system.log.entity.ChangePwdLog;
import com.jhnu.system.log.service.ChangePwdLogService;

@Service("changePwdLogService")
public class ChangePwdLogServiceImpl implements ChangePwdLogService{
	@Autowired
	private ChangePwdLogDao changePwdLogDao;

	@Override
	public ChangePwdLog addChangePwdLog(ChangePwdLog changePwdLog) {
		return changePwdLogDao.addChangePwdLog(changePwdLog);
	}

	@Override
	public Page getChangePwdLog(int currentPage, int numPerPage, int totalRow,
			String sort, boolean isAsc, ChangePwdLog changePwdLog) {
		if(!StringUtils.hasLength(sort)){
			sort="EXC_TIME";
			isAsc=false;
		}
		return changePwdLogDao.getChangePwdLog(currentPage, numPerPage, totalRow, sort, isAsc, changePwdLog);
	}

	@Override
	public int clearChangePwdLog(ChangePwdLog changePwdLog) {
		return changePwdLogDao.clearChangePwdLog(changePwdLog);
	}
	
}
