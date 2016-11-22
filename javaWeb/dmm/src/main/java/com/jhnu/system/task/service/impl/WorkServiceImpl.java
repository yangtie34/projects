package com.jhnu.system.task.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.dao.WorkDao;
import com.jhnu.system.task.entity.PlanLogDetails;
import com.jhnu.system.task.entity.Work;
import com.jhnu.system.task.service.WorkService;
@Service("taskWorkService")
public class WorkServiceImpl implements WorkService {
	@Autowired
	private WorkDao workDao;
	private static final Logger logger = Logger.getLogger(WorkServiceImpl.class);
	@Override
	public Page getWorks(int currentPage, int numPerPage) {
		return workDao.getWorks(currentPage,numPerPage);
	}

	@Override
	public Work getWorkById(String id) {
		return workDao.getWorkById(id);
	}

	@Override
	public Page getWorkByName(int currentPage, int numPerPage,String name) {
		return workDao.getWorkByName(currentPage,numPerPage,name);
	}

	@Override
	public boolean updateOrInsert(Work plan)  throws ParamException{
		return workDao.updateOrInsert(plan);
	}

	@Override
	public boolean del(Work plan) {
		return workDao.del(plan);
	}
	@Override
	public String insertReturnId(Work plan) {
		return workDao.insertReturnId(plan);
	}
}
