package com.jhnu.util.getSql;

import java.util.List;
import java.util.Map;

import com.jhnu.util.common.BaseDao;

public class ViewDao {
	public BaseDao getBaseDao() {
		return baseDao;
	}
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	private BaseDao baseDao;
	public List<Map<String,Object>> getSys(String userName){
		List<Map<String,Object>> groups=getUserGroups(userName);
		return groups;
	}
	
	public List<Map<String,Object>> getGroups() {
		String sql="select id,name_ name,(select tp.url_ from t_sys_resources tp where t.pid = tp.id) ||url_ url_,shiro_tag shiroTag,istrue from t_sys_resources t where resource_type_code='06'";
		return baseDao.getJdbcTemplate().queryForList(sql+" order by order_");
	}
	public List<Map<String,Object>> getUserGroups(String userName) {
		 String sql = "select b.name_ from "+
					   "( select '' name_ ,'1' s  from dual ) a  left join  "+
					   "(select r.name_ ,'1' s  "+
					   "from t_sys_user u, t_sys_role r, t_sys_user_role ur "+
					   "where u.username = ? "+
					   "and u.id = ur.user_id "+
					   "and r.id = ur.role_id "+
					  "and r.istrue = 1 "+ 
					   "and r.ismain=1 ) b on a.s=b.s ";
	      String roolRole=baseDao.getJdbcTemplate().queryForObject(sql, String.class, userName);
	      String wher=" where u.username = '"+userName+"' ";
		if("admin".equalsIgnoreCase(roolRole)){
			return getGroups();
		}
		sql="select id,name_ name,(select tp.url_ from t_sys_resources tp where t.pid = tp.id) ||url_ url_,shiro_tag shiroTag,istrue from t_sys_resources t where resource_type_code='06' and shiro_tag in"
		+"(select shiro_tag  w from  t_sys_resources  where";//substr(shiro_tag, 0, instr(shiro_tag||':', ':')-1 )
		sql+=" resource_type_code = '06' and id in "
				+ "(select rp.resource_id "
				+ " from t_sys_user  u, "
           + "    t_sys_role      r, "
         + "      t_sys_user_role ur, "
           + "    t_sys_role_perm rp "
        + wher
         + "  and u.id = ur.user_id "
        + "   and r.id = ur.role_id "
         + "  and r.id = rp.role_id "
       + " union all "
        + "select up.resource_id "
        + "  from t_sys_user u, t_sys_user_perm up "
       + wher
        + "   and u.id = up.user_id))";
		return baseDao.getJdbcTemplate().queryForList(sql+" order by order_");
	}
	
}
