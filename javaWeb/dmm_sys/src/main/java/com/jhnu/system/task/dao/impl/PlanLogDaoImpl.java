package com.jhnu.system.task.dao.impl;

import org.springframework.stereotype.Repository;

import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.dao.PlanLogDao;
import com.jhnu.system.task.entity.Plan;
import com.jhnu.system.task.entity.PlanLog;
@Repository("taskPlanLogDao")
public class PlanLogDaoImpl extends TaskBaseDaoImpl implements PlanLogDao {

	@Override
	public Page getPlanLogs(String id,int currentPage, int numPerPage) {
		String sql=sqlByBean.getQuerySql(new PlanLog())+" where PLANID='"+id+"'";
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
	public void updateRuningToError(PlanLog plan) {
		String sql="update t_sys_schedule_plan_log set isyes=0 ,endtime='' where isyes=2";
		baseDao.getBaseDao().executeSql(sql);
		sql="update t_sys_schedule_plan_log_detail set isyes=0 ,endtime='',result_desc='执行中断' where isyes=2 or isyes=3 ";
		baseDao.getBaseDao().executeSql(sql);
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

	@Override
	public boolean delAll(Plan plan) {
		boolean bool=false;
		String sql="delete from T_SYS_SCHEDULE_PLAN_LOG_DETAIL where planLogId in "
				+ "(select id from T_SYS_SCHEDULE_PLAN_LOG where PLANID='"+plan.getId()+"' and isyes <> 2 )";
		bool=excute(sql);
		sql="delete from T_SYS_SCHEDULE_PLAN_LOG where PLANID='"+plan.getId()+"' and isyes <> 2 ";
		bool=excute(sql);
		return bool;
	}
}
