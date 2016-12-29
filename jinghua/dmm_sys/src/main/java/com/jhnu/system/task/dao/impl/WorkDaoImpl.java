package com.jhnu.system.task.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.dao.WorkDao;
import com.jhnu.system.task.entity.Work;
@Repository("taskWorkDao")
public class WorkDaoImpl extends TaskBaseDaoImpl implements WorkDao {
	@Override
	public Page getWorks(int currentPage, int numPerPage,Work work) {
		String sql=sqlByBean.getQuerySql(new Work())+" where 1=1 ";
		
		if(work.getName_()!=null){
			sql+=" and (name_ like '%"+work.getName_()+"%' or SERVICE like '%"+work.getName_()+"%') " ;
		}
		if(work.getGroup_()!=null){
			sql+=" and GROUP_='"+work.getGroup_()+"' ";
		}
		if(work.getIsTrue()!=null){
			sql+=" and ISTRUE="+work.getIsTrue();
		}
		return getObjs(currentPage,numPerPage,sql, new Work());
	}

	@Override
	public Work getWorkById(String id) {
		Work plan=new Work();
		plan.setId(id);
		String sql=sqlByBean.getQuerySqlById(plan);
		List l=getObjs(sql, new Work());
		if(l.size()>0){
			return (Work) l.get(0);
		}
		return null;
	}

	@Override
	public Page getWorkByName(int currentPage, int numPerPage,String name) {
		Work plan=new Work();
		plan.setName_(name);
		String sql=sqlByBean.getQuerySqlByName_(plan);
		return getObjs(currentPage,numPerPage,sql, new Work());
	}

	@Override
	public boolean updateOrInsert(Work plan) {
		if(plan.getId()==null)plan.setId(getAId());
		String sql=sqlByBean.getUpdateSql(plan);
		return excute(sql);
	}

	@Override
	public boolean del(Work plan) {
		String sql=sqlByBean.getDelSql(plan);
		return excute(sql);
	}
	@Override
	public String insertReturnId(Work plan) {
		if(plan.getId()==null)plan.setId(getAId());
		updateOrInsert(plan);
		return plan.getId();
	}
}
