package com.jhnu.system.task.dao;

import java.util.List;

import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.entity.Plan;

public interface PlanDao {
	
	public Page getPlans(int currentPage, int numPerPage,Plan plan);
	
	public Plan getPlanById(String id);
	
	public Page getPlanByName(int currentPage, int numPerPage,String name);
	
	public Plan updateOrInsert(Plan plan);
	
	public boolean del(Plan plan);
	
	public String insertReturnId(Plan plan);

	List<Plan> getPlans();
	
}
