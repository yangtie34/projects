package com.jhnu.syspermiss.task.service;

import com.jhnu.syspermiss.task.entity.PlanLogDetails;


public interface PlanLogDetailsService {
	
	public void updateOrInsert(PlanLogDetails planLogDetails);
	
	public boolean isCheck(String id);
}
