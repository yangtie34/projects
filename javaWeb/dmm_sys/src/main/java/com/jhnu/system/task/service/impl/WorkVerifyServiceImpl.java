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
import com.jhnu.system.task.dao.WorkVerifyDao;
import com.jhnu.system.task.entity.PlanLogDetails;
import com.jhnu.system.task.entity.PlanWork;
import com.jhnu.system.task.entity.WorkVerify;
import com.jhnu.system.task.service.VerifyService;
import com.jhnu.system.task.service.WorkVerifyService;
@Service("taskWorkVerifyService")
public class WorkVerifyServiceImpl implements WorkVerifyService {
	@Autowired
	private WorkVerifyDao workVerifyDao;
	@Autowired
	private VerifyService verifyService;
	private static final Logger logger = Logger.getLogger(WorkVerifyServiceImpl.class);
	@Override
	public Page getWorkVerifys(int currentPage, int numPerPage) {
		return workVerifyDao.getWorkVerifys(currentPage,numPerPage);
	}
	@Override
	public WorkVerify getWorkVerifyById(String workId) {
		return workVerifyDao.getWorkVerifyById(workId);
	}

	@Override
	public Page getWorkVerifysByWorkId(int currentPage, int numPerPage,String workId) {//计划名称
		return workVerifyDao.getWorkVerifysByWorkId(currentPage,numPerPage,workId);
	}

	@Override
	public boolean updateOrInsert(WorkVerify plan)  throws ParamException{
		if(!workVerifyDao.checkWorkVerifysByOrder(plan)){
			workVerifyDao.updateWorkVerifysByOrder(plan);
		}
		return workVerifyDao.updateOrInsert(plan);
	}

	@Override
	public boolean del(WorkVerify plan) {
		return workVerifyDao.del(plan);
	}
	@Override
	public String insertReturnId(WorkVerify plan) {
		return workVerifyDao.insertReturnId(plan);
	}
	@Override
	public List<WorkVerify> getWorkVerifysByWorkId(String workId) {
		return workVerifyDao.getWorkVerifysByWorkId(workId);
	}
	@Override
	public Page getVerifysByPlanId(int currentPage, int numPerPage,String workId) {//计划名称
		Page page=getWorkVerifysByWorkId(currentPage, numPerPage, workId);
		List listWork=new LinkedList();
		List<?> list=page.getResultListObject();
		Map map=new HashMap();
		for(int i=0;i<list.size();i++){
			if(list.get(i)!=null){
			WorkVerify pw=(WorkVerify) list.get(i);
			listWork.add(verifyService.getVerifyById(pw.getVerifyId()));
			map.put(pw.getVerifyId()+i, pw.getOrder_());
			}
		}
		page.setResultListObject(listWork);
		List listWork1=new LinkedList();
		map.put("list", list);
		listWork1.add(map);
		page.setResultList(listWork1);
		return page;
	}
}
