package com.jhnu.system.permiss.dao.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.system.permiss.dao.DataServeDao;
import com.jhnu.system.permiss.entity.DataServe;

@Repository("dataServeDao")
public class DataServeDaoImpl implements DataServeDao {
	
	@Autowired
	private BaseDao baseDao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public DataServe findById(Long id) {
		String sql = "select * from t_sys_data_service t where t.id = ?";
		List<DataServe> dlist=baseDao.getBaseDao().getJdbcTemplate().query(sql.toString(), (RowMapper) new BeanPropertyRowMapper(DataServe.class),new Object[]{id});
		return dlist.size()==0?null:dlist.get(0);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<DataServe> getDataServe(String username, String shiroTag) {
		String sql="select ds.id,ds.name_,ds.servicename,up.id perm_id,'user' perm_type from T_SYS_USER_PERM up "+
					"inner join T_SYS_DATA_SERVICE ds on up.data_service_id=ds.id "+
					"inner join t_sys_user u on up.user_id=u.id "+
					"where u.username=? and up.wirldcard in ("+shiroTag+") "+
					"union all "+
					"select ds.id,ds.name_,ds.servicename,rp.id perm_id,'role' perm_type from T_SYS_USER_ROLE ur  "+
					"inner join T_SYS_Role_PERM rp on ur.role_id=rp.role_id  "+
					"inner join T_SYS_DATA_SERVICE ds on rp.data_service_id=ds.id "+
					"inner join t_sys_user u on ur.user_id=u.id "+
					"where u.username=? and rp.wirldcard in ("+shiroTag+") ";
		return baseDao.getBaseDao().getJdbcTemplate().query(sql.toString(), (RowMapper) new BeanPropertyRowMapper(DataServe.class),new Object[]{username,username});
	}

	@Override
	public List<Map<String, Object>> findAll() {
		String sql = "select * from t_sys_data_service";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

}
