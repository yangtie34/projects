package com.jhnu.system.task.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.product.manager.scientific.dao.TCodeDao;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.service.PlanLogService;
import com.jhnu.system.task.dao.PlanLogDao;
import com.jhnu.system.task.entity.PlanLog;
import com.jhnu.system.task.entity.PlanLogDetails;
@Service("taskPlanLogService")
public class PlanLogServiceImpl implements PlanLogService {
	@Autowired
	private PlanLogDao planLogDao;
	private static final Logger logger = Logger.getLogger(PlanLogServiceImpl.class);
	@Override
	public Page getPlanLogs(int currentPage, int numPerPage) {
		return planLogDao.getPlanLogs(currentPage,numPerPage);
	}

	@Override
	public PlanLog getPlanLogById(String id) {
		return planLogDao.getPlanLogById(id);
	}

	@Override
	public Page getPlanLogByPlanId(int currentPage, int numPerPage,String planId) {
		return planLogDao.getPlanLogByPlanId(currentPage,numPerPage,planId);
	}

	@Override
	public boolean updateOrInsert(PlanLog plan)  throws ParamException{
		return planLogDao.updateOrInsert(plan);
	}

	@Override
	public boolean del(PlanLog plan) {
		return planLogDao.del(plan);
	}
	@Override
	public String insertReturnId(PlanLog plan) {
		return planLogDao.insertReturnId(plan);
	}
}
