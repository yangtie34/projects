package com.jhnu.system.task.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jhnu.framework.exception.handle.HasOneException;
import com.jhnu.framework.exception.param.EmptyParamException;
import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.permiss.entity.Role;
import com.jhnu.system.permiss.service.impl.RoleServiceImpl;
import com.jhnu.system.task.dao.PlanLogDetailsDao;
import com.jhnu.system.task.entity.PlanLogDetails;
import com.jhnu.system.task.service.PlanLogDetailsService;
@Service("taskPlanLogDetailsService")
public class PlanLogDetailsServiceImpl implements PlanLogDetailsService {
	@Autowired
	private PlanLogDetailsDao planLogDetailsDao;
	
	private static final Logger logger = Logger.getLogger(PlanLogDetailsServiceImpl.class);
	@Override
	public Page getPlanLogDetailss(int currentPage, int numPerPage) {
		return planLogDetailsDao.getPlanLogDetailss(currentPage,numPerPage);
	}

	@Override
	public PlanLogDetails getPlanLogDetailsById(String id) {
		return planLogDetailsDao.getPlanLogDetailsById(id);
	}

	@Override
	public Page getWorkPlanLogDetailsByPlanLogId(int currentPage, int numPerPage,String planId) {
		return planLogDetailsDao.getWorkPlanLogDetailsByPlanLogId(currentPage,numPerPage,planId);
	}

	@Override
	public boolean updateOrInsert(PlanLogDetails plan) throws ParamException{
		if(plan != null){
    		if(StringUtils.isEmpty(plan.getPlanLogId())){
    			logger.error("====新增 计划日志id不能为空====");
    			throw new EmptyParamException("计划日志id不能为空");
    		}
    		logger.info("====新增角色结束====");
    	}
		return planLogDetailsDao.updateOrInsert(plan);
	}

	@Override
	public boolean del(PlanLogDetails plan) {
		return planLogDetailsDao.del(plan);
	}
	@Override
	public String insertReturnId(PlanLogDetails plan) {
		return planLogDetailsDao.insertReturnId(plan);
	}

	@Override
	public List getVerifyPlanLogDetailsByPlanLogId(String planLogId, String workId) {
		return planLogDetailsDao.getVerifyPlanLogDetailsByPlanLogId(planLogId,workId);
	}
}
