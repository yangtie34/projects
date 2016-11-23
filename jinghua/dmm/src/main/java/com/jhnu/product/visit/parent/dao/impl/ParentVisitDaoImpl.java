package com.jhnu.product.visit.parent.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.visit.parent.dao.ParentVisitDao;

@Repository("parentVisitDao")
public class ParentVisitDaoImpl implements ParentVisitDao{

	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Map<String,Object>> getVisitTotalCounts(String startDate, String endDate) {
		String sql = "select count(*) total_counts from t_sys_user_logging t where t.login_date > '"+ startDate +"' and t.login_date < '"+ endDate +"'";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String,Object>> getVisitPcCounts(String startDate, String endDate) {
		String sql = "select count(*) pc_counts from t_sys_user_logging t where t.login_date > '"+ startDate +"' and t.login_date < '"+ endDate +"' and t.login_way = 'REQUEST_PC'";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String,Object>> getVisitMobelCounts(String startDate,String endDate) {
		String sql = "select count(*) mobel_counts from t_sys_user_logging t where t.login_date > '"+ startDate +"' and t.login_date < '"+ endDate +"' and t.login_way = 'REQUEST_MOBILE'";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String,Object>> getUserCounts(String startDate, String endDate) {
		String sql = "select count(*) user_counts from (select t.username from t_sys_user_logging t where t.login_date > '"+ startDate +"' and t.login_date < '"+ endDate +"' group by t.username)";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getParentVisitCounts(String startDate,String endDate) {
		String sql = "select t.visit_menu_code , t.is_wechat,count(*) nums from t_wechat_parent_logging t "
				+ " where t.visit_date > '"+ startDate +"' and t.visit_date < '"+ endDate +"' and t.visit_menu_code = '02'"
				+ " group by t.visit_menu_code , t.is_wechat";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String,Object>> getParentNums(String startDate, String endDate) {
		String sql = "select count(distinct t.username) parent_nums from t_wechat_parent_logging t where  t.visit_date > '"+ startDate +"' and t.visit_date < '"+ endDate +"' "
				+ " and t.visit_menu_code = '02' group by t.visit_menu_code";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getOtherVisitCounts(String startDate,String endDate) {
		String sql = "select co.name_ visit_menu,count(*) counts from t_wechat_parent_logging t right join t_code co on co.code_type = 'VISIT_MENU_CODE' and co.code_ = t.visit_menu_code"
				+ " where t.visit_date > '"+ startDate +"' and t.visit_date < '"+ endDate +"' and t.visit_menu_code != '02' group by co.name_ order by counts desc";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	
	
	

}
