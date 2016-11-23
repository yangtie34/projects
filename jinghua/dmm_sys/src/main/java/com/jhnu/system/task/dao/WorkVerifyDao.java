package com.jhnu.system.task.dao;

import java.util.List;

import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.entity.WorkVerify;

public interface WorkVerifyDao {
	
	public Page getWorkVerifys(int currentPage, int numPerPage);
	
	public WorkVerify getWorkVerifyById(String id);
	
	public Page getWorkVerifysByWorkId(int currentPage, int numPerPage,String workId);
	
	public boolean updateOrInsert(WorkVerify workVerify);
	
	public boolean del(WorkVerify workVerify);
	public String insertReturnId(WorkVerify plan);

	public List<WorkVerify> getWorkVerifysByWorkId(String workId);

	boolean updateWorkVerifysByOrder(WorkVerify plan);

	boolean checkWorkVerifysByOrder(WorkVerify plan);
}
