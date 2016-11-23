package com.jhnu.system.task.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import com.jhnu.framework.entity.ResultBean;
import com.jhnu.framework.exception.handle.HasOneException;
import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.dao.PlanDao;
import com.jhnu.system.task.entity.Plan;
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
	public Page getPlans(int currentPage, int numPerPage,Plan plan) {
		return planDao.getPlans(currentPage,numPerPage,plan);
	}
	@Override
	public Page getPlans(int currentPage, int numPerPage) {
		return planDao.getPlans(currentPage,numPerPage,new Plan());
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
	@Transactional(rollbackFor = { Exception.class }) 
	public ResultBean updateOrInsert(Plan plan)  throws ParamException, HasOneException{
		ResultBean rb = new ResultBean();
		rb.setTrue(false);
		if(plan != null){
    		if(StringUtils.isEmpty(plan.getName_())){
    			logger.error("====新增计划 计划名称不能为空====");
    			rb.setName("计划名称不能为空");
    			return rb;
    		}else if(plan.getId()==null){
            	List<Plan> rl = (List<Plan>) planDao.getPlanByName(1, 1, plan.getName_()).getResultListObject();
            	for(int i=0;i<rl.size();i++){
            		if(plan.getName_().equals(rl.get(i).getName_())){
            			logger.error("====新增计划计划名称不能重复====");
                		rb.setName("计划名称不能重复");
                		return rb;
            		}
            	}
    		}
    		plan =planDao.updateOrInsert(plan);
    		if(!start(plan.getId())) {
				logger.error("====表达式错误====");
				rb.setName("表达式错误");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动回滚事务：
				return rb;
			}
    		if(plan.getIsTrue()==1){
    		}else{
    			stop(plan.getId());
    		}
    		logger.info("====新增计划结束====");
    		rb.setTrue(true);
    	}
		return rb;
	}

	@Override
	public boolean del(Plan plan) {
		stop(plan.getId());
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
		}catch (RuntimeException e) {
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
