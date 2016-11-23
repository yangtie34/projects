package com.jhkj.mosdc.permiss.domain;

import java.util.List;
import java.util.Map;

import com.jhkj.mosdc.permiss.po.TpUsergroupUserdatapermissLd;

public class UserGroupDataPermission {
	private Base base;
	
	private Long userId;
	
	protected boolean way = true;//true 组织结构 false 班级
	
	private Long usergroupId;
	
	private String sqlDataPermissRange = null;//班级数据范围的sql
	
	private String hqlDataPermissRange = null;//班级数据范围的hql
	
	private String sqlXzDataPermissRange = null;//行政数据权限sql-教职工
	
	private String hqlXzDataPermissRange = null;//行政数据权限hql-教职工
	
	public UserGroupDataPermission(Base base,Long userId, Long usergroupId) {
		super();
		this.base = base;
		this.userId = userId;
		this.usergroupId = usergroupId;
		this.initPermiss();
	}
	private void initPermiss(){
		TpUsergroupUserdatapermissLd tu = new TpUsergroupUserdatapermissLd();
		tu.setUserId(userId);
		tu.setUsergroupId(usergroupId);
//		Long num = base.countByEntity(tu);//是否已经设置粒度权限
//		way = num>0;
		if(way){
			initZzjgPermiss();
		}else{
			initBjPermiss();
		}
		initXzzzjgPermiss();
	}
	/**
	 * 初始化行政组织结构权限
	 */
	private void initXzzzjgPermiss() {
		// TODO Auto-generated method stub
		//sql数据权限初始化
		StringBuilder rangeSql = new StringBuilder();
		rangeSql.append(" select zg.id from TP_USERGROUP_XZ_UDPERMISS_LD tuxul inner join tb_xzzzjg zzjg on tuxul.xzzzjg_id = zzjg.id "+
                " inner join tb_xzzzjg zzjg1 on zzjg1.qxm like zzjg.qxm||'%'  "+
                " inner join tb_jzgxx zg on zzjg1.id = zg.ks_id  "+
                " and tuxul.user_id = "+userId+" and tuxul.usergroup_id  "+ getUserGroupCondition(usergroupId));
		//hql数据权限初始化
		StringBuilder rangeHql = new StringBuilder();
		rangeHql.append("and tuxul.userId=").append(userId)
						.append(" and tuxul.usergroupId").append(getUserGroupCondition(usergroupId));
						
		sqlXzDataPermissRange = rangeSql.toString();
		hqlXzDataPermissRange = rangeHql.toString();
	}
	private void initZzjgPermiss(){
		//sql数据权限初始化
		StringBuilder rangeSql = new StringBuilder();
		rangeSql.append(" select bj.id from TP_USERGROUP_UDPERMISS_LD tuul inner join tb_jxzzjg zzjg on tuul.jxzzjg_id = zzjg.id "+
                        " inner join tb_jxzzjg zzjg1 on zzjg1.qxm like zzjg.qxm||'%'  "+
                        " inner join tb_xxzy_bjxxb bj on zzjg1.id = bj.fjd_id  "+
                        " and tuul.user_id = "+userId+" and tuul.usergroup_id  "+ getUserGroupCondition(usergroupId));
		//hql数据权限初始化
		StringBuilder rangeHql = new StringBuilder();
		rangeHql.append(" tul.userId=").append(userId)
				.append(" and tul.usergroupId").append(getUserGroupCondition(usergroupId));
				
		sqlDataPermissRange = rangeSql.toString();
		hqlDataPermissRange = rangeHql.toString();
	}
	private void initBjPermiss() {
		// TODO Auto-generated method stub
		//sql数据权限初始化
		StringBuilder rangeSql = new StringBuilder();
		rangeSql.append("select t.BJ_ID from TP_USERGROUP_USERDATAPERMISS t,tb_xxzy_bjxxb bj where bj.id = t.bj_id and bj.sfky = 1 and t.user_id = ").append(userId);
		rangeSql.append(" and t.usergroup_id ").append(getUserGroupCondition(usergroupId));
		sqlDataPermissRange = rangeSql.toString();
		//hql数据权限初始化
		StringBuilder rangeHql = new StringBuilder();
		rangeHql.append(" t.userId=").append(userId);
		rangeHql.append(" and t.usergroupId ").append(getUserGroupCondition(usergroupId));
		hqlDataPermissRange = rangeHql.toString();
	}
	
	/**
	 * 获取当前菜单的数据权限,以sql形式
	 * @param menuId
	 * @return
	 */
	public String getSqlMenuDataPermiss(Long menuId){
		return sqlDataPermissRange;
//		StringBuilder allRangeSql = new StringBuilder("");
//		allRangeSql.append(sqlDataPermissRange)
//				   .append(" and t.BJ_ID not in (")
//		           .append("select t.except_bj_id from TP_USERGROUP_USERDATA_EXCEPT t where t.menu_id = ")
//		           .append(menuId).append(" and t.usergroup_id ").append(getUserGroupCondition(usergroupId))
//		           .append(" and t.user_id = ").append(this.userId)
//				   .append(")");
//		return allRangeSql.toString();
	}
	public String getHqlMenuDataPermissCondition(Long menuId){
		return hqlDataPermissRange;
//		StringBuilder allRangeHql = new StringBuilder("(");
//		allRangeHql.append(hqlDataPermissRange)
//				   .append(" and t.bjId not in (")
//				   .append("select t.exceptBjId from TpUsergroupUserdataExcept t where t.menuId = ")
//				   .append(menuId).append(" and t.usergroupId ").append(getUserGroupCondition(usergroupId))
//				   .append("))");
//		return allRangeHql.toString();
//		StringBuilder rangeHql = new StringBuilder();
//		rangeHql.append("  tul.userId=").append(userId)
//		.append(" and tul.usergroupId").append(getUserGroupCondition(usergroupId));
//		return rangeHql.toString();
	}
	/**
	 * 以sql形式，获取行政组织结构数据权限
	 * @param menuId
	 * @return
	 */
	public String getSqlMenuXzDataPermiss(Long menuId){
		return sqlXzDataPermissRange;
	}
	/**
	 * 以hql形式，获取行政组织结构数据权限
	 * @param menuId
	 * @return
	 */
	public String getHqlMenuXzDataPermissCondition(Long menuId){
		return hqlXzDataPermissRange;
	}
	/**
	 * 获取组查询条件
	 * @param usergroupId
	 * @return
	 */
	private String getUserGroupCondition(Long usergroupId){
		String condition = null;
		if(usergroupId == null)condition = " is null "; else condition = "="+usergroupId;
		return condition;
	}
	
}
