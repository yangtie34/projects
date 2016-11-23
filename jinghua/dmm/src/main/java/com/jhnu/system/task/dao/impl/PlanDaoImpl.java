package com.jhnu.system.task.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.dao.PlanDao;
import com.jhnu.system.task.entity.Plan;
@Repository("taskPlanDao")
public class PlanDaoImpl extends TaskBaseDaoImpl implements PlanDao {

	@Override
	public Page getPlans(int currentPage, int numPerPage) {
		String sql=sqlByBean.getQuerySql(new Plan());
		return getObjs(currentPage,numPerPage,sql, new Plan());
	}
	@Override
	public List<Plan> getPlans() {
		String sql=sqlByBean.getQuerySql(new Plan());
		return getObjs(sql, new Plan());
	}

	@Override
	public Plan getPlanById(String id) {
		Plan plan=new Plan();
		plan.setId(id);
		String sql=sqlByBean.getQuerySqlById(plan);
		return (Plan) getObjs(sql, new Plan()).get(0);
	}

	@Override
	public Page getPlanByName(int currentPage, int numPerPage,String name) {
		Plan plan=new Plan();
		plan.setName_(name);
		String sql=sqlByBean.getQuerySqlByName_(plan);
		return (Page) getObjs(currentPage, numPerPage ,sql, new Plan());
	}

	@Override
	public boolean updateOrInsert(Plan plan) {
		if(plan.getId()==null)plan.setId(getAId());
		String sql=sqlByBean.getUpdateSql(plan);
		return excute(sql);
	}

	@Override
	public boolean del(Plan plan) {
		String sql=sqlByBean.getDelSql(plan);
		return excute(sql);
	}

	@Override
	public String insertReturnId(Plan plan) {
		if(plan.getId()==null)plan.setId(getAId());
		updateOrInsert(plan);
		return plan.getId();
	}

}
