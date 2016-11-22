package com.jhnu.system.task.dao;

import java.util.List;

import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.entity.Plan;

public interface TaskBaseDao {

	public String getAId();

	Page getObjs(int currentPage, int numPerPage,String sql, Object obj);

	boolean excute(String sql);

	Object getObjs(String sql, Object obj);
	
	//public String getAId();
}
