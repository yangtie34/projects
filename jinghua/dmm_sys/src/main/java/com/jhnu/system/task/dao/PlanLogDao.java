package com.jhnu.system.task.dao;

import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.entity.Plan;
import com.jhnu.system.task.entity.PlanLog;

public interface PlanLogDao {
	
	public Page getPlanLogs(String id,int currentPage, int numPerPage);
	
	public PlanLog getPlanLogById(String id);
	
	public Page getPlanLogByPlanId(int currentPage, int numPerPage,String planId);
	
	public boolean updateOrInsert(PlanLog planLog);
	
	public boolean del(PlanLog planLog);
	public String insertReturnId(PlanLog plan);

	public boolean delAll(Plan plan);
	
	public void updateRuningToError(PlanLog plan);
}
