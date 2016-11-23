package com.jhnu.system.task.service;

import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.entity.PlanLog;

public interface PlanLogService {
	
	public Page getPlanLogs(int currentPage, int numPerPage);
	
	public PlanLog getPlanLogById(String id);
	
	public boolean updateOrInsert(PlanLog planLog) throws ParamException;
	
	public boolean del(PlanLog planLog);

	Page getPlanLogByPlanId(int currentPage, int numPerPage,String planId);
	public String insertReturnId(PlanLog plan);
}
