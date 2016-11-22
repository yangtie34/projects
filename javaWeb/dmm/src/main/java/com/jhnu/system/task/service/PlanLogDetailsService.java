package com.jhnu.system.task.service;

import java.util.List;

import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.entity.PlanLogDetails;

public interface PlanLogDetailsService {
	
	public Page getPlanLogDetailss(int currentPage, int numPerPage);
	
	public PlanLogDetails getPlanLogDetailsById(String id);
	
	public boolean updateOrInsert(PlanLogDetails planLogDetails) throws ParamException;
	
	public boolean del(PlanLogDetails planLogDetails);

	Page getWorkPlanLogDetailsByPlanLogId(int currentPage, int numPerPage,String planLogId);
	public String insertReturnId(PlanLogDetails plan);
	List getVerifyPlanLogDetailsByPlanLogId(String planLogId, String workId);
}
