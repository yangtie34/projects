package com.jhnu.system.task.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.dao.WorkVerifyDao;
import com.jhnu.system.task.entity.PlanLog;
import com.jhnu.system.task.entity.PlanWork;
import com.jhnu.system.task.entity.WorkVerify;
@Repository("taskWorkVerifyDao")
public class WorkVerifyDaoImpl extends TaskBaseDaoImpl implements WorkVerifyDao {
	@Override
	public Page getWorkVerifys(int currentPage, int numPerPage) {
		String sql=sqlByBean.getQuerySql(new WorkVerify());
		return getObjs(currentPage,numPerPage,sql, new WorkVerify());
	}

	@Override
	public WorkVerify getWorkVerifyById(String id) {
		WorkVerify plan=new WorkVerify();
		plan.setId(id);
		String sql=sqlByBean.getQuerySqlByIdOrder(plan);
		return (WorkVerify) getObjs(sql, new WorkVerify()).get(0);
	}

	@Override
	public Page getWorkVerifysByWorkId(int currentPage, int numPerPage,String workId) {//计划id
		String sql="select * from T_SYS_SCHEDULE_WORK_VERIFY where workid='"+workId+"'  order by order_";
		return  getObjs(currentPage,numPerPage,sql, new WorkVerify());
	}
	@Override
	public boolean checkWorkVerifysByOrder(WorkVerify plan) {//排序是否重复 
		String sql="select * from T_SYS_SCHEDULE_WORK_VERIFY where workid='"+plan.getWorkId()+"' and order_='"+plan.getOrder_()+"'";
		return getObjs(sql, new PlanWork()).size()==0;
	}
	@Override
	public boolean updateWorkVerifysByOrder(WorkVerify plan) {//插入 更新排序
		String sql="update T_SYS_SCHEDULE_WORK_VERIFY set order_=to_char(to_number(order_)+1) where workid='"+plan.getWorkId()+"' and order_>='"+plan.getOrder_()+"'";
		return excute(sql);
	}
	@Override
	public boolean updateOrInsert(WorkVerify plan) {
		if(plan.getId()==null)plan.setId(getAId());
		String sql=sqlByBean.getUpdateSql(plan);
		return excute(sql);
	}

	@Override
	public boolean del(WorkVerify plan) {
		String sql=sqlByBean.getDelSql(plan);
		return excute(sql);
	}
	@Override
	public String insertReturnId(WorkVerify plan) {
		if(plan.getId()==null)plan.setId(getAId());
		updateOrInsert(plan);
		return plan.getId();
	}

	@Override
	public List<WorkVerify> getWorkVerifysByWorkId(String workId) {
		String sql="select * from T_SYS_SCHEDULE_WORK_VERIFY where workid='"+workId+"'  order by order_";
		return  getObjs(sql, new WorkVerify());
	}
}
