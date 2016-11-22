package com.jhnu.system.task.service;

import java.util.List;

import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.entity.PlanWork;

public interface PlanWorkService {
	
	public Page getPlanWorks(int currentPage, int numPerPage);
	
	public PlanWork getPlanWorkById(String id);
	
	public boolean updateOrInsert(PlanWork planWork) throws ParamException;
	
	public boolean del(PlanWork planWork);

	Page getPlanWorksByPlanId(int currentPage, int numPerPage,String planId);
	Page getWorksByPlanId(int currentPage, int numPerPage,String planId);
	public String insertReturnId(PlanWork plan);

	public List<PlanWork> getPlanWorksByPlanId(String planId);
}
