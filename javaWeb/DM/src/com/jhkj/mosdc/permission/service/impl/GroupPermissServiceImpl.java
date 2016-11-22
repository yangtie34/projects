package com.jhkj.mosdc.permission.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.nobject.common.js.JSONUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.service.BaseService;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.permiss.util.JSONUtil;
import com.jhkj.mosdc.permission.po.TsUserCdzy;
import com.jhkj.mosdc.permission.po.TsUserCdzyGroup;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.jhkj.mosdc.permission.service.GroupPermissService;
import com.jhkj.mosdc.permission.util.UserPermissionUtil;

public class GroupPermissServiceImpl implements GroupPermissService {
	private BaseService baseService;
	private BaseDao baseDao;

	@Override
	public String queryDevolvedGroupPermissPerson(String params) {
		// TODO Auto-generated method stub
		UserInfo user = UserPermissionUtil.getUserInfo();
		String roleIds = user.getRoleIds();
		String sql = "SELECT jzg.id,jzg.xm,count(jzg.xm) qxnum FROM TS_USER_CDZY_GROUP uc inner join tb_jzgxx jzg on uc.user_id=jzg.id and uc.xf_role_id in ("+roleIds+") group by jzg.xm,jzg.id";
		List<Map> list = baseService.queryListMapInLowerKeyBySql(sql);
		
		Map map = new HashMap();
		map.put("data", list);
		map.put("count", list.size());
		map.put("success", true);
		
		return Struts2Utils.map2json(map);
	}

	@Override
	public String updateGroupPermissForPerson(String params) {
		// TODO Auto-generated method stub
		Long zgId = getZgId();
		String roleIds = getRoleIds();
		
		JSONObject json = JSONObject.fromObject(params);
		String ids = json.getString("menuIds");
		Long userId = json.getLong("userId");// 获取下放目标用户ID
		
		this.deleteGroupPermissWithPerson("{userId : "+userId+"}");//先删除权限ID集合
		
		String[] idArray = ids.split(",");
		if(idArray.length == 0 )return SysConstants.JSON_SUCCESS_TRUE;
		for (String id : idArray) {
			TsUserCdzyGroup cdzy = new TsUserCdzyGroup();
			cdzy.setId(baseService.getId());
			cdzy.setCdzyId(Long.parseLong(id));
			cdzy.setUserId(userId);
			cdzy.setXfUserId(zgId);
			cdzy.setXfRoleId(getRoleIdByCdzy(id,zgId,roleIds));
			baseService.insert(cdzy);
		}
		return SysConstants.JSON_SUCCESS_TRUE;
	}

	@Override
	public String deleteGroupPermissWithPerson(String params) {
		// TODO Auto-generated method stub
		String roleIds = getRoleIds();

		JSONObject json = JSONObject.fromObject(params);
		Long deleteUserId = JSONUtil.getLong(json, "userId");

		String hql = "delete from TsUserCdzyGroup where xfRoleId in (" + roleIds
				+ ") and userId=" + deleteUserId;
		baseDao.executeUpdateHql(hql);

		return SysConstants.JSON_SUCCESS_TRUE;
	}

	@Override
	public String addGroupPermissForPerson(String params) {
		// TODO Auto-generated method stub
		if(UserPermissionUtil.getUserInfo().getLoginName().equals("admin")){
			return SysConstants.JSON_SUCCESS_TRUE;
		}
		Long zgId = getZgId();
		String roleIds = getRoleIds();

		JSONObject json = JSONObject.fromObject(params);
		String ids = json.getString("menuIds");
		Long userId = json.getLong("userId");// 获取下放目标用户ID

		String[] idArray = ids.split(",");
		for (String id : idArray) {
			TsUserCdzyGroup cdzy = new TsUserCdzyGroup();
			cdzy.setId(baseService.getId());
			cdzy.setCdzyId(Long.parseLong(id));
			cdzy.setUserId(userId);
			cdzy.setXfUserId(zgId);
			cdzy.setXfRoleId(getRoleIdByCdzy(id,zgId,roleIds));
			baseService.insert(cdzy);
		}
		return SysConstants.JSON_SUCCESS_TRUE;
	}
	@Override
	public String getCdIds(String params) {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		Long userId = json.getLong("userId");
		String hql = "select cdzyId from TsUserCdzyGroup t where t.userId="+userId;
		List list = baseDao.queryHql(hql);
		return Struts2Utils.list2json(list);
	}
	public Long getRoleIdByCdzy(String cdzyId,Long zgId,String roleIds) {
		String sql = "select js.jslx_id as id from ts_user u inner join  " +
				" ts_user_js uj on u.id = uj.user_id inner join ts_js js on uj.js_id = js.id " +
				" inner join ts_js_cdzy jc on jc.js_id = js.id   inner join ts_cdzy cd on jc.cdzy_id = cd.id " +
				" and u.zg_id ="+zgId+" and cd.id ="+cdzyId + " and js.jslx_id in ("+roleIds+")";
		List<Map> list = baseDao.queryListMapInLowerKeyBySql(sql);
		if(list.size()>0){
			return MapUtils.getLong(list.get(0), "id");
		}else{
			return null;
		}
	}
	
	public Long getZgId() {
		UserInfo user = UserPermissionUtil.getUserInfo();
		Long zgId = user.getZgId();
		return zgId;
	}
	public String getRoleIds(){
		UserInfo user = UserPermissionUtil.getUserInfo();
		String roleIds = user.getRoleIds();
		return roleIds;
	}
	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	
	
}
