package com.jhnu.system.task.dao;

import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.entity.Work;

public interface WorkDao {
	
	public Page getWorks(int currentPage, int numPerPage,Work work);
	
	public Page getWorkByName(int currentPage, int numPerPage,String name);
	
	public boolean updateOrInsert(Work work);
	
	public boolean del(Work work);

	public Work getWorkById(String id);
	public String insertReturnId(Work plan);
}
