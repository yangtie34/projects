/**
 * @author:dangdong(mrdangdong@163.com)
 * @DATE:2012-5-22  下午09:05:58
 *
 */
package com.jhkj.mosdc.permission.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.jhkj.mosdc.framework.bean.PageParam;
import com.jhkj.mosdc.framework.dao.impl.BaseDaoImpl;
import com.jhkj.mosdc.framework.message.po.TsAnnouncement;
import com.jhkj.mosdc.framework.util.Page;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.output.po.TbTjgnZdytjym;
import com.jhkj.mosdc.permission.dao.MenuDao;
import com.jhkj.mosdc.permission.po.MenuRowMapper;
import com.jhkj.mosdc.permission.po.TjgnZdytjymMapper;
import com.jhkj.mosdc.permission.po.TsCdzy;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.jhkj.mosdc.permission.po.UserMenuRowMapper;

/**
 * @Comments: 菜单Dao接口实现类 Company: topMan Created by dangdong(mrdangdong@163.com)
 * @DATE:2012-5-22
 * @TIME: 下午09:05:58
 */
public class MenuDaoImpl extends BaseDaoImpl implements MenuDao {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.dao.MenuDao#addMenu(com.jhkj.mosdc.permission
	 * .po.TsCdzy)
	 */
	@Override
	public TsCdzy addMenu(TsCdzy menu) {
		this.insert(menu);
		return menu;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.dao.MenuDao#deleteMenu(com.jhkj.mosdc.permission
	 * .po.TsCdzy)
	 */
	@Override
	public void deleteMenu(TsCdzy menu) {
		this.delete(menu);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.dao.MenuDao#editMenu(com.jhkj.mosdc.permission
	 * .po.TsCdzy)
	 */
	@Override
	public TsCdzy editMenu(TsCdzy menu) {
		this.update(menu);
		return menu;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.dao.MenuDao#findMenuHqlPageById(java.lang.Long,
	 * com.jhkj.mosdc.framework.bean.PageParam)
	 */
	@Override
	public Page findMenuHqlPageById(Long id, PageParam pageParam) {
		// String hql =
		// " from TsCdzy as m where m.sjzyId=? order by  m.pxh asc";
		// return this.findHqlPage(hql, new Object[]{id}, pageParam);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jhkj.mosdc.permission.dao.MenuDao#findRecordCount()
	 */
	@Override
	public int findRecordCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.dao.MenuDao#findRecordCountByParentId(java.
	 * lang.Long)
	 */
	@Override
	public int findRecordCountByParentId(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.dao.MenuDao#getButtonIdsByRoleId(java.lang.
	 * Long)
	 */
	@Override
	public List<Map<String, Object>> getButtonIdsByRoleId(Long roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jhkj.mosdc.permission.dao.MenuDao#getMenu(java.lang.Long)
	 */
	@Override
	public TsCdzy getMenu(Long id) {
		return (TsCdzy)this.get(TsCdzy.class,id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jhkj.mosdc.permission.dao.MenuDao#getMenuAllList()
	 */
	/**
	 * 获取所有菜单资源记录
	 */
	@Override
	public List<TsCdzy> getMenuAllList()throws Exception {
		//定义HSQL变量
		String hsql = " from TsCdzy t where t.sfky=1 order by t.fjdId,t.pxh";
		
		return this.getSession().createQuery(hsql).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jhkj.mosdc.permission.dao.MenuDao#getMenuAllListByPage(int, int,
	 * java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<TsCdzy> getMenuAllListByPage(int start, int limit, String sort,
			String dir, Integer parentId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.dao.MenuDao#getMenuIdsByRoleId(java.lang.Long,
	 * java.lang.Long)
	 */
	@Override
	public List<Map<String, Object>> getMenuIdsByRoleId(Long roleId, Long menuId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.dao.MenuDao#getMenuRoleListBySql(java.lang.
	 * String)
	 */
	@Override
	public List<TsCdzy> getMenuRoleListBySql(String sql) {
		List<TsCdzy> list = this.getJdbcTemplate().query(sql,
				new MenuRowMapper());
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.dao.MenuDao#getMenuTreeByParentId(java.lang
	 * .Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TsCdzy> getMenuTreeByParentId(Long parentId) {
		String hql = " from TsCdzy as m where m.fjdId=?  and m.sfky='1' order by m.pxh asc";//
		return getHibernateTemplate().find(hql, new Object[] {parentId});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.dao.MenuDao#saveRolePermission(java.lang.Long,
	 * java.util.List)
	 */
	@Override
	public void saveRolePermission(final Long roleId, String menuIds) throws Exception {
		Session session = this.getSession();
		String sql = "";
		// 先删除角色权限
		sql = "delete from ts_js_cdzy  where js_id = '" + roleId + "'";
		int tt = 0;
		tt = session.createSQLQuery(sql).executeUpdate();
		this.logger.debug("清除角色原有的角色对应关系数:" + tt + " sql:" + sql);
		if (menuIds.length() > 2) {
			menuIds=menuIds.replaceAll("'", "");			
			final String[] menus = menuIds.split(",");
			Long menuId;
			String[] sqls = new String[menus.length];
			for (int i = 0; i < menus.length; i++) {
				
				menuId = Long.valueOf(menus[i]);
//				sql = MessageFormat
//						.format("insert into ts_js_cdzy(id,js_id,cdzy_id) values({0}, {1},{2})",Struts2Utils.quoteString(this.getId())
//								,Struts2Utils.quoteString(roleId), Struts2Utils.quoteString(menuId));
//				sqls[i] = sql;
				
				this.logger.debug("保存权限数据：" + sql);
				//tt = session.createSQLQuery(sql).executeUpdate();
			}
			String insertSql = "insert into ts_js_cdzy(id,js_id,cdzy_id) values(?, ?,?)";
			this.getJdbcTemplate().batchUpdate(insertSql, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					try {
						ps.setLong(1, getId());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ps.setLong(2, roleId);
					ps.setLong(3, Long.parseLong(menus[i]));
				}
				
				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return menus.length;
				}
			});
		}
		session.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jhkj.mosdc.permission.dao.MenuDao#sumChildById(java.lang.Long)
	 */
	@Override
	public Integer sumChildById(Long id) {
		String sql = "select count(*) from TS_CDZY t where t.fjd_id =" + id;
		int i = this.getJdbcTemplate().queryForInt(sql);
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.dao.MenuDao#sumZlnc_ROLE_MENUById(java.lang
	 * .Long)
	 */
	@Override
	public Integer sumRoleByMenuId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* 
	 * @see com.jhkj.mosdc.permission.dao.MenuDao#getMenuListByUserId(java.lang.Long)
	 */
	@Override
	public List<TsCdzy> getMenuListByUserId(Long userId) throws Exception {
		String sql="select distinct tjc.cdzy_id, tc.mc, tc.cdlj,tc.cdssfl_id, tc.fjd_id,tc.pxh,tc.anlx_id from ts_js_cdzy tjc left join ts_cdzy tc on tjc.cdzy_id = tc.id left join TC_XXBZDMJG tx on tc.cdssfl_id=tx.id where tx.dm <4  and tc.sfky ='1' and tjc.js_id in (select tjs.js_id from ts_user_js tjs where tjs.user_id = "+userId+")  order by tjc.cdzy_id asc";
		List<TsCdzy> list = this.getJdbcTemplate().query(sql,
				new UserMenuRowMapper());
		return list;
	}

	/* 
	 * @see com.jhkj.mosdc.permission.dao.MenuDao#getMenuListByUserIdAndMenuId(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<TsCdzy> getMenuListByUserIdAndMenuId(Long userId, Long menuId)
			throws Exception {
		String sql="select distinct tjc.cdzy_id, tc.mc, tc.cdlj,tc.cdssfl_id, tc.fjd_id,tc.pxh,tc.anlx_id " +
				" from ts_js_cdzy tjc left join ts_cdzy tc on tjc.cdzy_id = tc.id left join TC_XXBZDMJG tx on tc.cdssfl_id=tx.id ,ts_user_cdzy uc "+
				" where (uc.cdzy_id =tc.fjd_id and uc.user_id = "+userId+") or "+
				" (tx.bzdm = 'XXDM-LZCDFL' and tx.dm ='4' and tjc.js_id in (select tjs.js_id from ts_user_js tjs where tjs.user_id = "+userId+") and tc.sfky ='1'  and tc.fjd_id="+menuId+") and  tc.sfky = 1  order by tjc.cdzy_id asc";
		List<TsCdzy> list = this.getJdbcTemplate().query(sql,
				new UserMenuRowMapper());
		return list;
	}
	
	@Override
	public List<TsCdzy> getMenuListByUserIdAndMenuId(Long userId)
			throws Exception {
		String sql="select distinct tjc.cdzy_id, tc.mc, tc.cdlj,tc.cdssfl_id, tc.fjd_id,tc.pxh,tc.anlx_id " +
				" from ts_js_cdzy tjc left join ts_cdzy tc on tjc.cdzy_id = tc.id left join TC_XXBZDMJG tx on tc.cdssfl_id=tx.id ,ts_user_cdzy uc "+
				" where (uc.cdzy_id =tc.fjd_id and uc.user_id = "+userId+") or "+
				" (tx.bzdm = 'XXDM-LZCDFL' and tx.dm ='4' and tjc.js_id in (select tjs.js_id from ts_user_js tjs where tjs.user_id = "+userId+") and tc.sfky ='1' ) and  tc.sfky = 1  order by tjc.cdzy_id asc";
		List<TsCdzy> list = this.getJdbcTemplate().query(sql,
				new UserMenuRowMapper());
		return list;
	}

	/* 
	 * @see com.jhkj.mosdc.permission.dao.MenuDao#getAllMenuListByUserId(java.lang.Long)
	 */
	@Override
	public List<TsCdzy> getAllMenuListByUserId(Long userId) throws Exception {
		String sql="select distinct tjc.cdzy_id, tc.mc,tc.cdlj, tc.cdssfl_id, tc.fjd_id,tc.pxh,tc.anlx_id " +
				" from ts_js_cdzy tjc left join ts_cdzy tc on tjc.cdzy_id = tc.id left join TC_XXBZDMJG tx on tc.cdssfl_id=tx.id " +
				"where  tc.sfky='1' and tjc.js_id in (select tjs.js_id from ts_user_js tjs ts_js j where tjs.user_id = "+userId+")  order by tc.fjd_id,tc.pxh asc";
		List<TsCdzy> list = this.getJdbcTemplate().query(sql,
				new UserMenuRowMapper());
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<TsCdzy> getAllMenuListByUserId(Long userId,String cdzyIds) throws Exception {
		
		StringBuffer sql= new StringBuffer("select distinct tjc.cdzy_id, tc.mc,tc.cdlj, tc.cdssfl_id, tc.fjd_id,tc.pxh,tc.anlx_id " +
				"from ts_js_cdzy tjc left join ts_cdzy tc on tjc.cdzy_id = tc.id left join TC_XXBZDMJG tx on tc.cdssfl_id=tx.id " +
				"left join ts_user_cdzy uc on  uc.cdzy_id = tc.id " +
				"where tc.sfky = '1' and ( uc.user_id = "+userId+") or tjc.js_id in (select tjs.js_id from ts_user_js tjs where tjs.user_id = "+userId+")" );
		if(cdzyIds.length() > 0){
			sql.append(" and tjc.cdzy_id not in("+cdzyIds+") ");
		}
		sql.append("  order by tc.fjd_id,tc.pxh asc");
		List<TsCdzy> list = this.getJdbcTemplate().query(sql.toString(),
				new UserMenuRowMapper());
		return list;
	}

	/* 
	 * @see com.jhkj.mosdc.permission.dao.MenuDao#getAllButtonMenuListByUserId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TsCdzy> getAllButtonMenuListByUserId(Long userId)
			throws Exception {
		String sql="select distinct tjc.cdzy_id, tc.mc, tc.cdlj, tc.cdssfl_id, tc.fjd_id,tc.pxh,tc.anlx_id " +
				" from ts_js_cdzy tjc left join ts_cdzy tc on tjc.cdzy_id = tc.id left join TC_XXBZDMJG tx on tc.cdssfl_id=tx.id " +
				"where tc.sfky ='1' and tx.dm =4 and tjc.js_id in (select tjs.js_id from ts_user_js tjs where tjs.user_id = "+userId+")  order by tjc.cdzy_id asc";
		List<TsCdzy> list = this.getJdbcTemplate().query(sql,
				new UserMenuRowMapper());
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TsCdzy> getSyCdzy(Long userId){
		String sql = "select t.id,t.mc,t.cdlj,t.fid from tb_tjgn_zdytjym t,tc_xxbzdmjg g,tc_xxbzdmjg j where (t.sfsy =g.id and g.dm ='1') and (t.sftgsh = j.id and j.dm = '2') and t.USER_ID ="+userId;
		List<TsCdzy> syCdzyList = this.getJdbcTemplate().query(sql, new TjgnZdytjymMapper());
		return syCdzyList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TsCdzy> getGyCdzy(){
		String sql = " select t.id,t.mc,t.cdlj,t.fid from tb_tjgn_zdytjym t,tc_xxbzdmjg g,tc_xxbzdmjg j where (t.sfsy =g.id and g.dm ='2') and (t.sftgsh = j.id and j.dm = '2') ";
		List<TsCdzy> gyCdzyList = this.getJdbcTemplate().query(sql, new TjgnZdytjymMapper());
		return gyCdzyList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TsCdzy> getGroupDecentralizationCdzy(UserInfo user) {
		// TODO Auto-generated method stub
		Long zgId = user.getZgId();
		String sql = "SELECT t.* FROM TS_USER_CDZY_GROUP uc inner join ts_cdzy t on t.id = uc.cdzy_id and uc.user_id="+zgId;
		SQLQuery query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.addEntity(TsCdzy.class);
		return query.list();
	}
}
