package com.jhnu.system.log.dao;

import com.jhnu.system.common.page.Page;
import com.jhnu.system.log.entity.ChangePwdLog;

public interface ChangePwdLogDao {

	
	public ChangePwdLog addChangePwdLog(ChangePwdLog changePwdLog);
	
	public Page getChangePwdLog(int currentPage,int numPerPage,int totalRow,String sort,boolean isAsc,ChangePwdLog changePwdLog);
	
	public int clearChangePwdLog(ChangePwdLog changePwdLog);
	
}
