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
		String checkString="",checkString2="";
		String star=shiroTag.substring(0,shiroTag.lastIndexOf(":")+1);
		String lastag=shiroTag.substring(shiroTag.lastIndexOf(":")+1,shiroTag.length());
		if(lastag.equalsIgnoreCase("*")){
			checkString=" like '"+star+"%' and INSTR(up.wirldcard , ':', -1, 1) ="+star.length();
			checkString2=" like '"+star+"%' and INSTR(rp.wirldcard , ':', -1, 1) ="+star.length();
		}else{
			checkString="in ('"+shiroTag+"','"+star+"*') ";
			checkString2=checkString;
		}
		String sql="select ds.id,ds.name_,ds.servicename,up.id perm_id,'user' perm_type ,'"+username+"' username from T_SYS_USER_PERM up "+
					"inner join T_SYS_DATA_SERVICE ds on up.data_service_id=ds.id "+
					"inner join t_sys_user u on up.user_id=u.id "+
					"where u.username=? and up.wirldcard  "+checkString+"  "+
					"union all "+
					"select ds.id,ds.name_,ds.servicename,rp.id perm_id,'role' perm_type,'"+username+"' username from T_SYS_USER_ROLE ur  "+
					"inner join T_SYS_Role_PERM rp on ur.role_id=rp.role_id  "+
					"inner join T_SYS_DATA_SERVICE ds on rp.data_service_id=ds.id "+
					"inner join t_sys_user u on ur.user_id=u.id "+
					"where u.username=? and rp.wirldcard  "+checkString2+"  ";
		return baseDao.getBaseDao().getJdbcTemplate().query(sql.toString(), (RowMapper) new BeanPropertyRowMapper(DataServe.class),new Object[]{username,username});
	}

	@Override
	public List<Map<String, Object>> findAll() {
		String sql = "select * from t_sys_data_service";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

}
