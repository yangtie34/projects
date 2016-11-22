package com.jhnu.system.task.service;

import java.util.List;

import com.jhnu.framework.exception.handle.HasOneException;
import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.entity.Plan;

public interface PlanService {
	
	public Page getPlans(int currentPage, int numPerPage);
	
	public Plan getPlanById(String id);
	
	public Page getPlanByName(int currentPage, int numPerPage,String name);
	
	public boolean updateOrInsert(Plan plan) throws ParamException, HasOneException;
	
	public boolean del(Plan plan);
	
	public boolean start(String id);
	
	public boolean startNow(String id);
	
	public String insertReturnId(Plan plan);

	boolean stop(String id);

	List<Plan> getPlans();
}
