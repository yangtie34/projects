package com.jhnu.product.mosdc.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.mosdc.dao.UserSynDao;
import com.jhnu.system.permiss.entity.DataServe;
@Repository("userSynDao")
public class UserSynDaoImpl implements UserSynDao {
	@Autowired
	private BaseDao baseDao;
	@Override
	public List<Map<String, Object>> getWelStusNotInUser() {
		String sql = "select * from tb_welcome_xs where id  not in (select id from t_sys_user)";
		List<Map<String,Object>> list=baseDao.getBaseDao()
				.getJdbcTemplate().queryForList(sql);
		return list;
	}

	@Override
	public List<Map<String, Object>> getStusNotInUser() {
		String sql  ="select * from tb_xjda_xjxx where id  not in (select id from t_sys_user)";
		List<Map<String,Object>> list=baseDao.getBaseDao()
				.getJdbcTemplate().queryForList(sql);
		return list;
	}

	@Override
	public List<Map<String, Object>> getJzgsNotInUser() {
		String sql ="select * from tb_jzgxx where id  not in (select id from t_sys_user)";
		List<Map<String,Object>> list=baseDao.getBaseDao()
				.getJdbcTemplate().queryForList(sql);
		return list;
	}

}
