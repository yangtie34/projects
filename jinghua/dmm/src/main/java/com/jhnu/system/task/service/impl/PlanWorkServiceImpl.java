package com.jhnu.system.task.service.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.dao.PlanWorkDao;
import com.jhnu.system.task.entity.PlanWork;
import com.jhnu.system.task.service.PlanWorkService;
import com.jhnu.system.task.service.WorkService;
@Service("taskPlanWorkService")
public class PlanWorkServiceImpl implements PlanWorkService {
	@Autowired
	private PlanWorkDao planWorkDao;
	@Autowired
	private WorkService workService;
	private static final Logger logger = Logger.getLogger(PlanWorkServiceImpl.class);
	@Override
	public Page getPlanWorks(int currentPage, int numPerPage) {
		return planWorkDao.getPlanWorks(currentPage,numPerPage);
	}

	@Override
	public PlanWork getPlanWorkById(String id) {
		return planWorkDao.getPlanWorkById(id);
	}

	@Override
	public Page getPlanWorksByPlanId(int currentPage, int numPerPage,String planId) {//计划
		return planWorkDao.getPlanWorksByPlanId(currentPage,numPerPage,planId);
	}

	@Override
	public boolean updateOrInsert(PlanWork plan)  throws ParamException{
		if(!planWorkDao.checkPlanWorksByOrder(plan)){
			planWorkDao.updatePlanWorksByOrder(plan);
		}
		return planWorkDao.updateOrInsert(plan);
	}

	@Override
	public boolean del(PlanWork plan) {
		return planWorkDao.del(plan);
	}
	@Override
	public String insertReturnId(PlanWork plan) {
		return planWorkDao.insertReturnId(plan);
	}

	@Override
	public List<PlanWork> getPlanWorksByPlanId(String planId) {
		return planWorkDao.getPlanWorksByPlanId(planId);
	}

	@Override
	public Page getWorksByPlanId(int currentPage, int numPerPage, String planId) {
		Page page=getPlanWorksByPlanId(currentPage, numPerPage, planId);
		List listWork=new LinkedList();
		List<?> list=page.getResultListObject();
		Map map=new HashMap();
		for(int i=0;i<list.size();i++){
			if(list.get(i)!=null){
			PlanWork pw=(PlanWork) list.get(i);
			listWork.add(workService.getWorkById(pw.getWorkId()));
			map.put(pw.getWorkId()+i, pw.getOrder_());
			}
		}
		page.setResultListObject(listWork);
		map.put("list", list);
		List listWork1=new LinkedList();
		listWork1.add(map);
		page.setResultList(listWork1);
		return page;
	}
}
