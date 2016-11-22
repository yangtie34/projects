package com.jhnu.system.task.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.dao.PlanWorkDao;
import com.jhnu.system.task.entity.PlanLog;
import com.jhnu.system.task.entity.PlanWork;
@Repository("taskPlanWorkDao")
public class PlanWorkDaoImpl extends TaskBaseDaoImpl implements PlanWorkDao {

	@Override
	public Page getPlanWorks(int currentPage, int numPerPage) {
		String sql=sqlByBean.getQuerySql(new PlanWork());
		return getObjs(currentPage,numPerPage,sql, new PlanWork());
	}

	@Override
	public PlanWork getPlanWorkById(String planId) {
		PlanWork plan=new PlanWork();
		plan.setId(planId);
		String sql=sqlByBean.getQuerySqlById(plan);
		return (PlanWork) getObjs(sql, new PlanWork());
	}

	@Override
	public Page getPlanWorksByPlanId(int currentPage, int numPerPage,String planId) {//计划名称
		String sql="select * from T_SYS_SCHEDULE_PLAN_WORK where planid='"+planId+"'  order by order_";
		return getObjs(currentPage,numPerPage,sql, new PlanWork());
	}
	@Override
	public boolean checkPlanWorksByOrder(PlanWork plan) {//排序是否重复 
		String sql="select * from T_SYS_SCHEDULE_PLAN_WORK where planid='"+plan.getPlanId()+"' and order_='"+plan.getOrder_()+"'";
		return getObjs(sql, new PlanWork()).size()==0;
	}
	@Override
	public boolean updatePlanWorksByOrder(PlanWork plan) {//插入 更新排序
		String sql="update T_SYS_SCHEDULE_PLAN_WORK set order_=to_char(to_number(order_)+1) where planid='"+plan.getPlanId()+"' and order_>='"+plan.getOrder_()+"'";
		return excute(sql);
	}
	@Override
	public boolean updateOrInsert(PlanWork plan) {
		if(plan.getId()==null)plan.setId(getAId());
		String sql=sqlByBean.getUpdateSql(plan);
		return excute(sql);
	}

	@Override
	public boolean del(PlanWork plan) {
		String sql=sqlByBean.getDelSql(plan);
		return excute(sql);
	}
	@Override
	public String insertReturnId(PlanWork plan) {
		if(plan.getId()==null)plan.setId(getAId());
		updateOrInsert(plan);
		return plan.getId();
	}

	@Override
	public List<PlanWork> getPlanWorksByPlanId(String planId) {
		String sql="select * from T_SYS_SCHEDULE_PLAN_WORK where planid='"+planId+"'  order by order_";
		return getObjs(sql, new PlanWork());
	}
}
