package com.jhnu.system.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.dao.PlanLogDao;
import com.jhnu.system.task.entity.Plan;
import com.jhnu.system.task.entity.PlanLog;
import com.jhnu.system.task.service.PlanLogService;
@Service("taskPlanLogService")
public class PlanLogServiceImpl implements PlanLogService {
	@Autowired
	private PlanLogDao planLogDao;
	
	@Override
	public Page getPlanLogs(Plan plan,int currentPage, int numPerPage) {
		return planLogDao.getPlanLogs(plan.getId(),currentPage,numPerPage);
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
	public boolean delAll(Plan plan) {
		return planLogDao.delAll(plan);
	}
	@Override
	public String insertReturnId(PlanLog plan) {
		return planLogDao.insertReturnId(plan);
	}
	@Override
	public void updateRuningToError(PlanLog plan){
		planLogDao.updateRuningToError(plan);
	}
}
