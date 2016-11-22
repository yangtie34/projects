package com.jhnu.system.task.service;

import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.entity.Work;

public interface WorkService {
	
	public Page getWorks(int currentPage, int numPerPage,Work work);
	public Page getWorks(int currentPage, int numPerPage);
	
	public Work getWorkById(String id);
	
	public Page getWorkByName(int currentPage, int numPerPage,String name);
	
	public boolean updateOrInsert(Work work) throws ParamException;
	
	public boolean del(Work work);
	public String insertReturnId(Work plan);
}
