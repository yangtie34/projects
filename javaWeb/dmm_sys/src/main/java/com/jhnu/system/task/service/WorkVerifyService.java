package com.jhnu.system.task.service;

import java.util.List;

import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.entity.WorkVerify;

public interface WorkVerifyService {
	
	public Page getWorkVerifys(int currentPage, int numPerPage);
	
	public WorkVerify getWorkVerifyById(String id);
	
	public boolean updateOrInsert(WorkVerify workVerify) throws ParamException;
	
	public boolean del(WorkVerify workVerify);

	Page getWorkVerifysByWorkId(int currentPage, int numPerPage,String workId);
	public String insertReturnId(WorkVerify plan);

	public List<WorkVerify> getWorkVerifysByWorkId(String id);

	Page getVerifysByPlanId(int currentPage, int numPerPage, String workId);
}
