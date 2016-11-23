/**
 * @author:dangdong(mrdangdong@163.com)
 * @DATE:2012-5-22  下午08:57:35
 *
 */
package com.jhkj.mosdc.permission.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.jhkj.mosdc.framework.dao.impl.BaseDaoImpl;
import com.jhkj.mosdc.permission.dao.RoleDao;
import com.jhkj.mosdc.permission.po.TsCdzy;
import com.jhkj.mosdc.permission.po.TsJs;
import com.jhkj.mosdc.permission.po.TsJsCdzy;
import com.jhkj.mosdc.permission.po.TsJsTabs;
import com.jhkj.mosdc.permission.po.TsUserCdzy;

/**
 * @Comments: 角色Dao接口实现类
 * Company: topMan
 * Created by dangdong(mrdangdong@163.com) 
 * @DATE:2012-5-22
 * @TIME: 下午08:57:35
 */
public class RoleDaoImpl extends BaseDaoImpl implements RoleDao {
	
	/**
	 * 当前用户的角色信息
	 * @param userId
	 * @return
	 */
	@Override
	public List<TsJs> queryJsxx(Long userId){
		String hql = " from TsJs t where t.userId = "+userId;
		List<TsJs> list = this.getHibernateTemplate().find(hql);
		if(list != null && list.size() >0 ){
			return list;
		}
		return null;
	}
	
