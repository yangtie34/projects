package com.jhkj.mosdc.permiss.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.service.BaseService;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.permiss.domain.Node;
import com.jhkj.mosdc.permiss.po.TpMenu;
import com.jhkj.mosdc.permiss.po.TpUserMenuProxy;
import com.jhkj.mosdc.permiss.po.TpUserProxyMenu;
import com.jhkj.mosdc.permiss.service.ProxyPermiss;
import com.jhkj.mosdc.permiss.util.JSONUtil;
import com.jhkj.mosdc.permiss.util.SqlUtils;
import com.jhkj.mosdc.permiss.util.UserPermiss;

public class ProxyPermissServiceImpl implements ProxyPermiss {
	private BaseDao baseDao;
	private BaseService baseService;

	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@SuppressWarnings("unused")
	@Override
	public String queryMyProxy(String params) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		Long userId = UserPermiss.getUser().getCurrentUserId();

		String[] columnArray = "id, owner_user_id, xf_user_id,xfusername, startdate, enddate,xfloginname,bz"
				.split(",");
		String[] aliasArray = "id, ownerUserId, xfUserId,xfusername, startdate, enddate,xfloginname,bz"
				.split(",");

		String sql = SqlUtils.generateAliasSql(columnArray, aliasArray,
				"(select * from tp_user_menu_proxy where owner_user_id = "
						+ userId + " order by startdate)");
		String result = baseService.queryTableDataBySqlWithAlias(sql, params);

		return result;
	}

	@Override
	public String addProxy(String params) throws Exception {
		// TODO Auto-generated method stub
		TpUserMenuProxy tmp = (TpUserMenuProxy) JSONUtil.stringToBean(params,
				TpUserMenuProxy.class);
		tmp.setOwnerUserId(UserPermiss.getUser().getCurrentUserId());
		tmp.setId(baseDao.getId());
		baseDao.save(tmp);
		return SysConstants.JSON_SUCCESS_TRUE;
	}

	/**
	 * 修改委派
	 * 
	 * @throws Exception
	 */
	public String updateProxy(String params) throws Exception {
		TpUserMenuProxy tmp = (TpUserMenuProxy) JSONUtil.stringToBean(params,
				TpUserMenuProxy.class);
		baseDao.update(tmp);
		return SysConstants.JSON_SUCCESS_TRUE;
	}

	@Override
	public String deleteProxy(String params) {
		// TODO Auto-generated method stub
		Long proxyId = JSONUtil.getLong(JSONObject.fromObject(params),
				"proxyId");
		baseDao.delete(baseDao.get(TpUserMenuProxy.class, proxyId));
		
		//删除下放的菜单权限
		TpUserProxyMenu tpm = new TpUserProxyMenu();
		tpm.setXfId(proxyId);
		baseDao.deleteEqual(tpm);
		
		return SysConstants.JSON_SUCCESS_TRUE;
	}

	@Override
	public String updateProxyMenuPermiss(String params) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		Long proxyId = JSONUtil.getLong(json, "proxyId");
		String[] menuIds = JSONUtil.getString(json, "menuIds").split(",");
		// 删除以前的权限
		TpUserProxyMenu tpm = new TpUserProxyMenu();
		tpm.setXfId(proxyId);
		baseDao.deleteEqual(tpm);

		for (int i = 0; i < menuIds.length; i++) {
			Long menuId = Long.parseLong(menuIds[i]);
			tpm = new TpUserProxyMenu();
			tpm.setXfId(proxyId);
			tpm.setMenuId(menuId);
			tpm.setId(baseDao.getId());
			baseDao.save(tpm);
		}

		return SysConstants.JSON_SUCCESS_TRUE;
	}

	@Override
	public String queryUserMenus(String params) {
		// TODO Auto-generated method stub
		Node root = UserPermiss.getUser().getMenuTree();
		return Struts2Utils.bean2json(root);
	}

	@Override
	public String queryProxyMenus(String params) {
		// TODO Auto-generated method stub
		Long proxyId = JSONUtil.getLong(JSONObject.fromObject(params),
				"proxyId");
		TpUserProxyMenu tpm = new TpUserProxyMenu();
		tpm.setXfId(proxyId);
		List list = baseDao.loadEqual(tpm);
		return Struts2Utils.list2json(list);
	}

	@Override
	public String queryUsers(String params) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		String username = JSONUtil.getString(json, "username");
		json.remove("username");

		// 首先查询用户
		String[] columnArray = "id, ry_id, username, loginname,rylb_id,ghxh, bmmc"
				.split(",");
		String[] aliasArray = "id, ryId, username, loginname, rylbId, ghxh, bmmc"
				.split(",");
		
		StringBuilder tablesql = new StringBuilder("(select * from TP_USER t where 1=1 "); 
		tablesql.append(" and bmmc like '%").append(username).append("%'")
				.append(" or username like '%").append(username).append("%'")
				.append(" or loginname like '%").append(username).append("%'")
				.append(" )");
		
		String sql = SqlUtils.generateAliasSql(columnArray, aliasArray,tablesql.toString());
		String result = baseService.queryTableDataBySqlWithAlias(sql, json.toString());

		return result;
	}

}
