package com.jhnu.system.permiss.dao.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.system.permiss.dao.OperateDao;
import com.jhnu.system.permiss.entity.Operate;

@Repository("operateDao")
public class OperateDaoImpl implements OperateDao {
	@Autowired
	private BaseDao baseDao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Operate findById(Long id) {
		String sql = "select * from t_sys_operate t where t.id = ?";
		List<Operate> olist=baseDao.getBaseDao().getJdbcTemplate().query(sql.toString(), (RowMapper) new BeanPropertyRowMapper(Operate.class),new Object[]{id});
		return olist.size()==0?null:olist.get(0);
	}

	@Override
	public Operate createOperate(Operate operate) {
		final String SQL = "insert into t_sys_operate values(?,?,?)";
		final long ID=baseDao.getBaseDao().getSeqGenerator().nextLongValue();
		baseDao.getBaseDao().getJdbcTemplate().update(SQL, new Object[]{ID,operate.getName_(),operate.getDescription()});
        operate.setId(ID);
        return operate;
	}

	@Override
	public void deleteOperate(Long operateId) {
		String sql = "delete t_sys_operate t where t.id = ?";
		baseDao.getBaseDao().getJdbcTemplate().update(sql,operateId);
	}

	@Override
	public void updateOperate(Operate operate) {
		String sql = "update t_sys_operate t set t.name_ = ? ,t.description = ? where t.id = ?";
		baseDao.getBaseDao().getJdbcTemplate().update(sql,new Object[]{operate.getName_(),operate.getDescription(),operate.getId()});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
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
		return baseDao.getBaseDao().getJdbcTemplate().query(sql.toString(), (RowMapper) new BeanPropertyRowMapper(Operate.class));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Operate> findAll() {
		String sql = "select * from t_sys_operate t";
		return baseDao.getBaseDao().getJdbcTemplate().query(sql, (RowMapper) new BeanPropertyRowMapper(Operate.class));
	}

    

}
