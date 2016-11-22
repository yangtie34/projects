package com.jhnu.syspermiss.task.dao;

import com.jhnu.syspermiss.task.entity.PlanLogDetails;


public interface PlanLogDetailsDao {
	
	public void updateOrInsert(PlanLogDetails planLogDetails);
	
	public boolean isCheck(String id);
	
}
