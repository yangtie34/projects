package com.jhnu.system.log.dao;

import com.jhnu.system.common.page.Page;
import com.jhnu.system.log.entity.UserLoginLog;

public interface UserLoginLogDao {

	
	public UserLoginLog addUserLoginLog(UserLoginLog userLoginLog);
	
	public Page getUserLoginLog(int currentPage,int numPerPage,int totalRow,String sort,boolean isAsc,UserLoginLog userLoginLog);
	
	public int clearUserLoginLog(UserLoginLog userLoginLog);
	
}
