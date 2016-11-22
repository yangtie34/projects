package com.jhnu.system.task.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.dao.VerifyDao;
import com.jhnu.system.task.entity.Verify;
@Repository("taskVerifyDao")
public class VerifyDaoImpl extends TaskBaseDaoImpl implements VerifyDao {

	@Override
	public Page getVerifys(int currentPage, int numPerPage,Verify verify) {
		String sql=sqlByBean.getQuerySql(new Verify())+" where 1=1 ";
		
		if(verify.getName_()!=null){
			sql+=" and name_ like '%"+verify.getName_()+"%' or SERVICE like '%"+verify.getName_()+"%' ";
		}
		if(verify.getGroup_()!=null){
			sql+=" and GROUP_='"+verify.getGroup_()+"' ";
		}
		if(verify.getIsTrue()!=null){
			sql+=" and ISTRUE="+verify.getIsTrue();
		}
		return getObjs(currentPage,numPerPage,sql, new Verify());
	}

	@Override
	public Verify getVerifyById(String id) {
		Verify plan=new Verify();
		plan.setId(id);
		String sql=sqlByBean.getQuerySqlById(plan);
		List l=getObjs(sql, new Verify());
		if(l.size()>0){
			return (Verify) l.get(0);
		}
		return null;
	}

	@Override
	public Page getVerifysByName(int currentPage, int numPerPage,String name) {
		Verify plan=new Verify();
		plan.setName_(name);
		String sql=sqlByBean.getQuerySqlByName_(plan);
		return getObjs(currentPage,numPerPage,sql, new Verify());
	}

	@Override
	public boolean updateOrInsert(Verify plan) {
		if(plan.getId()==null)plan.setId(getAId());
		String sql=sqlByBean.getUpdateSql(plan);
		return excute(sql);
	}

	@Override
	public boolean del(Verify plan) {
		String sql=sqlByBean.getDelSql(plan);
		return excute(sql);
	}
	@Override
	public String insertReturnId(Verify plan) {
		if(plan.getId()==null)plan.setId(getAId());
		updateOrInsert(plan);
		return plan.getId();
	}
}