	@Override
	public List<TsJsCdzy> queryTsJsCdzys(String ids){
		List<TsJsCdzy> list = this.getHibernateTemplate().find(" from TsJsCdzy t where t.jsId in ("+ids+") ");
		if(list != null && list.size()>0){
			return list;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.jhkj.mosdc.permission.dao.RoleDao#addRole(com.jhkj.mosdc.permission.po.TsJs)
	 */
	@Override
	public TsJs saveRole(TsJs role) {
		this.insert(role);
		return role;
	}

	/* (non-Javadoc)
	 * @see com.jhkj.mosdc.permission.dao.RoleDao#deleteRole(com.jhkj.mosdc.permission.po.TsJs)
	 */
	@Override
	public void deleteRole(TsJs role) {
		this.delete(role);
	}

	/* (non-Javadoc)
	 * @see com.jhkj.mosdc.permission.dao.RoleDao#deleteRoleMenusByRoleId(java.lang.Long)
	 */
	@Override
	public int deleteRoleMenusByRoleId(Long roleId) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.jhkj.mosdc.permission.dao.RoleDao#editRole(com.jhkj.mosdc.permission.po.TsJs)
	 */
	@Override
	public void updateRole(TsJs role) {
		this.update(role);
	}
	
	
	@Override
	public TsJs getRole(Long userId,Long jslxId){
		List<TsJs> tsJsList = this.getSession().createQuery(" from TsJs t where t.jslxId ='"+jslxId+"' and t.userId = "+userId).list();
		if(tsJsList != null && tsJsList.size()>0){
			return tsJsList.get(0);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.jhkj.mosdc.permission.dao.RoleDao#findRecordCount()
	 */
	@Override
	public int findRecordCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.jhkj.mosdc.permission.dao.RoleDao#getRole(java.lang.Long)
	 */
	@Override
	public TsJs getRole(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.jhkj.mosdc.permission.dao.RoleDao#getRoleAllList()
	 */
	@Override
	public List<TsJs> getRoleAllList() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.jhkj.mosdc.permission.dao.RoleDao#getRoleAllListByPage(int, int, java.lang.String, java.lang.String)
	 */
	@Override
	public List<TsJs> getRoleAllListByPage(int start, int limit, String sort,
			String dir) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.jhkj.mosdc.permission.dao.RoleDao#getRoleReferenceRecordNum(java.lang.Long)
	 */
	@Override
	public int getRoleReferenceRecordNum(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.jhkj.mosdc.permission.dao.RoleDao#isNameExist(java.lang.Long, java.lang.String)
	 */
	@Override
	public boolean isNameExist(Long id, String name) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.jhkj.mosdc.permission.dao.RoleDao#saveRoleMenus(java.lang.Long, java.util.List)
	 */
	@Override
	public void saveRoleMenus(Long roleId, List<String> perms) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void saveUserMenuPermiss(TsUserCdzy tsUserCdzy) throws Exception{
		this.insert(tsUserCdzy);
//		tsJsCdzy.setId(this.getId());
//		this.getHibernateTemplate().save(tsJsCdzy);
	}
	
	@Override
	public List<TsUserCdzy> queryUserCdzy(String cdzyIds,Long userId){
		String hql = "select t from TsUserCdzy t where t.userId = "+userId+" and t.cdzyId in("+cdzyIds+") ";
		List<TsUserCdzy> list = this.getHibernateTemplate().find(hql);
		if(list != null && list.size()>= 0){
			return list;
		}
		return null;
	}
	
	@Override
	public void deleteUserCdzys(List<TsUserCdzy> tsUserCdzyList){
		this.getHibernateTemplate().deleteAll(tsUserCdzyList);
	}

	@Override
	public List<TsJs> getRoles(String ids) {
		List<TsJs> tsJsList = this.getHibernateTemplate().find(" from TsJs t where t.id in ("+ids+") ");
		if(tsJsList != null && tsJsList.size()>0){
			return tsJsList;
		}
		return null;
	}

	@Override
	public void deleteRoles(List<TsJs> tsJsList) {
		 this.getHibernateTemplate().deleteAll(tsJsList);
	}
	@Override
	public List queryJsTabs(String rolesId,Long rylb){
		List tsJsTabsList = this.getJdbcTemplate().queryForList("select max(t.mc) as zwm ,t.tabmc as name,t.pxxh from ts_js_tabs t where t.js_id in ("+rolesId+") and t.rylb = "+rylb+" group by t.tabmc,t.pxxh order by t.pxxh ");
		if(tsJsTabsList != null && tsJsTabsList.size()>0){
			return tsJsTabsList;
		}
		return null;
	}
	
	@Override
	public String getRoleIds(String roleTypeIds) {
		// TODO Auto-generated method stub
		List roleIds =this.getSession().createSQLQuery("select t.id from ts_js t where t.jslx_id in ("+roleTypeIds+") ").list();
		StringBuffer roleStrIds = new StringBuffer();
		for(int i = 0;i<roleIds.size();i++){
			roleStrIds.append(roleIds.get(i));
			if((i+1) != roleIds.size()){
				roleStrIds.append(",");
			}
		}
		return roleStrIds.toString();
	}
	/**
	 * 根据角色ID，查询其对应的菜单资源
	 */
	public List<TsCdzy> queryTsCdzysByRoleId(Long roleId){
//		String hql = "select cdzy from TsCdzy cdzy,TsJsCdzy jscdzy where jscdzy.jsId="+roleId + " and cdzy.id = jscdzy.cdzyId";
		String sql = "select cdzy.id,cdzy.mc,cdzy.fjd_id,jscdzy.id jscdzyid from ts_cdzy cdzy left join ts_js_cdzy jscdzy on cdzy.id=jscdzy.cdzy_id and jscdzy.js_id = "+roleId;
		sql = sql.concat(" where cdzy.sfky =1 ");
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		List<TsCdzy> cdzyList = new ArrayList<TsCdzy>();
		for(Map map : list){
			TsCdzy cdzy = new TsCdzy();
			cdzy.setId(MapUtils.getLong(map, "ID"));
			cdzy.setFjdId(MapUtils.getLong(map, "FJD_ID"));
			cdzy.setMc(MapUtils.getString(map, "MC"));
			cdzy.setChecked(map.get("JSCDZYID") != null);
			cdzyList.add(cdzy);
		}
		return cdzyList;
	}
}
