package com.jhnu.system.task.dao;

import java.util.List;

import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.entity.PlanWork;

public interface PlanWorkDao {
	
	public Page getPlanWorks(int currentPage, int numPerPage);
	
	public PlanWork getPlanWorkById(String id);
	
	public Page getPlanWorksByPlanId(int currentPage, int numPerPage,String planId);
	
	public boolean updateOrInsert(PlanWork planWork);
	
	public boolean del(PlanWork planWork);
	public String insertReturnId(PlanWork plan);

	public List<PlanWork> getPlanWorksByPlanId(String planId);

	boolean updatePlanWorksByOrder(PlanWork plan);

	boolean checkPlanWorksByOrder(PlanWork plan);
}
