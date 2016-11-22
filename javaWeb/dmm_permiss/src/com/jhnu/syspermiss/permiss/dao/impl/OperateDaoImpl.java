package com.jhnu.syspermiss.permiss.dao.impl;


import java.util.List;

import com.jhnu.syspermiss.permiss.dao.OperateDao;
import com.jhnu.syspermiss.permiss.entity.Operate;
import com.jhnu.syspermiss.util.StringUtils;
import com.jhnu.syspermiss.util.jdbcUtil.BaseDao;

public class OperateDaoImpl implements OperateDao {
	private BaseDao baseDao=BaseDao.getInstance();
	private OperateDaoImpl() {
		
	}  
    private static OperateDaoImpl OperateDaoImpl=null;
	
	public static OperateDaoImpl getInstance() {
		if (OperateDaoImpl == null){
			synchronized (new String()) {
				if (OperateDaoImpl == null){
					OperateDaoImpl = new OperateDaoImpl();
				}
			}
		}
		return OperateDaoImpl;
	}
	@SuppressWarnings({ "unchecked" })
	@Override
	public Operate findById(Long id) {
		String sql = "select * from t_sys_operate t where t.id = ?";
		List<Operate> olist=baseDao.query(sql.toString(),Operate.class,new Object[]{id});
		return olist.size()==0?null:olist.get(0);
	}


	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Operate> findOperateByThis(Operate operate) {
		StringBuffer sql = new StringBuffer("select * from t_sys_operate t where 1=1");
		if(operate != null){
			if(operate.getId() != null){
				sql.append(" and t.id = "+operate.getId());
			}
			if(StringUtils.hasLength(operate.getName_())){
				sql.append(" and t.name_='"+ operate.getName_() +"'");
			}
			if(StringUtils.hasLength(operate.getDescription())){
				sql.append(" and t.description like '%"+ operate.getDescription() +"'%");
			}
		}
		return baseDao.query(sql.toString(),Operate.class);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Operate> findAll() {
		String sql = "select * from t_sys_operate t";
		return baseDao.query(sql,Operate.class);
	}


	@Override
	public Operate createOperate(Operate operate) {
		final String SQL = "insert into t_sys_operate values(?,?,?)";
		final long ID=baseDao.getSeq();
		baseDao.excute(SQL, new Object[]{ID,operate.getName_(),operate.getDescription()});
        operate.setId(ID);
        return operate;
	}

	@Override
	public void deleteOperate(Long operateId) {
		String sql = "delete t_sys_operate t where t.id = ?";
		baseDao.excute(sql,new Object[]{operateId});
	}

	@Override
	public void updateOperate(Operate operate) {
		String sql = "update t_sys_operate t set t.name_ = ? ,t.description = ? where t.id = ?";
		baseDao.excute(sql,new Object[]{operate.getName_(),operate.getDescription(),operate.getId()});
	}

}
