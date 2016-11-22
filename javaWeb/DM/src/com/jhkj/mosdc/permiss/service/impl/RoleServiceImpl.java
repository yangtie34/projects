package com.jhkj.mosdc.permiss.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.service.BaseService;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.permiss.po.TpRole;
import com.jhkj.mosdc.permiss.po.TpRoleMenu;
import com.jhkj.mosdc.permiss.po.TpUserRole;
import com.jhkj.mosdc.permiss.service.RoleService;
import com.jhkj.mosdc.permiss.util.JSONUtil;
import com.jhkj.mosdc.permiss.util.SqlUtils;

public class RoleServiceImpl implements RoleService{
	private BaseDao baseDao;
	
	private BaseService baseService;

	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public String addRole(String params) throws Exception {
		// TODO Auto-generated method stub
		TpRole role = (TpRole) JSONUtil.stringToBean(params, TpRole.class);
		role.setId(baseDao.getId());
		baseDao.save(role);
		return SysConstants.JSON_SUCCESS_TRUE;
	}

	@Override
	public String updateRole(String params) throws Exception {
		// TODO Auto-generated method stub
		TpRole role = (TpRole) JSONUtil.stringToBean(params, TpRole.class);
		baseDao.update(role);
		return SysConstants.JSON_SUCCESS_TRUE;
	}

	@Override
	public String deleteRole(String params) {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		Long roleId = JSONUtil.getLong(json, "roleId");
		
		//删除角色
		baseDao.delete(baseDao.get(TpRole.class, roleId));
		
		//删除已经赋予角色的用户
		TpUserRole tur = new TpUserRole();
		tur.setRoleId(roleId);
		baseDao.deleteEqual(tur);
		//删除角色对应的菜单
		TpRoleMenu trm = new TpRoleMenu();
		trm.setRoleId(roleId);
		baseDao.deleteEqual(trm);
		
		return SysConstants.JSON_SUCCESS_TRUE;
	}

	@Override
	public String queryRolesBygroupId(String params) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
//		Long menuId = JSONUtil.getLong(json, "menuId");
		String[] columnArray = "id, jsmc, jslx_id, usergroup_id, jsms"
				.split(",");
		String[] aliasArray = "id, jsmc, jslxId, usergroupId, jsms"
				.split(",");
		String retSql = "tp_role";
		if(JSONUtil.getLong(json,"usergroupId") == null){
			retSql = "(select * from tp_role t where t.usergroup_id is null)";
		}
		String sql = SqlUtils.generateAliasSql(columnArray, aliasArray, retSql);
		String result = baseService.queryTableDataBySqlWithAlias(sql, params);
		return result;
	}

	@Override
	public String updateRoleMenuPermiss(String params) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		Long roleId = JSONUtil.getLong(json, "roleId");
		
		//删除以前的菜单权限
		TpRoleMenu trm = new TpRoleMenu();
		trm.setRoleId(roleId);
		baseDao.deleteEqual(trm);
		//添加新的菜单权限
		String menuIds = JSONUtil.getString(json,"menuIds");
		String[] menulist = StringUtils.split(menuIds, ",");
		for(String menuId : menulist){
			trm = new TpRoleMenu();
			trm.setId(baseDao.getId());
			trm.setRoleId(roleId);
			trm.setMenuId(Long.valueOf(menuId));
			baseDao.save(trm);
		}
		return SysConstants.JSON_SUCCESS_TRUE;
	}

	@Override
	public String queryRoleMenuPermiss(String params) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		Long roleId = JSONUtil.getLong(json, "roleId");
		TpRoleMenu trm = new TpRoleMenu();
		trm.setRoleId(roleId);
		List list = baseDao.loadEqual(trm);
		return Struts2Utils.list2json(list);
	}

}
