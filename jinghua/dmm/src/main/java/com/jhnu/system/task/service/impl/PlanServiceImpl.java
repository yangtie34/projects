package com.jhnu.system.task.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jhnu.framework.exception.handle.HasOneException;
import com.jhnu.framework.exception.param.EmptyParamException;
import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.framework.task.ScheduleJob;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.permiss.entity.Role;
import com.jhnu.system.task.dao.PlanDao;
import com.jhnu.system.task.entity.Plan;
import com.jhnu.system.task.entity.PlanLogDetails;
import com.jhnu.system.task.service.PlanService;
import com.jhnu.system.task.service.TaskBaseService;
@Service("taskPlanService")
public class PlanServiceImpl implements PlanService {
	@Autowired
	private PlanDao planDao;
	private static final Logger logger = Logger.getLogger(PlanServiceImpl.class);
	@Autowired
	private TaskBaseService taskBaseService;
	@Override
	public Page getPlans(int currentPage, int numPerPage) {
		return planDao.getPlans(currentPage,numPerPage);
	}
	@Override
	public List<Plan> getPlans() {
		return planDao.getPlans();
	}
	@Override
	public Plan getPlanById(String id) {
		return planDao.getPlanById(id);
	}

	@Override
	public Page getPlanByName(int currentPage, int numPerPage,String name) {
		return planDao.getPlanByName(currentPage,numPerPage,name);
	}

	@Override
	public boolean updateOrInsert(Plan plan)  throws ParamException, HasOneException{
		boolean ret=false;
		if(plan != null){
    		if(StringUtils.isEmpty(plan.getName_())){
    			logger.error("====新增计划 计划名称不能为空====");
    			throw new EmptyParamException("计划名称不能为空");
    		}else{
            	List rl = planDao.getPlanByName(1, 1, plan.getName_()).getResultList();
            	if(rl!=null && rl.size()>0){
            		logger.error("====新增计划计划名称不能重复====");
            		throw new HasOneException("计划名称不能重复");
            	}
    		}
    		ret=planDao.updateOrInsert(plan);
    		if(plan.getIsTrue()==1){
    			start(plan.getId());
    		}else{
    			stop(plan.getId());
    		}
    		logger.info("====新增计划结束====");
    	}
		return ret;
		
	}

	@Override
	public boolean del(Plan plan) {
		return planDao.del(plan);
	}

	@Override
	public boolean start(String id) {
		// TODO Auto-generated method stub
		Plan plan=getPlanById(id);
		try {
			taskBaseService.updateOrInsert(plan);
		} catch (SchedulerException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@Override
	public boolean stop(String id) {
		// TODO Auto-generated method stub
		Plan plan=getPlanById(id);
		try {
			taskBaseService.stopJob(plan);
		} catch (SchedulerException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@Override
	public String insertReturnId(Plan plan) {
		return planDao.insertReturnId(plan);
	}

	@Override
	public boolean startNow(String id) {
		Plan plan=getPlanById(id);
		try {
			taskBaseService.runJob(plan);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
