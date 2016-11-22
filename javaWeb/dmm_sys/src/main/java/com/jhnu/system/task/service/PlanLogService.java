package com.jhnu.system.task.service;

import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.entity.Plan;
import com.jhnu.system.task.entity.PlanLog;

public interface PlanLogService {
	
	public Page getPlanLogs(Plan plan,int currentPage, int numPerPage);
	
	public PlanLog getPlanLogById(String id);
	
	public boolean updateOrInsert(PlanLog planLog) throws ParamException;
	
	public boolean del(PlanLog planLog);

	Page getPlanLogByPlanId(int currentPage, int numPerPage,String planId);
	public String insertReturnId(PlanLog plan);

	boolean delAll(Plan plan);
	
	public void updateRuningToError(PlanLog plan);
}
