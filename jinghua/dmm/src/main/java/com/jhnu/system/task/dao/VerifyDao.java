package com.jhnu.system.task.dao;

import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.entity.Verify;

public interface VerifyDao {
	
	public Page getVerifys(int currentPage, int numPerPage);
	
	public Verify getVerifyById(String id);
	
	public Page getVerifysByName(int currentPage, int numPerPage,String name);
	
	public boolean updateOrInsert(Verify verify);
	
	public boolean del(Verify verify);
	public String insertReturnId(Verify plan);
}
