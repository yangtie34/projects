package com.jhnu.syspermiss.task.service.impl;

import com.jhnu.syspermiss.task.dao.PlanLogDetailsDao;
import com.jhnu.syspermiss.task.dao.impl.PlanLogDaoDetailsImpl;
import com.jhnu.syspermiss.task.entity.PlanLogDetails;
import com.jhnu.syspermiss.task.service.PlanLogDetailsService;


public class PlanLogDetailsServiceImpl implements PlanLogDetailsService {
	
	private PlanLogDetailsServiceImpl() {
		
	}  
    private static PlanLogDetailsServiceImpl planLogDetailsServiceImpl=null;
	
	public static PlanLogDetailsServiceImpl getInstance() {
		if (planLogDetailsServiceImpl == null){
			synchronized (new String()) {
				if (planLogDetailsServiceImpl == null){
					planLogDetailsServiceImpl = new PlanLogDetailsServiceImpl();
				}
			}
		}
		return planLogDetailsServiceImpl;
	}
	
	private PlanLogDetailsDao PlanLogDetailsDao=PlanLogDaoDetailsImpl.getInstance();

	@Override
	public void updateOrInsert(PlanLogDetails planLogDetails) {
		PlanLogDetailsDao.updateOrInsert(planLogDetails);
	}

	@Override
	public boolean isCheck(String id) {
		return PlanLogDetailsDao.isCheck(id);
	}
	
}
