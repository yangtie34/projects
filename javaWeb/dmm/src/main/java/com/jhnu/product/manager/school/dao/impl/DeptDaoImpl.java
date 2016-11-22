package com.jhnu.product.manager.school.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.manager.school.dao.DeptDao;
import com.jhnu.util.common.StringUtils;

@Repository("deptDao")
public class DeptDaoImpl implements DeptDao{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Map<String, Object>> getDeptTeach(String ids) {
		String sql2 = "";
		if(!StringUtils.hasText(ids)){
			return null;
		}else if(!"0".equals(ids)){
			sql2 = "and dt.id in ("+ ids +")";
		}else {
			sql2 = "and dt.pid = 0";
		}
		String sql = "select dt.id dept_id,dt.name_ dept_name,dt.level_ dept_level,count(*) nums from t_code_dept_teach dt"
				+ " inner join t_stu stu on stu.dept_id =  dt.id  or stu.major_id = dt.id "
				+ " where stu.stu_state_code <>4 and dt.istrue = '1' "+ sql2
				+ " group by dt.id,dt.name_,dt.level_";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getMajorTeach(String dept_id) {
		String sql = "select dt.id dept_id,dt.name_ dept_name,dt.level_ dept_level,pid ,count(*) nums from t_code_dept_teach dt"
				+ " inner join t_stu stu on stu.dept_id =  dt.id  or stu.major_id = dt.id "
				+ " where stu.stu_state_code <>4 and dt.istrue = '1' and  dt.pid = '"+ dept_id +"'"
				+ " group by dt.id,dt.name_,pid,dt.level_";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getDept(String ids) {
		String sql2 = "";
		if(!StringUtils.hasText(ids)){
			return null;
		}else if(!"0".equals(ids)){
			sql2 = "and cd.id in ("+ ids +")";
		}else {
			sql2 = "and cd.pid = 0";
		}
		String sql = "select cd.id dept_id,cd.name_ dept_name,cd.level_ dept_level ,count(*) nums from t_code_dept cd"
				+ " inner join t_tea tea on tea.dept_id = cd.id where cd.istrue = '1'"+ sql2
				+ " group by cd.id,cd.name_,cd.level_";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getDeptLeaf(String dept_id) {
		String sql = "select cd.id dept_id,cd.name_ dept_name,cd.level_ dept_level ,count(*) nums from t_code_dept cd"
				+ " inner join t_tea tea on tea.dept_id = cd.id where cd.pid = '"+ dept_id +"'"
				+ " group by cd.id,cd.name_,cd.level_";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String,Object>> getDepts(String ids) {
			String sql = "select dt.id dept_id,dt.name_ dept_name,dt.level_ dept_level from t_code_dept_teach dt where id in "
					+ "(select pid from t_code_dept_teach t where id in ("+ ids +")) or id = '0' or id in ("+ ids +") order by id ";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	
	@Override
	public List<Map<String,Object>> getTeaDepts(String ids) {
			String sql = "select dt.id dept_id,dt.name_ dept_name,dt.level_ dept_level from t_code_dept dt where id in "
					+ "(select pid from t_code_dept t where id in ("+ ids +")) or id = '0' or id in ("+ ids +") order by id ";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

}
