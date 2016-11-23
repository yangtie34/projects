package com.jhnu.system.task.service;

import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.entity.Verify;

public interface VerifyService {
	
	public Page getVerifys(int currentPage, int numPerPage);
	
	public Verify getVerifyById(String id);
	
	public Page getVerifyByName(int currentPage, int numPerPage,String name);
	
	public boolean updateOrInsert(Verify verify) throws ParamException;
	
	public boolean del(Verify verify);
	public String insertReturnId(Verify plan);
}
