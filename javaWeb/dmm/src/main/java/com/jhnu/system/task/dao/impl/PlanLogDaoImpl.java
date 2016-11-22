package com.jhnu.system.task.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.dao.PlanLogDao;
import com.jhnu.system.task.entity.PlanLog;
import com.jhnu.system.task.entity.PlanLogDetails;
@Repository("taskPlanLogDao")
public class PlanLogDaoImpl extends TaskBaseDaoImpl implements PlanLogDao {

	@Override
	public Page getPlanLogs(int currentPage, int numPerPage) {
		String sql=sqlByBean.getQuerySql(new PlanLog());
		return getObjs(currentPage,numPerPage,sql+" order by STARTTIME desc", new PlanLog());
	}

	@Override
	public PlanLog getPlanLogById(String id) {
		PlanLog plan=new PlanLog();
		plan.setId(id);
		String sql=sqlByBean.getQuerySqlByIdOrder(plan);
		return (PlanLog) getObjs(sql, new PlanLog()).get(0);
	}

	@Override
	public Page getPlanLogByPlanId(int currentPage, int numPerPage,String planId) {
		String sql="select * from T_SYS_SCHEDULE_PLAN_LOG where planid='"+planId+"'  order by STARTTIME desc";
		return getObjs(currentPage,numPerPage,sql, new PlanLog());
	}

	@Override
	public boolean updateOrInsert(PlanLog plan) {
		if(plan.getId()==null)plan.setId(getAId());
		String sql=sqlByBean.getUpdateSql(plan);
		return excute(sql);
	}

	@Override
	public boolean del(PlanLog plan) {
		String sql=sqlByBean.getDelSql(plan);
		return excute(sql);
	}
	@Override
	public String insertReturnId(PlanLog plan) {
		if(plan.getId()==null)plan.setId(getAId());
		updateOrInsert(plan);
		return plan.getId();
	}
}
