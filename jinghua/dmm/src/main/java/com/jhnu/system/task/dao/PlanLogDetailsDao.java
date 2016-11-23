package com.jhnu.system.task.dao;

import java.util.List;

import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.entity.PlanLogDetails;

public interface PlanLogDetailsDao {
	
	public Page getPlanLogDetailss(int currentPage, int numPerPage);
	
	public PlanLogDetails getPlanLogDetailsById(String id);
	
	public Page getWorkPlanLogDetailsByPlanLogId(int currentPage, int numPerPage,String planId);
	
	public boolean updateOrInsert(PlanLogDetails planLogDetails);
	
	public boolean del(PlanLogDetails planLogDetails);
	public String insertReturnId(PlanLogDetails plan);

	List getVerifyPlanLogDetailsByPlanLogId(String planLogId, String workId);
}
